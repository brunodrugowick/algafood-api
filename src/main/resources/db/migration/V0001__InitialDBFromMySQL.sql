-- This dump includes test data because this project is NOT INTENDED FOR PRODUCTION. This is a teaching/learning project.
-- The MySQL dump was made simpler to work with MySQL and H2 (for demonstration purposes on Heroku, without the need to provide a database).

DROP TABLE IF EXISTS `province`;
CREATE TABLE `province`
(
    `id`           bigint(20)  NOT NULL AUTO_INCREMENT,
    `abbreviation` varchar(2)  NOT NULL,
    `name`         varchar(60) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_province_name_abbreviation` (`name`, `abbreviation`)
) ENGINE = InnoDB
  charset = utf8;

DROP TABLE IF EXISTS `city`;
CREATE TABLE `city`
(
    `id`          bigint(20)  NOT NULL AUTO_INCREMENT,
    `name`        varchar(60) NOT NULL,
    `province_id` bigint(20)  NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_city_name_province` (`name`, `province_id`),
    CONSTRAINT `fk_province_id` FOREIGN KEY (`province_id`) REFERENCES `province` (`id`)
) ENGINE = InnoDB
  charset = utf8;

DROP TABLE IF EXISTS `cuisine`;
CREATE TABLE `cuisine`
(
    `id`   bigint(20)  NOT NULL AUTO_INCREMENT,
    `name` varchar(60) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_cuisine_name` (`name`)
) ENGINE = InnoDB
  charset = utf8;

DROP TABLE IF EXISTS `group_`;
CREATE TABLE `group_`
(
    `id`   bigint(20)  NOT NULL AUTO_INCREMENT,
    `name` varchar(60) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_group_name` (`name`)
) ENGINE = InnoDB
  charset = utf8;

DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT,
    `description` varchar(255) NOT NULL,
    `name`        varchar(60)  NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_permission_name` (`name`)
) ENGINE = InnoDB
  charset = utf8;

DROP TABLE IF EXISTS `group_permission`;
CREATE TABLE `group_permission`
(
    `group_id`      bigint(20) NOT NULL,
    `permission_id` bigint(20) NOT NULL,
    INDEX `index_permission_id` (`permission_id`),
    INDEX `index_group_id` (`group_id`),
    CONSTRAINT `fk_group-permission_group_id` FOREIGN KEY (`group_id`) REFERENCES `group_` (`id`),
    CONSTRAINT `fk_group-permission_permission_id` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`),
    UNIQUE KEY `unique_group_permission_relationship` (`group_id`, `permission_id`)
) ENGINE = InnoDB
  charset = utf8;

DROP TABLE IF EXISTS `payment_method`;
CREATE TABLE `payment_method`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT,
    `description` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_payment_method_description` (`description`)
) ENGINE = InnoDB
  charset = utf8;

DROP TABLE IF EXISTS `restaurant`;
CREATE TABLE `restaurant`
(
    `id`                     bigint(20)     NOT NULL AUTO_INCREMENT,
    `address_address_line_1` varchar(80) DEFAULT NULL,
    `address_address_line_2` varchar(80) DEFAULT NULL,
    `address_postal_code`    varchar(12) DEFAULT NULL,
    `address_region`         varchar(60) DEFAULT NULL,
    `created_date`           datetime       NOT NULL,
    `delivery_fee`           decimal(19, 2) NOT NULL,
    `name`                   varchar(60)    NOT NULL,
    `updated_date`           datetime       NOT NULL,
    `address_city_id`        bigint(20)  DEFAULT NULL,
    `cuisine_id`             bigint(20)     NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `index_restaurant_address_city_id` (`address_city_id`),
    INDEX `index_restaurant_cuisine_id` (`cuisine_id`),
    CONSTRAINT `fk_restaurant_address_city_id` FOREIGN KEY (`address_city_id`) REFERENCES `city` (`id`),
    CONSTRAINT `fk_restaurant_cuisine_id` FOREIGN KEY (`cuisine_id`) REFERENCES `cuisine` (`id`),
    UNIQUE KEY `unique_restaurant_name` (`name`)
) ENGINE = InnoDB
  charset = utf8;

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`
(
    `id`            bigint(20)     NOT NULL AUTO_INCREMENT,
    `active`        bit(1)         NOT NULL,
    `description`   varchar(255)   NOT NULL,
    `name`          varchar(60)    NOT NULL,
    `price`         decimal(19, 2) NOT NULL,
    `restaurant_id` bigint(20)     NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `index_product_restaurant_id` (`restaurant_id`),
    CONSTRAINT `fk_product_restaurant_id` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`),
    UNIQUE KEY `unique_product_name` (`name`)
) ENGINE = InnoDB
  charset = utf8;

DROP TABLE IF EXISTS `restaurant_payment_method`;
CREATE TABLE `restaurant_payment_method`
(
    `restaurant_id`     bigint(20) NOT NULL,
    `payment_method_id` bigint(20) NOT NULL,
    INDEX `index_restaurant-payment-method_payment_method_id` (`payment_method_id`),
    INDEX `index_restaurant-payment-method_restaurant_id` (`restaurant_id`),
    CONSTRAINT `fk_restaurant-payment-method_payment_method_id` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`id`),
    CONSTRAINT `fk_restaurant-payment-method_restaurant_id` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`),
    UNIQUE KEY `unique_restaurant_payment_method_relationship` (`restaurant_id`, `payment_method_id`)
) ENGINE = InnoDB
  charset = utf8;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`           bigint(20)  NOT NULL AUTO_INCREMENT,
    `created_date` datetime    NOT NULL,
    `email`        varchar(80) NOT NULL,
    `name`         varchar(60) NOT NULL,
    `password`     varchar(60) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_user_email` (`email`)
) ENGINE = InnoDB
  charset = utf8;

DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group`
(
    `user_id`  bigint(20) NOT NULL,
    `group_id` bigint(20) NOT NULL,
    INDEX `index_user-group_group_id` (`group_id`),
    INDEX `index_user-group_user_id` (`user_id`),
    CONSTRAINT `fk_user_group_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `fk_user_group_group_id` FOREIGN KEY (`group_id`) REFERENCES `permission` (`id`),
    UNIQUE KEY `unique_user_group_relationship` (`group_id`, `user_id`)
) ENGINE = InnoDB
  charset = utf8;
