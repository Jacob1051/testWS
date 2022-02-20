
CREATE DATABASE Signalisation;
USE Signalisation;

CREATE TABLE Region(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nom VARCHAR(100)
)ENGINE=InnoDB;

CREATE TABLE Admin(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    mdp VARCHAR(41)
)ENGINE=InnoDB;

insert into admin values(null, sha1('azerty'));

CREATE TABLE ChefRegion(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    idRegion INT,
    nom VARCHAR(100),
    mdp VARCHAR(41),
    FOREIGN KEY (idRegion) REFERENCES Region(id)
)ENGINE=InnoDB;

CREATE TABLE Utilisateur(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    email VARCHAR(100),
    mdp VARCHAR(41),
    naissance DATE
)ENGINE=InnoDB;

insert into Utilisateur values(null, 'user1@gmail.com', sha1('azerty'), '1999-02-02');
insert into Utilisateur values(null, 'user2@gmail.com', sha1('azerty'), '1999-02-03');

CREATE TABLE Category(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nom VARCHAR(100),
    image VARCHAR(100)
)ENGINE=InnoDB;

CREATE TABLE Signalement(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    idUtilisateur INT,
    idCategory INT,
    description TEXT,
    dateSignalement DATETIME,
    latitude FLOAT,
    longitude FLOAT,
    FOREIGN KEY(idUtilisateur) REFERENCES Utilisateur(id),
    FOREIGN KEY(idCategory) REFERENCES Category(id)
)ENGINE=InnoDB;

insert into Signalement values(null, 1, 1, 'Tady ho rodana le trano le', now(), 1,1);
insert into Signalement values(null, 2, 1, 'Tady ho rodana le trano io zoky a', now(), 1,1);

insert into Signalement values(null, 2, 1, 'Ratsy be le lalana', now(), 1,1);

CREATE TABLE Valide(
	idSignalement INT NOT NULL,
	valeur INT,
	dateReponse DATETIME,
	FOREIGN KEY(idSignalement) REFERENCES Signalement(id)
);

CREATE VIEW vSignalementRepondu AS select * from Signalement where id in(select idSignalement from Valide);
--select * from Signalement where id not in(select idSignalement from Affectation);

CREATE TABLE DetailsSignalement(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    idSignalement INT,
    photo VARCHAR(200),
    FOREIGN KEY(idSignalement) REFERENCES Signalement(id)
)ENGINE=InnoDB;

CREATE TABLE Affectation(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    idSignalement INT,
    idAdmin INT,
    idRegion INT,
    dateValidation DATETIME,
    FOREIGN KEY(idSignalement) REFERENCES Signalement(id),
    FOREIGN KEY(idAdmin) REFERENCES Admin(id),
    FOREIGN KEY(idRegion) REFERENCES Region(id)
)ENGINE=InnoDB;

CREATE TABLE Statut(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nom VARCHAR(100)
)ENGINE=InnoDB;

CREATE TABLE TraitementRegion(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    idSignalement INT,
    idStatut INT,
    FOREIGN KEY(idSignalement) REFERENCES Signalement(id),
    FOREIGN KEY(idStatut) REFERENCES Statut(id)
)ENGINE=InnoDB;

CREATE TABLE Token(
	idUser INT,
	valeur VARCHAR(100),
	validite DATE,
	FOREIGN KEY(idUser) REFERENCES Utilisateur(id)
)Engine=InnoDB;


CREATE EVENT IF NOT EXISTS Deleter
    ON SCHEDULE
      EVERY 1 DAY
    COMMENT 'Delete Unused Tokens'
    DO
      DELETE FROM Token WHERE validite < NOW();


INSERT INTO Region VALUES
(null,'Diana'),
(null,'Sava'),
(null,'Itasy'),
(null,'Analamanga'),
(null,'Vakinankaratra'),
(null,'Bongolava'),
(null,'Sofia'),
(null,'Boeny'),
(null,'Betsiboka'),
(null,'Melaky'),
(null,'Alaotra-Mangoro'),
(null,'Atsinanana'),
(null,'Analanjirofo'),
(null,'Amorony Mania'),
(null,'Haute Matsiatra'),
(null,'Vatovavy'),
(null,'Fitovinany'),
(null,'Atsimo-Atsinanana'),
(null,'Ihorombe'),
(null,'Menabe'),
(null,'Atsimo-Andrefana'),
(null,'Androy'),
(null,'Anosy');

INSERT INTO ChefRegion VALUES
(null,1,'Jean Donnes',SHA1('azerty')),
(null,2,'Justin Tokely',SHA1('azerty')),
(null,3,'Solofonirina Maherizo Andriamanana',SHA1('azerty')),
(null,4,'Hery Rasoamaromaka',SHA1('azerty')),
(null,5,'Fanevantsoa Malala',SHA1('azerty')),
(null,6,'Rasoharinana Melaky',SHA1('azerty')),
(null,7,'René de Rolland Urbain Lylison',SHA1('azerty')),
(null,8,'Justin Mahatsiaro',SHA1('azerty')),
(null,9,'Rakotomanjo Nahazo',SHA1('azerty')),
(null,10,'Davidson Maeva',SHA1('azerty')),
(null,11,'Richard Ramandehamanana',SHA1('azerty')),
(null,12,'Toky Mamonjy',SHA1('azerty')),
(null,13,'Safidy Hajaina',SHA1('azerty')),
(null,14,'Lova Narivelo Razafindrafito',SHA1('azerty')),
(null,15,'Herisoa Mickael',SHA1('azerty')),
(null,16,'Tafitasoa Nomena',SHA1('azerty')),
(null,17,'Justin Mahafaky',SHA1('azerty')),
(null,18,'Malala Safidy',SHA1('azerty')),
(null,19,'Mon Wai Tune Randriantsoa',SHA1('azerty')),
(null,20,'Tovondrainy Noëlson Edally Andriantsitohaina',SHA1('azerty')),
(null,21,'Lahimaro Tsimandilatse Soja',SHA1('azerty')),
(null,22,'Jerry Hatrefindrazana',SHA1('azerty'));

INSERT INTO Utilisateur VALUES 
(NULL,'user',SHA1('password'),'2021-12-27');

INSERT INTO Category VALUES
	(NULL, 'Batiments', 'assets/img/batiments.png'),
	(NULL, 'Transports', 'assets/img/transport.png'),
	(NULL, 'Propreté et déchets', 'assets/img/dechets.png'),
	(NULL, 'Signalisation', 'assets/img/signalisation.png'),
	(NULL, 'Voirie', 'assets/img/routes.png'),
	(NULL, 'Eau', 'assets/img/eau.png'),
	(NULL, 'Espaces verts', 'assets/img/vert.png'),
	(NULL, 'Eclairage', 'assets/img/Ampoule.png'),
	(NULL,'Chantier', 'assets/img/logo-bonhomme.png');

INSERT INTO Admin VALUES 
	(NULL,SHA1('azerty'));

----------------------new table------------------
CREATE TABLE TokenChefRegion(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    idChefRegion INT,
    valeur VARCHAR(100),
    validite DATE,
    FOREIGN KEY(idChefRegion) REFERENCES ChefRegion(id)
)ENGINE=InnoDB;
--------------------------------------------------