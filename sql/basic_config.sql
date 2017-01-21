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
alter table user_profile add column verified tinyint(2) default 0;

create table festival(
    id bigint(20) not null auto_increment,
    title varchar(50),
    genre varchar(20),
    begin_date timestamp default '2000-01-01 00:00:00',
    end_date timestamp default '2000-01-01 00:00:00',
    primary key(id)
);
alter table festival add column place varchar(20);
alter table festival add column artist varchar(20);
alter table festival add column times_seen bigint(20) default 0;
alter table festival add column tickets_sold bigint(20) default 0;

create table ticket (
    id bigint(20) not null auto_increment,
    user_id bigint(20),
    festival_id bigint(20),
    primary key(id)
);

create table image(
    id bigint(20) not null auto_increment, 
    festival_id bigint(20),
    img mediumblob,
    primary key(id)
);




-- inserting data

insert into user_profile(first_name,last_name,username, password, phone, email, kind, verified) VALUES ('Pera', 'Peric', 'pera987', '100j@d1n', '0645555555', 'pera.peric@gmail.com', 0, 0);
insert into user_profile(first_name,last_name,username, password, phone, email, kind, verified) VALUES ('Mika', 'Peric', 'Mika987', 'mikaperic555', '0645555555', 'mika.peric@gmail.com', 0, 0);


insert into festival(title, genre, begin_date, end_date, times_seen, tickets_sold) values ("Beer fest", "Rock" ,"2017-08-22 00:00:00", "2017-08-26 00:00:00", 25, 7);
insert into festival(title, genre, begin_date, end_date, times_seen, tickets_sold) values ("Guca", "Folk" ,"2017-08-12 00:00:00", "2017-08-20 00:00:00", 55, 15);
insert into festival(title, genre, begin_date, end_date, times_seen, tickets_sold) values ("Foam fest", "Techno" ,"2017-08-28 00:00:00", "2017-09-02 00:00:00", 56, 25);
insert into festival(title, genre, begin_date, end_date, times_seen, tickets_sold) values ("Exit", "multiple" ,"2017-07-08 00:00:00", "2017-07-12 00:00:00", 100, 78);
insert into festival(title, genre, begin_date, end_date, times_seen, tickets_sold) values ("Novogodisnji festival", "Rock" ,"2016-12-25 00:00:00", "2017-01-05 00:00:00", 32, 17);
insert into festival(title, genre, begin_date, end_date, times_seen, tickets_sold) values ("Lim fest", "Rock" ,"2017-08-01 00:00:00", "2017-08-04 00:00:00", 4, 2);
