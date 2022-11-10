create schema my_schema;

create table my_schema.customers
(
    id           bigserial,
    first_name   TEXT,
    last_name    TEXT,
    phone_number TEXT,
    rating       bigint
);