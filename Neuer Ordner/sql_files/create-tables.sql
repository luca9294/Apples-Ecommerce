CREATE TABLE customer
(
  customer_id serial NOT NULL,
  salutation character varying(16) NOT NULL,
  firstname character varying(32) NOT NULL,
  lastname character varying(32) NOT NULL,
  country character varying(16) NOT NULL,
  province character varying(32) NOT NULL,
  city character varying(32) NOT NULL,
  street character varying(64) NOT NULL,
  street_no character varying(5) NOT NULL,
  zip character varying(8) NOT NULL,
  email character varying(40) NOT NULL,
  pwd   character varying(30) NOT NULL,
  CONSTRAINT customer_pkey PRIMARY KEY (customer_id) --trivial primary key
);

CREATE TABLE category
(
  category_id serial NOT NULL,
  product_id integer NOT NULL,
  title character varying(32) NOT NULL,
  CONSTRAINT category_pkey PRIMARY KEY (category_id) --trivial primary key
);

CREATE TABLE products
(
  product_id serial NOT NULL,
  category_id integer NOT NULL,
  title character varying(32) NOT NULL,
  summary character varying(32) NOT NULL,
  description text NOT NULL,
  price INTEGER NOT NULL,
  price_type INTEGER NOT NULL, -- 1 per kg, 2 per piece
  CONSTRAINT product_pkey PRIMARY KEY (product_id), --trivial primary key
  CONSTRAINT product_category_fkey FOREIGN KEY (category_id)
      REFERENCES category (category_id) MATCH SIMPLE      --accept also partial filled columns
      ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE purchase
(
  purchase_id serial NOT NULL,
  customer_id integer NOT NULL,
  product_id integer NOT NULL,
  total integer NOT NULL,
  purchase_date date NOT NULL,
  purchase_status integer NOT NULL, --can be accepted, processed, refused, shipped
  CONSTRAINT purchase_pkey PRIMARY KEY (purchase_id), --trivial primary key
  CONSTRAINT purchase_customer_fkey FOREIGN KEY (customer_id) --foreign key of an N-1 relation to customer
      REFERENCES customer (customer_id) MATCH SIMPLE      --accept also partial filled columns
      ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT purchase_product_fkey FOREIGN KEY (product_id)
      REFERENCES products (product_id) MATCH SIMPLE      --accept also partial filled columns
      ON UPDATE CASCADE ON DELETE RESTRICT        --keep statuses actual, but we dont want to allow to 
);



CREATE TABLE quantity_as
(
  product_id integer NOT NULL,
  purchase_id integer NOT NULL,
  CONSTRAINT quantity_as_pkey PRIMARY KEY (product_id, purchase_id), --primary key out of foreign keys
  CONSTRAINT purchase_id_product_id_fkey FOREIGN KEY (product_id)    --this is a N-M relation
      REFERENCES products (product_id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE,                              
  CONSTRAINT quantity_as_purchase_id_fkey FOREIGN KEY (purchase_id)  --also apply changes automatically
      REFERENCES purchase (purchase_id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE                               --delete tuples if any of the keys is deleted
);


CREATE TABLE cart
(
  cart_id serial NOT NULL,
  product_id integer NOT NULL,
  total integer NOT NULL,
  cart_date date NOT NULL,
  CONSTRAINT cart_pkey PRIMARY KEY (cart_id), --trivial primary key
  CONSTRAINT cart_product_fkey FOREIGN KEY (product_id) --foreign key of an N-1 relation to customer
      REFERENCES products (product_id) MATCH SIMPLE      --accept also partial filled columns
      ON UPDATE CASCADE ON DELETE RESTRICT 
);


CREATE TABLE quantity_as_cart
(
  product_id integer NOT NULL,
  cart_id integer NOT NULL,
  CONSTRAINT quantity_as_pkey PRIMARY KEY (product_id, cart_id), --primary key out of foreign keys
  CONSTRAINT cart_id_product_id_fkey FOREIGN KEY (product_id)    --this is a N-M relation
      REFERENCES products (product_id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE,                              
  CONSTRAINT quantity_as_purchase_id_fkey FOREIGN KEY (purchase_id)  --also apply changes automatically
      REFERENCES purchase (purchase_id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE                               --delete tuples if any of the keys is deleted
);



