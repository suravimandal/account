CREATE TABLE tokens
  (
     id          BIGSERIAL    NOT NULL PRIMARY KEY,
     user_id 	 BIGSERIAL    NOT NULL,
     access_token VARCHAR(255) NOT NULL
     
 );
 