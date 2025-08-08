create table user_games
(
    id int auto_increment
        primary key,
    user_id int not null,
    game_id int not null,
    constraint user_games_game_id_fk
        foreign key (game_id) references games (id),
    constraint user_games_users_id_fk
        foreign key (user_id) references users (id)
);

