

CREATE TABLE Users(

id INT PRIMARY KEY Users(id),
name VARCHAR(255),
type VARCHAR(255),
phone INT (12),
FOREIGN KEY (address_id) REFERENCES Addresses(id)

);
CREATE TABLE orders (
id INT,
status_id VARCHAR(255),
user_id INT,
address_from_id INT FOREIGN KEY REFERENCES Addresses(id),r
address_to_id INT FOREIGN KEY REFERENCES Addresses(id),
distance int,
price int,
FOREIGN KEY (user_id) REFERENCES Users(id)
   );
CREATE TABLE Addresses(

id INT PRIMARY KEY,
country VARCHAR(255),
city VARCHAR(255),
street VARCHAR(255),
num VARCHAR(255),
);

CREATE TABLE Car(

id INT PRIMARY KEY,
model VARCHAR(255),
color VARCHAR(255),

)

CREATE TABLE Driver(

id INT FOREIGN KEY REFERENCES Users(id),
car_id INT FOREIGN KEY REFERENCES Car(id)

);

CREATE TABLE Passanger(
id INT FOREIGN KEY Users
order_id INT FOREIGN KEY orders (id),
address_id VARCHAR(255) FOREIGN KEY REFERENCES Addresses(id)
);
