create table menus
(
  id    int auto_increment,
  name  varchar(20)  not null,
  price float        not null,
  descs varchar(200) not null,
  constraint menu_id_uindex
  unique (id)
);

alter table menus
  add primary key (id);

create table orders
(
  id       int auto_increment,
  name     varchar(20)        not null,
  price    float              not null,
  descs    varchar(200)       not null,
  desk     int                not null,
  orderId  varchar(30)        not null,
  checkOut int(3) default '0' not null,
  constraint order_id_uindex
  unique (id)
);

alter table orders
  add primary key (id);

create table users
(
  id    int auto_increment,
  users varchar(20) not null,
  pwd   varchar(30) not null,
  constraint users_id_uindex
  unique (id)
);

alter table users
  add primary key (id);


