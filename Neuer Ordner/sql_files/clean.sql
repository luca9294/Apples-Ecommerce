/******************************************************************************
 * ds_group9
 * Patrick Alber - patrick.alber@stud-inf.unibz.it
 * Benjamin Groeber - benjamin.groeber@stud-inf.unibz.it
 * Julian Sanin - julian.sanin@stud-inf.unibz.it
 ******************************************************************************/

-- delete first the 1-n and n-m tables
DROP TABLE categorized_as CASCADE;
DROP TABLE has_address CASCADE;
DROP TABLE contains CASCADE;
DROP TABLE has_payment_method CASCADE;
DROP TABLE has_shipment_method CASCADE;
DROP TABLE tagged_as CASCADE;
-- delete other tables
DROP TABLE bet CASCADE;
DROP TABLE review CASCADE;
DROP TABLE auction CASCADE;
DROP TABLE auction_status;
DROP TABLE address;
DROP TABLE category;
DROP TABLE fav_list CASCADE;
DROP TABLE client CASCADE;
DROP TABLE customer_group;
DROP TABLE payment_method;
DROP TABLE shipment_method;
DROP TABLE tag;