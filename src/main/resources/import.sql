insert into cuisine (name) values ('Italian');
insert into cuisine (name) values ('Brazilian');

insert into payment_method (description) values ('Credit Card');
insert into payment_method (description) values ('Cash');
insert into payment_method (description) values ('Word');

insert into permission (name, description) values ('READ', 'READ records');
insert into permission (name, description) values ('WRITE', 'READ and WRITE records');

insert into group_ (name) values ("Group 1");
insert into group_ (name) values ("Group 2");

insert into group_permission (group_id, permission_id) values (1, 1);
insert into group_permission (group_id, permission_id) values (2, 1);
insert into group_permission (group_id, permission_id) values (2, 2);

insert into user (name, email, password) values ('drugowick', 'bruno.drugowick@gmail.com', 'password');
insert into user (name, email, password) values ('drugowick2', 'brunodrugowick@gmail.com', 'password');

insert into province (name, abbreviation) values ('São Paulo', 'SP');
insert into province (name, abbreviation) values ('Minas Gerais', 'MG');
insert into province (name, abbreviation) values ('Santa Catarina', 'SC');
insert into province (name, abbreviation) values ('Rio Grande do Sul', 'RS');

insert into city (name, province_id) values ('Campinas', 1);
insert into city (name, province_id) values ('São José dos Campos', 1);
insert into city (name, province_id) values ('Ribeirão Preto', 1);
insert into city (name, province_id) values ('Belo Horizonte', 2);
insert into city (name, province_id) values ('Florianópolis', 3);
insert into city (name, province_id) values ('Porto Alegre', 4);

insert into restaurant (address_address_line_1, address_address_line_2, address_postal_code, address_region, delivery_fee, name, created_date, updated_date, address_city_id, cuisine_id) values ('Cocada Street, 123456', 'Neighborhood', '13020', 'Region1', 10.00, 'Pizzaria Marcante', current_timestamp, current_timestamp, 1, 1);
insert into restaurant (address_address_line_1, address_address_line_2, address_postal_code, address_region, delivery_fee, name, created_date, updated_date, address_city_id, cuisine_id) values ('Cocada Street, 123456', 'Neighborhood', '13020', 'Region1', 5.00, 'Bar Preste Atenção', current_timestamp, current_timestamp, 2, 2);
insert into restaurant (address_address_line_1, address_address_line_2, address_postal_code, address_region, delivery_fee, name, created_date, updated_date, address_city_id, cuisine_id) values ('Cocada Street, 123456', 'Neighborhood', '13020', 'Region1', 10.00, 'Pizzaria Marcante', current_timestamp, current_timestamp, 2, 1);
insert into restaurant (address_address_line_1, address_address_line_2, address_postal_code, address_region, delivery_fee, name, created_date, updated_date, address_city_id, cuisine_id) values ('Cocada Street, 123456', 'Neighborhood', '13020', 'Region1', 0.00, 'Mexican Crazy Hat Food', current_timestamp, current_timestamp, 4, 2);

INSERT INTO product (active, description, name, price, restaurant_id) VALUES(1, 'Delicious potato', 'Potato', 25.00, 1);
INSERT INTO product (active, description, name, price, restaurant_id) VALUES(1, 'Nhami', 'Nhami', 5.00, 2);
INSERT INTO product (active, description, name, price, restaurant_id) VALUES(1, 'Burger Mara', 'Burger', 8.00, 3);
INSERT INTO product (active, description, name, price, restaurant_id) VALUES(1, 'Chocolate', 'Chocolate', 3.00, 4);

insert into restaurant_payment_method (restaurant_id, payment_method_id) values (1, 1), (1, 2), (2, 1), (3, 1), (3, 2), (3, 3);