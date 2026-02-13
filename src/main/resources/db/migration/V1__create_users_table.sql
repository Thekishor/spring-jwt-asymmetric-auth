create table users (
    id uuid primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(100) not null unique ,
    phone_number varchar(20) not null unique,
    password varchar(100) not null,
    date_of_birth timestamp,
    is_enabled boolean,
    is_account_locked boolean,
    is_credential_expired boolean,
    is_email_verified boolean,
    is_phone_verified boolean,
    credentials_expired boolean,
    created_date timestamp not null,
    last_modified_date timestamp
);

