CREATE TABLE categories (
                            id TINYINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100) NOT NULL UNIQUE
);
CREATE TABLE products (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(150) NOT NULL,
                          price DECIMAL(10,2) NOT NULL,
                          category_id TINYINT NOT NULL,

                          CONSTRAINT fk_product_category
                              FOREIGN KEY (category_id)
                                  REFERENCES categories(id)
                                  ON DELETE CASCADE
);