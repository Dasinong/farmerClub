CREATE TABLE DasinongApp (
	appId INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (appId),
	appName VARCHAR(100) NOT NULL,
	appSecret VARCHAR(100) NOT NULL,
	createdAt timestamp NOT NULL default current_timestamp,
	constraint uc_appName UNIQUE (appName)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
