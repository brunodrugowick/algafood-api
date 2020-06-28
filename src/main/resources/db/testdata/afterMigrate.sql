set foreign_key_checks = 0;

delete from order_item;
delete from order_;

delete from restaurant_payment_method;
delete from payment_method;
delete from product;
delete from restaurant_manager;
delete from restaurant;
delete from cuisine;
delete from city;
delete from province;

delete from user_group;
delete from group_permission;
delete from user;
delete from permission;
delete from group_;
delete from product_photo;

set foreign_key_checks = 1;

alter table city auto_increment = 1;
alter table cuisine auto_increment = 1;
alter table province auto_increment = 1;
alter table payment_method auto_increment = 1;
alter table group_ auto_increment = 1;
alter table permission auto_increment = 1;
alter table product auto_increment = 1;
alter table restaurant auto_increment = 1;
alter table user auto_increment = 1;
alter table order_ auto_increment = 1;
alter table order_item auto_increment = 1;

insert into cuisine (name)
values ('Italian');
insert into cuisine (name)
values ('Brazilian');

insert into payment_method (description)
values ('Credit Card');
insert into payment_method (description)
values ('Cash');
insert into payment_method (description)
values ('Word');

insert into permission (name, description)
values ('READ', 'READ records');
insert into permission (name, description)
values ('WRITE', 'READ and WRITE records');

insert into group_ (name)
values ('Group 1');
insert into group_ (name)
values ('Group 2');

insert into group_permission (group_id, permission_id)
values ((select id from group_ where name = 'Group 1'), (select id from permission where name = 'READ'));
insert into group_permission (group_id, permission_id)
values ((select id from group_ where name = 'Group 2'), (select id from permission where name = 'READ'));
insert into group_permission (group_id, permission_id)
values ((select id from group_ where name = 'Group 2'), (select id from permission where name = 'WRITE'));

insert into user (name, email, password, created_date)
values ('drugowick', 'bruno.drugowick+algafoodapi_1@gmail.com', 'password', current_timestamp);
insert into user (name, email, password, created_date)
values ('drugowick2', 'brunodrugowick+algafoodapi_2@gmail.com', 'password', current_timestamp);

insert into user_group (user_id, group_id)
values ((select id from user where name = 'drugowick'), (select id from group_ where name = 'Group 1'));
insert into user_group (user_id, group_id)
values ((select id from user where name = 'drugowick'), (select id from group_ where name = 'Group 2'));
insert into user_group (user_id, group_id)
values ((select id from user where name = 'drugowick2'), (select id from group_ where name = 'Group 1'));

insert into province (name, abbreviation)
values ('São Paulo', 'SP');
insert into province (name, abbreviation)
values ('Minas Gerais', 'MG');
insert into province (name, abbreviation)
values ('Santa Catarina', 'SC');
insert into province (name, abbreviation)
values ('Rio Grande do Sul', 'RS');

insert into city (name, province_id)
values ('Campinas', (select id from province where name = 'São Paulo'));
insert into city (name, province_id)
values ('São José dos Campos', (select id from province where name = 'São Paulo'));
insert into city (name, province_id)
values ('Ribeirão Preto', (select id from province where name = 'São Paulo'));
insert into city (name, province_id)
values ('Belo Horizonte', (select id from province where name = 'Minas Gerais'));
insert into city (name, province_id)
values ('Florianópolis', (select id from province where name = 'Santa Catarina'));
insert into city (name, province_id)
values ('Porto Alegre', (select id from province where name = 'Rio Grande do Sul'));

insert into restaurant (address_address_line_1, address_address_line_2, address_postal_code, address_region,
                        delivery_fee, name, created_date, updated_date, active, address_city_id, cuisine_id, opened)
values
    ('Cocada Street, 123456', 'Neighborhood', '13020', 'Region1', 12.00, 'Pizzaria Marcante', current_timestamp,
        current_timestamp, true, (select id from city where name = 'Campinas'),
        (select id from cuisine where name = 'Italian'), false),
    ('Cocada Street, 123456', 'Neighborhood', '13020', 'Region1', 6.00, 'Bar Preste Atenção', current_timestamp,
        current_timestamp, true, (select id from city where name = 'São José dos Campos'),
        (select id from cuisine where name = 'Brazilian'), true),
    ('Cocada Street, 123456', 'Neighborhood', '13020', 'Region1', 9.00, 'Pizzaria Embarcante', current_timestamp,
        current_timestamp, true, (select id from city where name = 'São José dos Campos'),
        (select id from cuisine where name = 'Italian'), true),
    ('Cocada Street, 123456', 'Neighborhood', '13020', 'Region1', 0.00, 'Mexican Crazy Hat Food', current_timestamp,
        current_timestamp, false, (select id from city where name = 'Belo Horizonte'),
        (select id from cuisine where name = 'Brazilian'), false);

