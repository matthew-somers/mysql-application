INSERT INTO Restaurant(name, type, address, city) VALUES('In \'n Out', 'fast food', '1159 N Rengstorff Ave', 'Mountain View');
INSERT INTO Restaurant(name, type, address, city) VALUES('Krispy Kreme', 'dessert', '2146 Leghorn St', 'Mountain View');
INSERT INTO Restaurant(name, type, address, city) VALUES('Carl\'s Jr', 'fast food', '15 S 1st St', 'San Jose');
INSERT INTO Restaurant(name, type, address, city) VALUES('In \'n Out', 'fast food', '550 Newhall Dr', 'San Jose');
INSERT INTO Restaurant(name, type, address, city) VALUES('In \'n Out', 'fast food', '5611 Santa Teresa Blvd', 'San Jose');
INSERT INTO Restaurant(name, type, address, city) VALUES('La Note', 'french', '2377 Shattuck Ave', 'Berkeley');
INSERT INTO Restaurant(name, type, address, city) VALUES('TK Noodle', 'chinese', '261 E William St', 'San Jose');
INSERT INTO Restaurant(name, type, address, city) VALUES('Pizza Antica', 'pizza', '334 Santana Row', 'San Jose');
INSERT INTO Restaurant(name, type, address, city) VALUES('Tofoo Com Chay', 'vietnamese', '388 E Santa Clara St', 'San Jose');
INSERT INTO Restaurant(name, type, address, city) VALUES('Loves Cupcakes', 'bakery', '85 E San Fernando St', 'San Jose');

insert into Serves values(1, 'hamburger', 2.99);
insert into Serves values(1, 'cheeseburger', 3.99);
insert into Serves values(2, 'donuts', 4.99);
insert into Serves values(3, 'hamburger', 4.99);
insert into Serves values(4, 'hamburger', 2.99);
insert into Serves values(4, 'cheeseburger', 3.99);
insert into Serves values(5, 'hamburger', 2.99);
insert into Serves values(5, 'cheeseburger', 3.99);
insert into Serves values(6, 'pancakes', 12.00);
insert into Serves values(6, 'french toast', 8.00);
insert into Serves values(6, 'omelette', 7.00);
insert into Serves values(6, 'potatoes', 5.00);
insert into Serves values(6, 'brioche', 5.00);
insert into Serves values(7, 'chow fun', 6.50);
insert into Serves values(7, 'fried rice', 6.50);
insert into Serves values(8, 'mushroom pizza', 11.00);
insert into Serves values(8, 'hamburger', 10.00);
insert into Serves values(9, 'fried rice', 6.00);
insert into Serves values(9, 'chicken', 6.50);
insert into Serves values(9, 'noodles', 6.00);
insert into Serves values(10, 'strawberry cupcake', 3.00);
insert into Serves values(10, 'chocolate cupcake', 3.00);
insert into Serves values(10, 'mini strawberry cupcake', 1.75);

insert into User(name, likes, type) values('Matthew', 'fast food', 'admin');
insert into User(name, likes, type) values('Jeffrey', 'dessert', 'admin');
insert into User(name, likes, type) values('Kevin', 'pizza', 'user');

insert into Review(reviewer_id, restaurant_id, food, rating, created) values(1, 2, 'donuts', 4, '2013-10-21');
insert into Review(reviewer_id, restaurant_id, food, rating, created) values(2, 3, 'hamburger', 3, '2013-10-22');
insert into Review(reviewer_id, restaurant_id, food, rating, created) values(3, 6, 'pancakes', 2, '2013-10-05');
insert into Review(reviewer_id, restaurant_id, food, rating, created) values(3, 6, 'french toast', 3, '2013-10-05');
insert into Review(reviewer_id, restaurant_id, food, rating, created) values(3, 9, 'noodles', 1, '2013-10-10');
insert into Review(reviewer_id, restaurant_id, food, rating, created) values(3, 10, 'strawberry cupcake', 5, '2013-10-18');

insert into Wishlist values(3, 8, 'mushroom pizza');
insert into Wishlist values(3, 10, 'chocolate cupcake');