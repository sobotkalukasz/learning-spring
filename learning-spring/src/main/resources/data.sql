insert into user_role (role_type) values ("ADMIN");
insert into user_role (role_type) values ("USER");
insert into user (email, password, user_details_id_user_details) values ("admin", "{bcrypt}$2a$10$30iwjvQgQcAyv0uXd8Ir2eJpkwC.NifExsZ/XJ.aUMPMLBKZ6w4ii", null);
insert into user_user_role (user_id, role_id) values (1, 1);
insert into user_user_role (user_id, role_id) values (1, 2);
insert into user (email, password, user_details_id_user_details) values ("tester", "{bcrypt}$2a$10$hkoTf9qrjBmKshtJDbvRAuL9MOQvdQE.MnkSmXAOsLbzGeEV9ErjW", null);
insert into user_user_role (user_id, role_id) values (2, 2);