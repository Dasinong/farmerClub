CREATE TABLE cPProduct (
  cPProductId INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (cPProductId) USING BTREE,
  cPProductName VARCHAR(30),
  registerationId VARCHAR(30) NOT NUll,
  UNIQUE KEY UNI_REGISTID (registerationId),
  activeIngredient VARCHAR(30),
  type VARCHAR(10),
  manufacturer VARCHAR(100),
  tip TEXT,
  guideline TEXT,
  crop VARCHAR(30),
  disease VARCHAR(100), 
  volume VARCHAR(500),
  method VARCHAR(100),
  model VARCHAR(10)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;