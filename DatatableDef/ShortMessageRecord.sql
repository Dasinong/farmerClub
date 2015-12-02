CREATE TABLE ShortMessageRecord (
	id int(10) unsigned not null auto_increment,
	primary key (id),
	externalId varchar(30),
	status tinyint not null,
	errorResponse varchar(3000),
	senderId int(10) unsigned default 0,
	receivers varchar(3000) not null,
	data text, 
	createdAt timestamp default current_timestamp,
	retriedAt timestamp,
	retriedTimes int(10) default 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
