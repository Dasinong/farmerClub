CREATE TABLE disasterReport (
  disasterReportId INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (disasterReportId) USING BTREE,
  cropName VARCHAR(30) NOT NULL,
  fieldId INT(10),
  disasterType VARCHAR(30),
  disasterName VARCHAR(50),
  affectedArea VARCHAR(10),
  eruptionTime VARCHAR(50),
  disasterDist VARCHAR(50),
  fieldOperations VARCHAR(100),
  imageFilenames VARCHAR(200) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;