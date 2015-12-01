CREATE TABLE natDis (
  natDisId INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (natDisId) USING BTREE,
  natDisSpecId int(10) default NULL,
  fieldId int(10) default NULL,
  natDisStatus int(1) default false
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;