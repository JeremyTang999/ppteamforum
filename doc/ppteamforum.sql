create database if not exists ppteamforum;

use ppteamforum;

create table user(
    id int primary key auto_increment,
    username varchar(64) unique not null,
    register_time bigint ,
    role varchar(32) not null default 'in_audit'
);
-- role包括：in_audit(待验证)，user(普通用户)，admin(管理员)

create table user_info(
    user_id int primary key,
    gender varchar(16) not null default 'secrecy',
    avatar_path varchar(255),
    personal_signature text
);
-- gender包括：male(男)，female(女)，other(其他)，secrecy(保密)

create table user_security(
    user_id int primary key,
    password varchar(64),
    question1 varchar(255),
    answer1 varchar(255),
    question2 varchar(255),
    answer2 varchar(255),
    question3 varchar(255),
    answer3 varchar(255)
);

create table article(
    id int primary key auto_increment,
    topic varchar(16) not null default 'sim',
    title varchar(255) not null,
    content text,
    author_id int,
    creation_time bigint,
    read_count int not null default 0,
    like_count int not null default 0
);
-- topic包括：sim(模拟赛车)，real(真实赛车)