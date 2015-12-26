create table RetailerStore (
	id int(10) unsigned not null auto_increment,
	primary key(id),
	ownerId int(10) unsigned,
	name varchar(1000),
	createdAt timestamp default current_timestamp
) engine=Innodb default charset=utf8;
