-- Schema: public

create table stock_order_item
(
    id                uuid      not null,
    order_id        uuid,
    product_id        uuid,
    quantity     integer,
    stock_order_item_status      varchar(100)   not null
);

alter table only stock_order_item
    add constraint stock_order_item_pkey primary key (id);

alter table only stock_order_item
    add constraint uk_stock_order_item unique (order_id, product_id);