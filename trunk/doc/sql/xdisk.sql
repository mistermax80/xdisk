-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.24-community

--
-- Create schema xdisk
--

-- CREATE DATABASE IF NOT EXISTS xdisk;
-- USE xdisk;


--
-- Definition of table `utente`
--

CREATE TABLE `utente` (
  `USERID` varchar(20) NOT NULL,
  `NOME` varchar(45) NOT NULL,
  `PASSWORD` varchar(20) NOT NULL,
  `EMAIL` varchar(45) NOT NULL,
  PRIMARY KEY  (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Definition of table `user`
--

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
-- Definition of table `admin`
--

CREATE TABLE `admin` (
  `USERID` varchar(20) NOT NULL,
  PRIMARY KEY  (`USERID`),
  CONSTRAINT `FK_admin_1` FOREIGN KEY (`USERID`) REFERENCES `utente` (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Definition of table `cartella`
--

CREATE TABLE `cartella` (
  `CODICE` int(11) NOT NULL,
  `NOME` varchar(20) NOT NULL,
  `DIMENSIONE` int(11) NOT NULL,
  PRIMARY KEY  (`CODICE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Definition of table `file`
--

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
-- Definition of table `appartenenza`
--

CREATE TABLE `appartenenza` (
  `FILE` int(11) NOT NULL,
  `UTENTE` varchar(20) NOT NULL,
  PRIMARY KEY  (`FILE`,`UTENTE`),
  KEY `FK_appartenenza_2` (`UTENTE`),
  CONSTRAINT `FK_appartenenza_1` FOREIGN KEY (`FILE`) REFERENCES `file` (`CODICE`),
  CONSTRAINT `FK_appartenenza_2` FOREIGN KEY (`UTENTE`) REFERENCES `utente` (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Definition of table `contienecartella`
--

CREATE TABLE `contienecartella` (
  `PADRE` int(11) NOT NULL,
  `FIGLIO` int(11) NOT NULL,
  PRIMARY KEY  (`PADRE`,`FIGLIO`),
  KEY `FK_contienecartella_2` (`FIGLIO`),
  CONSTRAINT `FK_contienecartella_1` FOREIGN KEY (`PADRE`) REFERENCES `cartella` (`CODICE`),
  CONSTRAINT `FK_contienecartella_2` FOREIGN KEY (`FIGLIO`) REFERENCES `cartella` (`CODICE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Definition of table `disco`
--

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
-- Definition of table `download`
--

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