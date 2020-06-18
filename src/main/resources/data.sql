USE pmb_sendmoney;

INSERT INTO buddy VALUES(1,1,1,1,"Al.Pacino@Hollywood.com",1,"Al","PACINO","$2a$10$Hthi7BOT2.Ez1nQkrf56.Ozia.pJRWNXnRtgf0gekoqn1qHxT0pIi");
INSERT INTO buddy VALUES(2,1,1,1,"Jean.Gabin@PatheCinema.fr",1,"Jean","GABIN","$2a$10$Hthi7BOT2.Ez1nQkrf56.Ozia.pJRWNXnRtgf0gekoqn1qHxT0pIi");
INSERT INTO buddy VALUES(3,1,1,1,"Kevin.Spacey@Holliwood.com",1,"Kevin","SPACEY","$2a$10$Hthi7BOT2.Ez1nQkrf56.Ozia.pJRWNXnRtgf0gekoqn1qHxT0pIi");
INSERT INTO buddy VALUES(4,1,1,1,"Brad.Pitt@Ocean11.cine",1,"Brad","PITT","$2a$10$Hthi7BOT2.Ez1nQkrf56.Ozia.pJRWNXnRtgf0gekoqn1qHxT0pIi");
INSERT INTO buddy VALUES(5,1,1,1,"Angelina.Jolie@Ocean11.cine",1,"Angelina","JOLIE","$2a$10$Hthi7BOT2.Ez1nQkrf56.Ozia.pJRWNXnRtgf0gekoqn1qHxT0pIi");
INSERT INTO buddy VALUES(6,1,1,1,"Dustin.Hoffman@Hollywood.com",1,"Dustin","HOFFMANN","$2a$10$Hthi7BOT2.Ez1nQkrf56.Ozia.pJRWNXnRtgf0gekoqn1qHxT0pIi");
INSERT INTO buddy VALUES(7,1,1,1,"Bebel@Belmondo.fr",1,"Jean-Paul","BELMONDO","$2a$10$Hthi7BOT2.Ez1nQkrf56.Ozia.pJRWNXnRtgf0gekoqn1qHxT0pIi");
INSERT INTO buddy VALUES(8,1,1,1,"Daniel.Craig@JamesBond.fr",1,"Daniel","CRAIG","$2a$10$Hthi7BOT2.Ez1nQkrf56.Ozia.pJRWNXnRtgf0gekoqn1qHxT0pIi");
INSERT INTO buddy VALUES(9,1,1,1,"E.T@ET.com",1,"E","T","$2a$10$Hthi7BOT2.Ez1nQkrf56.Ozia.pJRWNXnRtgf0gekoqn1qHxT0pIi");
INSERT INTO buddy VALUES(10,1,1,1,"Thierry.Schreiner@hotmail.fr",1,"Thierry","SCHREINER","$2a$10$Hthi7BOT2.Ez1nQkrf56.Ozia.pJRWNXnRtgf0gekoqn1qHxT0pIi");

INSERT INTO role VALUES(789654, "USER");
INSERT INTO role VALUES(987123654, "ADMIN");

INSERT INTO buddy_role (buddy_id, role_id)
VALUES (1,789654),
(2,789654),
(3,789654),
(4,789654),
(5,789654),
(6,789654),
(7,789654),
(8,789654),
(9,789654),
(10,987123654);

INSERT INTO bank_account (id,iban,swift,owner)
VALUES (11,"FR3330002005ALPACINO0000Z25","CRLYFRPPXXX",1),
(12,"FR3330002005JEANGABIN000Z25","CRLYFRPPXXX",2),
(13,"FR3330002005KEVINSPACEY0Z25","CRLYFRPPXXX",3),
(14,"FR3330002005BRADPITT0000Z25","CRLYFRPPXXX",4),
(15,"FR3330002005ANGELINAJOLIE25","CRLYFRPPXXX",5),
(16,"FR3330002005DUSTINHOFFMAN25","CRLYFRPPXXX",6),
(17,"FR3330002005JPBELMONDO00Z25","CRLYFRPPXXX",7),
(18,"FR3330002005DANIELCRAIG0Z25","CRLYFRPPXXX",8),
(19,"FR3330002005ET0000000000Z25","CRLYFRPPXXX",9);

INSERT INTO pmb_account (id,account_balance,pmb_account_number,owner)
VALUES (20,777,"PMB0000020",1),
(21,250,"PMB0000021",2),
(22,500,"PMB0000022",3),
(23,1000,"PMB0000023",4),
(24,260,"PMB0000024",5),
(25,300,"PMB0000025",6),
(26,2500,"PMB0000026",7),
(27,749,"PMB0000027",8),
(28,963,"PMB0000028",9);

update hibernate_sequence
set next_val = 29;
