alter table order_ add code varchar(36) after id;
update order_ set code = uuid();
alter table order_ add constraint unique_order_code unique (code);
alter table order_ alter column code varchar(36) not null;
