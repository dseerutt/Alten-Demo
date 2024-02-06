CREATE TABLE product (
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(10),
  name VARCHAR(50),
  description VARCHAR(255),
  category VARCHAR(50),
  image VARCHAR(255)
);

CREATE TABLE product_inventory (
  product_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  price INT,
  quantity INT,
  inventory_status VARCHAR(50),
  rating DOUBLE,
  FOREIGN KEY (product_id) REFERENCES product(id)
);