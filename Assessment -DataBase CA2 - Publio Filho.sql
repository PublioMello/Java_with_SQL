CREATE DATABASE IF NOT EXISTS HouseRenting;

USE HouseRenting;

DROP TABLE IF EXISTS Rentals;
DROP TABLE IF EXISTS Owner;
DROP TABLE IF EXISTS Properties;
DROP TABLE IF EXISTS Client;

CREATE TABLE Client (
    client_num VARCHAR(255) PRIMARY KEY,
    client_name VARCHAR(255)
);

CREATE TABLE Owner (
    owner_no VARCHAR(255) PRIMARY KEY,
    owner_name VARCHAR(255)
);

CREATE TABLE Properties (
    property_no VARCHAR(255) PRIMARY KEY,
    property_address VARCHAR(255),
    monthly_rent DECIMAL(10, 2),
    owner_no VARCHAR(255),
    
    FOREIGN KEY (owner_no) REFERENCES Owner(owner_no)
);

CREATE TABLE Rentals (
    client_num VARCHAR(255),
    property_no VARCHAR(255),
    rent_start DATE,
    rent_finish DATE,
    
    FOREIGN KEY (client_num) REFERENCES Client(client_num),
    FOREIGN KEY (property_no) REFERENCES Properties(property_no)
);

INSERT INTO Client (client_num, client_name) VALUES
('CR-50', 'Jack Sanders'),
('CR-51', 'Jasmine Dixon'),
('CR-52', 'Maxwell Jackson'),
('CR-53', 'Samantha Thompson'),
('CR-54', 'Dylan Walker'),
('CR-55', 'Emma Baker'),
('CR-56', 'Dominic Martin'),
('CR-57', 'Julia Turner'),
('CR-58', 'Caleb Adams'),
('CR-59', 'Jenna Taylor'),
('CR-60', 'Mason Gray'),
('CR-61', 'Amelia Hayes'),
('CR-62', 'Justin Young'),
('CR-63', 'Allison Moore'),
('CR-64', 'Riley Carter'),
('CR-67', 'Jake Kelly'),
('CR-68', 'Ava Stewart');

INSERT INTO Owner (owner_no, owner_name) VALUES
('C040', 'Jonathan Kelly'),
('C041', 'Rafaela Soprano'),
('C042', 'Jake Jonez'),
('C043', 'Bruno Santiago'),
('C044', 'Joana Gonsalvez'),
('C045', 'Publio Mello'),
('C046', 'Rafael Keay'),
('C047', 'Isabela Santana'),
('C048', 'Marcelo Ferraz'),
('C049', 'Vanessa Brozzegine'),
('C050', 'Roseana Gomes'),
('C093', 'Aline Santos');

INSERT INTO Properties (property_no, property_address, monthly_rent, owner_no) VALUES
('PG1', '10 Maple St, Cork','420','C041'),
('PG2', '5 Oak St, Cork','380','C042'),
('PG3', '7 Elm St, Cork','280','C043'),
('PG4', '12 Pine St, Cork','350','C040'),
('PG5', '15 Cedar St, Galway','390','C044'),
('PG6', '9 Birch St, Galway','380','C045'),
('PG7', '20 Willow St, Galway','430','C046'),
('PG8', '3 Pineapple St, Waterford','500','C047'),
('PG9', '8 Coconut St, Waterford','510','C048'),
('PG10', '11 Banana St, Waterford','360','C049'),
('PG11', '4 Orange St, Belfast','380','C050'),
('PG16', '6 Lemon St, Belfast','450','C093'),
('PG36', '2 Lime St, Belfast','375','C093');

INSERT INTO Rentals ( client_num, property_no, rent_start, rent_finish) VALUES
('CR-50','PG1',  '2017-07-01', '2018-08-01'),
( 'CR-51','PG2', '2023-04-01', '2023-09-01'),
( 'CR-52','PG3', '2023-03-01', '2023-10-01'),
('CR-53','PG4',  '2017-07-01', '2018-08-01'),
( 'CR-54','PG4', '2016-09-01', '2017-06-01'),
( 'CR-55','PG5', '2017-10-01', '2018-12-01'),
( 'CR-56','PG6', '2019-11-01', '2020-08-10'),
( 'CR-57','PG7', '2017-07-01', '2018-08-01'),
( 'CR-58','PG7', '2018-09-01', '2019-08-01'),
( 'CR-59','PG8', '2018-09-01', '2019-09-01'),
('CR-60','PG8', '2019-10-01', '2020-09-01'),
( 'CR-61','PG9', '2016-09-01', '2017-06-10'),
( 'CR-62','PG9', '2017-09-01', '2018-06-10'),
('CR-63','PG10', '2017-10-10', '2018-12-01'),
('CR-64','PG10', '2018-12-10', '2019-03-01'),
('CR-67','PG11', '2019-11-01', '2020-08-10'),
( 'CR-68','PG11', '2020-11-01', '2021-08-10'),
( 'CR-62','PG16', '2018-09-01', '2019-09-01'),
( 'CR-61','PG16', '2019-11-01', '2020-08-10'),
( 'CR-59','PG36', '2017-10-10', '2018-08-01');