insert into restaurant_manager (restaurant_id, manager_id)
values
    ((select id from user where name = 'drugowick'), (select id from restaurant where name = 'Pizzaria Marcante')),
    ((select id from user where name = 'drugowick2'), (select id from restaurant where name = 'Pizzaria Marcante')),
    ((select id from user where name = 'drugowick'), (select id from restaurant where name = 'Bar Preste Atenção')),
    ((select id from user where name = 'drugowick'), (select id from restaurant where name = 'Pizzaria Embarcante')),
    ((select id from user where name = 'drugowick2'), (select id from restaurant where name = 'Mexican Crazy Hat Food'));

INSERT INTO product (active, description, name, price, restaurant_id)
VALUES (1, 'Delicious potato', 'Potato', 25.00, (select id from restaurant where name = 'Pizzaria Marcante'));
INSERT INTO product (active, description, name, price, restaurant_id)
VALUES (1, 'Nhami', 'Nhami', 5.00, (select id from restaurant where name = 'Bar Preste Atenção'));
INSERT INTO product (active, description, name, price, restaurant_id)
VALUES (1, 'Burger Mara', 'Burger', 8.00, (select id from restaurant where name = 'Pizzaria Embarcante'));
INSERT INTO product (active, description, name, price, restaurant_id)
VALUES (1, 'Chocolate', 'Chocolate', 3.00, (select id from restaurant where name = 'Mexican Crazy Hat Food'));

insert into restaurant_payment_method (restaurant_id, payment_method_id)
values ((select id from restaurant where name = 'Pizzaria Marcante'),
        (select id from payment_method where description = 'Credit Card')),
       ((select id from restaurant where name = 'Pizzaria Marcante'),
        (select id from payment_method where description = 'Cash')),
       ((select id from restaurant where name = 'Bar Preste Atenção'),
        (select id from payment_method where description = 'Credit Card')),
       ((select id from restaurant where name = 'Pizzaria Embarcante'),
        (select id from payment_method where description = 'Credit Card')),
       ((select id from restaurant where name = 'Pizzaria Embarcante'),
        (select id from payment_method where description = 'Cash')),
       ((select id from restaurant where name = 'Pizzaria Embarcante'),
        (select id from payment_method where description = 'Word'));

insert into order_ (code, subtotal, delivery_fee, total, created_date, confirmation_date, cancellation_date, delivery_date,
                    payment_method_id, restaurant_id, client_id, status, address_address_line_1, address_address_line_2,
                    address_postal_code, address_region, address_city_id)
values ('aff37747-f651-4463-a6c3-03af003ccde9', 100.00, 12.00, 112.00, current_timestamp, null, null, null,
        (select id from payment_method where description = 'Credit Card'),
        (select id from restaurant where name = 'Pizzaria Marcante'), (select id from user where name = 'drugowick'),
        'CREATED', 'Rua 1', null, '13020-240', 'Botafogo', (select id from city where name = 'Campinas'));
insert into order_ (code, subtotal, delivery_fee, total, created_date, confirmation_date, cancellation_date, delivery_date,
                    payment_method_id, restaurant_id, client_id, status, address_address_line_1, address_address_line_2,
                    address_postal_code, address_region, address_city_id)
values ('7718a85e-c765-400c-b965-76bed8ae96e4', 200.00, 12.00, 212.00, current_timestamp, current_timestamp, null, current_timestamp,
        (select id from payment_method where description = 'Cash'),
        (select id from restaurant where name = 'Pizzaria Embarcante'), (select id from user where name = 'drugowick2'),
        'DELIVERED', 'Rua 2', null, '13020-140', 'Guanabara', (select id from city where name = 'Campinas'));

insert into order_item (amount, unit_price, total_price, notes, product_id, order_id)
values (4, 25.00, 100.00, null, (select id from product where name = 'Potato'),
        (select id from order_ where status = 'CREATED'));
insert into order_item (amount, unit_price, total_price, notes, product_id, order_id)
values (40, 5.00, 200.00, 'Lots of Nhamis', (select id from product where name = 'Nhami'),
        (select id from order_ where status = 'DELIVERED'));
