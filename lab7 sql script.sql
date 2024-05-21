CREATE TABLE users (
id SERIAL PRIMARY KEY,
username VARCHAR(90) UNIQUE,
hashedPassword VARCHAR(90)
);

CREATE TABLE vehicles (
id SERIAL PRIMARY KEY,
name VARCHAR(80),
x FLOAT,
y FLOAT,
creationDate TIMESTAMP,
area INT,
population INT,
metersAboveSeaLevel FLOAT,
telephoneCode DECIMAL,
agglomeration DECIMAL,
standardOfLiving VARCHAR(10),
governor VARCHAR(64),
creatorID INT REFERENCES users(id)
);