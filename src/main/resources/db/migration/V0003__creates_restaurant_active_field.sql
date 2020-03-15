alter table restaurant add "active" boolean not null;
update restaurant set active = true;
