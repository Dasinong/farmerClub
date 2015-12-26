create table CouponCampaign (
	id int(10) unsigned not null auto_increment,
	primary key (id),
	name varchar(100) not null,
	description varchar(3000),
	volume int(10) unsigned default 0,
	unclaimedVolume int(10) unsigned default 0,
	amount int(10) unsigned default 0,
	institutionId int(10) unsigned not null,
	type tinyint not null,
	claimTimeStart timestamp not null default 0,
	claimTimeEnd timestamp not null default 0,
	redeemTimeStart timestamp not null default 0,
	redeemTimeEnd timestamp not null default 0,
	createdAt timestamp default current_timestamp
) engine=InnoDB default charset=utf8;
