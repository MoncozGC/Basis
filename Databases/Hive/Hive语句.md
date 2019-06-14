[区分关键字与表的区别: 文档中有score course..为表的名称]

## Hive

### 数据库操作
#### 连接方式

> beeline连接hiveserver2
bin/beeline
beeline> !connect jdbc:hive2://node03.hadoop.com:10000

#### 创建数据库

> 如果不存在就创建数据库

create datavase if not exists myhive;

> 选择数据库

 use myhive;

> 创建数据库并指定hdfs存储位置 [创建在hdfs根目录下]

create database myhive2 location '/myhive2';
> 说明：hive的表存放位置模式是由hive-site.xml当中的一个属性指定的
<name>hive.metastore.warehouse.dir</name>
<value>/user/hive/warehouse</value>

#### 修改数据库

> 可以使用alter  database  命令来修改数据库的一些属性。但是数据库的元数据信息是不可更改的，包括数据库的名称以及数据库所在的位置
alter  database  myhive2  set  dbproperties('createtime'='20180611');

#### 查看数据库详细信息

> 基本信息

desc database myhive2;

> 更多详细信息

desc database extended  myhive2; 

#### 删除数据库

> 删除一个空的数据库, 如果其中有数据表,就会报错

drop database myhive2;

> 强制删除, 包含数据库下面的表一起删除

drop database myhive cascade; 

### 数据库表操作

分类	类型	描述	字面量示例

原始类型	
	BOOLEAN		        true/false	                                    TRUE
	TINYINT		        1字节的有符号整数 -128~127	            1Y
	SMALLINT	      2个字节的有符号整数，             -32768~32767	1S
	INT	                    4个字节的带符号整数	                          1
	BIGINT	             8字节带符号整数	                             1L
	FLOAT	             4字节单精度浮点数                               1.0	
	DOUBLE	            8字节双精度浮点数	                            1.0
	DEICIMAL	      任意精度的带符号小数	                         1.0
	STRING	            字符串，变长	                                  “a”,’b’
	VARCHAR	           变长字符串	                                      “a”,’b’
	CHAR	               固定长度字符串	                               “a”,’b’
	BINARY	            字节数组	                                        无法表示
	TIMESTAMP	    时间戳，纳秒精度	                     122327493795
	DATE	              日期	                                             ‘2016-03-29’
	INTERVAL 	      时间频率间隔	

复杂类型	
    ARRAY	                    有序的的同类型的集合	                                          array(1,2)
	MAP	                key-value,key必须为原始类型，value可以任意类型      	  map(‘a’,1,’b’,2)
	STRUCT	                    字段集合,类型可以不同	  struct(‘1’,1,1.0),named_stract(‘col1’,’1’,’col2’,1,’clo3’,1.0)
	UNION	        在有限取值范围内的一个值	                                            create_union(1,’a’,63)



#### 管理表(内部表)
> 创建数据库表

create table stu(id int,name string);

> 创建表并指定字段之间的分隔符, 并指定hdfs位置
>
> row format delimited fields terminated by '\t'  按照'\t' 分隔

create table if not exists stu2 (id int,name string) **row format delimited fields terminated by '\t'**  stored as textfile location '/user/hive/warehouse/myhive/stu2';

> 根据查询结果创建表

create table stu3 as select * from stu2;

> 根据已经存在的表结构创建表

create table stu4 like stu2;

> 查询表结构

desc formatted stu2;

> 查询所有表

show tables;

外部表 EXTERNAL

外部表因为是指定其他的hdfs路径的数据加载到表当中来，所以hive表会认为自己不完全独占这份数据，所以删除hive表的时候，数据仍然存放在hdfs当中，不会删掉

#### 管理表和外部表的使用场景：
每天将收集到的网站日志定期流入HDFS文本文件。在外部表（原始日志表）的基础上做大量的统计分析，用到的中间表、结果表使用内部表存储，数据通过SELECT+INSERT进入内部表。

> 关键字  external  [创建分区表类型的老师表, 其中有两个字段, 以 '\t' 为分隔符]

create **external** table teacher (t_id string,t_name string) row format delimited fields terminated by '\t';


#### 分区表 partitioned by (dirname 类型)
在大数据中，最常用的一种思想就是分治，我们可以把大的文件切割划分成一个个的小的文件，这样每次操作一个小的文件就会很容易了，同样的道理，在hive当中也是支持这种思想的，就是我们可以把大的数据，按照每天，或者每小时进行切分成一个个的小的文件，这样去操作小的文件就会容易得多了  **分文件夹**

> 创建分区表

create table score(s_id string,c_id string, s_score int) **partitioned by (month string)** row format delimited fields terminated by '\t';

> 加载数据到分区表中
>
> '/export/servers/hivedatas/score.csv' --> 本地路径  || score --> 表名 || (month='201806') -->根据什么来分区
>
> 再HDFS中的显示就是一层目录

load data local inpath '/export/servers/hivedatas/score.csv' into table score **partition(month='201806')**

> 创建一个表带多个分区

create table score2 (s_id string,c_id string, s_score int) **partitioned by (year string,month string,day string)** row format delimited fields terminated by '\t';

