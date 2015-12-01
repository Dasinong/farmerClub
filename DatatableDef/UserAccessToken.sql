CREATE TABLE UserAccessToken (
	token varchar(100) NOT NULL,
	PRIMARY KEY (token),
	userId INT(10) UNSIGNED NOT NULL,
	appId INT(10) UNSIGNED NOT NULL,
	createdAt timestamp NOT NULL default current_timestamp,
	expiredAt timestamp NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
