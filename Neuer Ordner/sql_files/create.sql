/******************************************************************************
 * System Security 2017
 * Luca Pellegrini - luca.pellegrini@stud-inf.unibz.it
 * Sebastiano Santini - sebastiano.santini@stud-inf.unibz.it
 * Julian Sanin - julian.sanin@stud-inf.unibz.it
 ******************************************************************************/

-- Table: address
CREATE TABLE user
(
  user_id serial NOT NULL unique,
  salutation character varying(16) NOT NULL,
  firstname character varying(32) NOT NULL,
  lastname character varying(32) NOT NULL,
  country character varying(16) NOT NULL,
  province character varying(32) NOT NULL,
  city character varying(32) NOT NULL,
  street character varying(64) NOT NULL,
  "streetNo" character varying(5) NOT NULL,
  zip character varying(8) NOT NULL,
  title character varying(16),
  CONSTRAINT user_pkey PRIMARY KEY (user_id) --trivial primary key
);

-- Table: auction_status
CREATE TABLE auction_status
(
  auction_status_id serial NOT NULL,
  name character varying(32) NOT NULL,
  CONSTRAINT auction_status_pkey PRIMARY KEY (auction_status_id) --trivial primary key
);

-- Table: auction
CREATE TABLE auction
(
  auction_id serial NOT NULL,
  description text NOT NULL,
  start_price integer NOT NULL,
  express_price integer,
  amount integer NOT NULL,
  date_added date NOT NULL,
  date_modified date,
  online boolean NOT NULL,
  image text, --path to one or more pictures 
  viewed integer NOT NULL DEFAULT 0, --counter how many times this auction was clicked by users
  auction_status integer NOT NULL, --auction can online/offline or also have some other status code for several problems
  CONSTRAINT auction_pkey PRIMARY KEY (auction_id), --trivial primary key
  CONSTRAINT auction_auction_status_fkey FOREIGN KEY (auction_status) --foreign key of an N-1 relation to action_status
      REFERENCES auction_status (auction_status_id) MATCH SIMPLE      --accept also partial filled columns
      ON UPDATE CASCADE ON DELETE RESTRICT                            --keep statuses actual, but we don't want to allow to 
);                                                                    --delete one until every auction has another status

-- Table: category
CREATE TABLE category
(
  category_id serial NOT NULL,
  parent_id integer,
  icon text,  --path to the icon of the category
  status boolean NOT NULL, --sets the category online/offline. Category gets shown or not
  name character varying(32) NOT NULL,
  CONSTRAINT category_pkey PRIMARY KEY (category_id) --trivial primary key
)
;

