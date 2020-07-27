USE pmb_sendmoney;

INSERT INTO buddy
VALUES -- (id, account_non_expired, account_non_locked, credentials_non_expired, email, enabled, first_name, last_name, password)
(1,1,1,1,"Al.Pacino@Hollywood.com",1,"Al","PACINO","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i"),
(2,1,1,1,"Jean.Gabin@PatheCinema.fr",1,"Jean","GABIN","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i"),
(3,1,1,1,"Kevin.Spacey@Holliwood.com",1,"Kevin","SPACEY","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i"),
(4,1,1,1,"Brad.Pitt@Ocean11.cine",1,"Brad","PITT","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i"),
(5,1,1,1,"Angelina.Jolie@Ocean11.cine",1,"Angelina","JOLIE","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i"),
(6,1,1,1,"Dustin.Hoffman@Hollywood.com",1,"Dustin","HOFFMANN","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i"),
(7,1,1,1,"Bebel@Belmondo.fr",1,"Jean-Paul","BELMONDO","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i"),
(8,1,1,1,"Daniel.Craig@JamesBond.fr",1,"Daniel","CRAIG","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i"),
(9,1,1,1,"E.T@ET.com",1,"E","T","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");


INSERT INTO buddy_role
VALUES -- (buddy_id, role_id)
(2,100),
(3,100),
(4,100),
(5,100),
(6,100),
(7,100),
(8,100),
(9,100);


INSERT INTO bank_account
VALUES -- (id,iban,swift,owner)
(11,"FR3330002005ALPACINO0000Z25","CRLYFRPPXXX",1),
(12,"FR3330002005JEANGABIN000Z25","CRLYFRPPXXX",2),
(13,"FR3330002005KEVINSPACEY0Z25","CRLYFRPPXXX",3),
(14,"FR3330002005BRADPITT0000Z25","CRLYFRPPXXX",4),
(15,"FR3330002005ANGELINAJOLIE25","CRLYFRPPXXX",5),
(16,"FR3330002005DUSTINHOFFMAN25","CRLYFRPPXXX",6),
(17,"FR3330002005JPBELMONDO00Z25","CRLYFRPPXXX",7),
(18,"FR3330002005DANIELCRAIG0Z25","CRLYFRPPXXX",8),
(19,"FR3330002005ET0000000000Z25","CRLYFRPPXXX",9);


INSERT INTO pmb_account
VALUES -- (id,account_balance,pmb_account_number,owner)
(20,777.00,"PMB0000020",1),
(21,250.00,"PMB0000021",2),
(22,500.50,"PMB0000022",3),
(23,1000.00,"PMB0000023",4),
(24,260.00,"PMB0000024",5),
(25,300.00,"PMB0000025",6),
(26,2500.00,"PMB0000026",7),
(27,749.00,"PMB0000027",8),
(28,963.00,"PMB0000028",9);


INSERT INTO connect 
VALUES -- (me_id, beneficiary_id)
(20, 22),
(20, 24),
(20, 27),
(23, 22),
(23, 25),
(23, 28);

INSERT INTO transfer 
VALUES -- (id, amount, fee, isEffective, transaction, description, transactionDate, valueDate, pmb-account-beneficiary, pmb-account-sender,bank_account_beneficiary_id) 
(29, 350, 1.75, 1, 'Sending', '', '2020-07-02 23:21:17.858000', '2020-07-02 23:21:18.858000', 27, 20, null),
(30, 50, 0.25, 1, 'Sending', 'Gift', '2020-06-08 23:21:17.858000', '2020-06-08 23:21:18.858000', 24, 20, null),
(31, 80, 0.40, 1, 'Sending', 'Loan', '2020-06-12 08:21:18.858000', '2020-06-12 08:21:20.858000', 24, 20, null),
(32, 150, 0.75, 1, 'Sending', 'Charity initiative', '2020-06-07 20:01:17.858000', '2020-06-07 20:01:18.858000', 22, 23, null),
(33, 250, 1.25, 1, 'Sending', 'Restaurant with friends', '2020-06-12 23:21:57.858000', '2020-06-12 23:22:00.858000', 25, 23, null),
(34, 40, 0.20, 1, 'Sending', 'Charitable donation', '2020-06-28 15:11:23.858000', '2020-06-28 15:11:24.858000', 22, 23, null),
(35, 25, 0.12, 1, 'Sending', 'Gift', '2020-07-02 03:51:27.858000', '2020-07-02 03:51:29.858000', 28, 23, null),
(39, 80, 0.40, 1, 'Sending', 'Repayment of loan', '2020-07-02 03:51:27.858000', '2020-07-02 03:51:29.858000', 20, 24, null),
(40, 80, 0.00, 1, 'Deposit', 'Deposit', '2020-06-02 23:21:17.858000', '2020-06-02 23:21:18.858000', 20, 38, null),
(41, 20, 0.10, 1, 'Withdrawal', 'Withdrawal', '2020-06-02 23:21:17.858000', '2020-06-02 23:21:18.858000', 38, 20, 11),
(42, 20, 0.10, 1, 'Withdrawal', 'Withdrawal', '2020-07-02 23:21:17.858000', '2020-07-02 23:21:18.858000', 38, 22, 13);


UPDATE hibernate_sequence
SET next_val = 101;

-- ------------------------------------------------------------------------------------------------

USE pmb_sendmoney_test;

