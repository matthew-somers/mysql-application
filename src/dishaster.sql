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
food varchar(100) REFERENCES Serves(food),
rating int,
created DATE);

create table Wishlist
(id int REFERENCES User(id),
restaurant_id int,
food varchar(100) REFERENCES Serves(food));


### TRIGGERS

# Removes item from wishlist after reviewing
DROP TRIGGER IF EXISTS RemoveReviewedWishlist;
DELIMITER |
CREATE TRIGGER RemoveReviewedWishlist
AFTER INSERT ON Review
FOR EACH ROW
BEGIN
	DELETE FROM Wishlist
	WHERE NEW.reviewer_id = id
	  AND NEW.restaurant_id = restaurant_id
	  AND NEW.dish = dish;
END|
DELIMITER ;

# Checks if review to be added has valid values
DROP TRIGGER IF EXISTS ValidReview;
DELIMITER |
CREATE TRIGGER ValidReview
BEFORE INSERT ON Review
FOR EACH ROW
BEGIN
	IF NEW.rating > 0
	AND NEW.rating <= 5
	AND NEW.restaurant_id EXISTS
		(SELECT restaurant_id
		 FROM Restaurant)
	AND NEW.food EXISTS
		(SELECT food
		 FROM Serves
		 WHERE restaurant_id = NEW.restaurant_id)
	THEN
		INSERT INTO Review VALUES
			(NEW.review_id,
			 NEW.reviewer_id,
			 NEW.restaurant_id,
			 NEW.food,
			 NEW.rating,
			 NEW.created);
	END IF;
END|
DELIMITER ;


### Stored Procedures

# Look up which stores sells a specific food
DELIMITER //
CREATE PROCEDURE foodLookUp
(IN foodToLook VARCHAR(50), OUT storeName VARCHAR(50))
BEGIN
SELECT name INTO storeName
FROM Restaurant join Serves using(restaurant_id)
WHERE food = foodToLook;
END //
DELIMITER ;

# Find which stores sell a specific food the cheapest
DELIMITER //
CREATE PROCEDURE cheapFoodLookUp
(IN foodToLook VARCHAR(50), OUT storeName VARCHAR(50))
BEGIN
SELECT name INTO storeName
FROM Restaurant join Serves using(restaurant_id)
WHERE food = foodToLook
AND price IN (SELECT min(price)
FROM serves
WHERE food = foodToLook);
END //
DELIMITER ;

insert into Restaurant values(1, 'In \'n Out', 'fast food', '1159 N Rengstorff Ave', 'Mountain View');
insert into Restaurant values(2, 'Krispy Kreme', 'dessert', '2146 Leghorn St', 'Mountain View');
insert into Restaurant values(3, 'Carl\'s Jr', 'fast food', '15 S 1st St', 'San Jose');
insert into Restaurant values(4, 'In \'n Out', 'fast food', '550 Newhall Dr', 'San Jose');
insert into Restaurant values(5, 'In \'n Out', 'fast food', '5611 Santa Teresa Blvd', 'San Jose');

insert into Serves values(2, 'donuts', 4.99);
insert into Serves values(1, 'hamburger', 2.99);
insert into Serves values(1, 'cheeseburger', 3.99);
insert into Serves values(3, 'hamburger', 4.99);
insert into Serves values(4, 'hamburger', 2.99);
insert into Serves values(4, 'cheeseburger', 3.99);
insert into Serves values(5, 'hamburger', 2.99);
insert into Serves values(5, 'cheeseburger', 3.99);

insert into User values(1, 'Matthew', 'fast food', 'admin');
insert into User values(2, 'Jeffrey', 'dessert', 'admin');
insert into User values(3, 'Foo', 'fast food', 'user');

insert into Review values(1, 3, 2, 'donuts', 4, '2013-10-21');

insert into Wishlist values(2, 2, 'pizza');