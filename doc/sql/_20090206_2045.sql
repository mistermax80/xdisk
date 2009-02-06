-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.51a-3ubuntu5.4


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema xdisk
--

CREATE DATABASE IF NOT EXISTS xdisk;
USE xdisk;

--
-- Definition of table `xdisk`.`client`
--

DROP TABLE IF EXISTS `xdisk`.`client`;
CREATE TABLE  `xdisk`.`client` (
  `USERID` varchar(20) NOT NULL,
  `SESSION_ID` varchar(32) NOT NULL,
  `INDIP` varchar(45) NOT NULL,
  `PORTA` int(11) NOT NULL,
  `TIPOCONN` varchar(45) NOT NULL,
  `SESSION_STARTED` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`USERID`),
  UNIQUE KEY `Index_2` (`INDIP`,`PORTA`),
  CONSTRAINT `FK_user_1` FOREIGN KEY (`USERID`) REFERENCES `user` (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `xdisk`.`client`
--

/*!40000 ALTER TABLE `client` DISABLE KEYS */;
LOCK TABLES `client` WRITE;
INSERT INTO `xdisk`.`client` VALUES  ('ciips','f581a18c8be596aa0feaef9ee3769c14','192.168.1.9',40409,'TCP','2009-02-06 20:42:39'),
 ('picchio','2644ef20cda3d80f2b21ed6ee22d3f44','192.168.1.9',40406,'TCP','2009-02-06 20:42:36');
UNLOCK TABLES;
/*!40000 ALTER TABLE `client` ENABLE KEYS */;


--
-- Definition of table `xdisk`.`disk`
--

DROP TABLE IF EXISTS `xdisk`.`disk`;
CREATE TABLE  `xdisk`.`disk` (
  `NOME` varchar(20) character set latin1 collate latin1_bin NOT NULL,
  `DIMENSIONE` int(11) NOT NULL,
  `USERID` varchar(20) NOT NULL,
  `CARTELLAROOT` int(11) NOT NULL,
  PRIMARY KEY  USING BTREE (`NOME`),
  KEY `FK_disco_1` (`CARTELLAROOT`),
  KEY `FK_disco_2` (`USERID`),
  CONSTRAINT `FK_disco_1` FOREIGN KEY (`CARTELLAROOT`) REFERENCES `folder` (`CODICE`),
  CONSTRAINT `FK_disco_2` FOREIGN KEY (`USERID`) REFERENCES `user` (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `xdisk`.`disk`
--

/*!40000 ALTER TABLE `disk` DISABLE KEYS */;
LOCK TABLES `disk` WRITE;
INSERT INTO `xdisk`.`disk` VALUES  (0x646973636F31,123,'picchio',23);
UNLOCK TABLES;
/*!40000 ALTER TABLE `disk` ENABLE KEYS */;


--
-- Definition of table `xdisk`.`download`
--

DROP TABLE IF EXISTS `xdisk`.`download`;
CREATE TABLE  `xdisk`.`download` (
  `TICKET` int(11) NOT NULL,
  `FILE` int(11) NOT NULL,
  `UTENTE` varchar(20) NOT NULL,
  `TIMESTAMP` datetime NOT NULL,
  PRIMARY KEY  (`TICKET`),
  KEY `FK_download_1` (`FILE`),
  KEY `FK_download_2` (`UTENTE`),
  CONSTRAINT `FK_download_1` FOREIGN KEY (`FILE`) REFERENCES `file2` (`CODICE`),
  CONSTRAINT `FK_download_2` FOREIGN KEY (`UTENTE`) REFERENCES `user` (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `xdisk`.`download`
--

/*!40000 ALTER TABLE `download` DISABLE KEYS */;
LOCK TABLES `download` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `download` ENABLE KEYS */;


--
-- Definition of table `xdisk`.`extension`
--

DROP TABLE IF EXISTS `xdisk`.`extension`;
CREATE TABLE  `xdisk`.`extension` (
  `allow` tinyint(1) NOT NULL,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY  (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `xdisk`.`extension`
--

/*!40000 ALTER TABLE `extension` DISABLE KEYS */;
LOCK TABLES `extension` WRITE;
INSERT INTO `xdisk`.`extension` VALUES  (1,'exe'),
 (1,'com'),
 (0,'mp3'),
 (0,'avi');
UNLOCK TABLES;
/*!40000 ALTER TABLE `extension` ENABLE KEYS */;


--
-- Definition of table `xdisk`.`file`
--

DROP TABLE IF EXISTS `xdisk`.`file`;
CREATE TABLE  `xdisk`.`file` (
  `code` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL,
  `extension` varchar(32) NOT NULL,
  `description` varchar(32) NOT NULL,
  `tags` varchar(32) NOT NULL,
  `size` int(11) NOT NULL,
  `owner` varchar(32) NOT NULL,
  `mime` varchar(32) NOT NULL,
  `parent` int(11) NOT NULL,
  PRIMARY KEY  (`code`),
  KEY `new_fk_constraint` (`parent`),
  KEY `new_fk_constraint2` (`owner`),
  CONSTRAINT `new_fk_constraint` FOREIGN KEY (`parent`) REFERENCES `folder` (`CODICE`),
  CONSTRAINT `new_fk_constraint2` FOREIGN KEY (`owner`) REFERENCES `user` (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `xdisk`.`file`
--

/*!40000 ALTER TABLE `file` DISABLE KEYS */;
LOCK TABLES `file` WRITE;
INSERT INTO `xdisk`.`file` VALUES  ('2b97aeb1fd60c1f6034ea786688baff8','case_Contact','pdf','','',2000,'ciips','<mime>',48),
 ('436c119d8f7ba3a50b64aa05d0c92aaa','blender246','jpg','dddd','ffff',2000,'ciips','<mime>',34),
 ('5eae4e78d3e5aad17cc976c2cc2e3726','docoun','sys','description','tag,tags,ta,t',2324,'ciips','mime',38),
 ('745116a8712c786408b3afc092a664ea','p1000938','jpg','me','me',2000,'ciips','<mime>',12),
 ('97ed886167e6e2a2e7a82dc95504afd6','massimo prova','html','','',2000,'ciips','<mime>',12),
 ('ab7fe26f836795b2ed884d477cc2655b','picchio','sys','description','tag,tags,ta,t',2324,'ciips','mime',34),
 ('c3345ccc17bc63593fcb3ddf84a734e0','doc','sys','description','tag,tags,ta,t',2324,'ciips','mime',34),
 ('ce2e3ca390e8a107d005e0fbe26ed6a9','docounc','sys','description','tag,tags,ta,t',2324,'ciips','mime',38),
 ('e0963e1db89606e7b9e3f2634f17abc6','DVBLive-20090121T215026','m2t','tgtgt','trg',2000,'ciips','<mime>',42);
UNLOCK TABLES;
/*!40000 ALTER TABLE `file` ENABLE KEYS */;


--
-- Definition of table `xdisk`.`file2`
--

DROP TABLE IF EXISTS `xdisk`.`file2`;
CREATE TABLE  `xdisk`.`file2` (
  `CODICE` int(11) NOT NULL,
  `NOME` varchar(45) NOT NULL,
  `DIMENSIONE` int(11) NOT NULL,
  `CARTELLA` int(11) NOT NULL,
  `AUTORE` varchar(45) NOT NULL,
  `UTENTEINS` varchar(45) NOT NULL,
  PRIMARY KEY  (`CODICE`),
  UNIQUE KEY `Index_2` (`CARTELLA`,`NOME`),
  KEY `FK_file_2` (`UTENTEINS`),
  CONSTRAINT `FK_file_1` FOREIGN KEY (`CARTELLA`) REFERENCES `folder` (`CODICE`),
  CONSTRAINT `FK_file_2` FOREIGN KEY (`UTENTEINS`) REFERENCES `user` (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `xdisk`.`file2`
--

/*!40000 ALTER TABLE `file2` DISABLE KEYS */;
LOCK TABLES `file2` WRITE;
INSERT INTO `xdisk`.`file2` VALUES  (34,'doc.exe',33445,42,'ksksk','picchio'),
 (35,'messag.com',777,42,'max','ciips');
UNLOCK TABLES;
/*!40000 ALTER TABLE `file2` ENABLE KEYS */;


--
-- Definition of table `xdisk`.`folder`
--

DROP TABLE IF EXISTS `xdisk`.`folder`;
CREATE TABLE  `xdisk`.`folder` (
  `CODICE` int(11) NOT NULL,
  `NOME` varchar(20) NOT NULL,
  `DIMENSIONE` int(11) NOT NULL,
  `parent` int(11) default NULL,
  `prova` tinyint(1) NOT NULL default '1',
  PRIMARY KEY  (`CODICE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `xdisk`.`folder`
--

/*!40000 ALTER TABLE `folder` DISABLE KEYS */;
LOCK TABLES `folder` WRITE;
INSERT INTO `xdisk`.`folder` VALUES  (12,'ricevuti',1234,23,1),
 (23,'ROOT',123,NULL,1),
 (34,'prova',0,23,0),
 (35,'immagini',22,34,1),
 (36,'video',22,34,0),
 (37,'massimo',0,23,0),
 (38,'picchio',0,34,0),
 (39,'bello',0,38,0),
 (40,'bello',0,38,0),
 (41,'pii',0,37,0),
 (42,'ciao',0,38,0),
 (44,'alsx',0,37,0),
 (45,'lulu',0,23,0),
 (46,'bubu',0,42,0),
 (47,'vitellone',0,12,0),
 (48,'biio',0,36,0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `folder` ENABLE KEYS */;


--
-- Definition of table `xdisk`.`ownership`
--

DROP TABLE IF EXISTS `xdisk`.`ownership`;
CREATE TABLE  `xdisk`.`ownership` (
  `FILE` varchar(32) NOT NULL,
  `UTENTE` varchar(20) NOT NULL,
  PRIMARY KEY  (`FILE`,`UTENTE`),
  KEY `FK_appartenenza_2` (`UTENTE`),
  CONSTRAINT `FK_appartenenza_2` FOREIGN KEY (`UTENTE`) REFERENCES `user` (`USERID`),
  CONSTRAINT `new_fk_constraintf` FOREIGN KEY (`FILE`) REFERENCES `file` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `xdisk`.`ownership`
--

/*!40000 ALTER TABLE `ownership` DISABLE KEYS */;
LOCK TABLES `ownership` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `ownership` ENABLE KEYS */;


--
-- Definition of table `xdisk`.`request`
--

DROP TABLE IF EXISTS `xdisk`.`request`;
CREATE TABLE  `xdisk`.`request` (
  `ticketId` varchar(32) NOT NULL,
  `file` varchar(32) NOT NULL,
  `userid` varchar(32) NOT NULL,
  PRIMARY KEY  (`ticketId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `xdisk`.`request`
--

/*!40000 ALTER TABLE `request` DISABLE KEYS */;
LOCK TABLES `request` WRITE;
INSERT INTO `xdisk`.`request` VALUES  ('23333','e0963e1db89606e7b9e3f2634f17abc6','piccho');
UNLOCK TABLES;
/*!40000 ALTER TABLE `request` ENABLE KEYS */;


--
-- Definition of table `xdisk`.`user`
--

DROP TABLE IF EXISTS `xdisk`.`user`;
CREATE TABLE  `xdisk`.`user` (
  `USERID` varchar(20) NOT NULL,
  `NOME` varchar(45) NOT NULL,
  `PASSWORD` varchar(20) NOT NULL,
  `EMAIL` varchar(45) NOT NULL,
  `admin` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `xdisk`.`user`
--

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
LOCK TABLES `user` WRITE;
INSERT INTO `xdisk`.`user` VALUES  ('ciips','Alex','c','mmmm',1),
 ('lulu','luisa','lu','ll@kkk.it',0),
 ('max','massimo2','a','max2@xdisk.org',1),
 ('mm','ool','nn','jjjj@kkk',1),
 ('p','p','p','p',0),
 ('picchio','massimo','m','max@xdisk.org',1),
 ('Sara','Sara','s','mmmm',0),
 ('us','no','pas','em',1),
 ('ww','ss','ss','ss',0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
