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
create table festival(
    id bigint(20) not null auto_increment,
    title varchar(20),
    genre varchar(20),
    begin_date timestamp default '2000-01-01 00:00:00',
    end_date timestamp default '2000-01-01 00:00:00',
    primary key(id)
);