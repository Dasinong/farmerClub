CREATE TABLE CropSubscription (
	id int(10) unsigned not null auto_increment,
	PRIMARY KEY (id),
	cropId int(10) not null,
	cropName varchar(300) not null,
	userId int(10) not null,
	createdAt timestamp default current_timestamp
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
