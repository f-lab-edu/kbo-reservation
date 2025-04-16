CREATE TABLE `stadium` (
    `allocated_seat_count` int NOT NULL,
    `capacity` int NOT NULL,
    `stadium_id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(50) NOT NULL,
    PRIMARY KEY (`stadium_id`)
);

CREATE TABLE `team` (
    `stadium_id` bigint DEFAULT NULL,
    `team_id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(30) NOT NULL,
    PRIMARY KEY (`team_id`),
    KEY `fk_team_stadium` (`stadium_id`),
    CONSTRAINT `fk_team_stadium` FOREIGN KEY (`stadium_id`) REFERENCES `stadium` (`stadium_id`)
);

CREATE TABLE `seat_grade` (
    `price` int NOT NULL,
    `seat_grade_id` bigint NOT NULL AUTO_INCREMENT,
    `stadium_id` bigint NOT NULL,
    `name` varchar(20) NOT NULL,
    `seat_side` varchar(10) NOT NULL,
    PRIMARY KEY (`seat_grade_id`),
    UNIQUE KEY `uk_seat_grade_name_side_stadium` (`name`,`seat_side`,`stadium_id`),
    KEY `fk_seat_grade_stadium` (`stadium_id`),
    CONSTRAINT `fk_seat_grade_stadium` FOREIGN KEY (`stadium_id`) REFERENCES `stadium` (`stadium_id`)
);

CREATE TABLE `seat_block` (
    `seat_count` int NOT NULL,
    `seat_block_id` bigint NOT NULL AUTO_INCREMENT,
    `seat_grade_id` bigint NOT NULL,
    `stadium_id` bigint DEFAULT NULL,
    `name` varchar(20) NOT NULL,
    PRIMARY KEY (`seat_block_id`),
    KEY `fk_seat_block_grade` (`seat_grade_id`),
    KEY `fk_seat_block_stadium` (`stadium_id`),
    CONSTRAINT `fk_seat_block_grade` FOREIGN KEY (`seat_grade_id`) REFERENCES `seat_grade` (`seat_grade_id`),
    CONSTRAINT `fk_seat_block_stadium` FOREIGN KEY (`stadium_id`) REFERENCES `stadium` (`stadium_id`)
);
