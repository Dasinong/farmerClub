CREATE TABLE WeatherSubscription (
	weatherSubscriptionId int(10) unsigned not null auto_increment,
	PRIMARY KEY (weatherSubscriptionId),
	locationId int(10) not null,
	locationName varchar(300) not null,
	monitorLocationId int(10) not null,
	userId int(10) not null,
	type tinyint not null,
	ordering int(10) not null,
	createdAt timestamp default current_timestamp
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
