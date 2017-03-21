DROP TABLE IF EXISTS category;
CREATE TABLE category (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(50) NOT NULL UNIQUE,
  PRIMARY KEY (`category_id`)
);

DROP TABLE IF EXISTS event;
CREATE TABLE IF NOT EXISTS event (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) NOT NULL,
  `event_name` varchar(150) NOT NULL,
  `event_place_name` int(11) NOT NULL,
  PRIMARY KEY (`event_id`),
  KEY `FK_event_category` (`category_id`),
  CONSTRAINT `FK_event_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
);

/*
DROP TABLE IF EXISTS event_place;
CREATE TABLE event_place (
  `event_place_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_place_name` varchar(70) NOT NULL,
  `event_place_address` varchar(150) NOT NULL,
  `category_id` int(11) NOT NULL,
  PRIMARY KEY (`event_place_id`),
  KEY `FK__category` (`category_id`),
  CONSTRAINT `FK__category` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
);

CREATE TABLE IF NOT EXISTS `time_period` (
  `time_period_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) NOT NULL,
  `beggining` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `end` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`time_period_id`),
  KEY `FK__event` (`event_id`),
  CONSTRAINT `FK__event` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`)
);

CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL,
  `user_login` varchar(70) NOT NULL,
  `user_pass` varchar(30) NOT NULL,
  PRIMARY KEY (`user_id`)
);
*/