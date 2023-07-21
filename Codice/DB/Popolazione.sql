/* Ristorante */
INSERT INTO `Ristorante`( `NameRistorante`, `AddressRistorante`, `Phone`, `Email`, `ID_Ristorante`) 
VALUES ('Porzio','Via Medina 88','0818556787','porzio@porzio.it',1);

/* Utente */
INSERT INTO `Utente`( `Nome`, `Cognome`, `Type_User`, `Email`,`Password`,`Token`, `ID_Ristorante`) 
VALUES ('Chris','Doarme','Amministratore','padrepio@gmail.com','Password123','TOKEN_STRING',1);
INSERT INTO `Utente`( `Nome`, `Cognome`, `Type_User`,  `Email`,`Password`,`Token`, `ID_Ristorante`) 
VALUES ('Piera','DiFusco','Supervisore','padrepio@gmail.com','Password123','TOKEN_STRING',1);
INSERT INTO `Utente`( `Nome`, `Cognome`, `Type_User`,  `Email`,`Password`,`Token`, `ID_Ristorante`) 
VALUES ('Daniele','Caiazo','Cameriere','padrepio@gmail.com','Password123','TOKEN_STRING',1);
INSERT INTO `Utente`( `Nome`, `Cognome`, `Type_User`,  `Email`,`Password`,`Token`, `ID_Ristorante`) 
VALUES ('Chiara','Sotira','Chef','padrepio@gmail.com','Password123','TOKEN_STRING',1);

/* Tavolo */
INSERT INTO Tavolo(`Numero_tavolo`, `ID_Ristorante`) 
VALUES (1,1);
INSERT INTO Tavolo(`Numero_tavolo`, `ID_Ristorante`) 
VALUES (2,1);

/* Ordine */
INSERT INTO `Ordine`( `ID_Tavolo`) 
VALUES (1);
UPDATE `Tavolo`
SET `State_Tavolo`= 1 WHERE (ID_Tavolo <=> 1);

INSERT INTO `Ordine`( `ID_Tavolo`) 
VALUES (2);
UPDATE `Tavolo`
SET `State_Tavolo`= 1 WHERE (ID_Tavolo <=> 2);

/* CategoriaMenu */
INSERT INTO `CategoriaMenu`( `ID_Ristorante`, `NameCategory`) 
VALUES (1,'Pizze');
INSERT INTO `CategoriaMenu`( `ID_Ristorante`, `NameCategory`) 
VALUES (1,'Bibite');

/* Prodotto */
INSERT INTO `Prodotto`(`ID_CategoryMenu`, `NameProdotto`, `PriceProdotto`, `Description`, `isSendToKitchen`) 
VALUES (1,'Margherita',3.5,'Pizza Classica con Pomodori e Mozzarella di Bufala',1);
INSERT INTO `Prodotto`(`ID_CategoryMenu`, `NameProdotto`, `PriceProdotto`, `Description`, `isSendToKitchen`) 
VALUES (2,'CocaCola',2.50,'bibita Coca cola',0);

/* Ingrediente */
INSERT INTO `Ingrediente`(`ID_Ristorante`, `NameIngrediente`, `Misura`, `UnitaMisura`) 
VALUES (1,'Farina',500.00,'gr');
INSERT INTO `Ingrediente`(`ID_Ristorante`, `NameIngrediente`, `Misura`, `UnitaMisura`) 
VALUES (1,'CocaCola',33.00,'cl');

/*Ricettario*/
INSERT INTO `Ricettario`(`ID_Prodotto`, `ID_Ingrediente`) 
VALUES (1,1);
INSERT INTO `Ricettario`(`ID_Prodotto`, `ID_Ingrediente`) 
VALUES (2,2);