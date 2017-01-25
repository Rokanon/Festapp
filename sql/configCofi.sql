create table reservation(
    id bigint(20) not null auto_increment,
    festival_id bigint(20),
    user_id bigint(20),
    time_of_reservation timestamp default current_timestamp,
    duration_time int,
    bought boolean,
    primary key(id)
);

create table comments(
    id bigint(20) not null auto_increment,
    festival_id bigint(20),
    festival_title varchar(50),
    user_id bigint(20),
    posted_time timestamp default current_timestamp,
    text varchar(512),
    rating decimal(4,2),
    primary key(id)
);