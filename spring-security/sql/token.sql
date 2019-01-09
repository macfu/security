use mldn;
CREATE TABLE persistent_logins(
 series VARCHAR(64),
 username VARCHAR(100),
 token VARCHAR(64),
 last_used TIMESTAMP ,
 CONSTRAINT pk_series PRIMARY KEY(series)
) engine=innodb;