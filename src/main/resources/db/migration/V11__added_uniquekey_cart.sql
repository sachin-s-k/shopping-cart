ALTER TABLE cart_items
ADD CONSTRAINT uk_cart_product
UNIQUE (cart_id, product_id);