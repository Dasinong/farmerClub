CREATE TABLE task (
  taskId INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (taskId) USING BTREE,
  taskSpecId int(10) default NULL,
  fieldId int(10) default NULL,
  taskStatus int(1) default false
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;