DROP DATABASE IF EXISTS jcost;
CREATE DATABASE jcost character set utf8;
USE jcost;

CREATE TABLE currency 
(
	id BIGINT,
	short_name VARCHAR(5) NOT NULL,
	name VARCHAR(255),
	
	UNIQUE(short_name),
	PRIMARY KEY (id)
);

CREATE TABLE good 
(
	id BIGINT,
	name VARCHAR(255) NOT NULL,
	
	UNIQUE(name),
	PRIMARY KEY(id)	
);

CREATE TABLE resident
(
	id BIGINT,
	name VARCHAR(255) NOT NULL,
	comment VARCHAR(255),
	
	UNIQUE(name),
	PRIMARY KEY(id)
);

CREATE TABLE payment
(
	id BIGINT,
	service_order BIGINT,
	payer BIGINT NOT NULL,
	amount DECIMAL(15,2),
	
	PRIMARY KEY(id)
);

CREATE TABLE service_order
(
	id BIGINT,
	created BIGINT NOT NULL,
	is_good TINYINT NOT NULL,
	payee BIGINT NOT NULL,
	currency BIGINT NOT NULL,
	comment VARCHAR(255),
	
	PRIMARY KEY(id)	
);

CREATE TABLE good_order
(
	id BIGINT,
	service_order BIGINT,
	good BIGINT,
	
	PRIMARY KEY(id)
)
