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
alter table user_profile add column verified default 0;

create table festival(
    id bigint(20) not null auto_increment,
    title varchar(20),
    genre varchar(20),
    begin_date timestamp default '2000-01-01 00:00:00',
    end_date timestamp default '2000-01-01 00:00:00',
    primary key(id)
);

create table ticket (
    id bigint(20) not null auto_increment,
    user_id bigint(20),
    festival_id bigint(20),
    primary key(id)
);




-- inserting data

insert into user_profile(first_name,last_name,username, password, phone, email, kind, verified) VALUES ('Pera', 'Peric', 'pera987', '100j@d1n', '0645555555', 'pera.peric@gmail.com', 0, 0);
insert into user_profile(first_name,last_name,username, password, phone, email, kind, verified) VALUES ('Mika', 'Peric', 'Mika987', 'mikaperic555', '0645555555', 'mika.peric@gmail.com', 0, 0);