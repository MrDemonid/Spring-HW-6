create table notes (
    id bigint auto_increment primary key,
    title varchar(64) not null,
    body varchar(1024) not null,
    created timestamp default current_timestamp
);
