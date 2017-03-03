create table Customers(
    code INT(10) AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20),
    surname VARCHAR(20),
    cf CHAR(16),
    piva CHAR(16),
    telephone VARCHAR(15),
    address VARCHAR(50),
    document VARCHAR(20),
    documentRelease DATE,
    documentExpiration DATE,
    documentEntity VARCHAR(50),
    cap VARCHAR(10),
    city VARCHAR(30),
    district VARCHAR(30)
);

create table Invoices(
    code INT(10) AUTO_INCREMENT PRIMARY KEY,
    progressive INT(10) default null,
    invoiceDate DATE,
    customer INT(10),
    FOREIGN KEY (customer) REFERENCES Customers(code)
);

create table DeliveryDocuments(
    code INT(10) AUTO_INCREMENT PRIMARY KEY,
    progressive INT(10),
    customer INT(10),
    documentDate DATE,
    FOREIGN KEY (customer) REFERENCES Customers(code)
);

create table Products(
    code INT(10) AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(40),
    description VARCHAR(200),
    qta INT(2),
    price FLOAT(7,2),
    commission INT(3),
    invoice INT(9) default null,
    deliveryDocument INT(10) default null,
    FOREIGN KEY (deliveryDocument) REFERENCES DeliveryDocuments(code) ON DELETE CASCADE,
    FOREIGN KEY (invoice) REFERENCES Invoices(code) ON DELETE SET NULL
);
/*
causal:
PCV: Preso in conto vendita
CPM: Ceduto per mandato
RES: Reso
VAR: Variazione prezzo
*/
create table Movements(
    code INT(10) AUTO_INCREMENT PRIMARY KEY,
    progressive INT(10) DEFAULT -1,
    product INT(10),
    causal CHAR(3),
    description VARCHAR(200),
    operationDate DATE,
    loadVar FLOAT(7,2),
    price FLOAT(7,2),
    commission INT(3),
    FOREIGN KEY (product) REFERENCES Products(code) ON DELETE CASCADE
);

create table Registers(
    code INT(10) AUTO_INCREMENT PRIMARY KEY,
    month INT(2),
    year INT(4)
);

create table RegisterMovements(
    movementProgressive INT(10) PRIMARY KEY,
    registerCode INT(10),
    customerCode INT(10),
    customerSurname VARCHAR(20),
    customerName VARCHAR(20),
    customerAddress VARCHAR(50),
    customerCap VARCHAR(10),
    customerCity VARCHAR(30),
    customerDistrict VARCHAR(30),
    customerDocument VARCHAR(20),
    customerDocumentRelease DATE,
    movementDate DATE,
    productName VARCHAR(40),
    productQta INT(2),
    productPrice FLOAT(7,2),
    productCommission INT(3),
    movementDescription VARCHAR(200),
    movementLoadVar FLOAT(7,2),
    totalGoods FLOAT(10,2),
    FOREIGN KEY (registerCode) REFERENCES Registers(code) ON DELETE RESTRICT
);