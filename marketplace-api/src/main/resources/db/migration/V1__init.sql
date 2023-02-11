--CREATE TABLE--

create table categories
(
    category_id bigserial not null primary key,
    name        text      not null unique
);

create table goods
(
    id          bigserial not null primary key,
    name        text      not null unique,
    category_id bigint    not null references categories (category_id) on delete cascade,
    price       decimal   not null,
    weight      bigint    not null,
    description text      not null,
    image_name  text      not null unique
);

create table roles
(
    id   bigserial not null primary key,
    name text      not null unique
);

create table users
(
    id         bigserial not null primary key,
    first_name text      not null,
    last_name  text,
    email      text      not null unique,
    password   text      not null
);

create table user_role
(
    user_id bigserial not null references users (id),
    role_id bigserial not null references roles (id)
);

-- FILL TABLES WITH DEV DATA --

insert into roles (id, name)
values (0, 'ROLE_ADMIN');
insert into roles (id, name)
values (1, 'ROLE_USER');
insert into users (id, first_name, last_name, email, password)
values (0, 'Admin', 'Super', 'sa@gmail.com', '$2a$10$EtqjLVUZi2b1iZDa1buSke.5BKTQ73hnhXavJzonOcB4l41srrdhG');
insert into user_role (user_id, role_id)
values (0, 0);