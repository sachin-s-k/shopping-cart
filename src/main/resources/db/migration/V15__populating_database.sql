INSERT INTO categories (name)
VALUES ('Electronics'),
       ('Books'),
       ('Clothing'),
       ('Home'),
       ('Sports');
INSERT INTO products (name, price, category_id)
VALUES ('Laptop', 75000.00, 1),
       ('Smartphone', 35000.00, 1),
       ('Headphones', 2500.00, 1),

       ('Java Programming Book', 799.00, 2),
       ('Spring Boot Guide', 999.00, 2),

       ('T-Shirt', 499.00, 3),
       ('Jeans', 1999.00, 3),

       ('Coffee Maker', 3499.00, 4),
       ('Vacuum Cleaner', 8999.00, 4),

       ('Football', 699.00, 5),
       ('Cricket Bat', 1499.00, 5);