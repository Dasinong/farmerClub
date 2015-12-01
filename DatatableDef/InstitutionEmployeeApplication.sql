CREATE TABLE InstitutionEmployeeApplication (
  id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id) USING BTREE,
  institutionId int(10) unsigned not null,
  cellphone varchar(30) not null,
  title varchar(100) not null,
  region varchar(100) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
