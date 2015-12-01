CREATE TABLE subStage (
  subStageId INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (subStageId) USING BTREE,
  subStageName VARCHAR(30) NOT NULL,
  stageName VARCHAR(30) NOT NULL,
  UNIQUE KEY UNI_SUBSTAGENAME (subStageName),
  triggerAccumulatedTemp INT(10),
  reqMinTemp INT(10),
  reqAvgTemp INT(10),
  maxFieldHumidity INT(10),
  minFieldHumidity INT(10),
  durationLow DOUBLE,
  durationMid DOUBLE,
  durationHigh DOUBLE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;