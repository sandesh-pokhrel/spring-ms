create database order_asia;
create database order_europe;


use order_asia;
create table product_order (id int not null primary key auto_increment,
    title varchar(100));
create table order_item (id int not null primary key auto_increment,
    product_id int, quantity int, total_price double, discount double,
    order_id int, constraint fk_order_item_order_id foreign key (order_id) references product_order(id));

select * from product_order;
select * from order_item;
insert into product_order (title) values ('Order 1'), ('Order 2');
insert into order_item (product_id, quantity, total_price, discount, order_id) values
            (1, 5, 200, 5, 1), (2, 1, 100, 0, 1), (1, 1, 25, 0, 2);