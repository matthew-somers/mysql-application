INSERT INTO Restaurant(name, type, address, city) VALUES('In \'n Out', 'fast food', '1159 N Rengstorff Ave', 'Mountain View');
INSERT INTO Restaurant(name, type, address, city) VALUES('Krispy Kreme', 'dessert', '2146 Leghorn St', 'Mountain View');
INSERT INTO Restaurant(name, type, address, city) VALUES('Carl\'s Jr', 'fast food', '15 S 1st St', 'San Jose');
INSERT INTO Restaurant(name, type, address, city) VALUES('In \'n Out', 'fast food', '550 Newhall Dr', 'San Jose');
INSERT INTO Restaurant(name, type, address, city) VALUES('In \'n Out', 'fast food', '5611 Santa Teresa Blvd', 'San Jose');
INSERT INTO Restaurant(name, type, address, city) VALUES('La Note', 'french', '2377 Shattuck Ave', 'Berkeley');
INSERT INTO Restaurant(name, type, address, city) VALUES('TK Noodle', 'chinese', '261 E William St', 'San Jose');
INSERT INTO Restaurant(name, type, address, city) VALUES('Pizza Antica', 'pizza', '334 Santana Row', 'San Jose');
INSERT INTO Restaurant(name, type, address, city) VALUES('Tofoo Com Chay', 'vietnamese', '388 E Santa Clara St', 'San Jose');

insert into Serves values(2, 'donuts', 4.99);
insert into Serves values(1, 'hamburger', 2.99);
insert into Serves values(1, 'cheeseburger', 3.99);
insert into Serves values(3, 'hamburger', 4.99);
insert into Serves values(4, 'hamburger', 2.99);
insert into Serves values(4, 'cheeseburger', 3.99);
insert into Serves values(5, 'hamburger', 2.99);
insert into Serves values(5, 'cheeseburger', 3.99);

insert into User(name, likes, type) values('Matthew', 'fast food', 'admin');
insert into User(name, likes, type) values('Jeffrey', 'dessert', 'admin');
insert into User(name, likes, type) values('Foo', 'fast food', 'user');

insert into Review(reviewer_id, restaurant_id, food, rating, created) values(3, 2, 'donuts', 4, '2013-10-21');

insert into Wishlist values(3, 2, 'pizza');