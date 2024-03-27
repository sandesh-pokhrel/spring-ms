--liquibase formatted sql

--changeset sandesh:1
create table person (
    id int not null primary key,
    name varchar(100),
    address varchar(100)
);
-- rollback drop table person

--changeset sandesh:2
alter table person modify column id int auto_increment;

--changeset sandesh:3
insert into person (name, address) values ('Jack', 'Chicago');
insert into person (name, address) values ('Russell', 'Texas');