CREATE TABLE addresses
  (
     id          BIGSERIAL    NOT NULL PRIMARY KEY,
     name        	VARCHAR(255),
     street        	VARCHAR(255),

     phone      BIGSERIAL NOT NULL,

	 alt_phone      VARCHAR(10),

	vill_or_sector  VARCHAR(255),

	town_or_city    VARCHAR(255),
     block        	VARCHAR(10),
     landmark    VARCHAR(255),
     postal_code	VARCHAR(6) NOT NULL,
     state          VARCHAR(255) NOT NULL,
     type           VARCHAR(4) NOT NULL,
     user_id      	bigint	NOT NULL,
     created_at    DATE,
     updated_at    DATE,
     is_deleted	BOOLEAN DEFAULT FALSE
    
     
 );
 