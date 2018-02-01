/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50523
Source Host           : localhost:3306
Source Database       : imi

Target Server Type    : MYSQL
Target Server Version : 50523
File Encoding         : 65001

Date: 2015-06-18 10:10:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for biz_complaints
-- ----------------------------
DROP TABLE IF EXISTS `biz_complaints`;
CREATE TABLE `biz_complaints` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `result` int(11) NOT NULL,
  `resultDate` datetime DEFAULT NULL,
  `resultReason` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `productNO` varchar(255) DEFAULT NULL,
  `resultMan_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_267upurio6jyd9p3mlbhwdxhg` (`productNO`),
  KEY `FK_oooiamrsgxmnmmgk11o2p1urd` (`resultMan_id`),
  CONSTRAINT `FK_oooiamrsgxmnmmgk11o2p1urd` FOREIGN KEY (`resultMan_id`) REFERENCES `sys_users` (`id`),
  CONSTRAINT `FK_267upurio6jyd9p3mlbhwdxhg` FOREIGN KEY (`productNO`) REFERENCES `biz_product` (`productNO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for biz_member_user
-- ----------------------------
DROP TABLE IF EXISTS `biz_member_user`;
CREATE TABLE `biz_member_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `companyName` varchar(255) DEFAULT NULL,
  `companyShortName` varchar(255) DEFAULT NULL,
  `companyType` int(11) DEFAULT NULL,
  `companyWebsit` varchar(255) DEFAULT NULL,
  `contactPerson1Email` varchar(255) DEFAULT NULL,
  `contactPerson1Name` varchar(255) DEFAULT NULL,
  `contactPerson1Phone` varchar(255) DEFAULT NULL,
  `contactPerson2Email` varchar(255) DEFAULT NULL,
  `contactPerson2Name` varchar(255) DEFAULT NULL,
  `contactPerson2Phone` varchar(255) DEFAULT NULL,
  `introduction` varchar(255) DEFAULT NULL,
  `legalPerson` varchar(255) DEFAULT NULL,
  `operType` int(11) DEFAULT NULL,
  `organizationCode` varchar(255) DEFAULT NULL,
  `registeredCode` varchar(255) DEFAULT NULL,
  `registeredDate` varchar(255) DEFAULT NULL,
  `registeredPerson` varchar(255) DEFAULT NULL,
  `registrationRights` varchar(255) DEFAULT NULL,
  `specialMail` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `swiftNum` int(11) DEFAULT NULL,
  `creater_id` bigint(20) DEFAULT NULL,
  `modifier_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_fyebojf7d3l8sbtheoyr3fw4a` (`registeredCode`),
  KEY `FK_2e8rsxy1ct02osn5duq2xdtcs` (`creater_id`),
  KEY `FK_ldse6x0xcx1y0ys8x6jpgdcij` (`modifier_id`),
  KEY `FK_582mrai20h4c7unaryupyjkds` (`user_id`),
  CONSTRAINT `FK_582mrai20h4c7unaryupyjkds` FOREIGN KEY (`user_id`) REFERENCES `sys_users` (`id`),
  CONSTRAINT `FK_2e8rsxy1ct02osn5duq2xdtcs` FOREIGN KEY (`creater_id`) REFERENCES `sys_users` (`id`),
  CONSTRAINT `FK_ldse6x0xcx1y0ys8x6jpgdcij` FOREIGN KEY (`modifier_id`) REFERENCES `sys_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for biz_news
-- ----------------------------
DROP TABLE IF EXISTS `biz_news`;
CREATE TABLE `biz_news` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `click_count` int(11) DEFAULT NULL,
  `content` longtext,
  `description` varchar(255) DEFAULT NULL,
  `editors` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `subtitle` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `creater_id` bigint(20) DEFAULT NULL,
  `modifier_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_hej31g82x2qrusn2w8tegellj` (`creater_id`),
  KEY `FK_iehvybwf3sxlm9p7yigtky8v4` (`modifier_id`),
  CONSTRAINT `FK_iehvybwf3sxlm9p7yigtky8v4` FOREIGN KEY (`modifier_id`) REFERENCES `sys_users` (`id`),
  CONSTRAINT `FK_hej31g82x2qrusn2w8tegellj` FOREIGN KEY (`creater_id`) REFERENCES `sys_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for biz_product
-- ----------------------------
DROP TABLE IF EXISTS `biz_product`;
CREATE TABLE `biz_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `accreditationCompany` varchar(255) DEFAULT NULL,
  `chineseName` varchar(255) DEFAULT NULL,
  `contactPersonEmail` varchar(255) DEFAULT NULL,
  `contactPersonName` varchar(255) DEFAULT NULL,
  `contactPersonPhone` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  `offreason` varchar(255) DEFAULT NULL,
  `orderNo` int(11) DEFAULT NULL,
  `productLanguage` varchar(255) DEFAULT NULL,
  `productNO` varchar(255) DEFAULT NULL,
  `productName` varchar(255) DEFAULT NULL,
  `productType` int(11) DEFAULT NULL,
  `protectionType` int(11) DEFAULT NULL,
  `registPerson` varchar(255) DEFAULT NULL,
  `registTime` datetime DEFAULT NULL,
  `riskName` varchar(255) DEFAULT NULL,
  `riskType` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `creater_id` bigint(20) DEFAULT NULL,
  `modifier_id` bigint(20) DEFAULT NULL,
  `registeredCode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_eh4ebp6rd25wjvkck06v1hmj4` (`productNO`),
  KEY `FK_gwcw7x3cu8qbmrwsn326py8rx` (`creater_id`),
  KEY `FK_43hqqtehfh3m6nc7ejmbwkq5` (`modifier_id`),
  KEY `FK_1xmln41jbn083m9wkjymhwsnw` (`registeredCode`),
  CONSTRAINT `FK_1xmln41jbn083m9wkjymhwsnw` FOREIGN KEY (`registeredCode`) REFERENCES `biz_member_user` (`registeredCode`),
  CONSTRAINT `FK_43hqqtehfh3m6nc7ejmbwkq5` FOREIGN KEY (`modifier_id`) REFERENCES `sys_users` (`id`),
  CONSTRAINT `FK_gwcw7x3cu8qbmrwsn326py8rx` FOREIGN KEY (`creater_id`) REFERENCES `sys_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for biz_product_file
-- ----------------------------
DROP TABLE IF EXISTS `biz_product_file`;
CREATE TABLE `biz_product_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `productNO` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `creater_id` bigint(20) DEFAULT NULL,
  `modifier_id` bigint(20) DEFAULT NULL,
  `attachment` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_bic343mdl94nutchf1igy1ygo` (`creater_id`),
  KEY `FK_gjqly17i1yoe1o7yfw4mhi5i6` (`modifier_id`),
  KEY `FK_mgwbvlnotxwqik97cc8jo63qm` (`attachment`),
  CONSTRAINT `FK_mgwbvlnotxwqik97cc8jo63qm` FOREIGN KEY (`attachment`) REFERENCES `sys_attachments` (`id`),
  CONSTRAINT `FK_bic343mdl94nutchf1igy1ygo` FOREIGN KEY (`creater_id`) REFERENCES `sys_users` (`id`),
  CONSTRAINT `FK_gjqly17i1yoe1o7yfw4mhi5i6` FOREIGN KEY (`modifier_id`) REFERENCES `sys_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for biz_product_history
-- ----------------------------
DROP TABLE IF EXISTS `biz_product_history`;
CREATE TABLE `biz_product_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `actionCode` varchar(255) DEFAULT NULL,
  `actionName` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `fkId` bigint(20) DEFAULT NULL,
  `operPerson` varchar(255) DEFAULT NULL,
  `productNO` varchar(255) DEFAULT NULL,
  `roleCode` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `creater_id` bigint(20) DEFAULT NULL,
  `modifier_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_injewx7wa9pbusdmqxdmqtob8` (`creater_id`),
  KEY `FK_cieu3iuauu4cf92hays82klok` (`modifier_id`),
  CONSTRAINT `FK_cieu3iuauu4cf92hays82klok` FOREIGN KEY (`modifier_id`) REFERENCES `sys_users` (`id`),
  CONSTRAINT `FK_injewx7wa9pbusdmqxdmqtob8` FOREIGN KEY (`creater_id`) REFERENCES `sys_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for biz_regist_rule
-- ----------------------------
DROP TABLE IF EXISTS `biz_regist_rule`;
CREATE TABLE `biz_regist_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `serialNumber` int(11) NOT NULL,
  `startTime` datetime DEFAULT NULL,
  `year` int(11) NOT NULL,
  `creater_id` bigint(20) DEFAULT NULL,
  `modifier_id` bigint(20) DEFAULT NULL,
  `registeredCode` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ggm5opmiedslpgec8oj4trnkc` (`creater_id`),
  KEY `FK_pdqwi1ct74gecmkyg9a1td22t` (`modifier_id`),
  KEY `FK_t4ca82xu6o6uo0s7eggdp0fkn` (`registeredCode`),
  CONSTRAINT `FK_t4ca82xu6o6uo0s7eggdp0fkn` FOREIGN KEY (`registeredCode`) REFERENCES `biz_member_user` (`id`),
  CONSTRAINT `FK_ggm5opmiedslpgec8oj4trnkc` FOREIGN KEY (`creater_id`) REFERENCES `sys_users` (`id`),
  CONSTRAINT `FK_pdqwi1ct74gecmkyg9a1td22t` FOREIGN KEY (`modifier_id`) REFERENCES `sys_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for biz_reviews
-- ----------------------------
DROP TABLE IF EXISTS `biz_reviews`;
CREATE TABLE `biz_reviews` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `flowCheck1` int(11) NOT NULL,
  `flowCheck2` int(11) NOT NULL,
  `flowCheck3` int(11) NOT NULL,
  `flowDate1` datetime DEFAULT NULL,
  `flowDate2` datetime DEFAULT NULL,
  `flowDate3` datetime DEFAULT NULL,
  `flowOperate1` varchar(255) DEFAULT NULL,
  `flowOperate2` varchar(255) DEFAULT NULL,
  `flowOperate3` varchar(255) DEFAULT NULL,
  `flowReason1` varchar(255) DEFAULT NULL,
  `flowReason2` varchar(255) DEFAULT NULL,
  `flowReason3` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `flowMan1_id` bigint(20) DEFAULT NULL,
  `flowMan2_id` bigint(20) DEFAULT NULL,
  `flowMan3_id` bigint(20) DEFAULT NULL,
  `productNO` varchar(255) DEFAULT NULL,
  `role_code` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_noainb6dbp9bv1ixxotc2u7b8` (`flowMan1_id`),
  KEY `FK_bjdufhyby0tokqpbowpsle0wh` (`flowMan2_id`),
  KEY `FK_8hnlu2kjiyymviaftfsprwqn7` (`flowMan3_id`),
  KEY `FK_3nmraaj1wfu1o7i9koir4chgm` (`productNO`),
  KEY `FK_28mjftv1x38m4hnxm72912y3d` (`role_code`),
  CONSTRAINT `FK_28mjftv1x38m4hnxm72912y3d` FOREIGN KEY (`role_code`) REFERENCES `sys_roles` (`code`),
  CONSTRAINT `FK_3nmraaj1wfu1o7i9koir4chgm` FOREIGN KEY (`productNO`) REFERENCES `biz_product` (`productNO`),
  CONSTRAINT `FK_8hnlu2kjiyymviaftfsprwqn7` FOREIGN KEY (`flowMan3_id`) REFERENCES `sys_users` (`id`),
  CONSTRAINT `FK_bjdufhyby0tokqpbowpsle0wh` FOREIGN KEY (`flowMan2_id`) REFERENCES `sys_users` (`id`),
  CONSTRAINT `FK_noainb6dbp9bv1ixxotc2u7b8` FOREIGN KEY (`flowMan1_id`) REFERENCES `sys_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for biz_sensitive_word
-- ----------------------------
DROP TABLE IF EXISTS `biz_sensitive_word`;
CREATE TABLE `biz_sensitive_word` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for biz_verifys
-- ----------------------------
DROP TABLE IF EXISTS `biz_verifys`;
CREATE TABLE `biz_verifys` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `isRecon` tinyint(1) DEFAULT NULL,
  `reconDate` datetime DEFAULT NULL,
  `reconReason` varchar(255) DEFAULT NULL,
  `reconResult` int(11) NOT NULL,
  `reconResultReason` varchar(255) DEFAULT NULL,
  `result` int(11) NOT NULL,
  `resultDate` datetime DEFAULT NULL,
  `resultReason` varchar(255) DEFAULT NULL,
  `reviewNum` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `verifyDate` datetime DEFAULT NULL,
  `verifyReason` varchar(255) DEFAULT NULL,
  `creater_id` bigint(20) DEFAULT NULL,
  `modifier_id` bigint(20) DEFAULT NULL,
  `productNO` varchar(255) DEFAULT NULL,
  `reconMan_id` bigint(20) DEFAULT NULL,
  `resultMan_id` bigint(20) DEFAULT NULL,
  `verifyMan_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_lor7y1xhbijxv7o735obcfchd` (`creater_id`),
  KEY `FK_1xsqr7i02wmrecli07gevyy5g` (`modifier_id`),
  KEY `FK_6qkymduheculsuh43bem2y9vx` (`productNO`),
  KEY `FK_cv2gdax9nutt3a0nl16cw63f2` (`reconMan_id`),
  KEY `FK_7bhjn06m1alt9d4y7uh6aycln` (`resultMan_id`),
  KEY `FK_57y53ebjgf6old5fji8rrll85` (`verifyMan_id`),
  CONSTRAINT `FK_57y53ebjgf6old5fji8rrll85` FOREIGN KEY (`verifyMan_id`) REFERENCES `sys_users` (`id`),
  CONSTRAINT `FK_1xsqr7i02wmrecli07gevyy5g` FOREIGN KEY (`modifier_id`) REFERENCES `sys_users` (`id`),
  CONSTRAINT `FK_6qkymduheculsuh43bem2y9vx` FOREIGN KEY (`productNO`) REFERENCES `biz_product` (`productNO`),
  CONSTRAINT `FK_7bhjn06m1alt9d4y7uh6aycln` FOREIGN KEY (`resultMan_id`) REFERENCES `sys_users` (`id`),
  CONSTRAINT `FK_cv2gdax9nutt3a0nl16cw63f2` FOREIGN KEY (`reconMan_id`) REFERENCES `sys_users` (`id`),
  CONSTRAINT `FK_lor7y1xhbijxv7o735obcfchd` FOREIGN KEY (`creater_id`) REFERENCES `sys_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for jbpm4_deployment
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_deployment`;
CREATE TABLE `jbpm4_deployment` (
  `DBID_` bigint(20) NOT NULL,
  `NAME_` longtext,
  `TIMESTAMP_` bigint(20) DEFAULT NULL,
  `STATE_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for jbpm4_deployprop
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_deployprop`;
CREATE TABLE `jbpm4_deployprop` (
  `DBID_` bigint(20) NOT NULL,
  `DEPLOYMENT_` bigint(20) DEFAULT NULL,
  `OBJNAME_` varchar(255) DEFAULT NULL,
  `KEY_` varchar(255) DEFAULT NULL,
  `STRINGVAL_` varchar(255) DEFAULT NULL,
  `LONGVAL_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_DEPLPROP_DEPL` (`DEPLOYMENT_`),
  KEY `FK_DEPLPROP_DEPL` (`DEPLOYMENT_`),
  CONSTRAINT `FK_DEPLPROP_DEPL` FOREIGN KEY (`DEPLOYMENT_`) REFERENCES `jbpm4_deployment` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for jbpm4_execution
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_execution`;
CREATE TABLE `jbpm4_execution` (
  `DBID_` bigint(20) NOT NULL,
  `CLASS_` varchar(255) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `ACTIVITYNAME_` varchar(255) DEFAULT NULL,
  `PROCDEFID_` varchar(255) DEFAULT NULL,
  `HASVARS_` tinyint(1) DEFAULT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `KEY_` varchar(255) DEFAULT NULL,
  `ID_` varchar(255) DEFAULT NULL,
  `STATE_` varchar(255) DEFAULT NULL,
  `SUSPHISTSTATE_` varchar(255) DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `HISACTINST_` bigint(20) DEFAULT NULL,
  `PARENT_` bigint(20) DEFAULT NULL,
  `INSTANCE_` bigint(20) DEFAULT NULL,
  `SUPEREXEC_` bigint(20) DEFAULT NULL,
  `SUBPROCINST_` bigint(20) DEFAULT NULL,
  `PARENT_IDX_` int(11) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  UNIQUE KEY `UK_5jra0ppj5pwgpd1udjpmx1p9g` (`ID_`),
  KEY `IDX_EXEC_PARENT` (`PARENT_`),
  KEY `IDX_EXEC_INSTANCE` (`INSTANCE_`),
  KEY `IDX_EXEC_SUPEREXEC` (`SUPEREXEC_`),
  KEY `IDX_EXEC_SUBPI` (`SUBPROCINST_`),
  KEY `FK_EXEC_PARENT` (`PARENT_`),
  KEY `FK_EXEC_INSTANCE` (`INSTANCE_`),
  KEY `FK_EXEC_SUPEREXEC` (`SUPEREXEC_`),
  KEY `FK_EXEC_SUBPI` (`SUBPROCINST_`),
  CONSTRAINT `FK_EXEC_SUBPI` FOREIGN KEY (`SUBPROCINST_`) REFERENCES `jbpm4_execution` (`DBID_`),
  CONSTRAINT `FK_EXEC_INSTANCE` FOREIGN KEY (`INSTANCE_`) REFERENCES `jbpm4_execution` (`DBID_`),
  CONSTRAINT `FK_EXEC_PARENT` FOREIGN KEY (`PARENT_`) REFERENCES `jbpm4_execution` (`DBID_`),
  CONSTRAINT `FK_EXEC_SUPEREXEC` FOREIGN KEY (`SUPEREXEC_`) REFERENCES `jbpm4_execution` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for jbpm4_hist_actinst
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_hist_actinst`;
CREATE TABLE `jbpm4_hist_actinst` (
  `DBID_` bigint(20) NOT NULL,
  `CLASS_` varchar(255) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `HPROCI_` bigint(20) DEFAULT NULL,
  `TYPE_` varchar(255) DEFAULT NULL,
  `EXECUTION_` varchar(255) DEFAULT NULL,
  `ACTIVITY_NAME_` varchar(255) DEFAULT NULL,
  `START_` datetime DEFAULT NULL,
  `END_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `TRANSITION_` varchar(255) DEFAULT NULL,
  `NEXTIDX_` int(11) DEFAULT NULL,
  `HTASK_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_HACTI_HPROCI` (`HPROCI_`),
  KEY `IDX_HTI_HTASK` (`HTASK_`),
  KEY `FK_HACTI_HPROCI` (`HPROCI_`),
  KEY `FK_HTI_HTASK` (`HTASK_`),
  CONSTRAINT `FK_HTI_HTASK` FOREIGN KEY (`HTASK_`) REFERENCES `jbpm4_hist_task` (`DBID_`),
  CONSTRAINT `FK_HACTI_HPROCI` FOREIGN KEY (`HPROCI_`) REFERENCES `jbpm4_hist_procinst` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for jbpm4_hist_detail
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_hist_detail`;
CREATE TABLE `jbpm4_hist_detail` (
  `DBID_` bigint(20) NOT NULL,
  `CLASS_` varchar(255) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `USERID_` varchar(255) DEFAULT NULL,
  `TIME_` datetime DEFAULT NULL,
  `HPROCI_` bigint(20) DEFAULT NULL,
  `HPROCIIDX_` int(11) DEFAULT NULL,
  `HACTI_` bigint(20) DEFAULT NULL,
  `HACTIIDX_` int(11) DEFAULT NULL,
  `HTASK_` bigint(20) DEFAULT NULL,
  `HTASKIDX_` int(11) DEFAULT NULL,
  `HVAR_` bigint(20) DEFAULT NULL,
  `HVARIDX_` int(11) DEFAULT NULL,
  `MESSAGE_` longtext,
  `OLD_STR_` varchar(255) DEFAULT NULL,
  `NEW_STR_` varchar(255) DEFAULT NULL,
  `OLD_INT_` int(11) DEFAULT NULL,
  `NEW_INT_` int(11) DEFAULT NULL,
  `OLD_TIME_` datetime DEFAULT NULL,
  `NEW_TIME_` datetime DEFAULT NULL,
  `PARENT_` bigint(20) DEFAULT NULL,
  `PARENT_IDX_` int(11) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_HDET_HPROCI` (`HPROCI_`),
  KEY `IDX_HDET_HACTI` (`HACTI_`),
  KEY `IDX_HDET_HTASK` (`HTASK_`),
  KEY `IDX_HDET_HVAR` (`HVAR_`),
  KEY `FK_HDETAIL_HPROCI` (`HPROCI_`),
  KEY `FK_HDETAIL_HACTI` (`HACTI_`),
  KEY `FK_HDETAIL_HTASK` (`HTASK_`),
  KEY `FK_HDETAIL_HVAR` (`HVAR_`),
  CONSTRAINT `FK_HDETAIL_HVAR` FOREIGN KEY (`HVAR_`) REFERENCES `jbpm4_hist_var` (`DBID_`),
  CONSTRAINT `FK_HDETAIL_HACTI` FOREIGN KEY (`HACTI_`) REFERENCES `jbpm4_hist_actinst` (`DBID_`),
  CONSTRAINT `FK_HDETAIL_HPROCI` FOREIGN KEY (`HPROCI_`) REFERENCES `jbpm4_hist_procinst` (`DBID_`),
  CONSTRAINT `FK_HDETAIL_HTASK` FOREIGN KEY (`HTASK_`) REFERENCES `jbpm4_hist_task` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for jbpm4_hist_procinst
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_hist_procinst`;
CREATE TABLE `jbpm4_hist_procinst` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `ID_` varchar(255) DEFAULT NULL,
  `PROCDEFID_` varchar(255) DEFAULT NULL,
  `KEY_` varchar(255) DEFAULT NULL,
  `START_` datetime DEFAULT NULL,
  `END_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `STATE_` varchar(255) DEFAULT NULL,
  `ENDACTIVITY_` varchar(255) DEFAULT NULL,
  `NEXTIDX_` int(11) DEFAULT NULL,
  PRIMARY KEY (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for jbpm4_hist_task
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_hist_task`;
CREATE TABLE `jbpm4_hist_task` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `EXECUTION_` varchar(255) DEFAULT NULL,
  `OUTCOME_` varchar(255) DEFAULT NULL,
  `ASSIGNEE_` varchar(255) DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `STATE_` varchar(255) DEFAULT NULL,
  `CREATE_` datetime DEFAULT NULL,
  `END_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `NEXTIDX_` int(11) DEFAULT NULL,
  `SUPERTASK_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_HSUPERT_SUB` (`SUPERTASK_`),
  KEY `FK_HSUPERT_SUB` (`SUPERTASK_`),
  CONSTRAINT `FK_HSUPERT_SUB` FOREIGN KEY (`SUPERTASK_`) REFERENCES `jbpm4_hist_task` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for jbpm4_hist_var
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_hist_var`;
CREATE TABLE `jbpm4_hist_var` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `PROCINSTID_` varchar(255) DEFAULT NULL,
  `EXECUTIONID_` varchar(255) DEFAULT NULL,
  `VARNAME_` varchar(255) DEFAULT NULL,
  `VALUE_` varchar(255) DEFAULT NULL,
  `HPROCI_` bigint(20) DEFAULT NULL,
  `HTASK_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_HVAR_HPROCI` (`HPROCI_`),
  KEY `IDX_HVAR_HTASK` (`HTASK_`),
  KEY `FK_HVAR_HPROCI` (`HPROCI_`),
  KEY `FK_HVAR_HTASK` (`HTASK_`),
  CONSTRAINT `FK_HVAR_HTASK` FOREIGN KEY (`HTASK_`) REFERENCES `jbpm4_hist_task` (`DBID_`),
  CONSTRAINT `FK_HVAR_HPROCI` FOREIGN KEY (`HPROCI_`) REFERENCES `jbpm4_hist_procinst` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for jbpm4_id_group
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_id_group`;
CREATE TABLE `jbpm4_id_group` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `ID_` varchar(255) DEFAULT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `TYPE_` varchar(255) DEFAULT NULL,
  `PARENT_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_GROUP_PARENT` (`PARENT_`),
  KEY `FK_GROUP_PARENT` (`PARENT_`),
  CONSTRAINT `FK_GROUP_PARENT` FOREIGN KEY (`PARENT_`) REFERENCES `jbpm4_id_group` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for jbpm4_id_membership
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_id_membership`;
CREATE TABLE `jbpm4_id_membership` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `USER_` bigint(20) DEFAULT NULL,
  `GROUP_` bigint(20) DEFAULT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_MEM_USER` (`USER_`),
  KEY `IDX_MEM_GROUP` (`GROUP_`),
  KEY `FK_MEM_USER` (`USER_`),
  KEY `FK_MEM_GROUP` (`GROUP_`),
  CONSTRAINT `FK_MEM_GROUP` FOREIGN KEY (`GROUP_`) REFERENCES `jbpm4_id_group` (`DBID_`),
  CONSTRAINT `FK_MEM_USER` FOREIGN KEY (`USER_`) REFERENCES `jbpm4_id_user` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for jbpm4_id_user
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_id_user`;
CREATE TABLE `jbpm4_id_user` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `ID_` varchar(255) DEFAULT NULL,
  `PASSWORD_` varchar(255) DEFAULT NULL,
  `GIVENNAME_` varchar(255) DEFAULT NULL,
  `FAMILYNAME_` varchar(255) DEFAULT NULL,
  `BUSINESSEMAIL_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for jbpm4_job
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_job`;
CREATE TABLE `jbpm4_job` (
  `DBID_` bigint(20) NOT NULL,
  `CLASS_` varchar(255) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `DUEDATE_` datetime DEFAULT NULL,
  `STATE_` varchar(255) DEFAULT NULL,
  `ISEXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `LOCKOWNER_` varchar(255) DEFAULT NULL,
  `LOCKEXPTIME_` datetime DEFAULT NULL,
  `EXCEPTION_` longtext,
  `RETRIES_` int(11) DEFAULT NULL,
  `PROCESSINSTANCE_` bigint(20) DEFAULT NULL,
  `EXECUTION_` bigint(20) DEFAULT NULL,
  `CFG_` bigint(20) DEFAULT NULL,
  `SIGNAL_` varchar(255) DEFAULT NULL,
  `EVENT_` varchar(255) DEFAULT NULL,
  `REPEAT_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_JOBDUEDATE` (`DUEDATE_`),
  KEY `IDX_JOBLOCKEXP` (`LOCKEXPTIME_`),
  KEY `IDX_JOBRETRIES` (`RETRIES_`),
  KEY `IDX_JOB_PRINST` (`PROCESSINSTANCE_`),
  KEY `IDX_JOB_EXE` (`EXECUTION_`),
  KEY `IDX_JOB_CFG` (`CFG_`),
  KEY `FK_JOB_CFG` (`CFG_`),
  CONSTRAINT `FK_JOB_CFG` FOREIGN KEY (`CFG_`) REFERENCES `jbpm4_lob` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for jbpm4_lob
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_lob`;
CREATE TABLE `jbpm4_lob` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `BLOB_VALUE_` longblob,
  `DEPLOYMENT_` bigint(20) DEFAULT NULL,
  `NAME_` longtext,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_LOB_DEPLOYMENT` (`DEPLOYMENT_`),
  KEY `FK_LOB_DEPLOYMENT` (`DEPLOYMENT_`),
  CONSTRAINT `FK_LOB_DEPLOYMENT` FOREIGN KEY (`DEPLOYMENT_`) REFERENCES `jbpm4_deployment` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for jbpm4_participation
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_participation`;
CREATE TABLE `jbpm4_participation` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `GROUPID_` varchar(255) DEFAULT NULL,
  `USERID_` varchar(255) DEFAULT NULL,
  `TYPE_` varchar(255) DEFAULT NULL,
  `TASK_` bigint(20) DEFAULT NULL,
  `SWIMLANE_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_PART_TASK` (`TASK_`),
  KEY `FK_PART_TASK` (`TASK_`),
  KEY `FK_PART_SWIMLANE` (`SWIMLANE_`),
  CONSTRAINT `FK_PART_SWIMLANE` FOREIGN KEY (`SWIMLANE_`) REFERENCES `jbpm4_swimlane` (`DBID_`),
  CONSTRAINT `FK_PART_TASK` FOREIGN KEY (`TASK_`) REFERENCES `jbpm4_task` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for jbpm4_property
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_property`;
CREATE TABLE `jbpm4_property` (
  `KEY_` varchar(255) NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `VALUE_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`KEY_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for jbpm4_swimlane
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_swimlane`;
CREATE TABLE `jbpm4_swimlane` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `ASSIGNEE_` varchar(255) DEFAULT NULL,
  `EXECUTION_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_SWIMLANE_EXEC` (`EXECUTION_`),
  KEY `FK_SWIMLANE_EXEC` (`EXECUTION_`),
  CONSTRAINT `FK_SWIMLANE_EXEC` FOREIGN KEY (`EXECUTION_`) REFERENCES `jbpm4_execution` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for jbpm4_task
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_task`;
CREATE TABLE `jbpm4_task` (
  `DBID_` bigint(20) NOT NULL,
  `CLASS_` char(1) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `DESCR_` longtext,
  `STATE_` varchar(255) DEFAULT NULL,
  `SUSPHISTSTATE_` varchar(255) DEFAULT NULL,
  `ASSIGNEE_` varchar(255) DEFAULT NULL,
  `FORM_` varchar(255) DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `CREATE_` datetime DEFAULT NULL,
  `DUEDATE_` datetime DEFAULT NULL,
  `PROGRESS_` int(11) DEFAULT NULL,
  `SIGNALLING_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(255) DEFAULT NULL,
  `ACTIVITY_NAME_` varchar(255) DEFAULT NULL,
  `HASVARS_` tinyint(1) DEFAULT NULL,
  `SUPERTASK_` bigint(20) DEFAULT NULL,
  `EXECUTION_` bigint(20) DEFAULT NULL,
  `PROCINST_` bigint(20) DEFAULT NULL,
  `SWIMLANE_` bigint(20) DEFAULT NULL,
  `TASKDEFNAME_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_TASK_SUPERTASK` (`SUPERTASK_`),
  KEY `FK_TASK_SUPERTASK` (`SUPERTASK_`),
  KEY `FK_TASK_SWIML` (`SWIMLANE_`),
  CONSTRAINT `FK_TASK_SWIML` FOREIGN KEY (`SWIMLANE_`) REFERENCES `jbpm4_swimlane` (`DBID_`),
  CONSTRAINT `FK_TASK_SUPERTASK` FOREIGN KEY (`SUPERTASK_`) REFERENCES `jbpm4_task` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for jbpm4_variable
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_variable`;
CREATE TABLE `jbpm4_variable` (
  `DBID_` bigint(20) NOT NULL,
  `CLASS_` varchar(255) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `KEY_` varchar(255) DEFAULT NULL,
  `CONVERTER_` varchar(255) DEFAULT NULL,
  `HIST_` tinyint(1) DEFAULT NULL,
  `EXECUTION_` bigint(20) DEFAULT NULL,
  `TASK_` bigint(20) DEFAULT NULL,
  `LOB_` bigint(20) DEFAULT NULL,
  `DATE_VALUE_` datetime DEFAULT NULL,
  `DOUBLE_VALUE_` double DEFAULT NULL,
  `CLASSNAME_` varchar(255) DEFAULT NULL,
  `LONG_VALUE_` bigint(20) DEFAULT NULL,
  `STRING_VALUE_` varchar(255) DEFAULT NULL,
  `TEXT_VALUE_` longtext,
  `EXESYS_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_VAR_LOB` (`LOB_`),
  KEY `IDX_VAR_EXECUTION` (`EXECUTION_`),
  KEY `IDX_VAR_EXESYS` (`EXESYS_`),
  KEY `IDX_VAR_TASK` (`TASK_`),
  KEY `FK_VAR_LOB` (`LOB_`),
  KEY `FK_VAR_EXECUTION` (`EXECUTION_`),
  KEY `FK_VAR_EXESYS` (`EXESYS_`),
  KEY `FK_VAR_TASK` (`TASK_`),
  CONSTRAINT `FK_VAR_TASK` FOREIGN KEY (`TASK_`) REFERENCES `jbpm4_task` (`DBID_`),
  CONSTRAINT `FK_VAR_EXECUTION` FOREIGN KEY (`EXECUTION_`) REFERENCES `jbpm4_execution` (`DBID_`),
  CONSTRAINT `FK_VAR_EXESYS` FOREIGN KEY (`EXESYS_`) REFERENCES `jbpm4_execution` (`DBID_`),
  CONSTRAINT `FK_VAR_LOB` FOREIGN KEY (`LOB_`) REFERENCES `jbpm4_lob` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_attachments
-- ----------------------------
DROP TABLE IF EXISTS `sys_attachments`;
CREATE TABLE `sys_attachments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `code` varchar(64) DEFAULT NULL,
  `contentType` varchar(64) DEFAULT NULL,
  `extension` varchar(32) DEFAULT NULL,
  `filePath` longtext,
  `filetype` varchar(255) DEFAULT NULL,
  `name` longtext,
  `size` bigint(20) DEFAULT NULL,
  `type` varchar(1) DEFAULT NULL,
  `creater_id` bigint(20) DEFAULT NULL,
  `modifier_id` bigint(20) DEFAULT NULL,
  `storage_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_jr51gn5jmrdj4hlwnperdi7rn` (`creater_id`),
  KEY `FK_16g0j4l5h20aq2b8nio0xpx7` (`modifier_id`),
  KEY `FK_q8mmnt1cue6940ubbdakqqghf` (`storage_id`),
  CONSTRAINT `FK_q8mmnt1cue6940ubbdakqqghf` FOREIGN KEY (`storage_id`) REFERENCES `sys_attachments_storages` (`id`),
  CONSTRAINT `FK_16g0j4l5h20aq2b8nio0xpx7` FOREIGN KEY (`modifier_id`) REFERENCES `sys_users` (`id`),
  CONSTRAINT `FK_jr51gn5jmrdj4hlwnperdi7rn` FOREIGN KEY (`creater_id`) REFERENCES `sys_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_attachments_storages
-- ----------------------------
DROP TABLE IF EXISTS `sys_attachments_storages`;
CREATE TABLE `sys_attachments_storages` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` longblob,
  `createTime` datetime DEFAULT NULL,
  `size` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_common_message
-- ----------------------------
DROP TABLE IF EXISTS `sys_common_message`;
CREATE TABLE `sys_common_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `firstMutualFlag` int(11) NOT NULL,
  `is_deleted_Receiver` int(11) NOT NULL,
  `is_deleted_Sender` int(11) NOT NULL,
  `mutualFlag` varchar(255) DEFAULT NULL,
  `pId` bigint(20) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `creater_id` bigint(20) DEFAULT NULL,
  `modifier_id` bigint(20) DEFAULT NULL,
  `fromUser_id` bigint(20) DEFAULT NULL,
  `toUser_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_mgxi484ibiborkq4cg2c0ivdu` (`creater_id`),
  KEY `FK_pj7s3mou8veawr5n5gpnrhf2f` (`modifier_id`),
  KEY `FK_1u2w9guayl9wvdir033ohg8g8` (`fromUser_id`),
  KEY `FK_48mu9o6f1ufsdbnycw63pgxwm` (`toUser_id`),
  CONSTRAINT `FK_48mu9o6f1ufsdbnycw63pgxwm` FOREIGN KEY (`toUser_id`) REFERENCES `sys_users` (`id`),
  CONSTRAINT `FK_1u2w9guayl9wvdir033ohg8g8` FOREIGN KEY (`fromUser_id`) REFERENCES `sys_users` (`id`),
  CONSTRAINT `FK_mgxi484ibiborkq4cg2c0ivdu` FOREIGN KEY (`creater_id`) REFERENCES `sys_users` (`id`),
  CONSTRAINT `FK_pj7s3mou8veawr5n5gpnrhf2f` FOREIGN KEY (`modifier_id`) REFERENCES `sys_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_dic
-- ----------------------------
DROP TABLE IF EXISTS `sys_dic`;
CREATE TABLE `sys_dic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `dic_desc` varchar(255) DEFAULT NULL,
  `dic_key` varchar(255) DEFAULT NULL,
  `dic_type` varchar(255) DEFAULT NULL,
  `dic_value` varchar(255) DEFAULT NULL,
  `is_businessDate` tinyint(1) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `level` int(11) NOT NULL,
  `orderNo` int(11) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `creater_id` bigint(20) DEFAULT NULL,
  `modifier_id` bigint(20) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_h8aijannfai65n37wyga0nhtf` (`creater_id`),
  KEY `FK_on579vpdmkh0jx7mno976ne58` (`modifier_id`),
  KEY `FK_p5636kejgv2unw6jkean3rn4w` (`parent_id`),
  CONSTRAINT `FK_p5636kejgv2unw6jkean3rn4w` FOREIGN KEY (`parent_id`) REFERENCES `sys_dic` (`id`),
  CONSTRAINT `FK_h8aijannfai65n37wyga0nhtf` FOREIGN KEY (`creater_id`) REFERENCES `sys_users` (`id`),
  CONSTRAINT `FK_on579vpdmkh0jx7mno976ne58` FOREIGN KEY (`modifier_id`) REFERENCES `sys_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL,
  `browser` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `create_id` bigint(20) DEFAULT NULL,
  `modifier_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_9x24tslq8joxswo15l55ty73p` (`create_id`),
  KEY `FK_merc7j79ahwshngprh05ef9xt` (`modifier_id`),
  CONSTRAINT `FK_merc7j79ahwshngprh05ef9xt` FOREIGN KEY (`modifier_id`) REFERENCES `sys_users` (`id`),
  CONSTRAINT `FK_9x24tslq8joxswo15l55ty73p` FOREIGN KEY (`create_id`) REFERENCES `sys_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_menu_rights
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_rights`;
CREATE TABLE `sys_menu_rights` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `menu_id` bigint(20) DEFAULT NULL,
  `right_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_p6j62tuitth8992niy7ywfjpd` (`menu_id`),
  KEY `FK_qnspqtmnb0968vk75lwdj2iww` (`right_id`),
  CONSTRAINT `FK_qnspqtmnb0968vk75lwdj2iww` FOREIGN KEY (`right_id`) REFERENCES `sys_rights` (`id`),
  CONSTRAINT `FK_p6j62tuitth8992niy7ywfjpd` FOREIGN KEY (`menu_id`) REFERENCES `sys_menus` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_menus
-- ----------------------------
DROP TABLE IF EXISTS `sys_menus`;
CREATE TABLE `sys_menus` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `icon` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `orderNo` int(11) NOT NULL,
  `uri` varchar(255) DEFAULT NULL,
  `p_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_1iwwjl3r3gv8il95n78t7lpb2` (`p_id`),
  CONSTRAINT `FK_1iwwjl3r3gv8il95n78t7lpb2` FOREIGN KEY (`p_id`) REFERENCES `sys_menus` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_producthistory_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_producthistory_file`;
CREATE TABLE `sys_producthistory_file` (
  `productHistory_id` bigint(20) NOT NULL,
  `attachment_id` bigint(20) NOT NULL,
  KEY `FK_1eowvpittq88sk3g1pgcluvb6` (`attachment_id`),
  KEY `FK_m1cu337xb5e7kme31b7nl88t8` (`productHistory_id`),
  CONSTRAINT `FK_m1cu337xb5e7kme31b7nl88t8` FOREIGN KEY (`productHistory_id`) REFERENCES `biz_product_history` (`id`),
  CONSTRAINT `FK_1eowvpittq88sk3g1pgcluvb6` FOREIGN KEY (`attachment_id`) REFERENCES `sys_attachments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_rights
-- ----------------------------
DROP TABLE IF EXISTS `sys_rights`;
CREATE TABLE `sys_rights` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `orderNo` int(11) NOT NULL,
  `value` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role_right
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_right`;
CREATE TABLE `sys_role_right` (
  `menuRight_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`menuRight_id`,`role_id`),
  KEY `FK_bpl5lt3jfg22rm22gdgch67fx` (`role_id`),
  KEY `FK_ox5lo8t544lptuie0riqoggrr` (`menuRight_id`),
  CONSTRAINT `FK_ox5lo8t544lptuie0riqoggrr` FOREIGN KEY (`menuRight_id`) REFERENCES `sys_menu_rights` (`id`),
  CONSTRAINT `FK_bpl5lt3jfg22rm22gdgch67fx` FOREIGN KEY (`role_id`) REFERENCES `sys_roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles`;
CREATE TABLE `sys_roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pjdyd7mxv3d909iq031ko99xv` (`code`),
  UNIQUE KEY `UK_gyacyknwahd8r7fwyeknk8ywq` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_roles`;
CREATE TABLE `sys_user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK_gtlijv9vkgl240ko5yco5qc6u` (`role_id`),
  KEY `FK_pos21hd0awqh6iq05k0v5o8le` (`user_id`),
  CONSTRAINT `FK_pos21hd0awqh6iq05k0v5o8le` FOREIGN KEY (`user_id`) REFERENCES `sys_users` (`id`),
  CONSTRAINT `FK_gtlijv9vkgl240ko5yco5qc6u` FOREIGN KEY (`role_id`) REFERENCES `sys_roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_users
-- ----------------------------
DROP TABLE IF EXISTS `sys_users`;
CREATE TABLE `sys_users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `account` varchar(32) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `gender` int(11) DEFAULT NULL,
  `imgUrl` varchar(255) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `nickName` longtext,
  `password` longtext,
  `phone` varchar(20) DEFAULT NULL,
  `qq` varchar(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `creater_id` bigint(20) DEFAULT NULL,
  `modifier_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_si606t2potyyq924sldvp3d0t` (`account`),
  KEY `FK_avppkfkcjwn2kg6l5pk5egfjf` (`creater_id`),
  KEY `FK_6u9mr5ud3e2f6j9wuvp1uorjr` (`modifier_id`),
  CONSTRAINT `FK_6u9mr5ud3e2f6j9wuvp1uorjr` FOREIGN KEY (`modifier_id`) REFERENCES `sys_users` (`id`),
  CONSTRAINT `FK_avppkfkcjwn2kg6l5pk5egfjf` FOREIGN KEY (`creater_id`) REFERENCES `sys_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
