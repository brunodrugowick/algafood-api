insert into cuisine (name) values ('Italian');
insert into cuisine (name) values ('Brazilian');

insert into restaurant (name, delivery_fee, cuisine_id) values ('Bar Preste Atenção', 5.00, 2);
insert into restaurant (name, delivery_fee, cuisine_id) values ('Pizzaria Marcante', 10.00, 1);
insert into restaurant (name, delivery_fee, cuisine_id) values ('Mexican Crazy Hat Food', 10.00, 2);

insert into payment_method (description) values ('Credit Card');
insert into payment_method (description) values ('Cash');
insert into payment_method (description) values ('Word');

insert into permission (name, description) values ('READ', 'READ records');
insert into permission (name, description) values ('WRITE', 'READ and WRITE records');

insert into province (name, abbreviation) values ('São Paulo', 'SP');
insert into province (name, abbreviation) values ('Minas Gerais', 'MG');
insert into province (name, abbreviation) values ('Santa Catarina', 'SC');
insert into province (name, abbreviation) values ('Rio Grande do Sul', 'RS');

insert into city (name, province_id) values ('Campinas', 1);
insert into city (name, province_id) values ('Ribeirão Preto', 1);
insert into city (name, province_id) values ('Belo Horizonte', 2);
insert into city (name, province_id) values ('Florianópolis', 3);
insert into city (name, province_id) values ('Porto Alegre', 4);
