CREATE TABLE `friends` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `pwd` varchar(200) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
);

INSERT  INTO `friends` (`email`, `pwd`, `role`) VALUES ('ajit@example.com', '{noop}ajit@12@345', 'read');
INSERT  INTO `friends` (`email`, `pwd`, `role`) VALUES ('samar@example.com', '{noop}samar@12@345', 'admin');


create table users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
create table authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);

INSERT IGNORE INTO `users` VALUES ('user', '{noop}EazyBytes@12345', '1');
INSERT IGNORE INTO `authorities` VALUES ('user', 'read');


drop table `authorities`;
drop table `users`;
drop table `customer`;


CREATE TABLE `friend` (
  `friend_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `mobile_number` varchar(20) NOT NULL,
  `pwd` varchar(500) NOT NULL,
  `role` varchar(100) NOT NULL,
  `create_dt` date DEFAULT NULL,
  PRIMARY KEY (`friend_id`)
);

INSERT INTO `friend` (`name`,`email`,`mobile_number`, `pwd`, `role`,`create_dt`)
VALUES ('Ajit','ajit@example.com','5334122365', '{bcrypt}$2a$12$c.f0S/vy/PvTOKApxtfdf.i/ZVQKnxjDlwAlMMez7DgQvEIuot0Om', 'admin',CURDATE());


CREATE TABLE `authorities` (
  `id` int NOT NULL AUTO_INCREMENT,
  `friend_id` int NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `friend_id` (`friend_id`),
  CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`friend_id`) REFERENCES `friend` (`friend_id`)
);

INSERT INTO `authorities` (`friend_id`, `name`)
 VALUES (1, 'VIEWACCOUNT');

INSERT INTO `authorities` (`friend_id`, `name`)
 VALUES (1, 'VIEWCARDS');

 INSERT INTO `authorities` (`friend_id`, `name`)
  VALUES (1, 'VIEWLOANS');

 INSERT INTO `authorities` (`friend_id`, `name`)
   VALUES (1, 'VIEWBALANCE');



 INSERT INTO `authorities` (`friend_id`, `name`)
  VALUES (2, 'ROLE_ADMIN');

 INSERT INTO `authorities` (`friend_id`, `name`)
  VALUES (2, 'ROLE_USER');


