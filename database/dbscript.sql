DROP DATABASE IF EXISTS finalproject;
CREATE DATABASE finalproject;

USE finalproject;
DROP TABLE IF EXISTS ListRows;
DROP TABLE IF EXISTS Lists;
DROP TABLE IF EXISTS Logins;
DROP TABLE IF EXISTS Items;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS ItemTypes;

CREATE TABLE Users (
user_id INT AUTO_INCREMENT PRIMARY KEY,
name NVARCHAR(128)
);

CREATE TABLE Logins (
login_id INT AUTO_INCREMENT PRIMARY KEY,
user_id INT,
username NVARCHAR(128) UNIQUE,
password TINYTEXT,
current_token nchar(40),
FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE ItemTypes (
type_id INT AUTO_INCREMENT PRIMARY KEY,
type_name NVARCHAR(128),
type_description NVARCHAR(128)
);

CREATE TABLE Items (
item_id INT AUTO_INCREMENT PRIMARY KEY,
item_name NVARCHAR(128),
item_type INT,
FOREIGN KEY (item_type) REFERENCES ItemTypes(type_id)
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

SELECT * FROM items;
SELECT * FROM itemtypes;
SELECT * FROM listrows;
SELECT * FROM lists;
SELECT * FROM logins;
SELECT * FROM users;


INSERT INTO users VALUES
("Yefri"),
("Mark"),
("Nikos"),
("Ignas");

INSERT INTO logins (user_id, username, password, current_token) VALUES 
(1, "Yefri51",	"$2a$10$YgplJjk43EBWZGNfxKN9S.JG3iEDA/cqxO32.a/HMJk8/uV.z9nJq",	"BqwkosRkCpavC3cvgUpweXLsYHUWVC1pi1yP6zrn"),
(2, "Mark98", "$2a$10$bRDFX9dqDrokQ2vI578jseOZlHBzsaSNja4p9fxGuZesVPkRejjOi", "8pAmZJ5hFskXq2IMyURdHxWGBCTe8xtcYzyJD9Lx"),
(3, "NikosPapa8", "$2a$10$ien4RG0E3s2/Yi3ChBjjH.JkwpM/G9qp6RBbqCAs3fmZsModbcK22", "FIRDyXlKPFTVqFCT5XpylnJoTsG5iMeae34q8y3b");


INSERT INTO ItemTypes (type_name, type_description) VALUES
("Application", "Executable Application that has user interaction"),
("Package", "Installable tools for the user envrionment"),
("Component", "Add-ons or smaller parts of Applications or Packages");