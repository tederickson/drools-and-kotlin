INSERT INTO CUSTOMER (first_name, last_name, phone, email)
VALUES      ('Bob',
             'Haskel',
             '3035551234',
             'bobbie.haskel@gmail.com'),
            ('Boba',
             'Loo',
             '8015556874',
             'bobaloo@live.com'),
            ('George',
             'Amish',
             '1035559876',
             'george.amish@spectrum.net');
INSERT INTO CUSTOMER (first_name, last_name) VALUES ('Almost', 'There');

INSERT INTO Loan_Officer (first_name, last_name, phone, email, manager_id, active_enum)
VALUES      ('Friendly',
             'Officer',
             '3035557777',
             'puppies@gmail.com',
             12345,
             'ACTIVE'),
            ('Glinda',
             'Good',
             '8015551234',
             'red.shoes@rainbow.com',
             707419,
             'VACATION');

INSERT INTO Mortgage_Loan(customer_id, loan_officer_id, status_enum)
VALUES (2, 1, 'APPROVED');
