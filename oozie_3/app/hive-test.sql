set hive.exec.dynamic.partition =true;
set hive.exec.dynamic.partition.mode=nonstrict;

CREATE TABLE IF NOT EXISTS partitioned_customer 
(customer_fname varchar(64),
 customer_lname varchar(64),
 customer_addr string,
 city varchar(64))
PARTITIONED BY (country VARCHAR(64),state VARCHAR(64));

--Assuming that the customer table already exists with data available. This is reused from Hive partitioning example.

INSERT INTO TABLE partitioned_customer 
PARTITION(country,state) 
SELECT customer_fname,customer_lname,customer_addr,city,country,state 
FROM customer ;

--SELECT * from partitioned_customer where country = 'india';

--SELECT * from customer where country = 'india';
