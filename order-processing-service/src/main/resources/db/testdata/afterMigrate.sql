delete from order_item;
delete from orders;
delete from buyer;

insert into buyer (id, user_id, name)
values ('1c94903f-59d4-4498-a737-beae5766accd', 'd8222b6b-f59b-4388-9a1c-e5fe9911c4c6', 'Emilija');

insert into orders (id, buyer_id, description, is_draft, order_date, order_status)
values ('7913e2b7-c190-470a-837f-ba2c84ab2200',
        '1c94903f-59d4-4498-a737-beae5766accd', 'description...', false, '2021-04-16', 'Shipped'
);

insert into order_item (id, discount, picture_url, product_id, product_name, unit_price, units, order_id)
values ('dffd044c-6ada-44bb-94c2-8df782d47bb5', 0, null, '4feb96b7-bb3a-4c0a-9763-af16f3f9c361', 'Nike shoes', 23.0, 1, '7913e2b7-c190-470a-837f-ba2c84ab2200');
