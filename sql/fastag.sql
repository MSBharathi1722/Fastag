create table balance_detail(
	id int AUTO_INCREMENT ,
	user_id int NOT NULL ,
	pin varchar(4) NOT NULL,
	balance int NOT NULL,
	primary key (id),
	foreign key (user_id) references user_detail(user_id)
);

create table vehicle_detail(
	id int AUTO_INCREMENT,
	user_id int NOT NULL ,
	reg_no varchar(10) NOT NULL Unique key,
	vehicle_type enum('type1','type2','type3','type4','type5','type6') not null,
	primary key (id),
	foreign key (user_id) references user_detail(user_id)
);

create table user_detail(
	user_id int AUTO_INCREMENT,
	name varchar(25) NOT NULL,
	mail varchar(25) UNIQUE kEY NOT NULL,
	mobile_number varchar(13) Not NULL ,
	password varchar(12) NOT NULL,
	PRIMARY KEY (user_id)
);

create table travel_detail(
	id int AUTO_INCREMENT,
	user_id int not null,
	place varchar(25) NOT NULL,
	time timestamp NOT NULL,
	primary key (id),
	foreign key (user_id) references user_detail(user_id)
);