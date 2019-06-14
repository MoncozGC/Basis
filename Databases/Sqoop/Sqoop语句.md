### Sqoop
>主要用于在Hadoop(Hive)与传统的数据库(mysql、postgresql...)间进行数据的传递，可以将一个关系型数据库（例如 ： MySQL ,Oracle ,Postgres等）中的数据导进到Hadoop的HDFS中，也可以将HDFS的数据导进到关系型数据库中。

sqoop是apache旗下一款**“Hadoop和关系数据库服务器之间传送数据”**的工具。

**导入数据**：MySQL，Oracle导入数据到Hadoop的HDFS、HIVE、HBASE等数据存储系统；

**导出数据：**从Hadoop的文件系统中导出数据到关系数据库mysql等

#### Sqoop初体验

> 命令行查看帮助

bin/sqoop list-databases --help

> 列出windows主机所有的数据库  注意ip 用户 密码是否一致
>
> \:   换行继续输入

bin/sqoop list-databases \

--connect jdbc:mysql://**[ip]**:3306/ \

--username **root** --password **root**

> 查看某一个数据库下面的所有数据表

bin/sqoop list-tables \

--connect jdbc:mysql://[ip]:3306/**[数据库名]** \

--username root --password root

##### 可能出现的问题

```properties
# 可能出现的问题, linux无法查看本地数据库
# 报错信息: 
		ERROR manager.CatalogQueryManager: Failed to list databases
		java.sql.SQLException: null,  message from server: "Host 'xxxx' is not allowed to connect to this MySQL server"
		ERROR sqoop.Sqoop: Got exception running Sqoop: java.lang.RuntimeException: java.sql.SQLException: null,  message from server: "Host 'xxxxx' is not allowed to connect to this MySQL server"
		
# 大致意思就是无法连接主机mysql服务

# 解决: 
找到电脑上Mysql的黑窗口--> MySQL 5.5 Command Line Client

# 登入mysql后，更改 mysql 数据库里的 user 表里的 host 项，将localhost改称%
mysql>update user set host = '%' where user = 'root';
# 查看是否修改成功
mysql>select host, user from user;

# 修改授权 eg: 如果你想允许用户myuser从ip为192.168.xxx.xxx的主机连接到mysql服务器，并使用xxx作为密码
# ip最好从cmd输入ifconfig中查看  密码也最好是mysql数据库的密码
mysql>GRANT ALL PRIVILEGES ON *.* TO 'myuser'@'192.168.xxx.xxx'IDENTIFIED BY 'xxx' WITH GRANT OPTION;
# 重新加载权限
mysql>FLUSH PRIVILEGES

重新启动本地Mysql服务

# 链接: https://www.cnblogs.com/wuotto/p/9682536.html

```

#### Sqoop数据导入
> 将Mysql数据库中的user库的enp表导入HDFS中  --m 1 代表多少个MapTask

bin/sqoop **import** \
--connect jdbc:mysql://192.168.12.78:3306/userdb \
--password root --username root \

--table emp --m 1

> 查看导入的数据目录 web界面也可以查看

hdfs dfs -ls /user/root/emp

> 导入到HDFS指定目录
>
> 使用参数 --target-dir来指定导出目的地，
> 使用参数—delete-target-dir来判断导出目录是否存在，如果存在就删掉

bin/sqoop import \
--connect jdbc:mysql://192.168.12.78:3306/userdb \
--username root --password root \
**--delete-target-dir** --table emp \
**--target-dir** /sqoop/emp --m 1

> 查看导入的数据 
> 
> 文本显示的会用逗号(,)分隔表中的数据和字段

hdfs dfs -text /sqoop/emp/part-m-00000

> 导入HDFS指定目录并指定字段之间的分隔符 
> 
> '\t'分隔

bin/sqoop import \
--connect jdbc:mysql://192.168.12.78:3306/userdb \
--username root --password root \
--delete-target-dir --table emp \
**--target-dir /sqoop/emp2 --m 1 \**
*--fields-terminated-by '\t'**

##### 导入关系表到Hive

前提准备

> 1. 将我们mysql表当中的数据直接导入到hive表中的话，我们需要将hive的一个叫做hive-exec-1.1.0-cdh5.14.0.jar的jar包拷贝到sqoop的lib目录下

cp /export/servers/hive-1.1.0-cdh5.14.0/lib/hive-exec-1.1.0-cdh5.14.0.jar /export/servers/sqoop-1.4.6-cdh5.14.0/lib/

> 2. hive准备数据库与表  确保hadoop集群启动 操作hive

```properties
# 开启hive2服务
cd /export/servers/hive-1.1.0-cdh5.14.0
nohup bin/hive --service hiveserver2 2>&1 &
# 进入hive客户端, 进行连接
bin/beeline
beeline> !connect jdbc:hive2://node03:10000
Enter username for jdbc:hive2://node03:10000: root
Enter password for jdbc:hive2://node03:10000: 123456
# 创建数据库, 选择数据库操作 sqooptohive
hive (default)> create database sqooptohive;
hive (default)> use sqooptohive;
# 创建表 emp_hive
hive (sqooptohive)> create external table emp_hive(id int,name string,deg string,salary int ,dept string) row format delimited fields terminated by '\001';

```

