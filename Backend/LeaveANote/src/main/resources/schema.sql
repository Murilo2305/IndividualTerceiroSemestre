create table if not exists `user`(
    id int primary key auto_increment,
    name varchar(150) not null,
    email varchar(150) not null,
    senha varchar(50) not null

);

create table if not exists note(

    id int primary key auto_increment,
    msg varchar(1000) not null,
    fkUser int,
    constraint fkUserNote
        foreign key (fkUser)
        references `user` (id)
);