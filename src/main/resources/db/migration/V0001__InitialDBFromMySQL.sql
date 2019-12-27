-- This dump includes test data because this project is NOT INTENDED FOR PRODUCTION. This is a teaching/learning project.
-- The MySQL dump was made simpler to work with MySQL and H2 (for demonstration purposes on Heroku, without the need to provide a database).

DROP TABLE IF EXISTS `province`;
CREATE TABLE `province`
(
    `id`           bigint(20)   NOT NULL AUTO_INCREMENT,
    `abbreviation` varchar(255) NOT NULL,
    `name`         varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UKs8eyw14ry213hhl6aol0booar` (`name`, `abbreviation`)
) ENGINE = InnoDB;
INSERT INTO `province`
VALUES (2, 'MG', 'Minas Gerais'),
       (4, 'RS', 'Rio Grande do Sul'),
       (3, 'SC', 'Santa Catarina'),
       (1, 'SP', 'São Paulo');

DROP TABLE IF EXISTS `city`;
CREATE TABLE `city`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT,
    `name`        varchar(255) NOT NULL,
    `province_id` bigint(20)   NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UKpa4krebion8wv6faaxvmloi5w` (`name`, `province_id`),
    CONSTRAINT `FKll21eddgtrjc9f40ueeouyr8f` FOREIGN KEY (`province_id`) REFERENCES `province` (`id`)
) ENGINE = InnoDB;
INSERT INTO `city`
VALUES (4, 'Belo Horizonte', 2),
       (1, 'Campinas', 1),
       (5, 'Florianópolis', 3),
       (6, 'Porto Alegre', 4),
       (3, 'Ribeirão Preto', 1),
       (2, 'São José dos Campos', 1);

DROP TABLE IF EXISTS `cuisine`;
CREATE TABLE `cuisine`
(
    `id`   bigint(20)   NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UKnt6pfms6803io217o7ky605r7` (`name`)
) ENGINE = InnoDB;
INSERT INTO `cuisine`
VALUES (2, 'Brazilian'),
       (1, 'Italian');

DROP TABLE IF EXISTS `group_`;
CREATE TABLE `group_`
(
    `id`   bigint(20)   NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_group__name` (`name`)
) ENGINE = InnoDB;
INSERT INTO `group_`
VALUES (1, 'Group 1'),
       (2, 'Group 2');

DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT,
    `description` varchar(255) NOT NULL,
    `name`        varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_permission_name` (`name`)
) ENGINE = InnoDB;
INSERT INTO `permission`
VALUES (1, 'READ records', 'READ'),
       (2, 'READ and WRITE records', 'WRITE');

DROP TABLE IF EXISTS `group_permission`;
CREATE TABLE `group_permission`
(
    `group_id`      bigint(20) NOT NULL,
    `permission_id` bigint(20) NOT NULL,
    KEY `FKss14p30qbokhpkpdov4ha3wro` (`permission_id`),
    KEY `FKfaw9e4t7wpfql8yhgxs8i4dl` (`group_id`),
    CONSTRAINT `FKfaw9e4t7wpfql8yhgxs8i4dl` FOREIGN KEY (`group_id`) REFERENCES `group_` (`id`),
    CONSTRAINT `FKss14p30qbokhpkpdov4ha3wro` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`),
    UNIQUE KEY `unique_group_permission_relationship` (`group_id`, `permission_id`)
) ENGINE = InnoDB;
INSERT INTO `group_permission`
VALUES (1, 1),
       (2, 1),
       (2, 2);

