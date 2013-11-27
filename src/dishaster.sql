#drop database dishaster; #comment this out as needed
create database dishaster;
use dishaster;

create table Restaurant
(restaurant_id int PRIMARY KEY AUTO_INCREMENT,
name varchar(30),
type varchar(30),
address varchar(100) UNIQUE,
city varchar(30));

create table Serves
(restaurant_id int,
food varchar(100),
price numeric(10,2),
FOREIGN KEY (restaurant_id) REFERENCES Restaurant(restaurant_id));

create table User
(id int PRIMARY KEY AUTO_INCREMENT,
name varchar(30),
likes varchar(30),
type varchar(30));

create table Review
(review_id int PRIMARY KEY AUTO_INCREMENT,
reviewer_id int REFERENCES User(id),
restaurant_id int,
food varchar(100) REFERENCES Serves(food),
rating int,
updatedAt DATE,
UNIQUE `UniqueReview` (reviewer_id, restaurant_id, food));

create table Wishlist
(id int REFERENCES User(id),
restaurant_id int,
food varchar(100) REFERENCES Serves(food),
UNIQUE `UniqueWishlist` (id, restaurant_id, food));

create table ReviewArchive
(review_id int PRIMARY KEY AUTO_INCREMENT,
reviewer_id int REFERENCES User(id),
restaurant_id int,
food varchar(100) REFERENCES Serves(food),
rating int,
updatedAt DATE);


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
	  AND NEW.food = food;
END|
DELIMITER ;

# Checks if review to be added has valid values
DROP TRIGGER IF EXISTS ValidReview;
DELIMITER |
CREATE TRIGGER ValidReview
BEFORE INSERT ON Review
FOR EACH ROW
BEGIN
	IF NEW.rating <= 0
	OR NEW.rating > 5
	OR NEW.restaurant_id NOT IN
		(SELECT restaurant_id
		 FROM Restaurant)
	OR NEW.food NOT IN
		(SELECT food
		 FROM Serves
		 WHERE Serves.restaurant_id = NEW.restaurant_id)
	THEN
		SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'Not valid review';
	END IF;
END|
DELIMITER ;


### Stored Procedures

# Archive the review table
DROP PROCEDURE IF EXISTS UpdateArchive;
DELIMITER |
CREATE PROCEDURE UpdateArchive (cutoffdate DATE)
BEGIN
	DELETE FROM ReviewArchive WHERE updatedAt >= cutoffdate;
	INSERT INTO ReviewArchive(SELECT * FROM Review WHERE updatedAt >= cutoffdate);
END |
DELIMITER ;