INSERT INTO buddy
VALUES -- (id, account_non_expired, account_non_locked, credentials_non_expired, email, enabled, first_name, last_name, password)
(1,1,1,1,"Al.Pacino@Hollywood.com",1,"Al","PACINO","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i"),
(2,1,1,1,"Jean.Gabin@PatheCinema.fr",1,"Jean","GABIN","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i"),
(3,1,1,1,"Kevin.Spacey@Holliwood.com",1,"Kevin","SPACEY","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i"),
(4,1,1,1,"Brad.Pitt@Ocean11.cine",1,"Brad","PITT","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i"),
(5,1,1,1,"Angelina.Jolie@Ocean11.cine",1,"Angelina","JOLIE","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i"),
(6,1,1,1,"Dustin.Hoffman@Hollywood.com",1,"Dustin","HOFFMANN","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i"),
(7,1,1,1,"Bebel@Belmondo.fr",1,"Jean-Paul","BELMONDO","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i"),
(8,1,1,1,"Daniel.Craig@JamesBond.fr",1,"Daniel","CRAIG","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i"),
(9,1,1,1,"E.T@ET.com",1,"E","T","$2a$10$hb87OnOgcnDz6V.GA2J.sOP2sCSbPaADxRh6PnA1HM3qUqV.Got8i");


INSERT INTO buddy_role
VALUES -- (buddy_id, role_id)
(2,100),
(3,100),
(4,100),
(5,100),
(6,100),
(7,100),
(8,100),
(9,100);


INSERT INTO bank_account
VALUES -- (id,iban,swift,owner)
(11,"FR3330002005ALPACINO0000Z25","CRLYFRPPXXX",1),
(12,"FR3330002005JEANGABIN000Z25","CRLYFRPPXXX",2),
(13,"FR3330002005KEVINSPACEY0Z25","CRLYFRPPXXX",3),
(14,"FR3330002005BRADPITT0000Z25","CRLYFRPPXXX",4),
(15,"FR3330002005ANGELINAJOLIE25","CRLYFRPPXXX",5),
(16,"FR3330002005DUSTINHOFFMAN25","CRLYFRPPXXX",6),
(17,"FR3330002005JPBELMONDO00Z25","CRLYFRPPXXX",7),
(18,"FR3330002005DANIELCRAIG0Z25","CRLYFRPPXXX",8),
(19,"FR3330002005ET0000000000Z25","CRLYFRPPXXX",9);


INSERT INTO pmb_account
VALUES -- (id,account_balance,pmb_account_number,owner)
(20,777.00,"PMB0000020",1),
(21,250.00,"PMB0000021",2),
(22,500.50,"PMB0000022",3),
(23,1000.00,"PMB0000023",4),
(24,260.00,"PMB0000024",5),
(25,300.00,"PMB0000025",6),
(26,2500.00,"PMB0000026",7),
(27,749.00,"PMB0000027",8),
(28,963.00,"PMB0000028",9);


INSERT INTO connect 
VALUES -- (me_id, beneficiary_id)
(20, 22),
(20, 24),
(20, 27),
(23, 22),
(23, 25),
(23, 28);

INSERT INTO transfer 
VALUES -- (id, amount, fee, isEffective, transaction, description, transactionDate, valueDate, pmb-account-beneficiary, pmb-account-sender,bank_account_beneficiary_id) 
(29, 350, 1.75, 1, 'Sending', '', '2020-07-02 23:21:17.858000', '2020-07-02 23:21:18.858000', 27, 20, null),
(30, 50, 0.25, 1, 'Sending', 'Gift', '2020-06-08 23:21:17.858000', '2020-06-08 23:21:18.858000', 24, 20, null),
(31, 80, 0.40, 1, 'Sending', 'Loan', '2020-06-12 08:21:18.858000', '2020-06-12 08:21:20.858000', 24, 20, null),
(32, 150, 0.75, 1, 'Sending', 'Charity initiative', '2020-06-07 20:01:17.858000', '2020-06-07 20:01:18.858000', 22, 23, null),
(33, 250, 1.25, 1, 'Sending', 'Restaurant with friends', '2020-06-12 23:21:57.858000', '2020-06-12 23:22:00.858000', 25, 23, null),
(34, 40, 0.20, 1, 'Sending', 'Charitable donation', '2020-06-28 15:11:23.858000', '2020-06-28 15:11:24.858000', 22, 23, null),
(35, 25, 0.12, 1, 'Sending', 'Gift', '2020-07-02 03:51:27.858000', '2020-07-02 03:51:29.858000', 28, 23, null),
(39, 80, 0.40, 1, 'Sending', 'Repayment of loan', '2020-07-02 03:51:27.858000', '2020-07-02 03:51:29.858000', 20, 24, null),
(40, 80, 0.00, 1, 'Deposit', 'Deposit', '2020-06-02 23:21:17.858000', '2020-06-02 23:21:18.858000', 20, 38, null),
(41, 20, 0.10, 1, 'Withdrawal', 'Withdrawal', '2020-06-02 23:21:17.858000', '2020-06-02 23:21:18.858000', 38, 20, 11),
(42, 20, 0.10, 1, 'Withdrawal', 'Withdrawal', '2020-07-02 23:21:17.858000', '2020-07-02 23:21:18.858000', 38, 22, 13);


UPDATE hibernate_sequence
SET next_val = 101;

-- ------------------------------------------------------------------------------------------------

COMMIT;
