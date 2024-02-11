-- Schema: public

create table stock_order_item
(
    id                uuid      not null,
    order_id        uuid,
    product_id        uuid,
    quantity     integer,
    stock_order_item_status      varchar(100)   not null
);

create table client_request
(
    id   uuid not null,
    name varchar(255),
    time timestamp
);

alter table only stock_order_item
    add constraint stock_order_item_pkey primary key (id);

alter table only stock_order_item
    add constraint uk_stock_order_item unique (order_id, product_id);

alter table only client_request
    add constraint client_request_pkey primary key (id);