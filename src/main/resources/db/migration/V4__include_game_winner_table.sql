CREATE table game_winner(
    id bigint auto_increment primary key not null,
    game_id bigint not null,
    user_id bigint not null,

    constraint game_winner_game_id_fk
        foreign key(game_id) references games(id),
    constraint game_winner_user_id_fk
        foreign key(user_id) references users(id)
);