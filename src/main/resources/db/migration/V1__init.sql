CREATE TABLE users
  (
     id          BIGSERIAL    NOT NULL PRIMARY KEY,
     email       VARCHAR(255),
     NAME        VARCHAR(255),
	 password    VARCHAR(255),
	 created_at    DATE,
     updated_at    DATE,
     is_deleted	BOOLEAN DEFAULT FALSE   
 );
 