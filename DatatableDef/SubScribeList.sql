CREATE TABLE subScribeList (
  id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id) USING BTREE,
  ownerId INT(10) NOT NULL,
  cropId INT(10) NOT NULL,
  targetName VARCHAR(20),
  cellphone VARCHAR(20),
  province VARCHAR(10),
  city VARCHAR(10),
  country VARCHAR(10),
  district VARCHAR(10),
  area DOUBLE(8,2),
  isAgriWeather INT(1),
  isNatAler INT(1),
  isRiceHelper INT(1)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;