-- 1 Retrieve all clients along with their associated properties. 
SELECT 
    c.client_num,
    c.client_name,
    p.property_no,
    p.property_address
FROM 
    Client c
JOIN 
    Rentals r ON c.client_num = r.client_num
JOIN 
    Properties p ON r.property_no = p.property_no;

-- 2 List all properties rented out by all clients whose name begins with ‘D’.
SELECT 
    c.client_name, 
    p.property_no, 
    p.property_address
FROM 
    Client c
JOIN 
    Rentals r ON c.client_num = r.client_num
JOIN 
    Properties p ON r.property_no = p.property_no
WHERE 
    c.client_name LIKE 'D%'
ORDER BY 
    c.client_name, p.property_no;



--  3 List all clients who have properties rented out for a specific duration, from the date 2023-02-20 to 2023-10-20. 
SELECT DISTINCT 
    c.client_num,
    c.client_name
FROM 
    Client c
JOIN 
    Rentals r ON c.client_num = r.client_num
WHERE 
    (r.rent_start <= '2023-02-20' AND r.rent_finish >= '2023-10-20') OR
    (r.rent_start >= '2023-02-20' AND r.rent_start <= '2023-10-20') OR
    (r.rent_finish >= '2023-02-20' AND r.rent_finish <= '2023-10-20');

-- 4 Calculate the total monthly rent for each client.
SELECT
    c.client_num,
    c.client_name,
    SUM(monthly_rent) AS total_monthly_rent
FROM
    Client c
JOIN
    Rentals r ON c.client_num = r.client_num
JOIN
    Properties p ON r.property_no = p.property_no
GROUP BY
    c.client_num, c.client_name;

-- 5 Find the owner of a specific property. 
SELECT o.owner_no, o.owner_name
FROM Properties p
JOIN Owner o ON p.owner_no = o.owner_no
WHERE p.property_no = 'PG16';


 -- 6 Count the total number of properties owned by each owner.
SELECT o.owner_no, o.owner_name, COUNT(*) AS total_properties_owned
FROM Owner o
JOIN Properties p ON o.owner_no = p.owner_no
GROUP BY o.owner_no, o.owner_name;

-- 7 Identify Owners who own multiple properties
SELECT 
    o.owner_no,
    o.owner_name,
    COUNT(*) AS total_properties_owned
FROM 
    Owner o
JOIN 
    Properties p ON o.owner_no = p.owner_no
GROUP BY 
    o.owner_no,
    o.owner_name
HAVING 
    COUNT(*) > 1;

--  8 List all clients along with the total rent they pay annually, sorted in ascending order (i.e. lowest rent at the top).
SELECT c.client_num, c.client_name, SUM(p.monthly_rent * 12) AS total_rent_annually
FROM Client c
JOIN Rentals r ON c.client_num = r.client_num
JOIN Properties p ON r.property_no = p.property_no
GROUP BY c.client_num, c.client_name
ORDER BY total_rent_annually ASC;


 -- 9 Find the client who pays the highest monthly rent. 
SELECT c.client_num, c.client_name, MAX(p.monthly_rent) AS highest_monthly_rent
FROM Client c
JOIN Rentals r ON c.client_num = r.client_num
JOIN Properties p ON r.property_no = p.property_no
GROUP BY c.client_num, c.client_name
ORDER BY highest_monthly_rent DESC
LIMIT 1;


-- 10 List all properties with rent amounts greater than the average rent amount across all properties. 
SELECT property_no, property_address, monthly_rent
FROM Properties
WHERE monthly_rent > (
    SELECT AVG(monthly_rent)
    FROM Properties
);

SELECT AVG(monthly_rent) AS average_rent FROM Properties;
