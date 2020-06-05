alter table restaurant add opened boolean not null;
update restaurant set opened = false;
