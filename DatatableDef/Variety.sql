CREATE TABLE  variety (
  varietyId INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`varietyId`) USING BTREE,
  varietyName VARCHAR(50) NOT NULL,
  subId VARCHAR(50),
  registerationId VARCHAR(30) NOT NULL,
  UNIQUE KEY `UNI_REGISTERATIONID` (`registerationId`),
  cropId int(10) unsigned not null,
  KEY FK_TRANSACTION_CROP_ID (cropId),
  CONSTRAINT FK_TRANSACTION_CROP_ID FOREIGN KEY (cropId)
  REFERENCES crop (cropId) ON DELETE CASCADE ON UPDATE CASCADE,
  
  yearofReg INT(6),
  issuedBy TEXT,
  owner TEXT,
  varietySource TEXT,
  isTransgenic TINYINT(1) DEFAULT 0,
  suitableArea TEXT,
  characteristics TEXT,
  yieldPerformance TEXT,
  type VARCHAR(10),
  genoType VARCHAR(10),
  maturityType VARCHAR(20),
  totalAccumulatedTempNeeded INT(10),
  fullCycleDuration DOUBLE,
  typicalYield INT(10),
  nationalStandard VARCHAR(20)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;