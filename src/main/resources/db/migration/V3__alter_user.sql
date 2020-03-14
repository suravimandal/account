ALTER TABLE users
  DROP COLUMN registered_on;

ALTER TABLE users
  DROP COLUMN last_login_date;
  
ALTER TABLE users
 ADD registered_dt DATE;
  
ALTER TABLE users
  ADD created_by VARCHAR(255);
  
ALTER TABLE users
  ADD created_dt DATE;
  
ALTER TABLE users
  ADD last_updated_by VARCHAR(255);
  
ALTER TABLE users
  ADD last_updated_dt DATE;
  
