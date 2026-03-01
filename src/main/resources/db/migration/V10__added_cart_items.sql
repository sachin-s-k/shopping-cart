CREATE TABLE cart_items (
                            id BINARY(16) PRIMARY KEY
        DEFAULT (UUID_TO_BIN(UUID())),

                            cart_id BINARY(16) NOT NULL,
                            product_id BIGINT NOT NULL,

                            quantity INT NOT NULL DEFAULT 1,

                            CONSTRAINT fk_carts
                                FOREIGN KEY (cart_id)
                                    REFERENCES carts(id)
                                    ON DELETE CASCADE,

                            CONSTRAINT fk_product
                                FOREIGN KEY (product_id)
                                    REFERENCES products(id)
                                    ON DELETE CASCADE
);