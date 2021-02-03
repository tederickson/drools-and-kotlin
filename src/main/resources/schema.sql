DROP TABLE CUSTOMER IF EXISTS;
CREATE TABLE customer (
	customer_id INTEGER NOT NULL AUTO_INCREMENT,
	first_name VARCHAR(60) NOT NULL,
	last_name VARCHAR(60) NOT NULL,
	phone VARCHAR(10),
	email VARCHAR(200),

	PRIMARY KEY (customer_id)
);

DROP TABLE Loan_Officer IF EXISTS;
CREATE TABLE Loan_Officer (
	loan_officer_id INTEGER NOT NULL AUTO_INCREMENT,
	first_name VARCHAR(60) NOT NULL,
	last_name VARCHAR(60) NOT NULL,
	phone VARCHAR(10) NOT NULL,
	email VARCHAR(200) NOT NULL,
	manager_id INTEGER NOT NULL,
	active_enum VARCHAR(20) NOT NULL,

	PRIMARY KEY (loan_officer_id)
);

DROP TABLE Mortgage_Loan IF EXISTS;
CREATE TABLE Mortgage_Loan (
	mortgage_id INTEGER NOT NULL AUTO_INCREMENT,
	customer_id INTEGER,
	loan_officer_id INTEGER,
  status_enum VARCHAR(80) NOT NULL,

	PRIMARY KEY (mortgage_id)
);
CREATE INDEX Mortgage_Loan_customer ON Mortgage_Loan(customer_id);
CREATE INDEX Mortgage_Loan_loan_officer ON Mortgage_Loan(loan_officer_id);
