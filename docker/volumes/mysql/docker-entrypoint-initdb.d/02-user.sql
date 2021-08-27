use tryjwt;

CREATE TABLE IF NOT EXISTS user
(
    username varchar(32) PRIMARY KEY,
    password varchar(128)
);
