CREATE TABLE Ristorante (
	ID_Ristorante INTEGER AUTO_INCREMENT,
    NameRistorante VARCHAR(120) NOT NULL,
    AddressRistorante VARCHAR(120) NOT NULL,
    Phone VARCHAR(14) NOT NULL,
    Email VARCHAR(254) NOT NULL,

    PRIMARY KEY (ID_Ristorante)
);

CREATE TABLE Utente (
	ID_Utente INTEGER AUTO_INCREMENT,
    Nome VARCHAR(120) NOT NULL,
    Cognome VARCHAR(120) NOT NULL,
    Type_User VARCHAR(120) NOT NULL,
    Email VARCHAR(254) NOT NULL,
    ID_Ristorante INTEGER NOT NULL,

    PRIMARY KEY (ID_UTENTE),
    CONSTRAINT FK_Ristorante FOREIGN KEY (ID_Ristorante) REFERENCES Ristorante(ID_Ristorante) ON DELETE CASCADE,
    CONSTRAINT CHK_TypeUser CHECK (Type_User IN ('Amministratore','Supervisore','Chef','Cameriere'))
);

CREATE TABLE Tavolo (
	ID_Tavolo INTEGER AUTO_INCREMENT,
    Numero_tavolo VARCHAR(120) NOT NULL UNIQUE,
    ID_Ristorante INTEGER NOT NULL,
    State_Tavolo TINYINT NOT NULL DEFAULT 0,

    PRIMARY KEY (ID_Tavolo),
    CONSTRAINT FK_RistoranteTavolo FOREIGN KEY (ID_Ristorante) REFERENCES Ristorante(ID_Ristorante) ON DELETE CASCADE
);

CREATE TABLE Ordine (
	ID_Ordine INTEGER AUTO_INCREMENT,
    ID_Tavolo INTEGER NOT NULL,
    Prezzo_Totale FLOAT(5,2) NOT NULL DEFAULT 0.00,
    
    PRIMARY KEY (ID_Ordine),
    CONSTRAINT FK_TavoloOrdine FOREIGN KEY (ID_Tavolo) REFERENCES Tavolo(ID_Tavolo)
);

CREATE TABLE CategoriaMenu (
	ID_CategoryMenu INTEGER AUTO_INCREMENT,
	ID_Ristorante INTEGER NOT NULL,
    NameCategory VARCHAR(120) NOT NULL,
    PRIMARY KEY (ID_CategoryMenu),
    CONSTRAINT FK_RistoranteCategoriaMenu FOREIGN KEY (ID_Ristorante) REFERENCES Ristorante(ID_Ristorante) ON DELETE CASCADE
);

CREATE TABLE Prodotto (
	ID_Prodotto INTEGER AUTO_INCREMENT,
	ID_CategoryMenu INTEGER NOT NULL,
    NameProdotto VARCHAR(120) NOT NULL,
    PriceProdotto FLOAT(5,2) NOT NULL DEFAULT 0.00,
    Description VARCHAR(500),
    Allergeni VARCHAR(500),
    isSendToKitchen TINYINT DEFAULT 0,
    PhotoURL VARCHAR(300) ,
    
    PRIMARY KEY (ID_Prodotto),
    CONSTRAINT FK_CategoriaMenuProdotto FOREIGN KEY (ID_CategoryMenu) REFERENCES CategoriaMenu(ID_CategoryMenu) ON DELETE CASCADE
);

CREATE TABLE Ingrediente (
	ID_Ingrediente INTEGER AUTO_INCREMENT,
	ID_Ristorante INTEGER NOT NULL,
    NameIngrediente VARCHAR(120) NOT NULL,
    Misura FLOAT(5,2) NOT NULL DEFAULT 0.00,
    UnitaMisura VARCHAR(2),
    PRIMARY KEY (ID_Ingrediente),
    CONSTRAINT FK_RistoranteIngrediente FOREIGN KEY (ID_Ristorante) REFERENCES Ristorante(ID_Ristorante) ON DELETE CASCADE,
    CONSTRAINT CHK_UnitaMisura CHECK (UnitaMisura IN ('kg','gr','mg','L','cl','ml'))
);

CREATE TABLE Ricettario (
	ID_Prodotto INTEGER NOT NULL ,
	ID_Ingrediente INTEGER NOT NULL ,
    PRIMARY KEY (ID_Prodotto,ID_Ingrediente),
    CONSTRAINT FK_ProdottoIngrediente FOREIGN KEY (ID_Prodotto) REFERENCES Prodotto(ID_Prodotto) ON DELETE CASCADE,
    CONSTRAINT FK_IngredienteProdotto FOREIGN KEY (ID_Ingrediente) REFERENCES Ingrediente(ID_Ingrediente) ON DELETE CASCADE
);