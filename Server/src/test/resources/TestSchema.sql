

use TaxiProjectTest;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE Users(

user_id INT AUTO_INCREMENT,
phone VARCHAR (14) NOT NULL UNIQUE,
pass VARCHAR (30) NOT NULL,
name VARCHAR(30) NOT NULL,
type VARCHAR(15) NOT NULL,
PRIMARY KEY (user_id)
);

CREATE TABLE Cars(

car_id INT AUTO_INCREMENT,
number VARCHAR(8) NOT NULL UNIQUE,
model VARCHAR(30) NOT NULL,
color VARCHAR(30) NOT NULL,
PRIMARY KEY (car_id)
);

CREATE TABLE Drivers(

user_id INT NOT NULL,
car_id INT NOT NULL,
FOREIGN KEY (car_id) REFERENCES Cars(car_id),
FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE Addresses(

address_id INT AUTO_INCREMENT,
country VARCHAR(30) DEFAULT 'Ukraine',
city VARCHAR(30) DEFAULT 'Kiev',
street VARCHAR(80),
num VARCHAR(6),
PRIMARY KEY (address_id)
);

CREATE TABLE Passengers(

user_id INT NOT NULL,
address_id INT NOT NULL,
FOREIGN KEY (user_id) REFERENCES Users(user_id),
FOREIGN KEY (address_id) REFERENCES Addresses(address_id)
);

CREATE TABLE Statuses(

status_id INT AUTO_INCREMENT,
name VARCHAR(15),
PRIMARY KEY (status_id)
);

CREATE TABLE Orders(

order_id INT AUTO_INCREMENT,
from_address_id INT NOT NULL,
to_address_id INT NOT NULL,
userPhone VARCHAR(14) UNIQUE NOT NULL,
userName VARCHAR(30),
driverPhone VARCHAR(14),
price DECIMAL(5,2),
distance DECIMAL(7,1),
createTime TIMESTAMP,
status_id INT NOT NULL,
PRIMARY KEY (order_id),
FOREIGN KEY (from_address_id) REFERENCES Addresses(address_id),
FOREIGN KEY (to_address_id) REFERENCES Addresses(address_id),
FOREIGN KEY (status_id) REFERENCES Statuses(status_id)
);

SET FOREIGN_KEY_CHECKS =1;

INSERT INTO Statuses (name) VALUES ('NEW');
INSERT INTO Statuses (name) VALUES ('DONE');
INSERT INTO Statuses (name) VALUES ('IN_PROGRESS');
