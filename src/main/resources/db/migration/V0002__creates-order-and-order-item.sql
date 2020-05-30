DROP TABLE IF EXISTS `order_`;
CREATE TABLE `order_`
(
    `id`                     bigint(20)     NOT NULL AUTO_INCREMENT,
    `subtotal`               decimal(19, 2) NOT NULL,
    `delivery_fee`           decimal(19, 2) NOT NULL,
    `total`                  decimal(19, 2) NOT NULL,
    `created_date`           datetime       NOT NULL,
    `confirmation_date`      datetime    DEFAULT NULL,
    `cancellation_date`      datetime    DEFAULT NULL,
    `delivery_date`          datetime    DEFAULT NULL,
    `payment_method_id`      bigint(20)     NOT NULL,
    `restaurant_id`          bigint(20)     NOT NULL,
    `client_id`              bigint(20)     NOT NULL,
    `status`                 varchar(20)    NOT NULL,
    `address_address_line_1` varchar(80)    NOT NULL,
    `address_address_line_2` varchar(80) DEFAULT NULL,
    `address_postal_code`    varchar(12)    NOT NULL,
    `address_region`         varchar(60)    NOT NULL,
    `address_city_id`        bigint(20)     NOT NULL,

    PRIMARY KEY (`id`),
    INDEX `index_order__payment_method_id` (`payment_method_id`),
    INDEX `index_order__restaurant_id` (`restaurant_id`),
    INDEX `index_order__client_id` (`client_id`),
    INDEX `index_order__address_city_id` (`address_city_id`),
    CONSTRAINT `fk_order__payment_method_id` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`id`),
    CONSTRAINT `fk_order__restaurant_id` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`),
    CONSTRAINT `fk_order__client_id` FOREIGN KEY (`client_id`) REFERENCES `user` (`id`),
    CONSTRAINT `fk_order__address_city_id` FOREIGN KEY (`address_city_id`) REFERENCES `city` (`id`),
    UNIQUE KEY `unique_id` (`id`)
) ENGINE = InnoDB
  charset = utf8;

DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item`
(
    `id`          bigint(20)     NOT NULL AUTO_INCREMENT,
    `amount`      smallint(6)    NOT NULL,
    `unit_price`  decimal(19, 2) NOT NULL,
    `total_price` decimal(19, 2) NOT NULL,
    `notes`       varchar(80) DEFAULT NULL,
    `product_id`  bigint(20)     NOT NULL,
    `order_id`    bigint(20)     NOT NULL,

    PRIMARY KEY (`id`),
    INDEX `index_order_item_product_id` (`product_id`),
    INDEX `index_order_item_order_id` (`order_id`),
    CONSTRAINT `fk_order_item_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
    CONSTRAINT `fk_order_item_order_id` FOREIGN KEY (`order_id`) REFERENCES `order_` (`id`)
) ENGINE = InnoDB
  charset = utf8;
