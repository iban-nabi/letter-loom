create table submitted_words
(
    id      int auto_increment
        primary key,
    user_game_id int          not null,
    word    VARCHAR(255) null,
    constraint submitted_words_user_game_id_fk
        foreign key (user_game_id) references user_games (id)
);