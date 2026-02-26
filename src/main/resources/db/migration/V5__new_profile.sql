CREATE TABLE profiles (
                          id BIGINT NOT NULL,
                          bio TEXT,
                          phone_number VARCHAR(20),
                          loyalty_points INT DEFAULT 0,
                          date_of_birth DATE,

                          CONSTRAINT pk_profiles PRIMARY KEY (id),

                          CONSTRAINT fk_profiles_users
                              FOREIGN KEY (id)
                                  REFERENCES users(id)
                                  ON DELETE CASCADE
);