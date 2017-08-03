DROP TABLE IF EXISTS category;
CREATE TABLE category (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(50) NOT NULL UNIQUE,
  PRIMARY KEY (`category_id`)
);

DROP TABLE IF EXISTS event;
CREATE TABLE event (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) NOT NULL,
  `event_name` varchar(150) NOT NULL,
  `event_place_name` varchar(150) NOT NULL,
  PRIMARY KEY (`event_id`),
  KEY `FK_event_category` (`category_id`),
  CONSTRAINT `FK_event_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
);

DROP TABLE IF EXISTS time_period;
CREATE TABLE time_period (
  `time_period_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) NOT NULL,
  `beginning` timestamp NOT NULL,
  `end` timestamp NOT NULL,
  PRIMARY KEY (`time_period_id`),
  KEY `FK_event` (`event_id`),
  CONSTRAINT `FK_event` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`)
);

DROP TABLE IF EXISTS users;
CREATE TABLE users (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(150) NOT NULL,
  `user_password` varchar(60) NOT NULL,
  `user_email`  varchar(100) NOT NULL UNIQUE,
  `user_phone_number` varchar(150) NOT NULL,
  `user_service_plan` varchar(30) NOT NULL,
  `user_balance` DECIMAL(10,2) NOT NULL,
  `user_role` varchar(150) NOT NULL,
  `user_permissions` varchar(500) NOT NULL,
  `user_is_enabled` varchar(5) NOT NULL,
  PRIMARY KEY (`user_id`)
);

DROP TABLE IF EXISTS our_locations;
CREATE TABLE our_locations (
  `location_id` int(11) NOT NULL AUTO_INCREMENT,
  `city_name` varchar(50) NOT NULL,
  PRIMARY KEY (`location_id`)
);

DROP TABLE IF EXISTS `users_locations_correlation`;
CREATE TABLE `users_locations_correlation` (
  `user_id` int(11) DEFAULT NULL,
  `location_id` int(11) DEFAULT NULL,
  KEY `FK__user` (`user_id`),
  KEY `FK__our_locations` (`location_id`),
  CONSTRAINT `FK__user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FK__our_locations` FOREIGN KEY (`location_id`) REFERENCES `our_locations` (`location_id`)
);

DROP TABLE IF EXISTS event_place;
CREATE TABLE event_place (
  `event_place_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(150) NOT NULL,
  `description` VARCHAR(1500) NOT NULL,
  `img_urls` VARCHAR(700) NOT NULL,
  `icon_url` VARCHAR(200) NOT NULL,
  `address` VARCHAR(400) NOT NULL,
  `contacts` VARCHAR(500) NOT NULL,
  `latitude_coordinate` DECIMAL(10,5) NOT NULL,
  `longitude_coordinate` DECIMAL(10,5) NOT NULL,
  PRIMARY KEY (`event_place_id`)
);

DROP TABLE IF EXISTS `users_event_place_correlation`;
CREATE TABLE `users_event_place_correlation` (
  `user_id` int(11) DEFAULT NULL,
  `event_place_id` int(11) DEFAULT NULL,
  KEY `FK__user1` (`user_id`),
  KEY `FK__event_place` (`event_place_id`),
  CONSTRAINT `FK__user1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FK__event_place` FOREIGN KEY (`event_place_id`) REFERENCES `event_place` (`event_place_id`)
);

DROP TABLE IF EXISTS `user_totals`;
CREATE TABLE `user_totals` (
  `user_totals_id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_role` VARCHAR(11) NULL DEFAULT NULL,
  `location_id` INT(11) NULL DEFAULT NULL,
  `count` INT(11) NULL DEFAULT '0',
  PRIMARY KEY (`user_totals_id`),
  KEY `FK_user_totals_our_locations` (`location_id`),
  CONSTRAINT `FK_user_totals_our_locations` FOREIGN KEY (`location_id`) REFERENCES `our_locations` (`location_id`)
)


