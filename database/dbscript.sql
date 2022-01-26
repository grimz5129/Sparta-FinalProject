DROP DATABASE IF EXISTS finalproject;
CREATE DATABASE finalproject;

USE finalproject;

DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS ItemTypes;
DROP TABLE IF EXISTS Logins;
DROP TABLE IF EXISTS Items;

CREATE TABLE Users (
user_id INT AUTO_INCREMENT PRIMARY KEY,
name NVARCHAR(128)
);

CREATE TABLE ItemTypes (
type_id INT AUTO_INCREMENT PRIMARY KEY,
type_name NVARCHAR(128),
type_description NVARCHAR(128)
);

CREATE TABLE Logins (
login_id INT AUTO_INCREMENT PRIMARY KEY,
user_id INT,
username NVARCHAR(128) UNIQUE,
password TINYTEXT,
FOREIGN KEY (user_id) REFERENCES Users(user_id)
);


CREATE TABLE Items (
item_id INT AUTO_INCREMENT PRIMARY KEY,
item_name NVARCHAR(128),
item_type INT,
user_id INT,
FOREIGN KEY (item_type) REFERENCES ItemTypes(type_id),
FOREIGN KEY (user_id) REFERENCES Users(user_id)
);


