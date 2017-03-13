INSERT  INTO `category` (`category_id`, `category_name`) VALUES
        (1, 'Театр'),
        (2, 'Кино'),
        (3, 'Вечеринка'),
        (4, 'Open air'),
        (5, 'Музеи');

INSERT INTO `event` (`event_id`, `event_name`, `category_id`, `event_place_id`) VALUES
        (1, 'Премьера "Весёлый вдовец"', 1, 4),
        (2, 'Три толстяка', 1, 4),
        (3, 'ОДНОКЛАССНИЦЫ. НОВЫЙ ПОВОРОТ', 2, 2),
        (4, 'КОНГ: ОСТРОВ ЧЕРЕПА 3Д', 2, 2),
        (5, 'ЛОГАН: РОСОМАХА 2Д', 2, 1);

REPLACE INTO `event_place` (`event_place_id`, `event_place_name`, `event_place_address`, `category_id`) VALUES
        (1, 'Кинотеатр "МИР"', 'г. Брест, ул. Пушкинская, 7', 2),
        (2, 'Кинотеатр "Беларусь"', 'г. Брест, ул. Советская, 62', 2),
        (3, 'Музей «Спасённые художественные ценности»', 'г.Брест, ул.Ленина, 39', 5),
        (4, '«БРЕСТСКИЙ АКАДЕМИЧЕСКИЙ ТЕАТР ДРАМЫ', 'г.Брест, ул.Ленина, 21', 1);

REPLACE INTO `time_period` (`time_period_id`, `event_id`, `beggining`, `end`) VALUES
        (1, 5, '2017-03-13 22:49:49', '2017-03-13 23:55:00');

REPLACE INTO `user` (`user_id`, `user_name`, `user_login`, `user_pass`) VALUES
        (1, 'andrei', 'andrei@mail.ru', 'somepass'),
        (2, 'vasiliy', 'vasiliy@gmail.com', 'pass1'),
        (3, 'sergei', 'sergei@tut.by', 'pass2');