-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.24-community


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
-- Definition of table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `USERID` varchar(20) NOT NULL,
  PRIMARY KEY  (`USERID`),
  CONSTRAINT `FK_admin_1` FOREIGN KEY (`USERID`) REFERENCES `utente` (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;


--
-- Definition of table `appartenenza`
--

DROP TABLE IF EXISTS `appartenenza`;
CREATE TABLE `appartenenza` (
  `FILE` int(11) NOT NULL,
  `UTENTE` varchar(20) NOT NULL,
  PRIMARY KEY  (`FILE`,`UTENTE`),
  KEY `FK_appartenenza_2` (`UTENTE`),
  CONSTRAINT `FK_appartenenza_1` FOREIGN KEY (`FILE`) REFERENCES `file` (`CODICE`),
  CONSTRAINT `FK_appartenenza_2` FOREIGN KEY (`UTENTE`) REFERENCES `utente` (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `appartenenza`
--

/*!40000 ALTER TABLE `appartenenza` DISABLE KEYS */;
/*!40000 ALTER TABLE `appartenenza` ENABLE KEYS */;


--
-- Definition of table `cartella`
--

DROP TABLE IF EXISTS `cartella`;
CREATE TABLE `cartella` (
  `CODICE` int(11) NOT NULL,
  `NOME` varchar(20) NOT NULL,
  `DIMENSIONE` int(11) NOT NULL,
  PRIMARY KEY  (`CODICE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cartella`
--

/*!40000 ALTER TABLE `cartella` DISABLE KEYS */;
/*!40000 ALTER TABLE `cartella` ENABLE KEYS */;


--
-- Definition of table `contienecartella`
--

DROP TABLE IF EXISTS `contienecartella`;
CREATE TABLE `contienecartella` (
  `PADRE` int(11) NOT NULL,
  `FIGLIO` int(11) NOT NULL,
  PRIMARY KEY  (`PADRE`,`FIGLIO`),
  KEY `FK_contienecartella_2` (`FIGLIO`),
  CONSTRAINT `FK_contienecartella_1` FOREIGN KEY (`PADRE`) REFERENCES `cartella` (`CODICE`),
  CONSTRAINT `FK_contienecartella_2` FOREIGN KEY (`FIGLIO`) REFERENCES `cartella` (`CODICE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `contienecartella`
--

/*!40000 ALTER TABLE `contienecartella` DISABLE KEYS */;
/*!40000 ALTER TABLE `contienecartella` ENABLE KEYS */;


--
-- Definition of table `disco`
--

DROP TABLE IF EXISTS `disco`;
CREATE TABLE `disco` (
  `NOME` varchar(20) character set latin1 collate latin1_bin NOT NULL,
  `DIMENSIONE` int(11) NOT NULL,
  `USERID` varchar(20) NOT NULL,
  `CARTELLAROOT` int(11) NOT NULL,
  PRIMARY KEY  USING BTREE (`NOME`),
  KEY `FK_disco_1` (`CARTELLAROOT`),
  KEY `FK_disco_2` (`USERID`),
  CONSTRAINT `FK_disco_1` FOREIGN KEY (`CARTELLAROOT`) REFERENCES `cartella` (`CODICE`),
  CONSTRAINT `FK_disco_2` FOREIGN KEY (`USERID`) REFERENCES `admin` (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `disco`
--

/*!40000 ALTER TABLE `disco` DISABLE KEYS */;
/*!40000 ALTER TABLE `disco` ENABLE KEYS */;


--
-- Definition of table `download`
--

DROP TABLE IF EXISTS `download`;
CREATE TABLE `download` (
  `TICKET` int(11) NOT NULL,
  `FILE` int(11) NOT NULL,
  `UTENTE` varchar(20) NOT NULL,
  `TIMESTAMP` datetime NOT NULL,
  PRIMARY KEY  (`TICKET`),
  KEY `FK_download_1` (`FILE`),
  KEY `FK_download_2` (`UTENTE`),
  CONSTRAINT `FK_download_1` FOREIGN KEY (`FILE`) REFERENCES `file` (`CODICE`),
  CONSTRAINT `FK_download_2` FOREIGN KEY (`UTENTE`) REFERENCES `utente` (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `download`
--

/*!40000 ALTER TABLE `download` DISABLE KEYS */;
/*!40000 ALTER TABLE `download` ENABLE KEYS */;


--
-- Definition of table `file`
--

DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `CODICE` int(11) NOT NULL,
  `NOME` varchar(45) NOT NULL,
  `DIMENSIONE` int(11) NOT NULL,
  `CARTELLA` int(11) NOT NULL,
  `AUTORE` varchar(45) NOT NULL,
  `UTENTEINS` varchar(45) NOT NULL,
  PRIMARY KEY  (`CODICE`),
  UNIQUE KEY `Index_2` (`CARTELLA`,`NOME`),
  KEY `FK_file_2` (`UTENTEINS`),
  CONSTRAINT `FK_file_1` FOREIGN KEY (`CARTELLA`) REFERENCES `cartella` (`CODICE`),
  CONSTRAINT `FK_file_2` FOREIGN KEY (`UTENTEINS`) REFERENCES `utente` (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `file`
--

/*!40000 ALTER TABLE `file` DISABLE KEYS */;
/*!40000 ALTER TABLE `file` ENABLE KEYS */;


--
-- Definition of table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `USERID` varchar(20) NOT NULL,
  `INDIP` varchar(45) NOT NULL,
  `PORTA` int(11) NOT NULL,
  `TIPOCONN` varchar(45) NOT NULL,
  PRIMARY KEY  (`USERID`),
  UNIQUE KEY `Index_2` (`INDIP`,`PORTA`),
  CONSTRAINT `FK_user_1` FOREIGN KEY (`USERID`) REFERENCES `utente` (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


--
-- Definition of table `utente`
--

DROP TABLE IF EXISTS `utente`;
CREATE TABLE `utente` (
  `USERID` varchar(20) NOT NULL,
  `NOME` varchar(45) NOT NULL,
  `PASSWORD` varchar(20) NOT NULL,
  `EMAIL` varchar(45) NOT NULL,
  PRIMARY KEY  (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `utente`
--

/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` (`USERID`,`NOME`,`PASSWORD`,`EMAIL`) VALUES 
 ('','','stefffff',''),
 ('alvaro','alvaro','a',''),
 ('anto11','antonio','abcde',''),
 ('asz','sss','ss','aa'),
 ('cesva','cesare valocchia','cessva','cesarw@it'),
 ('cla','claudio di biagio','ff','ss'),
 ('di sora','peppe','ssssss',''),
 ('farfa','fabio faraglia','fff','fabio@123'),
 ('la rana','serafino','larana',''),
 ('poms','tarquo tarquini','ppp','poms'),
 ('salsa','debora','sdrtyu',''),
 ('ssa','stefano','aaa',''),
 ('ste22','stefano','sss',''),
 ('stelvio','stefano piersanti','stela222','ste@boh');
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
