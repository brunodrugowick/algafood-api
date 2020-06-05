alter table restaurant add active tinyint(1) not null;
update restaurant set active = true;
