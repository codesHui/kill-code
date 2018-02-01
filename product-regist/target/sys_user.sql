/*
SQLyog Ultimate v10.51 
MySQL - 5.6.16 : Database - imi
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`imi` /*!40100 DEFAULT CHARACTER SET utf8 */;

/*Data for the table `sys_roles` */

insert  into `sys_roles`(`id`,`code`,`description`,`name`,`status`) values (1,'sysAdmin','系统管理员','系统管理员',1);
insert  into `sys_roles`(`id`,`code`,`description`,`name`,`status`) values (2,'admin1','初级管理员','初级管理员',1);
insert  into `sys_roles`(`id`,`code`,`description`,`name`,`status`) values (3,'admin2','中级管理员','中级管理员',1);
insert  into `sys_roles`(`id`,`code`,`description`,`name`,`status`) values (4,'admin3','高级管理员','高级管理员',1);
insert  into `sys_roles`(`id`,`code`,`description`,`name`,`status`) values (5,'user','会员用户','会员用户',1);
insert  into `sys_roles`(`id`,`code`,`description`,`name`,`status`) values (6,'buser','保险经纪公司会员','保险经纪公司会员',1);
INSERT INTO   `sys_roles` (`id`, `code`, `description`, `name`, `status`) VALUES ('7', 'cirsAdmin', '保监会管理员', '保监会管理员', '1');
INSERT INTO `sys_roles` (`id`, `code`, `description`, `name`, `status`) VALUES ('8', 'bjsAdmin', '保交所管理员', '保交所管理员', '1');


/*Data for the table `sys_users` */

insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (1,'2015-06-03 11:44:38','2015-06-03 11:44:38','admin',NULL,NULL,NULL,'admin','系统管理员','admin',NULL,NULL,1,1,1,1);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (2,'2015-06-03 11:44:38','2015-06-03 11:44:38','admin1',NULL,NULL,NULL,'admin1','初级管理员','admin',NULL,NULL,1,1,1,1);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (3,'2015-06-03 11:44:38','2015-06-03 11:44:38','admin2',NULL,NULL,NULL,'admin2','中级管理员','admin',NULL,NULL,1,1,1,1);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (4,'2015-06-03 11:44:38','2015-06-03 11:44:38','admin3',NULL,NULL,NULL,'admin3','高级管理员','admin',NULL,NULL,1,1,1,1);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (5,'2015-06-03 11:44:38','2015-06-03 11:44:38','user','',NULL,'','user','世范测试','user','','',1,2,1,1);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (6,'2015-06-03 11:44:38','2015-06-03 11:44:38','user1','zhangjianguo@163.com',NULL,'','user1','上海航保协会','user1','','',1,2,1,1);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (7,'2015-06-03 11:44:38','2015-06-03 11:44:38','user2','',NULL,NULL,'user2','上海航运保险协会','user2',NULL,NULL,1,2,1,NULL);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (8,'2015-06-03 11:44:38','2015-06-03 11:44:38','user3','test@qq.com',NULL,NULL,'user3','保险经纪公司测试','user3',NULL,NULL,1,2,1,NULL);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (9,'2015-06-03 11:44:38','2015-06-03 11:44:38','user4','',NULL,'','user4','会员4','user4','','',1,2,1,1);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (10,'2015-06-03 11:44:38','2015-06-03 11:44:38','user5','',NULL,'','user5','会员5','user5','','',1,2,1,1);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (11,'2015-06-03 11:44:38','2015-06-03 11:44:38','user6','',NULL,'','user6','会员6','user6','','',1,2,1,1);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (12,'2015-06-03 11:44:38','2015-06-03 11:44:38','user7','',NULL,'','user7','会员7','user7','','',1,2,1,1);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (13,'2015-06-03 11:44:38','2015-06-03 11:44:38','user8','',NULL,'','user8','会员8','user8','','',1,2,1,1);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (14,'2015-06-03 11:44:38','2015-06-03 11:44:38','user9','',NULL,'','user9','会员9','user9','','',1,2,1,1);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (15,'2015-06-03 11:44:38','2015-06-03 11:44:38','user10','',NULL,'','user10','会员10','user11','','',1,2,1,1);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (16,'2015-06-03 11:44:38','2015-06-03 11:44:38','user11','',NULL,'','user12','会员12','user12','','',1,2,1,1);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (18,'2015-06-03 11:44:38','2015-06-03 11:44:38','wangyuan',NULL,NULL,NULL,'wangyuan','人保航保中心','wangyuan',NULL,NULL,1,2,1,NULL);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (19,'2015-06-03 11:44:38','2015-06-03 11:44:38','liudm','liudm@ccic-net.com.cn',NULL,NULL,'liudm','大地财产保险','liudm',NULL,NULL,1,2,1,NULL);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (20,'2015-06-03 11:44:38','2015-06-03 11:44:38','shangqing','liudm@ccic-net.com.cn',NULL,NULL,'shangqing','华泰航保中心','shangqing',NULL,NULL,1,2,1,NULL);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (21,'2015-06-03 11:44:38','2015-06-03 11:44:38','fulinyun',NULL,NULL,NULL,'fulinyun',NULL,'fulinyun',NULL,NULL,1,2,1,NULL);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (22,'2015-06-03 11:44:38','2015-06-03 11:44:38','zouwzhen',NULL,NULL,NULL,'zouwzhen',NULL,'zouwzhen',NULL,NULL,1,2,1,NULL);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (28,NULL,NULL,'',NULL,NULL,NULL,'',NULL,'',NULL,NULL,1,2,NULL,NULL);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (29,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,2,NULL,NULL);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (34,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,2,NULL,NULL);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (35,NULL,NULL,'test',NULL,NULL,NULL,'test',NULL,'111111',NULL,NULL,1,2,NULL,NULL);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (37,'2015-06-15 14:26:45','2015-06-15 14:26:49','wangyuan1',NULL,NULL,NULL,'人保航保中心','人保航保中心','wangyuan1',NULL,NULL,1,2,1,1);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (38,NULL,NULL,'user01','test@163.com',NULL,NULL,'user01','测试人员','user01',NULL,NULL,1,2,1,NULL);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (41,NULL,NULL,'user02','test02@163.com',NULL,NULL,'user02','test','user02',NULL,NULL,1,1,1,NULL);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (42,NULL,NULL,'user03','test@163.com',NULL,NULL,'user03','test001','user03',NULL,NULL,1,1,1,NULL);
insert  into `sys_users`(`id`,`create_time`,`modified_time`,`account`,`email`,`gender`,`imgUrl`,`name`,`nickName`,`password`,`phone`,`qq`,`status`,`type`,`creater_id`,`modifier_id`) values (44,NULL,NULL,'user04','jing.li@softvan.com.cn',NULL,NULL,'user04','testing','user04',NULL,NULL,1,1,1,NULL);