> 加载数据到一个多分区的表中去

load data **local** inpath '/export/servers/hivedatas/score.csv' into table score2 **partition(year='2018',month='06',day='01');**

> 查看分区

show partitions score;

> 添加分区

alter table score add partition(month='201805');

> 同时添加多个分区 [测试:不能同时删除两个以上的分区]

alter table score add partition(month='201804') partition(month = '201803');

> 删除分区

alter table score drop partition(month = '201806');


#### 分桶表
**关键字: clustered by (xxxfield) into xxxNum buckets**

将数据按照指定的字段进行分成多个桶中去，说白了就是将数据按照字段进行划分，可以将数据按照字段划分到多个文件当中去 **分文件**

> 分桶表需先开启桶表的功能以及设置reduce的个数. 注意: 只要关闭了窗口这两个设置就会被重置

> 开启hive的桶表功能 [窗口关闭在进入就会失效]

set hive.enforce.bucketing=true;

> 检查桶表功能是否开启

set hive.enforce.bucketing;

> 设置reduce的个数 [窗口关闭会恢复到初始值]

set mapreduce.job.reduces=3;
> 检查reduce个数

Set mapreduce.job.reduces;

> 创建分桶表 字段条件根据c_id分成3个reduce

create table course(c_id string, c_name string, t_id string) **clustered by (c_id) into 3 buckets** row format delimited fields terminated by '\t';

>创建普通表：


create table course_common (c_id string,c_name string,t_id string) row format delimited fields terminated by '\t';

>普通表中加载数据


load data local inpath '/export/servers/hivedatas/course.csv' into table course_common;

> 通过insert  overwrite给桶表中加载数据   根据c_id加载数据

**insert overwrite** table course select * from course_common cluster by(c_id);

```
分桶表的数据加载:
分桶表的数据加载需要有一个中间表, 数据插入中间表再由中间表插入到分桶表中.

先创建普通表，并通过insert  overwrite的方式将普通表的数据通过查询的方式加载到桶表当中去

加载的数据如上述: 
course_common为中间表
+---------------------+-----------------------+---------------------+--+
| course_common.c_id  | course_common.c_name  | course_common.t_id  |
+---------------------+-----------------------+---------------------+--+
| 01                  | 语文                    | 02                  |
|                     | 数学                    | 01                  |
| 03                  | 英语                    | 03                  |
+---------------------+-----------------------+---------------------+--+
coure为分桶表:
+--------------+----------------+--------------+--+
| course.c_id  | course.c_name  | course.t_id  |
+--------------+----------------+--------------+--+
| 03           | 英语             | 03           |
|              | 数学             | 01           |
| 01           | 语文             | 02           |
+--------------+----------------+--------------+--+
通过观察插入到分桶表的数据是倒序排序的
并且在HDFS上查看数据时(分成了三个文件,有3个reduce), 因为其中有一条c_id为空所以整个文本为空
```

#### 修改表

> 表的重命名 把表score4修改成score5

alter table score4 **rename to** score5;

> 添加列 [向score5表中添加mycol和mysco列 类型为string]

alter table score5 **add columns** (mycol string, mysco string);

> 更新列 [将score5表中的mysco列名修改为myscinew 类型修改为int]

alter table score5 **change column** mysco mysconew int;

> 删除表 [生产环境中慎用]

drop table score5;

### hive表中加载数据

#### 1.直接向分区表中插入数据 

**[不推荐这种方式, 产生的小文件太多]**

> 将score的表结构复制给score3


create table score3 like score;

> 插入数据 [不推荐insert into]  注意partition这个关键字与之前的有差别


insert into table score3 **partition**(month ='201807') values ('001','002','100');

#### 2.通过查询插入数据 
> 通过load方式加载数据  [常用]  不加local就是hdfs上的路径

load data local inpath '/export/servers/hivedatas/score.csv' overwrite into table score partition(month='201806');

> 通过查询方法加载数据

create table score4 like score;
insert overwrite table score4 partition(month = '201806') select s_id,c_id,s_score from score;

#### 3.多插入模式
常用于实际生产环境当中，将一张表拆开成两部分或者多部分

> 给score表加载数据

load data local inpath '/export/servers/hivedatas/score.csv' overwrite into table score partition(month='201806');

> 创建第一部分表：


create table score_first( s_id string,c_id  string) partitioned by (month string) row format delimited fields terminated by '\t' ;


> 创建第二部分表：


create table score_second(c_id string,s_score int) partitioned by (month string) row format delimited fields terminated by '\t';

> 分别给第一部分与第二部分表加载数据 score_first表存储score表中分区为201806列为s_id,c_id的字段  score_second表存储score表中分区为201806列为c_id,s_score的字段

from score insert **overwrite** table **score_first** partition(month='201806') select **s_id,c_id** insert **overwrite** table **score_second** partition(month = '201806')  select **c_id,s_score**;	

