CREATE DATABASE `pmb_sendmoney` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE DATABASE `pmb_sendmoney_test` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

----------------------------------------------------------------------------------------------------------------------------------------------

USE pmb_sendmoney;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `buddy` (
  `id` bigint NOT NULL,
  `account_non_expired` bit(1) NOT NULL,
  `account_non_locked` bit(1) NOT NULL,
  `credentials_non_expired` bit(1) NOT NULL,
  `email` varchar(70) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `first_name` varchar(35) DEFAULT NULL,
  `last_name` varchar(35) DEFAULT NULL,
  `password` binary(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `role` (
  `id` bigint NOT NULL,
  `name` varchar(20) DEFAULT NULL,
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
  `iban` varchar(27) DEFAULT NULL,
  `swift` varchar(11) DEFAULT NULL,
  `owner` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbotx7rau23x60bnc5c700sj8e` (`owner`),
  CONSTRAINT `FKbotx7rau23x60bnc5c700sj8e` FOREIGN KEY (`owner`) REFERENCES `buddy` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `pmb_account` (
  `id` bigint NOT NULL,
  `account_balance` decimal(7,2) DEFAULT NULL,
  `pmb_account_number` varchar(10) DEFAULT NULL,
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
  `transaction` varchar(20) DEFAULT NULL,
  `description` varchar(30) DEFAULT NULL,
  `transaction_date` datetime(6) DEFAULT NULL,
  `value_date` datetime(6) DEFAULT NULL,
  `pmb_account_beneficiary_id` bigint DEFAULT NULL,
  `pmb_account_sender_id` bigint DEFAULT NULL,
  `bank_account_beneficiary_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhgeq7y21ikt914s80edpq7vd8` (`pmb_account_beneficiary_id`),
  KEY `FK8h5ra8wg3d4g71scf9jhv71cr` (`pmb_account_sender_id`),
  KEY `FKto2o27nq0oep43ilcjy18db84` (`bank_account_beneficiary_id`),
  CONSTRAINT `FK8h5ra8wg3d4g71scf9jhv71cr` FOREIGN KEY (`pmb_account_sender_id`) REFERENCES `pmb_account` (`id`),
  CONSTRAINT `FKhgeq7y21ikt914s80edpq7vd8` FOREIGN KEY (`pmb_account_beneficiary_id`) REFERENCES `pmb_account` (`id`),
  CONSTRAINT `FKto2o27nq0oep43ilcjy18db84` FOREIGN KEY (`bank_account_beneficiary_id`) REFERENCES `bank_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


----------------------------------------------------------------------------------------------------------------------------------------

-- initialize hibernate_sequence
insert into hibernate_sequence VALUE (1);
insert into hibernate_sequence VALUE (1);
insert into hibernate_sequence VALUE (1);
insert into hibernate_sequence VALUE (1);
insert into hibernate_sequence VALUE (1);


-- add a user account for author:
INSERT INTO buddy VALUES(10,1,1,1,"Thierry.Schreiner@hotmail.fr",1,"Thierry","SCHREINER","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");
-- add a user account for the SendMoney application:
INSERT INTO buddy VALUES(36,1,1,1,"send.money@pmb.com",1,"SendMoney","PMB","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");


-- add roles:
INSERT INTO role VALUES(100, "USER");
INSERT INTO role VALUES(91, "ADMIN");


-- set roles:
INSERT INTO buddy_role (buddy_id, role_id)
VALUES 
(10,91),	-- admin account for "Thierry.Schreiner@hotmail.fr"
(36,91); -- admin account for "send.money@pmb.com" (the user account of SendMoney application)


-- add the references of the SendMoney application bank account
INSERT INTO bank_account (id,iban,swift,owner)
VALUES (37,"FR333-PMB-BANK-ACCOUNT--PMB","CRLYFRPPXXX",36);	


-- add the PMB account of SendMoney application
INSERT INTO pmb_account (id,account_balance,pmb_account_number,owner)
VALUES (38,2595.50,"PMB--APPLI",36); 


-- set hibernate_sequence value
UPDATE hibernate_sequence
SET next_val = 101;

-- ------------------------------------------------------------------------------------------------

USE pmb_sendmoney_test;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `buddy` (
  `id` bigint NOT NULL,
  `account_non_expired` bit(1) NOT NULL,
  `account_non_locked` bit(1) NOT NULL,
  `credentials_non_expired` bit(1) NOT NULL,
  `email` varchar(70) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `first_name` varchar(35) DEFAULT NULL,
  `last_name` varchar(35) DEFAULT NULL,
  `password` binary(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `role` (
  `id` bigint NOT NULL,
  `name` varchar(20) DEFAULT NULL,
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
  `iban` varchar(27) DEFAULT NULL,
  `swift` varchar(11) DEFAULT NULL,
  `owner` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbotx7rau23x60bnc5c700sj8e` (`owner`),
  CONSTRAINT `FKbotx7rau23x60bnc5c700sj8e` FOREIGN KEY (`owner`) REFERENCES `buddy` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `pmb_account` (
  `id` bigint NOT NULL,
  `account_balance` decimal(7,2) DEFAULT NULL,
  `pmb_account_number` varchar(10) DEFAULT NULL,
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
  `transaction` varchar(20) DEFAULT NULL,
  `description` varchar(30) DEFAULT NULL,
  `transaction_date` datetime(6) DEFAULT NULL,
  `value_date` datetime(6) DEFAULT NULL,
  `pmb_account_beneficiary_id` bigint DEFAULT NULL,
  `pmb_account_sender_id` bigint DEFAULT NULL,
  `bank_account_beneficiary_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhgeq7y21ikt914s80edpq7vd8` (`pmb_account_beneficiary_id`),
  KEY `FK8h5ra8wg3d4g71scf9jhv71cr` (`pmb_account_sender_id`),
  KEY `FKto2o27nq0oep43ilcjy18db84` (`bank_account_beneficiary_id`),
  CONSTRAINT `FK8h5ra8wg3d4g71scf9jhv71cr` FOREIGN KEY (`pmb_account_sender_id`) REFERENCES `pmb_account` (`id`),
  CONSTRAINT `FKhgeq7y21ikt914s80edpq7vd8` FOREIGN KEY (`pmb_account_beneficiary_id`) REFERENCES `pmb_account` (`id`),
  CONSTRAINT `FKto2o27nq0oep43ilcjy18db84` FOREIGN KEY (`bank_account_beneficiary_id`) REFERENCES `bank_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
----------------------------------------------------------------------------------------------------------------------------------------

-- initialize hibernate_sequence
insert into hibernate_sequence VALUE (1);
insert into hibernate_sequence VALUE (1);
insert into hibernate_sequence VALUE (1);
insert into hibernate_sequence VALUE (1);
insert into hibernate_sequence VALUE (1);


-- add a user account for author:
INSERT INTO buddy VALUES(10,1,1,1,"Thierry.Schreiner@hotmail.fr",1,"Thierry","SCHREINER","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");
-- add a user account for the SendMoney application:
INSERT INTO buddy VALUES(36,1,1,1,"send.money@pmb.com",1,"SendMoney","PMB","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");


-- add roles:
INSERT INTO role VALUES(100, "USER");
INSERT INTO role VALUES(91, "ADMIN");


-- set roles:
INSERT INTO buddy_role (buddy_id, role_id)
VALUES 
(10,91),	-- admin account for "Thierry.Schreiner@hotmail.fr"
(36,91); -- admin account for "send.money@pmb.com" (the user account of SendMoney application)


-- add the references of the SendMoney application bank account
INSERT INTO bank_account (id,iban,swift,owner)
VALUES (37,"FR333-PMB-BANK-ACCOUNT--PMB","CRLYFRPPXXX",36);	


-- add the PMB account of SendMoney application
INSERT INTO pmb_account (id,account_balance,pmb_account_number,owner)
VALUES (38,2595.50,"PMB--APPLI",36); 


-- set hibernate_sequence value
UPDATE hibernate_sequence
SET next_val = 101;

-- ------------------------------------------------------------------------------------------------
COMMIT;
