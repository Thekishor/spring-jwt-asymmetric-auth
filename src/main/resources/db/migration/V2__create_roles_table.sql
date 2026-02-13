create table roles (
   id uuid primary key,
   name varchar(20) not null,
   created_date timestamp not null,
   last_modified_date timestamp,
   created_by varchar(100) not null,
   last_modified_by varchar(100)
)