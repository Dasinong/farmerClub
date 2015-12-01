CREATE TABLE qualityItemValue (
  qualityItemValueId INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (qualityItemValueId) USING BTREE,
  varietyId INT(10) default NULL,
  qualityItemId INT(10) default NULL,
  itemValue VARCHAR(30) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;