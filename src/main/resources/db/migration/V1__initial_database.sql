create table users
(
    id        bigint auto_increment
        primary key,
    username  varchar(255) not null,
    firstname varchar(255) null,
    lastname  varchar(255) null,
    email     varchar(255) null,
    password  varchar(255) not null
);

create table games
(
    id         bigint auto_increment
        primary key,
    status     ENUM ('waiting', 'ongoing', 'finished') not null,
    date       date                                    not null,
    time_start time                                    null,
    time_end   time                                    null
);