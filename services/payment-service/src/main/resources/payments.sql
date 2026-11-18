-- Database: payment_db

-- DROP DATABASE IF EXISTS payment_db;


CREATE DATABASE payment_db;

CREATE DATABASE payment_db
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_India.1252'
    LC_CTYPE = 'English_India.1252'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;



	INSERT INTO payments (order_id, amount, status)
VALUES
(1001, 85000.00, 'SUCCESS'),
(1002, 45000.00, 'SUCCESS'),
(1003, 1200.00, 'SUCCESS'),
(1004, 3500.00, 'SUCCESS'),
(1005, 15000.00, 'SUCCESS'),
(1006, 2500.00, 'SUCCESS'),
(1007, 8000.00, 'SUCCESS'),
(1008, 3000.00, 'FAILED'),
(1009, 5000.00, 'SUCCESS'),
(1010, 18000.00, 'PENDING');