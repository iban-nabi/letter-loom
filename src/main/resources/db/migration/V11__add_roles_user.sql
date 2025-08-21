alter table users
    add role enum('PLAYER', 'ADMIN') not null default 'PLAYER';

update users set role = 'PLAYER' where role is null;
alter table users modify role enum('PLAYER', 'ADMIN') not null;