@grep.sql

CREATE TABLE employees (
  employee_id INT,
  full_name VARCHAR2(64),
  first_name VARCHAR2(32),
  last_name VARCHAR2(32),
  email_address VARCHAR2(64)
);

INSERT INTO employees (employee_id, full_name, first_name, last_name, email_address) VALUES (1, 'John Doe', 'John', 'Doe', 'john.doe@example.com');

INSERT INTO employees (employee_id, full_name, first_name, last_name, email_address) VALUES (2, 'Jane Doe', 'Jane', 'Doe', 'jane.doe@example.com');

EXECUTE grep('HR', '%John%');
