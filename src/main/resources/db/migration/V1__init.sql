CREATE TABLE users
  (
     id          BIGSERIAL    NOT NULL PRIMARY KEY,
     email       VARCHAR(255),
     NAME        VARCHAR(255),
     address     VARCHAR(255),
	 password    VARCHAR(255),
	 registered_on DATE,
	 last_login_date DATE 
     
 );
 