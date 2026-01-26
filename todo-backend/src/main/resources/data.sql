insert into users(id, email, username, password)
values(1000, 'ai@as.com','aiden', '$2a$10$F0G2eqcO9RwFQaGbVIsKBOalX60w6YZitoVEt1GIC7Oscy5.pjg2G');

insert into users(id, email, username, password)
values(1001, 'ai@as.com','piny', '$2a$10$F0G2eqcO9RwFQaGbVIsKBOalX60w6YZitoVEt1GIC7Oscy5.pjg2G');

insert into todos(id, user_id, title, completed)
values(10001, 1001, 'hello world', false);

insert into todos(id, user_id, title, completed)
values(10002, 1001, 'Nice world', false);

insert into todos(id, user_id, title, completed)
values(10003, 1001, 'Awesome world', false);

insert into todos(id, user_id, title, completed)
values(10004, 1001, 'Fantasy world', false);

insert into todos(id, user_id, title, completed)
values(10005, 1000, 'Thank you world', false);

insert into todos(id, user_id, title, completed)
values(10006, 1000, 'Happy world', false);