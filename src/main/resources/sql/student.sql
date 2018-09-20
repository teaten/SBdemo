/*
SQLyog Ultimate v12.3.1 (64 bit)
MySQL - 5.7.21-log 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `student` (
	`id` tinyint (4),
	`name` varchar (30),
	`sex` char (3),
	`city` varchar (60),
	`age` tinyint (4)
); 
insert into `student` (`id`, `name`, `sex`, `city`, `age`) values('1','小明','男','readA','10');
insert into `student` (`id`, `name`, `sex`, `city`, `age`) values('2','小花','女','readA','17');
insert into `student` (`id`, `name`, `sex`, `city`, `age`) values('3','小明','女','readA','11');