```
score表:
+-------------+-------------+----------------+--------------+--+
| score.s_id  | score.c_id  | score.s_score  | score.month  |
+-------------+-------------+----------------+--------------+--+
| 01          | 01          | 80             | 201806       |
| 01          | 02          | 90             | 201806       |
| 01          | 03          | 99             | 201806       |
| 02          | 01          | 70             | 201806       |
| 02          | 02          | 60             | 201806       |
| 02          | 03          | 80             | 201806       |
| 03          | 01          | 80             | 201806       |
| 03          | 02          | 80             | 201806       |
| 03          | 03          | 80             | 201806       |
| 04          | 01          | 50             | 201806       |
| 04          | 02          | 30             | 201806       |
| 04          | 03          | 20             | 201806       |
| 05          | 01          | 76             | 201806       |
| 05          | 02          | 87             | 201806       |
| 06          | 01          | 31             | 201806       |
| 06          | 03          | 34             | 201806       |
| 07          | 02          | 89             | 201806       |
| 07          | 03          | 98             | 201806       |
+-------------+-------------+----------------+--------------+--+

score_first表:
+-------------------+-------------------+--------------------+--+
| score_first.s_id  | score_first.c_id  | score_first.month  |
+-------------------+-------------------+--------------------+--+
| 01                | 01                | 201806             |
| 01                | 02                | 201806             |
| 01                | 03                | 201806             |
| 02                | 01                | 201806             |
| 02                | 02                | 201806             |
| 02                | 03                | 201806             |
| 03                | 01                | 201806             |
| 03                | 02                | 201806             |
| 03                | 03                | 201806             |
| 04                | 01                | 201806             |
| 04                | 02                | 201806             |
| 04                | 03                | 201806             |
| 05                | 01                | 201806             |
| 05                | 02                | 201806             |
| 06                | 01                | 201806             |
| 06                | 03                | 201806             |
| 07                | 02                | 201806             |
| 07                | 03                | 201806             |
+-------------------+-------------------+--------------------+--+

score_second表:
+--------------------+-----------------------+---------------------+--+
| score_second.c_id  | score_second.s_score  | score_second.month  |
+--------------------+-----------------------+---------------------+--+
| 01                 | 80                    | 201806              |
| 02                 | 90                    | 201806              |
| 03                 | 99                    | 201806              |
| 01                 | 70                    | 201806              |
| 02                 | 60                    | 201806              |
| 03                 | 80                    | 201806              |
| 01                 | 80                    | 201806              |
| 02                 | 80                    | 201806              |
| 03                 | 80                    | 201806              |
| 01                 | 50                    | 201806              |
| 02                 | 30                    | 201806              |
| 03                 | 20                    | 201806              |
| 01                 | 76                    | 201806              |
| 02                 | 87                    | 201806              |
| 01                 | 31                    | 201806              |
| 03                 | 34                    | 201806              |
| 02                 | 89                    | 201806              |
| 03                 | 98                    | 201806              |
+--------------------+-----------------------+---------------------+--+
```

- 查询语句中创建表并加载数据（as select）

> 将查询的结果保存到一张表当中去

create table score5 as select * from score;

- 创建表时通过location指定加载数据路径 指定路径后数据在Hive外

> 创建表, 并指定在hdfs上的位置 [相当于有一个指针指向了这个目录 hdfs虽然还没有这个目录也没关系, 我们先只创建了元数据]


create external table score6 (s_id string,c_id string,s_score int) row format delimited fields terminated by '\t' location '/myscore6';

> 上传数据到hdfs上, 查询数据


hdfs dfs -mkdir -p /myscore6
hdfs dfs -put score.csv /myscore6
select * from score6;

- export导出与import 导入 hive表数据（内部表操作）

> 复制teacher的表结构给teacher2并创建这个表


create table teacher2 like teacher;

> 导出teacher表的数据 数据目录在hdfs上


export table teacher to  '/export/teacher';

> 导入teacher表的数据目录也是在hdfs上


import table teacher2 from '/export/teacher';


### hive表中的数据导出

- insert导出
> 将查询的结果导出到本地
>
> 导出时候指定的路径为linux服务器的本地路径！
> 其导出的本质就是启动一个MapReduce任务去执行！！
> hive导出这里指定的路径一定不能给“/”，因为hive导出会将你指定的路径目录先删除再创建导出数据，如果你给的是"/"，那么整个linux系统都没了！！！



insert overwrite local directory '/export/servers/exporthive' select * from score;

> 将查询的结果格式化导出到本地


insert overwrite local directory '/export/servers/exporthive' row format delimited fields terminated by '\t' collection items terminated by '#' select * from student;

> 将查询的结果导出到HDFS上(没有local)

insert overwrite directory '/export/servers/exporthive' row format delimited fields terminated by '\t' collection items terminated by  '#' select * from score;


> Hadoop命令导出到本地 
前面是HDFS上的路径后面是本地路径

dfs -get /export/servers/exporthive/000000_0 /export/servers/exporthive/local.txt;

> export导出到HDFS上

export table score to '/export/exporthive/score';

> hive shell 命令导出  在shell客户端上执行, 不要在hive中执行

bin/hive -e "select * from  myhive.score;" > /export/servers/exporthive/score.txt