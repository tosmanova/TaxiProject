CREATE TABLE Users(

phone VARCHAR,
pass VARCHAR,
name VARCHAR,
car VARCHAR,
homeAdress VARCHAR

PRIMARY KEY Users(phone)
);

CREATE TABLE Oreders(

address_from VARCHAR,
address_to VARCHAR,
phone VARCHAR,
userName VARCHAR,
driverPhone VARCHAR,
price VARCHAR,
distance VARCHAR,
createTime DATETIME,
orderStatus VARCHAR

FOREIGN KEY (phone) REFERENCES Users(phone)

);


