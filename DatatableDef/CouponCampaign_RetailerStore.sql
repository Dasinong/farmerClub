create table CouponCampaign_RetailerStore (
	id int(10) unsigned not null auto_increment,
	primary key(id),
	campaignId int(10) unsigned not null,
	storeId int(10) unsigned not null,
	createdAt timestamp default current_timestamp
) engine=Innodb default charset=utf8;