----------
> 开始导入

bin/sqoop import \
--connect jdbc:mysql://172.16.59.28:3306/userdb \
--username root --password root \
--table emp **--fields-terminated-by '\001' \
--hive-import --hive-table sqooptohive.emp_hive \
--hive-overwrite** \
--delete-target-dir --m 1

> 在Hive中查看表数据

select * from emp_hive;

> 导入关系表到hive并自动创建hive表

bin/sqoop import \
--connect jdbc:mysql://192.168.12.78:3306/userdb \
--username root --password 0712 \
-m 1 **--table emp_conn --hive-import** \ 
**--hive-database sqooptohive** \ 
--fields-terminated-by '\001' \
 --hive-overwrite --delete-target-dir


##### 导入表数据子集
> 语法: -where<条件>
>
> 它执行在各自的数据库服务器相应的SQL查询，并将结果存储在HDFS的目标目录。
>
> 按照条件进行查找，通过—where参数来查找表emp_add当中city字段的值为sec-bad的所有数据导入到hdfs上面去

bin/sqoop import \
--connect jdbc:mysql://192.168.12.78:3306/userdb \
--username root --password root \
-m 1 --table emp_add --fields-terminated-by '\t' \
** --target-dir /sqoop/emp_add --delete-target-dir \
--where "city = 'sec-bad'"**

##### 导入查找的sql语句到HDFS
> 通过 -query参数来指定sql语句

bin/sqoop **import** \ 
--connect jdbc:mysql://192.168.12.78:3306/userdb \
--username root --password root \
-m 1 --fields-terminated-by '\t' \ 
**--target-dir /sqoop/emp_conn \ 
--delete-target-dir \ 
--query 'select phno from emp_conn where 1=1 and  $CONDITIONS'**

> 查看HDFS数据内容

hdfs dfs -text /sqoop/emp_conn/part*


##### 增量导入

```
很多时候都是只需要导入增量数据即可，并不需要将表中的数据全部导入到hive或者hdfs当中去，肯定会出现重复的数据的状况
所以我们一般都是选用一些字段进行增量的导入
增量导入是仅导入新添加的表中的行的技术

下面的语法用于Sqoop导入命令增量选项。
--incremental <mode>
--check-column <column name>
--last value <last check column value>
```

> 第一种增量导入:使用上面的选项来实现
> 注意: 增量导入的时候，一定不能加参数--delete-target-dir否则会报错
>
> 需求: 导入emp表当中id大于1202的所有数据

bin/sqoop import \
--connect jdbc:mysql://192.168.12.78:3306/userdb \
--username root \
--password root\
**--table emp \
-m 1 \
--incremental append \
--check-column id \
--last-value 1202  \
--target-dir /sqoop/increment**

> 查看数据内容

 hdfs dfs -text /sqoop/increment/part*

> 第二种增量导入:通过--where条件来实现（推荐）

bin/sqoop **import** \
--connect jdbc:mysql:// 192.168.12.78:3306/userdb \
--username root \
--password root\
--table emp \
--m 1 \
**--incremental append  \
--where "create_time > '2018-06-17 00:00:00' and create_time < '2019-06-17 23:59:59' and is_delete='1'" \
--target-dir /sqoop/increment2 \
--check-column id**

#### HDFS导出到Mysql
> 前期准备
> 本地需要有mysql表
> 
>执行导出命令

bin/sqoop **export** \
--connect jdbc:mysql://192.168.12.78:3306/userdb \
--username root --password root\
**--table emp_out \ **
--export-dir /sqoop/emp \
--input-fields-terminated-by "\t"


#### 常用命令列举

| **序号** | **命令**          | **类**              | **说明**                                                     |
| -------- | ----------------- | ------------------- | ------------------------------------------------------------ |
| 1        | import            | ImportTool          | 将数据导入到集群                                             |
| 2        | export            | ExportTool          | 将集群数据导出                                               |
| 3        | codegen           | CodeGenTool         | 获取数据库中某张表数据生成Java并打包Jar                      |
| 4        | create-hive-table | CreateHiveTableTool | 创建Hive表                                                   |
| 5        | eval              | EvalSqlTool         | 查看SQL执行结果                                              |
| 6        | import-all-tables | ImportAllTablesTool | 导入某个数据库下所有表到HDFS中                               |
| 7        | job               | JobTool             | 用来生成一个sqoop的任务，生成后，该任务并不执行，除非使用命令执行该任务。 |
| 8        | list-databases    | ListDatabasesTool   | 列出所有数据库名                                             |
| 9        | list-tables       | ListTablesTool      | 列出某个数据库下所有表                                       |
| 10       | merge             | MergeTool           | 将HDFS中不同目录下面的数据合在一起，并存放在指定的目录中     |
| 11       | metastore         | MetastoreTool       | 记录sqoop job的元数据信息，如果不启动metastore实例，则默认的元数据存储目录为：~/.sqoop，如果要更改存储目录，可以在配置文件sqoop-site.xml中进行更改。 |
| 12       | help              | HelpTool            | 打印sqoop帮助信息                                            |
| 13       | version           | VersionTool         | 打印sqoop版本信息                                            |

其他命令&参数看word文档