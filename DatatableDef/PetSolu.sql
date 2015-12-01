CREATE TABLE petSolu (
  petSoluId INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (petSoluId) USING BTREE,
  petSoluDes VARCHAR(2000) NOT NULL,
  petDisSpecId int(10) unsigned not null,
  KEY FK_PSPDS_ID (petDisSpecId),
  CONSTRAINT FK_PSPDS_ID FOREIGN KEY (petDisSpecId)
  REFERENCES petDisSpec (petDisSpecId) ON DELETE CASCADE ON UPDATE CASCADE,
  providedBy VARCHAR(50),
  isRemedy TINYINT(1) DEFAULT 1,
  isCPSolu TINYINT(1) DEFAULT 1,
  rank int(4),
  subStageId varchar(50)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;