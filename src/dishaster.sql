drop database dishaster; #comment this out as needed
create database dishaster;
use dishaster;

create table Restaurant
(restaurant_id int PRIMARY KEY,
name varchar(30),
type varchar(30),
address varchar(100),
city varchar(30));

create table Serves
(restaurant_id int,
food varchar(100),
price double,
FOREIGN KEY (restaurant_id) REFERENCES Restaurant(restaurant_id));

create table User
(id int PRIMARY KEY,
name varchar(30),
likes varchar(30),
type varchar(30));

create table Review
(review_id int PRIMARY KEY,
reviewer_id int REFERENCES User(id),
restaurant_id int,
type varchar(30),
dish varchar(100) REFERENCES Serves(food),
rating int,
created DATE);

create table Wishlist
(id int REFERENCES User(id),
restaurant_id int,
dish varchar(100) REFERENCES Serves(food));

insert into Restaurant values(1, 'In \'n Out', 'fast food', '1159 N Rengstorff Ave', 'Mountain View');
insert into Restaurant values(2, 'Krispy Kreme', 'dessert', '2146 Leghorn St', 'Mountain View');
insert into Restaurant values(3, 'Carl\'s Jr', 'fast food', '15 S 1st St', 'San Jose');

insert into Serves values(2, 'donuts', 4.99);
insert into Serves values(1, 'hamburger', 2.99);
insert into Serves values(1, 'cheeseburger', 3.99);
insert into Serves values(3, 'hamburger', 4.99);

insert into User values(1, 'Matthew', 'fast food', 'admin');
