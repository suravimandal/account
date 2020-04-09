CREATE TABLE addresses
  (
     id          BIGSERIAL    NOT NULL PRIMARY KEY,
     name        	VARCHAR(255),
     street        	VARCHAR(255),
     block        	VARCHAR(10),
     unit_number    VARCHAR(10),
     postal_code	VARCHAR(6),
     user_id      	bigint	NOT NULL,
     created_at    DATE,
     updated_at    DATE,
     is_deleted	BOOLEAN DEFAULT FALSE
    
     
 );
 