DROP TABLE IF EXISTS `payment_method`;
CREATE TABLE `payment_method`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT,
    `description` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_payment_method_description` (`description`)
) ENGINE = InnoDB;
INSERT INTO `payment_method`
VALUES (1, 'Credit Card'),
       (2, 'Cash'),
       (3, 'Word');

DROP TABLE IF EXISTS `restaurant`;
CREATE TABLE `restaurant`
(
    `id`                     bigint(20)     NOT NULL AUTO_INCREMENT,
    `address_address_line_1` varchar(255) DEFAULT NULL,
    `address_address_line_2` varchar(255) DEFAULT NULL,
    `address_postal_code`    varchar(255) DEFAULT NULL,
    `address_region`         varchar(255) DEFAULT NULL,
    `created_date`           datetime       NOT NULL,
    `delivery_fee`           decimal(19, 2) NOT NULL,
    `name`                   varchar(255)   NOT NULL,
    `updated_date`           datetime       NOT NULL,
    `address_city_id`        bigint(20)   DEFAULT NULL,
    `cuisine_id`             bigint(20)     NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK8pcwgn41lfg43d8u82ewn3cn` (`address_city_id`),
    KEY `FKa1mopooywwfnvq23on35n6xdm` (`cuisine_id`),
    CONSTRAINT `FK8pcwgn41lfg43d8u82ewn3cn` FOREIGN KEY (`address_city_id`) REFERENCES `city` (`id`),
    CONSTRAINT `FKa1mopooywwfnvq23on35n6xdm` FOREIGN KEY (`cuisine_id`) REFERENCES `cuisine` (`id`),
    UNIQUE KEY `unique_restaurant_name` (`name`)
) ENGINE = InnoDB;
INSERT INTO `restaurant`
VALUES (1, 'Cocada Street, 123456', 'Neighborhood', '13020', 'Region1', '2019-12-27 20:18:28', 10.00,
        'Pizzaria Marcante', '2019-12-27 20:18:28', 1, 1),
       (2, 'Cocada Street, 123456', 'Neighborhood', '13020', 'Region1', '2019-12-27 20:18:28', 5.00,
        'Bar Preste Atenção', '2019-12-27 20:18:28', 2, 2),
       (3, 'Cocada Street, 123456', 'Neighborhood', '13020', 'Region1', '2019-12-27 20:18:28', 10.00,
        'Pizzaria Tretante', '2019-12-27 20:18:28', 2, 1),
       (4, 'Cocada Street, 123456', 'Neighborhood', '13020', 'Region1', '2019-12-27 20:18:28', 0.00,
        'Mexican Crazy Hat Food', '2019-12-27 20:18:28', 4, 2);

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`
(
    `id`            bigint(20)     NOT NULL AUTO_INCREMENT,
    `active`        bit(1)         NOT NULL,
    `description`   varchar(255)   NOT NULL,
    `name`          varchar(255)   NOT NULL,
    `price`         decimal(19, 2) NOT NULL,
    `restaurant_id` bigint(20)     NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKp4b7e36gck7975p51raud8juk` (`restaurant_id`),
    CONSTRAINT `FKp4b7e36gck7975p51raud8juk` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`),
    UNIQUE KEY `unique_product_name` (`name`)
) ENGINE = InnoDB;
INSERT INTO `product`
VALUES (1, 1, 'Delicious potato', 'Potato', 25.00, 1),
       (2, 1, 'Nhami', 'Nhami', 5.00, 2),
       (3, 1, 'Burger Mara', 'Burger', 8.00, 3),
       (4, 1, 'Chocolate', 'Chocolate', 3.00, 4);

DROP TABLE IF EXISTS `restaurant_payment_method`;
CREATE TABLE `restaurant_payment_method`
(
    `restaurant_id`     bigint(20) NOT NULL,
    `payment_method_id` bigint(20) NOT NULL,
    KEY `FK5dxd5cjhjqf8eai6xugad3e1g` (`payment_method_id`),
    KEY `FKbjuwyavt07p2uihdqt6jtmkyj` (`restaurant_id`),
    CONSTRAINT `FK5dxd5cjhjqf8eai6xugad3e1g` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`id`),
    CONSTRAINT `FKbjuwyavt07p2uihdqt6jtmkyj` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`),
    UNIQUE KEY `unique_restaurant_payment_method_relationship` (`restaurant_id`, `payment_method_id`)
) ENGINE = InnoDB;
INSERT INTO `restaurant_payment_method`
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (3, 1),
       (3, 2),
       (3, 3);

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`           bigint(20)   NOT NULL AUTO_INCREMENT,
    `created_date` datetime     NOT NULL,
    `email`        varchar(255) NOT NULL,
    `name`         varchar(255) NOT NULL,
    `password`     varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE = InnoDB;
INSERT INTO `user`
VALUES (1, '2019-12-27 20:18:28', 'bruno.drugowick@gmail.com', 'drugowick', 'password'),
       (2, '2019-12-27 20:18:28', 'brunodrugowick@gmail.com', 'drugowick2', 'password');

DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group`
(
    `user_id`  bigint(20) NOT NULL,
    `group_id` bigint(20) NOT NULL,
    KEY `FKri78m7uqr3nq44xs48hid5xx2` (`group_id`),
    KEY `FK1c1dsw3q36679vaiqwvtv36a6` (`user_id`),
    CONSTRAINT `FK1c1dsw3q36679vaiqwvtv36a6` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKri78m7uqr3nq44xs48hid5xx2` FOREIGN KEY (`group_id`) REFERENCES `permission` (`id`),
    UNIQUE KEY `unique_user_group_relationship` (`group_id`, `user_id`)
) ENGINE = InnoDB;
