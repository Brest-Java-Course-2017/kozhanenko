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