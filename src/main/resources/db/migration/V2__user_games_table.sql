create table user_games
(
    id bigint auto_increment
        primary key,
    user_id bigint not null,
    game_id bigint not null,
    result enum('win','lose') not null,
    winning_word varchar(255) null,
    constraint user_games_game_id_fk
        foreign key (game_id) references games (id),
    constraint user_games_users_id_fk
        foreign key (user_id) references users (id)
);

