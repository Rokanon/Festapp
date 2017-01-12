create database fest;

use fest;

create table user_profile(
    id bigint(20) not null auto_increment,
    first_name varchar(20),
    last_name varchar(20),
    username varchar(20),
    password varchar(50),
    phone varchar(15),
    email varchar(30),
    kind tinyint(2) default 0,
    primary key(id)
);