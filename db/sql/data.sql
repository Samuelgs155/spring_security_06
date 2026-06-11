/*
insert into users (username, password, enabled) VALUES
                                                    ('admin', 'to_be_encoded', true),
                                                    ('user', 'to_be_encoded', true);

insert into authorities (username, authority) VALUES
                                                  ('admin', 'admin'),
                                                  ('user', 'user');
*/

/*
insert into customers (email, pwd, rol) VALUES
                                            ('super_user@gmail.com', 'to_be_encoded', 'admin'),
                                            ('basic_user@gmail.com', 'to_be_encoded', 'user');
 */

insert into customers (email, pwd) VALUES
                                       ('account@custom.com', 'to_be_encoded'),
                                       ('cards@custom.com', 'to_be_encoded'),
                                       ('loans@custom.com', 'to_be_encoded'),
                                       ('balance@custom.com', 'to_be_encoded');

insert into roles(role_name, description, id_customer) VALUES
                                                           ('VIEW_ACCOUNT', 'can view account endpoint', 1),
                                                           ('VIEW_CARDS', 'can view cards endpoint', 2),
                                                           ('VIEW_LOANS', 'can view loans endpoint', 3),
                                                           ('VIEW_BALANCE', 'can view balance endpoint', 4);