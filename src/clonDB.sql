CREATE TABLE customers (
	customer_id INTEGER PRIMARY KEY AUTOINCREMENT,
	name VARCHAR(140) NOT NULL,
	company VARCHAR(140) NOT NULL,
	department VARCHAR(140) NOT NULL,
	email VARCHAR(140) UNIQUE NOT NULL ,
	phone INTEGER UNIQUE,
	cel_phone INTEGER UNIQUE,
	street VARCHAR(140) NOT NULL,
	colony VARCHAR(140) NOT NULL,
	city VARCHAR(64) NOT NULL,
	state VARCHAR(64) NOT NULL,
	zip VARCHAR(8) NOT NULL
)

CREATE TABLE items (
	item_id INTEGER PRIMARY KEY AUTOINCREMENT,
	name VARCHAR(140) NOT NULL,
	unit_price NUMERIC(10,2) NOT NULL
)

CREATE TABLE orders (
	order_id INTEGER PRIMARY KEY AUTOINCREMENT,
	customer_id INTEGER NOT NULL,
	order_date DATETIME DEFAULT (DATETIME('now','localtime')),
	attn VARCHAR(140) NOT NULL,
	supplier VARCHAR(140) NOT NULL,
	bill_to VARCHAR(140) NOT NULL,
	comments VARCHAR(140) NOT NULL,
	review INTEGER NOT NULL,
	last_update_date DATETIME DEFAULT (DATETIME('now','localtime')),
	subtotal NUMERIC(10,2) NOT NULL,
	total NUMERIC(10,2) NOT NULL
)

CREATE TABLE orders_details (
	order_detail_id INTEGER PRIMARY KEY AUTOINCREMENT,
	order_id INTEGER NOT NULL,
	item_id INTEGER NOT NULL,
	um INTEGER NOT NULL,
	quantity INTEGER NOT NULL,
	total_price NUMERIC(10,2) NOT NULL
)

--Sentencias
SELECT * FROM customers
SELECT * FROM customers WHERE cel_phone =? OR phone = ?
SELECT * FROM customers WHERE ? = LIKE?%;
SELECT * FROM items
SELECT * FROM items WHERE item_id = ?
SELECT * FROM orders INNER JOIN orders_details AS ords ON orders.order_id = ords.order_id
SELECT * FROM orders INNER JOIN orders_details AS ords 
	ON orders.order_id = ords.order_id WHERE order_id = ?

INSERT INTO customers(name, company, department,
            email, phone, cel_phone, street, colony, city, state, zip) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
INSERT INTO items(name,unit_price) VALUES(?, ?)
INSERT INTO orders(customer_id, attn, dupplier, bill_to, comments,review, subtotal, total) 
	VALUES(?, ?, ?, ?, ?, ?, ?, ?)



UPDATE customers SET name=?, company=?, department=?, email=?, phone=?, cel_phone=?, street=?, colony=?, city=?, state=?, zip=? customer_id=?
UPDATE items SET name = ?, unit_price = ? WHERE item_id = ?


DELETE FROM items WHERE item_id = ?
DELETE FROM customers WHERE customer_id = ?
DELETE * FROM orders AS o INNER JOIN orders_details AS od 
	ON o.order_id = od.order_id WHERE o.order_id = 1

DELETE a,b FROM actions AS a INNER JOIN books AS b ON a.book_id = b.book_id
	WHERE a.book_id = 1