create table schedule
(
    id          int auto_increment primary key,
    username    varchar(50) not null,
    contents    varchar(200)                        not null,
    password    varchar(20)                         not null,
    createdTime timestamp default CURRENT_TIMESTAMP null,
    modifiedTime timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    userid int,
    foreign key (userid) references user(userid) on update cascade
);

create table user
(
    userid int auto_increment primary key,
    username varchar(50),
    email varchar(50),
    createdTime timestamp default CURRENT_TIMESTAMP null,
    modifiedTime timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);