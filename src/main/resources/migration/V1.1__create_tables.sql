CREATE TABLE sido_area (
	id	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY,

	adm_code	VARCHAR(2)	NOT NULL,
	name	VARCHAR(50)	NOT NULL,
	version	TIMESTAMP	NOT NULL
);

CREATE TABLE sigg_area (
	id	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY,

	sido_area_id	BIGINT	NOT NULL,

	adm_code	VARCHAR(5)	NOT NULL,
	name	VARCHAR(50)	NOT NULL,
	version	TIMESTAMP	NOT NULL,

	FOREIGN KEY (sido_area_id) REFERENCES sido_area(id)
);

CREATE TABLE emd_area (
	id	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY,

	sigg_area_id	BIGINT	NOT NULL,

	adm_code	VARCHAR(10)	NOT NULL,
	name	VARCHAR(50)	NOT NULL,
	version	TIMESTAMP	NOT NULL,

	FOREIGN KEY (sigg_area_id) REFERENCES sigg_area(id)
);

CREATE TABLE user (
	id	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY,

	role	VARCHAR(10)	NOT NULL,
	phone_number	VARCHAR(11)	NOT NULL,
	activated	BOOLEAN	NOT NULL,
	rating_score	FLOAT	NOT NULL,

	created_at	TIMESTAMP	NOT NULL,
	modified_at	TIMESTAMP	NOT NULL
);

CREATE TABLE activity_area (
  id	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY,

	user_id	BIGINT	NOT NULL,
	area_id	BIGINT	NOT NULL,

	area_scope	INT	NOT NULL,
	authenticated_at	TIMESTAMP	NULL,

	FOREIGN KEY (user_id) REFERENCES user(id),
	FOREIGN KEY (area_id) REFERENCES emd_area(id)
);

CREATE TABLE category (
	id	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY,

	name	VARCHAR(20)	NOT NULL,

	created_at	TIMESTAMP	NOT NULL,
	modified_at	TIMESTAMP	NOT NULL
);

CREATE TABLE product (
	id	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY,

	seller_id	BIGINT	NOT NULL,
	selling_area_id	BIGINT	NOT NULL,
	category_id	BIGINT	NOT NULL,

	title	VARCHAR(50)	NOT NULL,
	status	VARCHAR(10)	NOT NULL,
	sell_price	INT	NULL,
	view_count	INT	NOT NULL,
	description	TEXT	NOT NULL,
	refreshed_at	TIMESTAMP	NOT NULL,

	created_at	TIMESTAMP	NOT NULL,
	modified_at	TIMESTAMP	NOT NULL,

	FOREIGN KEY (seller_id) REFERENCES user(id),
	FOREIGN KEY (selling_area_id) REFERENCES emd_area(id),
	FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE file (
	id	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY,

	uploader_id	BIGINT	NOT NULL,

	type	VARCHAR(50)	NOT NULL,
	name	VARCHAR(255)	NOT NULL,

	created_at	TIMESTAMP	NOT NULL,
	modified_at	TIMESTAMP	NOT NULL,

	FOREIGN KEY (uploader_id) REFERENCES user(id)
);

CREATE TABLE product_image (
  id	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY,

	product_id	BIGINT	NOT NULL,
	file_id	BIGINT	NOT NULL,

	FOREIGN KEY (product_id) REFERENCES product(id),
	FOREIGN KEY (file_id) REFERENCES file(id)
);

CREATE TABLE wish_list (
	id	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY,

	register_id	BIGINT	NOT NULL,
	product_id	BIGINT	NOT NULL,

	created_at	TIMESTAMP	NOT NULL,
	modified_at	TIMESTAMP	NOT NULL,

	FOREIGN KEY (register_id) REFERENCES user(id),
	FOREIGN KEY (product_id) REFERENCES product(id)
);

CREATE TABLE price_offer (
	id	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY,

	offerer_id	BIGINT	NOT NULL,
	product_id	BIGINT	NOT NULL,

	offered_price	INT	NOT NULL,
	accept_or_not	BOOLEAN	NULL,

	created_at	TIMESTAMP	NOT NULL,
	modified_at	TIMESTAMP	NOT NULL,

	FOREIGN KEY (offerer_id) REFERENCES user(id),
	FOREIGN KEY (product_id) REFERENCES product(id)
);

CREATE TABLE review (
	id	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY,

	author_id	BIGINT	NOT NULL,
	product_id	BIGINT	NOT NULL,

	message	TEXT	NOT NULL,

	created_at	TIMESTAMP	NOT NULL,
	modified_at	TIMESTAMP	NOT NULL,

	FOREIGN KEY (author_id) REFERENCES user(id),
	FOREIGN KEY (product_id) REFERENCES product(id)
);

CREATE TABLE chat_room (
	id	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY,

	product_id	BIGINT	NOT NULL,
	buyer_id	BIGINT	NOT NULL,

	created_at	TIMESTAMP	NOT NULL,
	modified_at	TIMESTAMP	NOT NULL,

	FOREIGN KEY (buyer_id) REFERENCES user(id),
	FOREIGN KEY (product_id) REFERENCES product(id)
);

CREATE TABLE chat_message (
	id	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY,

	chat_room_id	BIGINT	NOT NULL,
	sender_id	BIGINT	NULL,

	message	VARCHAR(500)	NOT NULL,
	read_or_not	BOOLEAN	NOT NULL,

  created_at	TIMESTAMP	NOT NULL,
	modified_at	TIMESTAMP	NOT NULL,

	FOREIGN KEY (sender_id) REFERENCES user(id),
	FOREIGN KEY (chat_room_id) REFERENCES chat_room(id)
);