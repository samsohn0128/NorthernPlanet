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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `oauth_id` varchar(255) DEFAULT NULL,
  `oauth_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'aeeun0222@gmail.com','https://lh3.googleusercontent.com/a-/AOh14Gi9wNPZlkNxf3LAZakFhQ_VAbkRwgHATZbhemAOjQ=s96-c','aeeun','112824604817088491836','google'),(3,'yesfordev@gmail.com','https://lh3.googleusercontent.com/a/AATXAJzUmziUu1TheuoVk7vn8gEfua4DYnZ16Xq_AlA=s96-c','Youngeun Seo','109869303488073247854','google'),(4,'samsohn0128@gmail.com','https://lh3.googleusercontent.com/a/AATXAJwLvUZ62ew7xC-FYuzYRNc2k9hT2_xXeptS65E2=s96-c','손동우','103975685773102095698','google'),(5,'wnduq125@gmail.com','https://lh3.googleusercontent.com/a-/AOh14GhqOzoifYkeoRnK1cPwYr9nPU38yFeHlWHG5DKh=s96-c','윤주엽','112852581113618740227','google'),(6,'anne.902011@gmail.com','https://lh3.googleusercontent.com/a/AATXAJzLn_a113T17DyICtE3AO1uj1Zzy204dGSq7Tbj=s96-c','김애은','111690592832210984186','google'),(11,'mjkim103301@gmail.com','https://lh3.googleusercontent.com/a/AATXAJyHY8aVNAqt8AuLkCG9oCogKPGwEF8bh5ZEdHqE=s96-c','김민지','107510285731070355785','google'),(12,'yes9717@gmail.com','https://lh3.googleusercontent.com/a-/AOh14GhyLHQ3_1ZOh8Z74TPjWsWysQfLyxfF39TSJmu1=s96-c','yeS','111898224070705973353','google'),(13,'32162125@dankook.ac.kr','https://lh3.googleusercontent.com/a/AATXAJz_L5bESddnwMqKWtiRz-TH_rFhFvkdwDuvUDiy=s96-c','서영은','117934944422654362629','google'),(15,'northernplanet204@gmail.com','https://lh3.googleusercontent.com/a/AATXAJwb9p-EJgphCphk-XmDL-lU2dOtb5je5EbV7RHx=s96-c','행성북쪽','108623461847878301462','google'),(16,'sgs1159@gmail.com','https://lh3.googleusercontent.com/a/AATXAJxsbwolNrwpJeP7T6mxoCMpFYacSkcOA9P6KjUx=s96-c','KingBlackCow','117081566721869804566','google'),(17,'litch0s2@gmail.com','https://lh3.googleusercontent.com/a-/AOh14GjpkxFN4CIhOmU30ooJJEzEriS4sloHhfgcqTZ4Eg=s96-c','정진주','116241363528440122262','google'),(18,'6513032@gmail.com','https://lh3.googleusercontent.com/a/AATXAJzlicEw4g1HYtvfUDWV7Z4EHblyJ_YcIno7XOaj=s96-c','만승','104573136407466695770','google');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-19  0:24:21
