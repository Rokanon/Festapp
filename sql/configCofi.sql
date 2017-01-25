create table reservation(
    id bigint(20) not null auto_increment,
    festival_id bigint(20),
    user_id bigint(20),
    time_of_reservation timestamp default current_timestamp,
    duration_time int,
    bought boolean default false,
    primary key(id)
)