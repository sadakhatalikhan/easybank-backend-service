create table users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
create table authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);

INSERT IGNORE INTO `users` VALUES ('user', '{bcrypt}$2a$12$k9p.uYaZP2LKWyVLCNBLXutjWeO34l8uqEPU3T0JhQkPWKqkoiTPy', '1');
INSERT IGNORE INTO `authorities` VALUES ('user', 'read');

INSERT IGNORE INTO `users` VALUES ('admin', '{bcrypt}$2a$12$k9p.uYaZP2LKWyVLCNBLXutjWeO34l8uqEPU3T0JhQkPWKqkoiTPy', '1');
INSERT IGNORE INTO `authorities` VALUES ('admin', 'admin');

create table customer (
    id int not null auto_increment,
    email varchar(45) not null,
    pwd varchar(200) not null,
    role varchar(45) not null,
    primary key(id)
);

insert into customer (email, pwd, role) values ('user@gmail.com', '{bcrypt}$2a$12$sKqMhK5enr6Yz8k/0JexBO7Bk/tGTDStr3/fyWpnAX5DzbpEJeE12', 'read');
insert into customer (email, pwd, role) values ('admin@gmail.com', '{bcrypt}$2a$12$sKqMhK5enr6Yz8k/0JexBO7Bk/tGTDStr3/fyWpnAX5DzbpEJeE12', 'admin');

