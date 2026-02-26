-- Create tags table
CREATE TABLE tags (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(100) NOT NULL UNIQUE
);

-- Create user_tags join table
CREATE TABLE user_tags (
                           user_id BIGINT NOT NULL,
                           tag_id BIGINT NOT NULL,

                           PRIMARY KEY (user_id, tag_id),

                           CONSTRAINT fk_user_tags_user
                               FOREIGN KEY (user_id)
                                   REFERENCES users(id)
                                   ON DELETE CASCADE,

                           CONSTRAINT fk_user_tags_tag
                               FOREIGN KEY (tag_id)
                                   REFERENCES tags(id)
                                   ON DELETE CASCADE
);
