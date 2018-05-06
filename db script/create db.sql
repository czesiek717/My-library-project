DROP SCHEMA IF EXISTS `my-library-project`;

CREATE SCHEMA `my-library-project`;

use `my-library-project`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `author`;

CREATE TABLE `author` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `last_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `description` varchar(1000) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `image_link` varchar(128) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_polish_ci;

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(70) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `description` varchar(1000) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `image_link` varchar(128) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `rating` double(3,2) NOT NULL DEFAULT '0.00',
  `number_of_ratings` int(11) NOT NULL DEFAULT '0',
  `release_date_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `TITLE_UNIQUE` (`title`),
  KEY `FK_RELEASE_DATE_idx` (`release_date_id`),
  CONSTRAINT `FK_RELEASE_DATE` FOREIGN KEY (`release_date_id`) REFERENCES `release_date` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_polish_ci;

DROP TABLE IF EXISTS `book_author`;

CREATE TABLE `book_author` (
  `book_id` int(11) NOT NULL,
  `author_id` int(11) NOT NULL,
  PRIMARY KEY (`book_id`,`author_id`),
  KEY `FK_BOOK_idx` (`book_id`),
  KEY `FK_AUTHOR` (`author_id`),
  CONSTRAINT `FK_AUTHOR_idx` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_BOOK_idx` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_polish_ci;

DROP TABLE IF EXISTS `book_bookshelf`;

CREATE TABLE `book_bookshelf` (
  `book_id_2` int(11) NOT NULL,
  `bookshelf_id_1` int(11) NOT NULL,
  PRIMARY KEY (`book_id_2`,`bookshelf_id_1`),
  KEY `FK_BOOK_2_idx` (`book_id_2`),
  KEY `FK_BOOKSHELF_1_idx` (`bookshelf_id_1`),
  CONSTRAINT `FK_BOOKSHELF_1` FOREIGN KEY (`bookshelf_id_1`) REFERENCES `bookshelf` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_BOOK_2` FOREIGN KEY (`book_id_2`) REFERENCES `book` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_polish_ci;

DROP TABLE IF EXISTS `bookshelf`;

CREATE TABLE `bookshelf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_polish_ci;

DROP TABLE IF EXISTS `release_date`;

CREATE TABLE `release_date` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `day` int(11) NOT NULL,
  `month` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_polish_ci;

DROP TABLE IF EXISTS `review`;

CREATE TABLE `review` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment` varchar(256) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `book_id_1` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_BOOK_1_idx` (`book_id_1`),
  KEY `FK_USER_idx` (`user_id`),
  CONSTRAINT `FK_BOOK_1` FOREIGN KEY (`book_id_1`) REFERENCES `book` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARACTER SET utf8 COLLATE utf8_polish_ci;

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(15) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `password` varchar(60) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `email` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `role` varchar(45) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `bookshelf_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `USERNAME_UNIQUE` (`username`),
  UNIQUE KEY `EMAIL_UNIQUE` (`email`),
  KEY `FK_BOOKSHELF_idx` (`bookshelf_id`),
  CONSTRAINT `FK_BOOKSHELF` FOREIGN KEY (`bookshelf_id`) REFERENCES `bookshelf` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARACTER SET utf8 COLLATE utf8_polish_ci;

DROP TABLE IF EXISTS `user_rated_book`;

CREATE TABLE `user_rated_book` (
  `user_id_1` int(11) NOT NULL,
  `book_id_3` int(11) NOT NULL,
  PRIMARY KEY (`user_id_1`,`book_id_3`),
  KEY `FK_USER_1_idx` (`user_id_1`),
  KEY `FK_BOOK_3_idx` (`book_id_3`),
  CONSTRAINT `FK_BOOK_3` FOREIGN KEY (`book_id_3`) REFERENCES `book` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_USER_1` FOREIGN KEY (`user_id_1`) REFERENCES `user` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_polish_ci;

SET FOREIGN_KEY_CHECKS = 1;
