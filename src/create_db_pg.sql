create table boards(

id SERIAL primary key,

name varchar(255) not null

);

create table users(

id SERIAL primary key,

username varchar(255) not null,
password varchar(255) not null,
name varchar(255) not null,
board_id integer,

foreign key (board_id) references boards(id)

);

create table roles(

id SERIAL primary key,
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

create table board_users(

board_id integer not null,
user_id integer not null,

foreign key (board_id) references boards(id),
foreign key (user_id) references users(id),

unique(board_id, user_id)

);

create table clients(

id SERIAL primary key,
name varchar not null default ''

);

create table tasks(

id SERIAL primary key,
name varchar not null,
board_id integer,
client_id integer,
status smallint,
started boolean not null default false,
closed boolean not null default false,
duration integer not null default 0

);

create table time_intervals(

id SERIAL primary key,
task_id integer not null,
performer_id integer not null,
start_time timestamp not null,
stop_time timestamp,
duration integer not null default 0,

foreign key (task_id) references tasks(id),
foreign key (performer_id) references users(id)

);