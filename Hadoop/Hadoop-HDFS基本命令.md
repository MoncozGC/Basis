# Hadoop基本命令

[Hadoop Shell命令](http://hadoop.apache.org/docs/r1.0.4/cn/hdfs_shell.html)

* 查看文件
  * hadoop  fs  -ls  /   `查看根目录文件`
  * hadoop  fs  -du -h /  `查看目录的所占资源大小`
  * hadoop  fs  -du -h -s /   `查看当前路径总的容量大小`
* 删除文件
  * hadoop  fs  -rm -r  /user/hdfs/xxx  `删除xxx文件`
  * hadoop  fs -expunge  `清空当前用户回收站的文件 [/user/$用户/.Trash]`
* 拉取/上传文件
  * hadoop  fs  -get  \<src>  \<localdst>  `复制文件到本地文件系统中`
  * hadoop  fs  -put  \<localdst>  \<src>  `从本地文件系统中复制单个或多个源路径到目标文件系统。也支持从标准输入中读取输入写入目标文件系统。`
* 修改权限
  * hadoop  fs  -chmod [-R]  `改变文件权限,命令的使用者必须是所有者或者超级用户`
  * hadoop  fs  -chown [-R] `改变文件的拥有者,命令的使用者必须是超级用户`
  * hadoop  fs  -chgrp  [-R]  `改变文件所属的组,命令的使用者必须是文件的所有者或者超级用户`