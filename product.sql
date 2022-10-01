create table product
(
    id           serial primary key,
    name         text,
    description  text,
    availability integer,
    price        bigint
);
