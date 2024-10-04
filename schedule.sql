create table schedule
(
    id          int auto_increment primary key,
    email       varchar(50)                         not null,
    todo        varchar(200)                        not null,
    password    varchar(20)                         not null,
    create_date timestamp default CURRENT_TIMESTAMP null,
    update_date timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);

create table user
(
    userid int auto_increment primary key,
    username varchar(50),
    email varchar(50),
    createdTime timestamp default CURRENT_TIMESTAMP null,
    modifiedTime timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);