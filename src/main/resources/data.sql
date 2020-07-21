CREATE DATABASE `pmb_sendmoney` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
CREATE DATABASE `pmb_sendmoney_test` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE pmb_sendmoney;

CREATE TABLE `buddy` (
  `id` bigint NOT NULL,
  `account_non_expired` bit(1) NOT NULL,
  `account_non_locked` bit(1) NOT NULL,
  `credentials_non_expired` bit(1) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` binary(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `role` (
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `buddy_role` (
  `buddy_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`buddy_id`,`role_id`),
  KEY `FK25x1sn5nnlckstcmkac6gpb6l` (`role_id`),
  CONSTRAINT `FK25x1sn5nnlckstcmkac6gpb6l` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK6fpq5usl7y5egl456ssg56jf7` FOREIGN KEY (`buddy_id`) REFERENCES `buddy` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `bank_account` (
  `id` bigint NOT NULL,
  `iban` varchar(255) DEFAULT NULL,
  `swift` varchar(255) DEFAULT NULL,
  `owner` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbotx7rau23x60bnc5c700sj8e` (`owner`),
  CONSTRAINT `FKbotx7rau23x60bnc5c700sj8e` FOREIGN KEY (`owner`) REFERENCES `buddy` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `pmb_account` (
  `id` bigint NOT NULL,
  `account_balance` decimal(7,2) DEFAULT NULL,
  `pmb_account_number` varchar(255) DEFAULT NULL,
  `owner` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK32tqsppbtk7mkv1mbd06ds8l0` (`owner`),
  CONSTRAINT `FK32tqsppbtk7mkv1mbd06ds8l0` FOREIGN KEY (`owner`) REFERENCES `buddy` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `connect` (
  `me_id` bigint NOT NULL,
  `beneficiary_id` bigint NOT NULL,
  PRIMARY KEY (`me_id`,`beneficiary_id`),
  KEY `FK4o4nn0q5c5f3l60qgd6avahg3` (`beneficiary_id`),
  CONSTRAINT `FK4o4nn0q5c5f3l60qgd6avahg3` FOREIGN KEY (`beneficiary_id`) REFERENCES `pmb_account` (`id`),
  CONSTRAINT `FKblb2raptt2bla9bwaw6vo0jbn` FOREIGN KEY (`me_id`) REFERENCES `pmb_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `transfer` (
  `id` bigint NOT NULL,
  `amount` decimal(5,2) NOT NULL,
  `fee` decimal(3,2) NOT NULL,
  `is_effective` bit(1) NOT NULL,
  `transaction` varchar(255) DEFAULT NULL,
  `transaction_date` datetime(6) DEFAULT NULL,
  `value_date` datetime(6) DEFAULT NULL,
  `pmb_account_beneficiary_id` bigint DEFAULT NULL,
  `pmb_account_sender_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhgeq7y21ikt914s80edpq7vd8` (`pmb_account_beneficiary_id`),
  KEY `FK8h5ra8wg3d4g71scf9jhv71cr` (`pmb_account_sender_id`),
  CONSTRAINT `FK8h5ra8wg3d4g71scf9jhv71cr` FOREIGN KEY (`pmb_account_sender_id`) REFERENCES `pmb_account` (`id`),
  CONSTRAINT `FKhgeq7y21ikt914s80edpq7vd8` FOREIGN KEY (`pmb_account_beneficiary_id`) REFERENCES `pmb_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

insert into hibernate_sequence VALUE (1);
insert into hibernate_sequence VALUE (1);
insert into hibernate_sequence VALUE (1);
insert into hibernate_sequence VALUE (1);
insert into hibernate_sequence VALUE (1);

INSERT INTO buddy VALUES(1,1,1,1,"Al.Pacino@Hollywood.com",1,"Al","PACINO","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");
INSERT INTO buddy VALUES(2,1,1,1,"Jean.Gabin@PatheCinema.fr",1,"Jean","GABIN","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");
INSERT INTO buddy VALUES(3,1,1,1,"Kevin.Spacey@Holliwood.com",1,"Kevin","SPACEY","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");
INSERT INTO buddy VALUES(4,1,1,1,"Brad.Pitt@Ocean11.cine",1,"Brad","PITT","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");
INSERT INTO buddy VALUES(5,1,1,1,"Angelina.Jolie@Ocean11.cine",1,"Angelina","JOLIE","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");
INSERT INTO buddy VALUES(6,1,1,1,"Dustin.Hoffman@Hollywood.com",1,"Dustin","HOFFMANN","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");
INSERT INTO buddy VALUES(7,1,1,1,"Bebel@Belmondo.fr",1,"Jean-Paul","BELMONDO","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");
INSERT INTO buddy VALUES(8,1,1,1,"Daniel.Craig@JamesBond.fr",1,"Daniel","CRAIG","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");
INSERT INTO buddy VALUES(9,1,1,1,"E.T@ET.com",1,"E","T","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");
INSERT INTO buddy VALUES(10,1,1,1,"Thierry.Schreiner@hotmail.fr",1,"Thierry","SCHREINER","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");

-- and a user account for the SendMoney application:
INSERT INTO buddy VALUES(36,1,1,1,"send.money@pmb.com",1,"SendMoney","PMB","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");


INSERT INTO role VALUES(789654, "USER");
INSERT INTO role VALUES(987123654, "ADMIN");


INSERT INTO buddy_role (buddy_id, role_id)
VALUES (2,789654),
(3,789654),
(4,789654),
(5,789654),
(6,789654),
(7,789654),
(8,789654),
(9,789654),
(10,987123654),	-- admin account for "Thierry.Schreiner@hotmail.fr"
(36,987123654); -- admin account for "send.money@pmb.com" (the user account of SendMoney application)


INSERT INTO bank_account (id,iban,swift,owner)
VALUES (11,"FR3330002005ALPACINO0000Z25","CRLYFRPPXXX",1),
(12,"FR3330002005JEANGABIN000Z25","CRLYFRPPXXX",2),
(13,"FR3330002005KEVINSPACEY0Z25","CRLYFRPPXXX",3),
(14,"FR3330002005BRADPITT0000Z25","CRLYFRPPXXX",4),
(15,"FR3330002005ANGELINAJOLIE25","CRLYFRPPXXX",5),
(16,"FR3330002005DUSTINHOFFMAN25","CRLYFRPPXXX",6),
(17,"FR3330002005JPBELMONDO00Z25","CRLYFRPPXXX",7),
(18,"FR3330002005DANIELCRAIG0Z25","CRLYFRPPXXX",8),
(19,"FR3330002005ET0000000000Z25","CRLYFRPPXXX",9),
(37,"FR333-PMB-BANK-ACCOUNT--PMB","CRLYFRPPXXX",36);	-- Application bank account ***************


INSERT INTO pmb_account (id,account_balance,pmb_account_number,owner)
VALUES (20,777.00,"PMB0000020",1),
(21,250.00,"PMB0000021",2),
(22,500.50,"PMB0000022",3),
(23,1000.00,"PMB0000023",4),
(24,260.00,"PMB0000024",5),
(25,300.00,"PMB0000025",6),
(26,2500.00,"PMB0000026",7),
(27,749.00,"PMB0000027",8),
(28,963.00,"PMB0000028",9),
(38,2595.50,"PMB--APPLI",36);	-- Application PMB account ***************

INSERT INTO connect VALUES(20, 22),
(20, 24),
(20, 27),
(23, 22),
(23, 25),
(23, 28);

-- (id, amount, fee, isEffective, transactionDate, valueDate, pmb-account-beneficiary, pmb-account-sender, transaction) 
INSERT INTO transfer VALUES(29, 350, 1.75, 1, 'Sending', '2020-07-02 23:21:17.858000', '2020-07-02 23:21:18.858000', 27, 20),
(30, 50, 0.25, 1, 'Sending', '2020-06-08 23:21:17.858000', '2020-06-08 23:21:18.858000', 24, 20),
(31, 80, 0.40, 1, 'Sending', '2020-06-12 08:21:18.858000', '2020-06-12 08:21:20.858000', 24, 20),
(32, 150, 0.75, 1, 'Sending', '2020-06-07 20:01:17.858000', '2020-06-07 20:01:18.858000', 22, 23),
(33, 250, 1.25, 1, 'Sending', '2020-06-12 23:21:57.858000', '2020-06-12 23:22:00.858000', 25, 23),
(34, 40, 0.20, 1, 'Sending', '2020-06-28 15:11:23.858000', '2020-06-28 15:11:24.858000', 22, 23),
(35, 25, 0.12, 1, 'Sending', '2020-07-02 03:51:27.858000', '2020-07-02 03:51:29.858000', 28, 23);

UPDATE hibernate_sequence
SET next_val = 101;

-- ------------------------------------------------------------------------------------------------

USE pmb_sendmoney_test;

CREATE TABLE `buddy` (
  `id` bigint NOT NULL,
  `account_non_expired` bit(1) NOT NULL,
  `account_non_locked` bit(1) NOT NULL,
  `credentials_non_expired` bit(1) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` binary(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `role` (
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `buddy_role` (
  `buddy_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`buddy_id`,`role_id`),
  KEY `FK25x1sn5nnlckstcmkac6gpb6l` (`role_id`),
  CONSTRAINT `FK25x1sn5nnlckstcmkac6gpb6l` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK6fpq5usl7y5egl456ssg56jf7` FOREIGN KEY (`buddy_id`) REFERENCES `buddy` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `bank_account` (
  `id` bigint NOT NULL,
  `iban` varchar(255) DEFAULT NULL,
  `swift` varchar(255) DEFAULT NULL,
  `owner` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbotx7rau23x60bnc5c700sj8e` (`owner`),
  CONSTRAINT `FKbotx7rau23x60bnc5c700sj8e` FOREIGN KEY (`owner`) REFERENCES `buddy` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `pmb_account` (
  `id` bigint NOT NULL,
  `account_balance` decimal(7,2) DEFAULT NULL,
  `pmb_account_number` varchar(255) DEFAULT NULL,
  `owner` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK32tqsppbtk7mkv1mbd06ds8l0` (`owner`),
  CONSTRAINT `FK32tqsppbtk7mkv1mbd06ds8l0` FOREIGN KEY (`owner`) REFERENCES `buddy` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `connect` (
  `me_id` bigint NOT NULL,
  `beneficiary_id` bigint NOT NULL,
  PRIMARY KEY (`me_id`,`beneficiary_id`),
  KEY `FK4o4nn0q5c5f3l60qgd6avahg3` (`beneficiary_id`),
  CONSTRAINT `FK4o4nn0q5c5f3l60qgd6avahg3` FOREIGN KEY (`beneficiary_id`) REFERENCES `pmb_account` (`id`),
  CONSTRAINT `FKblb2raptt2bla9bwaw6vo0jbn` FOREIGN KEY (`me_id`) REFERENCES `pmb_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `transfer` (
  `id` bigint NOT NULL,
  `amount` decimal(5,2) NOT NULL,
  `fee` decimal(3,2) NOT NULL,
  `is_effective` bit(1) NOT NULL,
  `transaction` varchar(255) DEFAULT NULL,
  `transaction_date` datetime(6) DEFAULT NULL,
  `value_date` datetime(6) DEFAULT NULL,
  `pmb_account_beneficiary_id` bigint DEFAULT NULL,
  `pmb_account_sender_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhgeq7y21ikt914s80edpq7vd8` (`pmb_account_beneficiary_id`),
  KEY `FK8h5ra8wg3d4g71scf9jhv71cr` (`pmb_account_sender_id`),
  CONSTRAINT `FK8h5ra8wg3d4g71scf9jhv71cr` FOREIGN KEY (`pmb_account_sender_id`) REFERENCES `pmb_account` (`id`),
  CONSTRAINT `FKhgeq7y21ikt914s80edpq7vd8` FOREIGN KEY (`pmb_account_beneficiary_id`) REFERENCES `pmb_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

insert into hibernate_sequence VALUE (1);
insert into hibernate_sequence VALUE (1);
insert into hibernate_sequence VALUE (1);
insert into hibernate_sequence VALUE (1);
insert into hibernate_sequence VALUE (1);

INSERT INTO buddy VALUES(1,1,1,1,"Al.Pacino@Hollywood.com",1,"Al","PACINO","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");
INSERT INTO buddy VALUES(2,1,1,1,"Jean.Gabin@PatheCinema.fr",1,"Jean","GABIN","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");
INSERT INTO buddy VALUES(3,1,1,1,"Kevin.Spacey@Holliwood.com",1,"Kevin","SPACEY","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");
INSERT INTO buddy VALUES(4,1,1,1,"Brad.Pitt@Ocean11.cine",1,"Brad","PITT","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");
INSERT INTO buddy VALUES(5,1,1,1,"Angelina.Jolie@Ocean11.cine",1,"Angelina","JOLIE","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");
INSERT INTO buddy VALUES(6,1,1,1,"Dustin.Hoffman@Hollywood.com",1,"Dustin","HOFFMANN","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");
INSERT INTO buddy VALUES(7,1,1,1,"Bebel@Belmondo.fr",1,"Jean-Paul","BELMONDO","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");
INSERT INTO buddy VALUES(8,1,1,1,"Daniel.Craig@JamesBond.fr",1,"Daniel","CRAIG","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");
INSERT INTO buddy VALUES(9,1,1,1,"E.T@ET.com",1,"E","T","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");
INSERT INTO buddy VALUES(10,1,1,1,"Thierry.Schreiner@hotmail.fr",1,"Thierry","SCHREINER","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");

-- and a user account for the SendMoney application:
INSERT INTO buddy VALUES(36,1,1,1,"send.money@pmb.com",1,"SendMoney","PMB","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");


INSERT INTO role VALUES(789654, "USER");
INSERT INTO role VALUES(987123654, "ADMIN");


INSERT INTO buddy_role (buddy_id, role_id)
VALUES (2,789654),
(3,789654),
(4,789654),
(5,789654),
(6,789654),
(7,789654),
(8,789654),
(9,789654),
(10,987123654),	-- admin account for "Thierry.Schreiner@hotmail.fr"
(36,987123654); -- admin account for "send.money@pmb.com" (the user account of SendMoney application)


INSERT INTO bank_account (id,iban,swift,owner)
VALUES (11,"FR3330002005ALPACINO0000Z25","CRLYFRPPXXX",1),
(12,"FR3330002005JEANGABIN000Z25","CRLYFRPPXXX",2),
(13,"FR3330002005KEVINSPACEY0Z25","CRLYFRPPXXX",3),
(14,"FR3330002005BRADPITT0000Z25","CRLYFRPPXXX",4),
(15,"FR3330002005ANGELINAJOLIE25","CRLYFRPPXXX",5),
(16,"FR3330002005DUSTINHOFFMAN25","CRLYFRPPXXX",6),
(17,"FR3330002005JPBELMONDO00Z25","CRLYFRPPXXX",7),
(18,"FR3330002005DANIELCRAIG0Z25","CRLYFRPPXXX",8),
(19,"FR3330002005ET0000000000Z25","CRLYFRPPXXX",9),
(37,"FR333-PMB-BANK-ACCOUNT--PMB","CRLYFRPPXXX",36);	-- Application bank account ***************


INSERT INTO pmb_account (id,account_balance,pmb_account_number,owner)
VALUES (20,777.00,"PMB0000020",1),
(21,250.00,"PMB0000021",2),
(22,500.50,"PMB0000022",3),
(23,1000.00,"PMB0000023",4),
(24,260.00,"PMB0000024",5),
(25,300.00,"PMB0000025",6),
(26,2500.00,"PMB0000026",7),
(27,749.00,"PMB0000027",8),
(28,963.00,"PMB0000028",9),
(38,2595.50,"PMB--APPLI",36);	-- Application PMB account ***************

INSERT INTO connect VALUES(20, 22),
(20, 24),
(20, 27),
(23, 22),
(23, 25),
(23, 28);

-- (id, amount, fee, isEffective, transactionDate, valueDate, pmb-account-beneficiary, pmb-account-sender, transaction) 
INSERT INTO transfer VALUES(29, 350, 1.75, 1, 'Sending', '2020-07-02 23:21:17.858000', '2020-07-02 23:21:18.858000', 27, 20),
(30, 50, 0.25, 1, 'Sending', '2020-06-08 23:21:17.858000', '2020-06-08 23:21:18.858000', 24, 20),
(31, 80, 0.40, 1, 'Sending', '2020-06-12 08:21:18.858000', '2020-06-12 08:21:20.858000', 24, 20),
(32, 150, 0.75, 1, 'Sending', '2020-06-07 20:01:17.858000', '2020-06-07 20:01:18.858000', 22, 23),
(33, 250, 1.25, 1, 'Sending', '2020-06-12 23:21:57.858000', '2020-06-12 23:22:00.858000', 25, 23),
(34, 40, 0.20, 1, 'Sending', '2020-06-28 15:11:23.858000', '2020-06-28 15:11:24.858000', 22, 23),
(35, 25, 0.12, 1, 'Sending', '2020-07-02 03:51:27.858000', '2020-07-02 03:51:29.858000', 28, 23);

UPDATE hibernate_sequence
SET next_val = 101;

COMMIT;
