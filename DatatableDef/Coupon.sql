create table Coupon (
	id int(10) unsigned not null auto_increment,
	primary key (id),
	amount int(10) unsigned not null,
	type tinyint not null,
	campaignId int(10) unsigned not null,
	scannerId int(10) unsigned,
	ownerId int(10) unsigned,
	claimedAt timestamp null default null,
	redeemedAt timestamp null default null,
	createdAt timestamp default current_timestamp
) engine=InnoDB default charset=utf8;
