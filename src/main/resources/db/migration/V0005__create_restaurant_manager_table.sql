drop table if exists `restaurant_manager`;
create table `restaurant_manager`
(
    `restaurant_id` bigint(20) not null,
    `manager_id` bigint(20) not null,

    index `index_restaurant_id` (`restaurant_id`),
    index `index_manager_id` (`manager_id`),
    CONSTRAINT `fk_restaurant-manager_restaurant_id` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`),
    CONSTRAINT `fk_restaurant-manager_manager_id` FOREIGN KEY (`manager_id`) REFERENCES `restaurant` (`id`),
    unique key `unique_restaurant_manager_relationship` (`restaurant_id`, `manager_id`)
) ENGINE = InnoDB
  charset = utf8;
