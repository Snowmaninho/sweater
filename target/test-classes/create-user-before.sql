SET FOREIGN_KEY_CHECKS=0;

delete from user_role;
delete from usr;

insert into usr(id, active, password, username) values
(1, true, '$2y$08$qUVCE4A82kdAIcw5.fzsxOLp7R16xa2LYPa32wXR/oRIQyZif6CaW', 'admin'),
(2, true, '$2y$08$qUVCE4A82kdAIcw5.fzsxOLp7R16xa2LYPa32wXR/oRIQyZif6CaW', 'mike');

insert into user_role(user_id, roles) values
(1, 'USER'), (1, 'ADMIN'),
(2, 'USER');