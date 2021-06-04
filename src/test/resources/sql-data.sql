INSERT INTO `customers` (`first_name`, `surname`) VALUES ('jordan', 'harrison');
INSERT INTO `items` (`item_name`, `item_price`) VALUES ('chris', 5.99);
INSERT INTO `items` (`item_name`, `item_price`) VALUES ('Google', 1.99);
INSERT INTO `orders` (`customer_id`,`comments`) VALUES (1, "perrins");
INSERT INTO `orders_item` (`order_id`,`item_id`, `quantity`) VALUES (1, 1, 1);