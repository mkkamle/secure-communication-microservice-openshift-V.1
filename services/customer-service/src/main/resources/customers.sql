CREATE DATABASE customer_db

SELECT * FROM public.customers
ORDER BY id ASC 


INSERT INTO customers (name, email) 
VALUES 
    ('Aarav Sharma', 'aarav.sharma@example.com'),
    ('Priya Patel', 'priya.patel@example.com'),
    ('Rohan Verma', 'rohan.verma@example.com'),
    ('Ananya Iyer', 'ananya.iyer@example.com'),
    ('Vikram Reddy', 'vikram.reddy@example.com')
ON CONFLICT (email) DO NOTHING;
