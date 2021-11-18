-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: 3.36.57.26    Database: northernplanet
-- ------------------------------------------------------
-- Server version	8.0.27-0ubuntu0.20.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `room_id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `end_time` timestamp NULL DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `on_live` tinyint(1) DEFAULT '0',
  `start_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`room_id`),
  KEY `FKj8a5tk6wghd3x2sxgksj2fv3o` (`user_id`),
  CONSTRAINT `FKj8a5tk6wghd3x2sxgksj2fv3o` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (8,'ㅎㅇ','2021-11-15 14:21:33','하위',0,'2021-11-03 14:21:33',3),(9,'잘생쁘다','2021-11-18 14:03:49','잘생쁜팸',0,'2021-11-04 13:22:11',5),(11,'r',NULL,'r',0,'2021-11-05 09:46:15',6),(12,'zxcvqwer','2021-11-18 15:42:09','asdf',0,'2021-11-05 13:35:01',4),(13,'방방방',NULL,'방방',0,'2021-11-05 13:58:49',11),(14,'테테스트',NULL,'테테스트',0,'2021-11-12 15:29:07',11),(15,'33',NULL,'626262',0,'2021-11-16 11:56:14',5),(16,'ㄴㅇㄹ','2021-11-18 14:44:44','ㄴㅇㄹ',0,'2021-11-16 15:00:00',3),(18,'미래방',NULL,'미래방',0,'2021-11-29 15:00:00',11),(19,'북쪽행성~',NULL,'북쪽행성',0,'2021-11-18 00:56:33',4),(20,'ㄴㅇㄹ','2021-11-18 14:52:22','ㄴㄹㅇㅇㄴ',0,'2021-11-18 02:19:10',3),(21,'123123123123123',NULL,'123123123',0,'2021-11-18 02:20:36',11),(22,'ㅎ2','2021-11-18 14:53:39','ㅎ2',0,'2021-11-18 13:44:21',3),(23,'test','2021-11-18 14:13:22','테스트다',0,'2021-11-18 14:54:35',3),(24,'ssss','2021-11-18 14:56:30','testss',0,'2021-11-18 14:56:25',3),(25,'fdsfsdfdsfsdf',NULL,'yes',0,'2021-11-18 14:58:13',3),(27,'zxcv','2021-11-18 14:13:11','zxcv',0,'2021-11-18 16:42:33',4),(28,'HiHi','2021-11-18 21:21:47','focus goodgood',0,'2021-11-18 21:20:58',17),(29,'잘생쁘다',NULL,'잘생쁜팸',0,'2021-11-18 14:04:25',5);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-19  0:24:20
