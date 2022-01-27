DROP DATABASE IF EXISTS finalproject;
CREATE DATABASE finalproject;

USE finalproject;

DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS ItemTypes;
DROP TABLE IF EXISTS Logins;
DROP TABLE IF EXISTS Items;
DROP TABLE IF EXISTS Lists;
DROP TABLE IF EXISTS ListRows;

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

CREATE TABLE Lists (
list_id INT AUTO_INCREMENT PRIMARY KEY,
list_title nvarchar(255) NOT NULL,
list_description nvarchar(255) NOT NULL,
belongs_to_user INT NOT NULL,
FOREIGN KEY (belongs_to_user) REFERENCES Users(user_id)
);


CREATE TABLE ListRows (
row_id INT AUTO_INCREMENT PRIMARY KEY,
list_id INT NOT NULL,
item_id INT NOT NULL,
FOREIGN KEY (list_id) REFERENCES Lists(list_id),
FOREIGN KEY (item_id) REFERENCES Items(item_id)
);
