create table if not exists product (
    id int not null primary key auto_increment,
    name varchar(100),
    description varchar(300),
    price int
);