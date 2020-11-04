create table message_likes (
    user_id bigint not null,
    message_id bigint not null,
    primary key (user_id, message_id),
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES usr (id),
    CONSTRAINT message_id_fk FOREIGN KEY (message_id) REFERENCES message (id)
) engine=InnoDB;