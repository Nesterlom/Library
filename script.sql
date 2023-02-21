# use library;

# create table users
# (
# id int primary key auto_increment,
# name varchar(30) not null unique,
# password varchar(100) not null
# );
#
# create table books
# (
# id int primary key auto_increment,
# name varchar(30) not null,
# author varchar(30) not null,
# year int not null
# );
#
# create table savedBooks
# (
# userId int not null,
# bookId int not null,
# foreign key(userId) references users(id),
# foreign key(bookId) references books(id)
# );
#

#select * from users;
#select * from books;
#select bookId from savedBooks where userId = 1
#select * from savedBooks;

#drop table users;
#drop table books;
#drop table savedBooks;

