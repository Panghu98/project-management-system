-- MySQL dump 10.13  Distrib 8.0.12, for Linux (x86_64)
--
-- Host: localhost    Database: project
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `allocation_info`
--

DROP TABLE IF EXISTS `allocation_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `allocation_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `user_score` double DEFAULT NULL COMMENT '获得的分数',
  `project_id` varchar(20) NOT NULL COMMENT '所属项目',
  `proportion` double NOT NULL COMMENT '划分比例',
  `status` smallint(2) DEFAULT '1' COMMENT '有效状态:\n1,有效(默认)\n0.无效',
  PRIMARY KEY (`id`),
  KEY `user_id_index` (`user_id`),
  KEY `foreign_pro_id_idx` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allocation_info`
--

LOCK TABLES `allocation_info` WRITE;
/*!40000 ALTER TABLE `allocation_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `allocation_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `allocation_info_temp`
--

DROP TABLE IF EXISTS `allocation_info_temp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `allocation_info_temp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `user_score` double DEFAULT NULL COMMENT '获得的分数',
  `project_id` varchar(20) NOT NULL COMMENT '所属项目',
  `proportion` double NOT NULL COMMENT '划分比例',
  `status` smallint(2) DEFAULT '1' COMMENT '1为有效,0为无效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `composite_key_idx` (`user_id`,`project_id`),
  KEY `user_id_index` (`user_id`),
  KEY `foreign_pro_id_idx` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allocation_info_temp`
--

LOCK TABLES `allocation_info_temp` WRITE;
/*!40000 ALTER TABLE `allocation_info_temp` DISABLE KEYS */;
/*!40000 ALTER TABLE `allocation_info_temp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apply_info`
--

DROP TABLE IF EXISTS `apply_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `apply_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '申请人ID',
  `project_id` varchar(20) NOT NULL,
  `approval_status` smallint(2) DEFAULT '0' COMMENT '0,未审核\\n1,审核不通过\\n2,审核通过',
  `apply_type` smallint(2) DEFAULT NULL COMMENT '申请类型\n1.延时\n2.重新分配',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apply_info`
--

LOCK TABLES `apply_info` WRITE;
/*!40000 ALTER TABLE `apply_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `apply_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file_info`
--

DROP TABLE IF EXISTS `file_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `file_info` (
  `project_id` varchar(20) NOT NULL COMMENT '项目名',
  `file_complate_name` varchar(20) NOT NULL COMMENT '文件完整名称',
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file_info`
--

LOCK TABLES `file_info` WRITE;
/*!40000 ALTER TABLE `file_info` DISABLE KEYS */;
INSERT INTO `file_info` VALUES ('P114','xiaohui.png'),('P461','学习路线.jpg');
/*!40000 ALTER TABLE `file_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_info`
--

DROP TABLE IF EXISTS `project_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `project_info` (
  `id` varchar(20) NOT NULL,
  `category` varchar(20) NOT NULL COMMENT '类别',
  `instruction` varchar(255) DEFAULT NULL COMMENT '项目说明',
  `level` varchar(10) DEFAULT NULL COMMENT '等级  --省级  国家级 等等',
  `grade` varchar(15) DEFAULT NULL COMMENT '级别  1 2 3 ',
  `number` tinyint(2) DEFAULT NULL COMMENT '项数',
  `variety` varchar(15) DEFAULT NULL COMMENT '类型   组织申报  比准当年 等',
  `score` double NOT NULL COMMENT '分数',
  `leader` varchar(15) NOT NULL COMMENT '负责人',
  `division` smallint(3) NOT NULL COMMENT '比例划分',
  `date` date DEFAULT NULL COMMENT '项目分配信息上传时间',
  `allocation_status` smallint(2) DEFAULT '0' COMMENT '0是未分配,1是shen请重新分配,2已分配',
  `deadline` date DEFAULT '1970-01-01' COMMENT '项目提交截止日期',
  `remaining_time` smallint(2) NOT NULL DEFAULT '2' COMMENT '剩余重新分配的次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_info`
--

LOCK TABLES `project_info` WRITE;
/*!40000 ALTER TABLE `project_info` DISABLE KEYS */;
INSERT INTO `project_info` VALUES ('P114','其它专业建设项目','计算机科学与技术省级示范专业申报','省级','',1,'组织申报',25,'李建',50,'2019-09-01',0,'2019-09-10',998),('P315','专业认证','计算机公共基础协助2018年石工和化工的专业认证','     ','',3,'',40,'刘忠慧',20,'2019-09-01',0,'2019-08-27',1000),('P461','专业建设水平','计算机科学与技术专业','','',2,'建设运行',40,'李建',20,'2019-09-01',0,'2019-09-27',994),('P4611','专业建设水平','计算机科学与技术专业','','',2,'建设运行',40,'李建',0,'2019-09-01',0,'2019-10-01',994),('P4613','专业建设水平','计算机科学与技术专业','','',2,'建设运行',40,'李建',0,'2019-09-01',0,'2019-10-01',997),('P4614','专业建设水平','计算机科学与技术专业','','',2,'建设运行',40,'李建',0,'1970-01-01',0,'2019-09-12',998);
/*!40000 ALTER TABLE `project_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `update_user_role` AFTER INSERT ON `project_info` FOR EACH ROW begin
declare leader_name VARCHAR(10) character set utf8;
set leader_name = NEW.leader;
update user set role = '2' where username =  leader_name and role = 1; 
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL,
  `username` varchar(20) DEFAULT NULL,
  `password` char(50) NOT NULL,
  `role` tinyint(10) DEFAULT '1',
  `salt` char(15) NOT NULL COMMENT '用于密码加密的盐值',
  `position` varchar(45) DEFAULT NULL COMMENT '职位',
  `office` varchar(45) DEFAULT NULL COMMENT '教研室',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (123456789101,'张三','4756a51e387552db1fa1dfc64cb6b194',1,'4995bd91','讲师','组织部'),(123456789102,'李建','4756a51e387552db1fa1dfc64cb6b194',2,'4995bd91','讲师','组织部'),(123456789103,'刘忠慧','4756a51e387552db1fa1dfc64cb6b194',3,'4995bd91','教授','计科院'),(123456789104,'测试用户1','4756a51e387552db1fa1dfc64cb6b194',2,'4995bd91','讲师','组织部'),(123456789105,'测试用户2','4756a51e387552db1fa1dfc64cb6b194',1,'4995bd91','讲师','组织部'),(123456789106,'测试用户3','4756a51e387552db1fa1dfc64cb6b194',1,'4995bd91','讲师','组织部'),(123456789107,'测试用户4','4756a51e387552db1fa1dfc64cb6b194',1,'4995bd91','讲师','组织部'),(123456789108,'测试用户5','ad42c8e1c2b0adfff795ed3930e3e0cd',2,'4a40a93e','教授','组织部');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'project'
--

--
-- Dumping routines for database 'project'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-09-09 16:49:51
