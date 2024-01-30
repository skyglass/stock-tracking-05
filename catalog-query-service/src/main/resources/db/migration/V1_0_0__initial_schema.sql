-- Schema: public

create table catalog_item
(
    id                  uuid           not null,
    available_stock     integer,
    description         varchar(255),
    name                varchar(50)    not null,
    picture_file_name   varchar(255),
    price               float8 not null
);

alter table only catalog_item
    add constraint catalog_item_pkey primary key (id);