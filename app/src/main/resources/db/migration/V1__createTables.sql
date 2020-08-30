CREATE TABLE users(
    id serial primary key,
    username varchar(50) unique not null,
    password varchar(500) not null,
    enabled boolean not null
);

CREATE TABLE authorities (
    id serial primary key,
    username varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);
CREATE unique index ix_auth_username on authorities (username,authority);