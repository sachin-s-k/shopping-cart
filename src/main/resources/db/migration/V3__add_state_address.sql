-- Remove state column from users
ALTER TABLE users
DROP COLUMN state;

-- Add state column to addresses
ALTER TABLE addresses
    ADD COLUMN state VARCHAR(100);