-- Table: categorized_as
CREATE TABLE categorized_as
(
  auction_id integer NOT NULL,
  category_id integer NOT NULL,
  CONSTRAINT categorized_as_pkey PRIMARY KEY (auction_id, category_id), --primary key out of foreign keys
  CONSTRAINT categorized_as_auction_id_fkey FOREIGN KEY (auction_id)    --this is a N-M relation between
      REFERENCES auction (auction_id) MATCH SIMPLE                      --category and auction
      ON UPDATE CASCADE ON DELETE CASCADE,                              
  CONSTRAINT categorized_as_category_id_fkey FOREIGN KEY (category_id)  --also apply changes automatically
      REFERENCES category (category_id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE                               --delete tuples if any of the keys is deleted
);

-- Table: customer_group
CREATE TABLE customer_group
(
  customer_group_id serial NOT NULL,
  name character varying(32) NOT NULL,
  CONSTRAINT customer_group_pkey PRIMARY KEY (customer_group_id) --trivial primary key
);

-- Table: client
CREATE TABLE client
(
  customer_id serial NOT NULL,
  login character varying(32) NOT NULL,
  pw character varying(64) NOT NULL,
  url text, --to the clients website (if he is for example a company)
  ip character varying(45), --the ip of the client if there are some problems
  birthday date NOT NULL,
  language_id character(2) NOT NULL,
  "customerNo" integer NOT NULL, --the internal number of the client
  count_offers integer NOT NULL DEFAULT 0, --number of auctions a client made
  count_orders integer NOT NULL DEFAULT 0, --number of auctions a client won
  email text NOT NULL,
  comment text,
  "lastLogin" date, --in combantion with id-field in case of problems
  customer_group_id integer NOT NULL,
  CONSTRAINT client_pkey PRIMARY KEY (customer_id),                        --trivial primary key
  CONSTRAINT client_customer_group_id_fkey FOREIGN KEY (customer_group_id) --foreign key of an N-1 relation to customer_group
      REFERENCES customer_group (customer_group_id) MATCH SIMPLE           --Every user MUST have a Group, but MUST NOT be
      ON UPDATE CASCADE ON DELETE RESTRICT                                 --deleted just 'cause a customer group being deleted 
);

-- Table: bet
CREATE TABLE bet
(
  bet_id integer NOT NULL,
  amount integer NOT NULL,
  "timestamp" bigint NOT NULL, --we use int because it's easier to format the date!
  client_id integer NOT NULL,
  auction_id integer NOT NULL,
  CONSTRAINT bet_pkey PRIMARY KEY (bet_id),               --trivial primary key
  CONSTRAINT bet_auction_id_fkey FOREIGN KEY (auction_id) --foreign key of an N-1 relation to auction
      REFERENCES auction (auction_id) MATCH SIMPLE        --accept also partial filled columns
      ON UPDATE CASCADE ON DELETE CASCADE,                --also apply changes automatically
  CONSTRAINT bet_client_id_fkey FOREIGN KEY (client_id)   --foreign key of an N-1 relation to auction
      REFERENCES client (customer_id) MATCH SIMPLE        --also apply changes and reset to null on delete
      ON UPDATE CASCADE ON DELETE SET NULL                --so if only the user gets deleted, the bet stays 
);                                                        --anonymously for the history stored.
														  
-- Table: fav_list
CREATE TABLE fav_list
(
  list_id serial NOT NULL,
  date_added date NOT NULL,
  date_modified date,
  name character varying(32) NOT NULL,
  comment text,
  client_id integer NOT NULL,
  CONSTRAINT fav_list_pkey PRIMARY KEY (list_id),            --trivial primary key
  CONSTRAINT fav_list_client_id_fkey FOREIGN KEY (client_id) --foreign key of an N-1 relation to client
      REFERENCES client (customer_id) MATCH SIMPLE           
      ON UPDATE CASCADE ON DELETE CASCADE                    --apply changes automatically
);

-- Table: contains
-- which fav_list contains which auctions
CREATE TABLE contains
(
  list_id integer NOT NULL,
  auction_id integer NOT NULL,
  CONSTRAINT contains_pkey PRIMARY KEY (list_id, auction_id),  --primary key out of foreign keys
  CONSTRAINT contains_auction_id_fkey FOREIGN KEY (auction_id) --this is a N-M relation between
      REFERENCES auction (auction_id) MATCH SIMPLE             --fav_list and auction
      ON UPDATE CASCADE ON DELETE CASCADE,                     
  CONSTRAINT contains_list_id_fkey FOREIGN KEY (list_id)       --apply changes automatically
      REFERENCES fav_list (list_id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
);

-- Table: has_address
CREATE TABLE has_address
(
  address_id integer NOT NULL,
  client_id integer NOT NULL,
  CONSTRAINT has_address_pkey PRIMARY KEY (address_id, client_id), --primary key out of foreign keys
  CONSTRAINT has_address_address_id_fkey FOREIGN KEY (address_id)  --this is a N-M relation between
      REFERENCES address (address_id) MATCH SIMPLE                 --address and client
      ON UPDATE CASCADE ON DELETE CASCADE,                         
  CONSTRAINT has_address_client_id_fkey FOREIGN KEY (client_id)    --apply changes automatically
      REFERENCES client (customer_id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
);

-- Table: payment_method
CREATE TABLE payment_method
(
  payment_method_id serial NOT NULL,
  comment text,
  dues integer NOT NULL,
  name character varying(32) NOT NULL,
  CONSTRAINT payment_method_pkey PRIMARY KEY (payment_method_id) --trivial primary key
);

-- Table: review
CREATE TABLE review
(
  review_id serial NOT NULL,
  text text NOT NULL,
  date_added date NOT NULL,
  date_modified date,
  rating integer NOT NULL,
  customer_id integer,
  auction_id integer NOT NULL,
  CONSTRAINT review_pkey PRIMARY KEY (review_id),            --trivial primary key
  CONSTRAINT customer_id_fkey FOREIGN KEY (customer_id)      --foreign key of an N-1 relation to client and review
      REFERENCES client (customer_id) MATCH SIMPLE           --apply all changes automatically
      ON UPDATE CASCADE ON DELETE SET NULL,                  --unless a user is deleted, then the review will still
  CONSTRAINT review_auction_id_fkey FOREIGN KEY (auction_id) --stay and (anonymously) available, so it doesn't get lost
      REFERENCES auction (auction_id) MATCH SIMPLE           --just because a user is deleted.
      ON UPDATE CASCADE ON DELETE CASCADE                    
);

-- Table: shipment_method
CREATE TABLE shipment_method
(
  shipment_method_id serial NOT NULL,
  name character varying(32) NOT NULL,
  dues integer NOT NULL,
  comment text,
  CONSTRAINT shipment_method_pkey PRIMARY KEY (shipment_method_id) --trivial primary key
);

-- Table: has_payment_method
CREATE TABLE has_payment_method
(
  auction_id integer NOT NULL,
  payment_method_id integer NOT NULL,
  CONSTRAINT has_payment_method_pkey PRIMARY KEY (auction_id, payment_method_id),      --primary key out of foreign keys
  CONSTRAINT has_payment_method_auction_id_fkey FOREIGN KEY (auction_id)               --this is a N-M relation between
      REFERENCES auction (auction_id) MATCH SIMPLE                                     --auction and payment_method
      ON UPDATE CASCADE ON DELETE CASCADE,                                             --also apply all changes automatically
  CONSTRAINT has_payment_method_payment_method_id_fkey FOREIGN KEY (payment_method_id) 
      REFERENCES payment_method (payment_method_id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
);

-- Table: has_shipment_method
CREATE TABLE has_shipment_method
(
  auction_id integer NOT NULL,
  shipment_id integer NOT NULL,
  CONSTRAINT has_shipment_method_pkey PRIMARY KEY (auction_id, shipment_id), --primary key out of foreign keys
  CONSTRAINT has_shipment_method_auction_id_fkey FOREIGN KEY (auction_id)    --this is a N-M relation between
      REFERENCES auction (auction_id) MATCH SIMPLE                           --auction and shipment_method
      ON UPDATE CASCADE ON DELETE CASCADE,                                   --also apply all changes automatically
  CONSTRAINT has_shipment_method_shipment_id_fkey FOREIGN KEY (shipment_id)  
      REFERENCES shipment_method (shipment_method_id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
);

-- Table: tag
CREATE TABLE tag
(
  tag_id serial NOT NULL,
  name character varying(32) NOT NULL,
  meta integer, --additional meta information of the tag
  CONSTRAINT tag_pkey PRIMARY KEY (tag_id) --trivial primary key
);

-- Table: tagged_as
CREATE TABLE tagged_as
(
  tag_id integer NOT NULL,
  auction_id integer NOT NULL,
  CONSTRAINT tagged_as_pkey PRIMARY KEY (tag_id, auction_id),   --primary key out of foreign keys
  CONSTRAINT tagged_as_auction_id_fkey FOREIGN KEY (auction_id) --this is a N-M relation between
      REFERENCES auction (auction_id) MATCH SIMPLE              --auction and tag
      ON UPDATE CASCADE ON DELETE CASCADE,                      --also apply all changes automatically
  CONSTRAINT tagged_as_tag_id_fkey FOREIGN KEY (tag_id)         
      REFERENCES tag (tag_id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
);