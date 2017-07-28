DROP TABLE IF EXISTS category;
CREATE TABLE category (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(50) NOT NULL UNIQUE,
  PRIMARY KEY (`category_id`)
);

DROP TABLE IF EXISTS event_place;
CREATE TABLE event_place (
  `event_place_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(150) NOT NULL,
  `description` VARCHAR(1500) NOT NULL,
  `img_urls` VARCHAR(400) NOT NULL,
  `address` VARCHAR(400) NOT NULL,
  `contacts` VARCHAR(500) NOT NULL,
  `latitude_coordinate` DECIMAL(4,5) NOT NULL,
  `longitude_coordinate` DECIMAL(4,5) NOT NULL,
  PRIMARY KEY (`event_place_id`)
);

DROP TABLE IF EXISTS event;
CREATE TABLE event (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) NOT NULL,
  `event_name` varchar(150) NOT NULL,
  `event_place_id` INT(11) NOT NULL,
  `img_urls` VARCHAR(300) NOT NULL,
  `description` VARCHAR(300) NOT NULL,
  `contact_info` VARCHAR(300) NOT NULL,
  `cost` VARCHAR(300) NOT NULL,
  PRIMARY KEY (`event_id`),
  KEY `FK_event_category` (`category_id`),
  KEY `FK_event_event_place` (`event_place_id`),
  CONSTRAINT `FK_event_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
  CONSTRAINT `FK_event_event_place` FOREIGN KEY (`event_place_id`) REFERENCES `event_place` (`event_place_id`)
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

DROP TABLE IF EXISTS 'user';
CREATE TABLE user (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(150) NOT NULL,
  'user_password' varchar(30) NOT NULL,
  'user_email'  varchar(100) NOT NULL UNIQUE,
  'user_phone_number' varchar(150) NOT NULL,
  'user_service_plan' varchar(30) NOT NULL,
  'user_balance' DECIMAL(10,2) NOT NULL DEFAULT '0.00',
  'user_role' varchar(150) NOT NULL,
  'user_permissions' varchar(500) NOT NULL,
  'user_is_enabled' varchar(5) NOT NULL,
  PRIMARY KEY (`user_id`)
);