/*Data for the table `sys_user_roles` */

insert  into `sys_user_roles`(`role_id`,`user_id`) values (1,1);
insert  into `sys_user_roles`(`role_id`,`user_id`) values (2,2);
insert  into `sys_user_roles`(`role_id`,`user_id`) values (3,3);
insert  into `sys_user_roles`(`role_id`,`user_id`) values (4,4);
insert  into `sys_user_roles`(`role_id`,`user_id`) values (5,5);
insert  into `sys_user_roles`(`role_id`,`user_id`) values (5,6);
insert  into `sys_user_roles`(`role_id`,`user_id`) values (5,7);
insert  into `sys_user_roles`(`role_id`,`user_id`) values (6,8);
insert  into `sys_user_roles`(`role_id`,`user_id`) values (5,9);
insert  into `sys_user_roles`(`role_id`,`user_id`) values (5,10);
insert  into `sys_user_roles`(`role_id`,`user_id`) values (5,11);
insert  into `sys_user_roles`(`role_id`,`user_id`) values (5,12);
insert  into `sys_user_roles`(`role_id`,`user_id`) values (5,18);
insert  into `sys_user_roles`(`role_id`,`user_id`) values (5,19);
insert  into `sys_user_roles`(`role_id`,`user_id`) values (5,20);
insert  into `sys_user_roles`(`role_id`,`user_id`) values (5,21);
insert  into `sys_user_roles`(`role_id`,`user_id`) values (5,22);
insert  into `sys_user_roles`(`role_id`,`user_id`) values (5,35);
insert  into `sys_user_roles`(`role_id`,`user_id`) values (5,37);
insert  into `sys_user_roles`(`role_id`,`user_id`) values (6,38);
insert  into `sys_user_roles`(`role_id`,`user_id`) values (2,41);
insert  into `sys_user_roles`(`role_id`,`user_id`) values (3,42);
insert  into `sys_user_roles`(`role_id`,`user_id`) values (4,44);
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
