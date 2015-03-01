-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: localhost    Database: weather
-- ------------------------------------------------------
-- Server version	5.6.23-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `daily_weather`
--

DROP TABLE IF EXISTS `daily_weather`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `daily_weather` (
  `weather_id` int(11) NOT NULL AUTO_INCREMENT,
  `rain_amount` decimal(8,2) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `temperature` decimal(10,0) DEFAULT NULL,
  `wind` decimal(10,0) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `rain_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`weather_id`),
  KEY `FK_daily_weather_opiframe_user_user_id` (`user_id`),
  CONSTRAINT `FK_daily_weather_opiframe_user_user_id` FOREIGN KEY (`user_id`) REFERENCES `opiframe_user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='Table for weather data';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `daily_weather`
--

LOCK TABLES `daily_weather` WRITE;
/*!40000 ALTER TABLE `daily_weather` DISABLE KEYS */;
INSERT INTO `daily_weather` VALUES (1,10.00,'2015-02-12 00:00:00',20,10,1,'Gentle rain');
/*!40000 ALTER TABLE `daily_weather` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `opiframe_user`
--

DROP TABLE IF EXISTS `opiframe_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `opiframe_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='Table for WeatherApp users';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `opiframe_user`
--

LOCK TABLES `opiframe_user` WRITE;
/*!40000 ALTER TABLE `opiframe_user` DISABLE KEYS */;
INSERT INTO `opiframe_user` VALUES (1,'testi1'),(2,'testi2');
/*!40000 ALTER TABLE `opiframe_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-02-27 13:09:04
