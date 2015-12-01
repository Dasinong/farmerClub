CREATE TABLE location (
  locationId INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (locationId) USING BTREE,
  region VARCHAR(30),
  province VARCHAR(30),
  city VARCHAR(30),
  country VARCHAR(30),
  district VARCHAR(30),
  community VARCHAR(30),
  latitude DOUBLE(20,15),
  longtitude DOUBLE(20,15)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;