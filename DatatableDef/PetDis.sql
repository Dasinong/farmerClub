CREATE TABLE petDis (
  petDisId INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (petDisId) USING BTREE,
  petDisSpecId int(10) default NULL,
  fieldId int(10) default NULL,
  petDisStatus int(1) default false
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;