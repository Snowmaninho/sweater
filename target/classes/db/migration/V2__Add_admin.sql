insert into usr (id, username, password, active)
   values (1, 'admin', '$2y$08$qUVCE4A82kdAIcw5.fzsxOLp7R16xa2LYPa32wXR/oRIQyZif6CaW', true);

insert into user_role (user_id, roles)
   values (1, 'USER'), (1, 'ADMIN');