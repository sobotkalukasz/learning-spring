insert into user_role (role_type) values ("ADMIN");
insert into user_role (role_type) values ("USER");
insert into user (login, password, user_details_id_user_details) values ("admin", "admin", null);
insert into user_user_role (user_id, role_id) values (1, 1);
insert into user_user_role (user_id, role_id) values (1, 2);
insert into user (login, password, user_details_id_user_details) values ("tester", "tester", null);
insert into user_user_role (user_id, role_id) values (2, 2);