/*
SQLyog Ultimate v8.32 
MySQL - 5.6.22-log : Database - userdb
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`userdb` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `userdb`;

/*Table structure for table `emp` */

DROP TABLE IF EXISTS `emp`;

CREATE TABLE `emp` (
  `id` int(11) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `deg` varchar(100) DEFAULT NULL,
  `salary` int(11) DEFAULT NULL,
  `dept` varchar(10) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp ,
  `is_delete` bigint(20) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `emp` */

insert  into `emp`(`id`,`name`,`deg`,`salary`,`dept`,`create_time`,`update_time`,`is_delete`) values (1201,'gopal','manager',50000,'TP','2018-06-17 18:54:32','2018-06-17 18:54:32',1),(1202,'manisha','Proof reader',50000,'TPP','2018-06-15 18:54:32','2018-09-07 10:48:38',0),(1203,'khalillskjds','php dev',30000,'AC','2018-06-17 18:54:32','2018-08-17 18:29:05',1),(1204,'prasanth','php dev',30000,'AC','2018-06-17 18:54:32','2018-06-17 21:05:52',0),(1205,'kranthixxx','admin',20000,'TP','2018-06-17 18:54:32','2018-09-23 10:20:33',1);

/*Table structure for table `emp_add` */

DROP TABLE IF EXISTS `emp_add`;

CREATE TABLE `emp_add` (
  `id` int(11) DEFAULT NULL,
  `hno` varchar(100) DEFAULT NULL,
  `street` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp ,
  `is_delete` bigint(20) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `emp_add` */

insert  into `emp_add`(`id`,`hno`,`street`,`city`,`create_time`,`update_time`,`is_delete`) values (1201,'288A','vgiri','jublee','2018-06-17 18:54:34','2018-06-17 18:54:34',1),(1202,'108I','aoc','sec-bad','2018-06-17 18:54:34','2018-06-17 18:54:34',1),(1203,'144Z','pgutta','hyd','2018-06-17 18:54:34','2018-06-17 18:54:34',1),(1204,'78B','old city','sec-bad','2018-06-17 18:54:34','2018-06-17 18:54:34',1),(1205,'720X','hitec','sec-bad','2018-06-17 18:54:34','2018-06-17 18:54:34',1);

/*Table structure for table `emp_conn` */

DROP TABLE IF EXISTS `emp_conn`;

CREATE TABLE `emp_conn` (
  `id` int(100) DEFAULT NULL,
  `phno` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp ,
  `is_delete` bigint(20) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `emp_conn` */

insert  into `emp_conn`(`id`,`phno`,`email`,`create_time`,`update_time`,`is_delete`) values (1201,'2356742','gopal@tp.com','2018-06-17 18:54:36','2018-06-17 18:54:36',1),(1202,'1661663','manisha@tp.com','2018-06-17 18:54:36','2018-06-17 18:54:36',1),(1203,'8887776','khalil@ac.com','2018-06-17 18:54:36','2018-06-17 18:54:36',1),(1204,'9988774','prasanth@ac.com','2018-06-17 18:54:36','2018-06-17 18:54:36',1),(1205,'1231231','kranthi@tp.com','2018-06-17 18:54:36','2018-06-17 18:54:36',1);

/*Table structure for table `emp_out` */

DROP TABLE IF EXISTS `emp_out`;

CREATE TABLE `emp_out` (
  `id` int(11) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `deg` varchar(100) DEFAULT NULL,
  `salary` int(11) DEFAULT NULL,
  `dept` varchar(10) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp ,
  `is_delete` bigint(20) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `emp_out` */

insert  into `emp_out`(`id`,`name`,`deg`,`salary`,`dept`,`create_time`,`update_time`,`is_delete`) values (1205,'kranthixxx','admin',20000,'TP','2018-06-17 18:54:32','2018-09-23 10:20:33',1),(1201,'gopal','manager',50000,'TP','2018-06-17 18:54:32','2018-06-17 18:54:32',1),(1202,'manisha','Proof reader',50000,'TPP','2018-06-15 18:54:32','2018-09-07 10:48:38',0),(1203,'khalillskjds','php dev',30000,'AC','2018-06-17 18:54:32','2018-08-17 18:29:05',1),(1204,'prasanth','php dev',30000,'AC','2018-06-17 18:54:32','2018-06-17 21:05:52',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
