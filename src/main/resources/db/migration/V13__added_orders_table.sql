CREATE TABLE orders (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        customer_id BIGINT NOT NULL,
                        status VARCHAR(20),
                        created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        total_price DECIMAL(10,2) NOT NULL,

                        PRIMARY KEY (id),

                        CONSTRAINT fk_orders_user
                            FOREIGN KEY (customer_id)
                                REFERENCES users(id)
);