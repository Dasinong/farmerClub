CREATE TABLE step (
  stepId INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (stepId) USING BTREE,
  stepName VARCHAR(30) NOT NULL,
  taskSpecId int(10) unsigned not null,
  KEY FK_STEP_TS_ID (taskSpecId),
  CONSTRAINT FK_STEP_TS_ID FOREIGN KEY (taskSpecId)
  REFERENCES taskSpec (taskSpecId) ON DELETE CASCADE ON UPDATE CASCADE,
  fitRegion VARCHAR(10),
  description VARCHAR(2000),
  picture VARCHAR(200),
  idx INT(10)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;