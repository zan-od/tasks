create table users(

id integer not null auto_increment primary key,

username varchar(255) not null,
password varchar(255) not null,
name varchar(255) not null,
board_id integer,

foreign key (board_id) references boards(id)

);

create table roles(

id integer not null auto_increment primary key,
name varchar(100) not null

);

insert into roles (id, name) values (1, 'ROLE_ADMIN');
insert into roles (id, name) values (2, 'ROLE_USER');

create table user_roles(

user_id integer not null,
role_id integer not null,

foreign key (user_id) references users(id),
foreign key (role_id) references roles(id),

unique(user_id, role_id)

);

create table boards(

id integer not null auto_increment primary key,

name varchar(255) not null

);

create table board_users(

board_id integer not null,
user_id integer not null,

foreign key (board_id) references boards(id),
foreign key (user_id) references users(id),

unique(board_id, user_id)

);

create table clients(

id integer not null auto_increment primary key,
board_id integer not null,
name varchar not null default ''

);

create table tasks(

id integer not null auto_increment primary key,
name varchar not null,
board_id integer not null,
client_id integer not null,
status smallint not null,
started boolean not null default false,
closed boolean not null default false,
duration integer not null default 0

);

create table time_intervals(

id integer not null auto_increment primary key,
task_id integer not null,
performer_id integer not null,
start_time timestamp not null,
stop_time timestamp,
duration integer not null default 0,

foreign key (task_id) references tasks(id),
foreign key (performer_id) references users(id)

);

create table invoices(

id integer not null auto_increment primary key,
number varchar not null,
date timestamp not null,
board_id integer not null,
client_id integer not null,
closed boolean not null default false,

foreign key (board_id) references boards(id),
foreign key (client_id) references clients(id)

);

create table invoice_items(

id integer not null auto_increment primary key,
description varchar not null,
invoice_id integer not null,
task_id integer,
quantity numeric(15,2) not null,
price numeric(15,2) not null,
amount numeric(15,2) not null,

foreign key (invoice_id) references invoices(id),
foreign key (task_id) references tasks(id)

);