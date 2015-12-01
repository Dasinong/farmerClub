CREATE TABLE SecurityCode (
	codeId INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (codeId),
	code VARCHAR(30) NOT NULL,
	createdAt timestamp NOT NULL default current_timestamp,
	expiredAt timestamp NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
