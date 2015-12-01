  CREATE TABLE soilTestReport (
  soilTestReportId INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (soilTestReportId) USING BTREE,
  fieldId INT(10) UNSIGNED NOT NULL,
  userId INT(10),
  type varchar(20),
  color varchar(20),
  fertility varchar(20),
  testDate DATETIME,
  humidity DOUBLE(8,2),
  phValue DOUBLE(3,1),
  organic varchar(20),
  an DOUBLE(8,2),
  qn DOUBLE(8,2),
  p DOUBLE(8,2),
  qK DOUBLE(8,2),
  sK DOUBLE(8,2),
  fe DOUBLE(8,2),
  mn DOUBLE(8,2),
  cu DOUBLE(8,2),
  b DOUBLE(8,2),
  mo DOUBLE(8,2),
  ca DOUBLE(8,2),
  s DOUBLE(8,2),
  si DOUBLE(8,2),
  mg DOUBLE(8,2),
  KEY FK_STRI (fieldId),
  CONSTRAINT FK_STR_F_ID FOREIGN KEY (fieldId)
  REFERENCES field (fieldId) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
  

      
   
     