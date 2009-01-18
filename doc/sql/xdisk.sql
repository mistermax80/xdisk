CREATE TABLE admin (
	USERID VARCHAR(20) NOT NULL,
	PRIMARY KEY (USERID)
);

CREATE TABLE file (
	CODICE INT NOT NULL,
	NOME VARCHAR(45) NOT NULL,
	DIMENSIONE INT NOT NULL,
	CARTELLA INT NOT NULL,
	AUTORE VARCHAR(45) NOT NULL,
	UTENTEINS VARCHAR(45) NOT NULL,
	PRIMARY KEY (CODICE)
);

CREATE TABLE parent (
	PADRE INT NOT NULL,
	FIGLIO INT NOT NULL,
	PRIMARY KEY (PADRE,FIGLIO)
);

CREATE TABLE folder (
	CODICE INT NOT NULL,
	NOME VARCHAR(20) NOT NULL,
	DIMENSIONE INT NOT NULL,
	PRIMARY KEY (CODICE)
);

CREATE TABLE ownership (
	FILE INT NOT NULL,
	UTENTE VARCHAR(20) NOT NULL,
	PRIMARY KEY (FILE,UTENTE)
);

CREATE TABLE disk (
	NOME VARCHAR(20) NOT NULL,
	DIMENSIONE INT NOT NULL,
	USERID VARCHAR(20) NOT NULL,
	CARTELLAROOT INT NOT NULL,
	PRIMARY KEY (NOME)
);

CREATE TABLE client (
	USERID VARCHAR(20) NOT NULL,
	INDIP VARCHAR(45) NOT NULL,
	PORTA INT NOT NULL,
	TIPOCONN VARCHAR(45) NOT NULL,
	PRIMARY KEY (USERID)
);

CREATE TABLE user (
	USERID VARCHAR(20) NOT NULL,
	NOME VARCHAR(45) NOT NULL,
	PASSWORD VARCHAR(20) NOT NULL,
	EMAIL VARCHAR(45) NOT NULL,
	admin BIT DEFAULT 0 NOT NULL,
	PRIMARY KEY (USERID)
);

CREATE TABLE download (
	TICKET INT NOT NULL,
	FILE INT NOT NULL,
	UTENTE VARCHAR(20) NOT NULL,
	TIMESTAMP DATETIME NOT NULL,
	PRIMARY KEY (TICKET)
);

CREATE INDEX FK_appartenenza_2 ON ownership (UTENTE ASC);

CREATE INDEX FK_disco_1 ON disk (CARTELLAROOT ASC);

CREATE INDEX FK_file_2 ON file (UTENTEINS ASC);

CREATE INDEX FK_disco_2 ON disk (USERID ASC);

CREATE UNIQUE INDEX Index_2 ON client (INDIP ASC, PORTA ASC);

CREATE INDEX FK_download_1 ON download (FILE ASC);

CREATE INDEX FK_download_2 ON download (UTENTE ASC);

CREATE INDEX FK_contienecartella_2 ON parent (FIGLIO ASC);

CREATE UNIQUE INDEX Index_2 ON file (CARTELLA ASC, NOME ASC);

ALTER TABLE user ADD PRIMARY KEY (USERID);

ALTER TABLE parent ADD PRIMARY KEY (PADRE, FIGLIO);

ALTER TABLE ownership ADD PRIMARY KEY (FILE, UTENTE);

ALTER TABLE download ADD CONSTRAINT FK_download_1 FOREIGN KEY (FILE)
	REFERENCES file (CODICE);

ALTER TABLE file ADD CONSTRAINT FK_file_2 FOREIGN KEY (UTENTEINS)
	REFERENCES user (USERID);

ALTER TABLE disk ADD CONSTRAINT FK_disco_2 FOREIGN KEY (USERID)
	REFERENCES admin (USERID);

ALTER TABLE download ADD CONSTRAINT FK_download_2 FOREIGN KEY (UTENTE)
	REFERENCES user (USERID);

ALTER TABLE parent ADD CONSTRAINT FK_contienecartella_1 FOREIGN KEY (PADRE)
	REFERENCES folder (CODICE);

ALTER TABLE file ADD CONSTRAINT FK_file_1 FOREIGN KEY (CARTELLA)
	REFERENCES folder (CODICE);

ALTER TABLE ownership ADD CONSTRAINT FK_appartenenza_2 FOREIGN KEY (UTENTE)
	REFERENCES user (USERID);

ALTER TABLE ownership ADD CONSTRAINT FK_appartenenza_1 FOREIGN KEY (FILE)
	REFERENCES file (CODICE);

ALTER TABLE client ADD CONSTRAINT FK_user_1 FOREIGN KEY (USERID)
	REFERENCES user (USERID);

ALTER TABLE disk ADD CONSTRAINT FK_disco_1 FOREIGN KEY (CARTELLAROOT)
	REFERENCES folder (CODICE);

ALTER TABLE parent ADD CONSTRAINT FK_contienecartella_2 FOREIGN KEY (FIGLIO)
	REFERENCES folder (CODICE);
