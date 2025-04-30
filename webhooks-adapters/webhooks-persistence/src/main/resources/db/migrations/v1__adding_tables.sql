create table webhook_client (
    id uuid primary key,
    name varchar(255) not null,
    header_key varchar(30) not null,
    secret_key varchar(100) not null,
    max_age bigint not null,
    timestamp_header_key varchar(10) not null,
    signature_header_key varchar(10) not null,
    separator varchar(10) not null
);

create table webhook_event (
    id uuid primary key,
    payload varchar(10000) not null,
    headers varchar(10000) not null,
    timestamp timestamp not null,
    client_id uuid not null,
    foreign key (client_id) references webhook_client(id)
);