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
-- Table structure for table `presentation`
--

DROP TABLE IF EXISTS `presentation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `presentation` (
  `presentation_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `size` int NOT NULL,
  `upload_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`presentation_id`),
  KEY `FKgd68fuen5ib1t00dr20eem9va` (`user_id`),
  CONSTRAINT `FKgd68fuen5ib1t00dr20eem9va` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `presentation`
--

LOCK TABLES `presentation` WRITE;
/*!40000 ALTER TABLE `presentation` DISABLE KEYS */;
INSERT INTO `presentation` VALUES (4,'happy.pptx',9,'2021-11-02 14:58:29',NULL),(56,'bird',2,'2021-11-11 10:46:15',11),(57,'focus_camera',3,'2021-11-11 10:52:01',11),(60,'happy',9,'2021-11-12 11:07:31',6),(62,'exit',4,'2021-11-12 15:29:49',11),(63,'wm_cartoonize_LwwAi_',1,'2021-11-15 14:10:18',3),(64,'text',2,'2021-11-15 14:14:00',3),(65,'text',2,'2021-11-15 14:16:52',3),(66,'text',2,'2021-11-15 14:18:33',3),(67,'text',2,'2021-11-15 14:18:45',3),(71,'left',2,'2021-11-15 16:50:34',11),(72,'5기 2학기 프로젝트 평가안내_특화_Sub-PJT Ⅲ_중간평가_공지용',10,'2021-11-15 16:52:09',11),(80,'logo-google-thumbnail',3,'2021-11-16 17:25:34',11),(85,'Numbers pdf',50,'2021-11-17 10:00:03',4),(86,'wm_cartoonize_LwwAi_',1,'2021-11-17 13:49:16',3),(91,'focus',1,'2021-11-17 14:22:20',11),(95,'left',2,'2021-11-17 22:11:11',11),(97,'focus_camera3',3,'2021-11-17 22:13:01',11),(98,'wm_cartoonize_LwwAi_',1,'2021-11-17 22:13:36',3),(104,'다운로드',1,'2021-11-18 21:22:43',17),(105,'최종 발표',18,'2021-11-18 21:25:23',4),(106,'북쪽행성001',15,'2021-11-18 22:06:32',5),(107,'특화수정3',36,'2021-11-18 14:02:42',5),(108,'공통PJT_서울6반_A605_윤주엽',20,'2021-11-18 14:04:49',5),(109,'210528_5기_서울_5반_관통PJT_윤주엽_최재연',24,'2021-11-18 14:05:13',5);
/*!40000 ALTER TABLE `presentation` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-19  0:24:22
