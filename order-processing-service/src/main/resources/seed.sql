insert into buyer (id, user_id, name)
values (1, 'user-id-1', 'Hristijan Dimitrieski');

insert into buyer (id, user_id, name)
values (2, 'user-id-2', 'Emilija Nechkoska');

insert into orders(id, buyer_id, description, is_draft, order_date, order_status_id)
values (1, 1, 'Shoes order', false, now(), 1);

insert into order_item (id, discount, picture_url, product_id, product_name, unit_price, units, order_id)
values (1, 20.0, 'nike_shoes.jpg', 9, 'nike shoes', 100, 1, 1);

insert into order_item (id, discount, picture_url, product_id, product_name, unit_price, units, order_id)
values (2, 23.0, 'nike_bag.jpg', 10, 'nike bag', 22, 1, 1);
