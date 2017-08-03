INSERT  INTO `category` (`category_id`, `category_name`) VALUES
        (1, 'Театр'),
        (2, 'Кино'),
        (3, 'Вечеринка'),
        (4, 'Open air'),
        (5, 'Музеи');

INSERT INTO `event` (`event_id`, `category_id`, `event_name`, `event_place_name`) VALUES
        (1, 1, 'Премьера "Весёлый вдовец"', '«БРЕСТСКИЙ АКАДЕМИЧЕСКИЙ ТЕАТР ДРАМЫ»'),
        (2, 1, 'Три толстяка', '«БРЕСТСКИЙ АКАДЕМИЧЕСКИЙ ТЕАТР ДРАМЫ»'),
        (3, 2,' ОДНОКЛАССНИЦЫ. НОВЫЙ ПОВОРОТ', 'Кинотеатр "Беларусь"'),
        (4, 2, 'КОНГ: ОСТРОВ ЧЕРЕПА 3Д', 'Кинотеатр "Беларусь"'),
        (5, 2, 'ЛОГАН: РОСОМАХА 2Д', 'Кинотеатр "МИР"'),
        (6, 5, '"Камни Беларуси"', '"Краеведческий музей"');


INSERT INTO `time_period` (`time_period_id`, `event_id`, `beginning`, `end`) VALUES
        (1, 4, '2017-03-13 22:49:49', '2017-03-13 23:55:00'),
        (2, 5, '2017-03-14 22:49:49', '2017-03-14 23:55:00'),
        (3, 2, '2017-03-14 18:15:00', '2017-03-14 19:15:00'),
        (4, 1, '2017-03-14 10:00:00', '2017-03-14 12:00:00'),
        (5, 3, '2017-03-21 22:00:00', '2017-03-21 22:50:00'),
        (6, 3, '2017-03-14 11:00:00', '2017-03-14 12:00:00');

INSERT INTO `our_locations` (`location_id`, `city_name`) VALUES
        (1, 'Брест'),
        (2, 'Гродно'),
        (3, 'Минск'),
        (4, 'Витебск'),
        (5, 'Гомель'),
        (6, 'Могилёв');

INSERT INTO `users` (`user_id`, `user_name`, `user_password`, `user_email`, `user_phone_number`,
                     `user_service_plan`, `user_balance`, `user_role`, `user_permissions`, `user_is_enabled`) VALUES
        (1, 'Василий', '$2a$10$WCZcoy1wRZyuM51bA2LYveYQj1bxkEmiIny53VS0J9l8r01LX5TvO', 'sss@mail.ru', '55555555', NULL, NULL, 'SUPER_ADMIN', NULL, 'true'),
        (2, 'Алекс', '$2a$10$WCZcoy1wRZyuM51bA2LYveYQj1bxkEmiIny53VS0J9l8r01LX5TvO', 'fff@mail.ru', '66666666', NULL, NULL, 'CITY_ADMIN', NULL, 'true'),
        (3, 'Макс', '$2a$10$WCZcoy1wRZyuM51bA2LYveYQj1bxkEmiIny53VS0J9l8r01LX5TvO', 'ggg@mail.ru', '66666666', NULL, NULL, 'LOCAL_ADMIN', NULL, 'true'),
        (4, 'Ольга', '$2a$10$WCZcoy1wRZyuM51bA2LYveYQj1bxkEmiIny53VS0J9l8r01LX5TvO', 'ccc@mail.ru', '66666666', NULL, NULL, 'LOCAL_ADMIN', NULL, 'true');

INSERT INTO `users_locations_correlation` (`user_id`, `location_id`) VALUES
        (2, 1),
        (3, 1),
        (4, 3);

INSERT INTO `user_totals` (`user_totals_id`, `user_role`, `location_id`, `count`) VALUES
        (1, 'SUPER_ADMIN', NULL, 1),
        (2, 'CITY_ADMIN', 1, 1),
        (3, 'LOCAL_ADMIN', 1, 1),
        (4, 'LOCAL_ADMIN', 3, 1);
