INSERT INTO role ( name ) VALUES
     ('ADMIN'),
     ('USER'),
     ('SUPPLIER');

insert into app_user (role_id, created_at, updated_at, pseudo, email, password)
values  (1, '2021-03-26 15:58:47.513000', '2026-03-26 15:58:47.513103', 'Utilisateur A', 'a@a.com', 'root'),
        (2, '2026-10-26 15:58:47.513000', '2026-03-26 15:58:47.513103', 'Utilisateur C', 'c@c.com', 'root'),
        (3, '2023-03-26 15:58:47.513000', '2026-03-26 15:58:47.513103', 'Utilisateur B', 'b@b.com', 'root'),
        (3, '2023-03-26 15:58:47.513000', '2026-03-26 15:58:47.513103', 'Utilisateur D', 'd@d.com', 'root'),
        (null, '2023-03-26 15:58:47.513000', '2026-03-26 15:58:47.513103', 'Utilisateur E', 'e@e.com', 'root');


INSERT INTO component (serial_number, name, description, loaner_id) VALUES
    ('ECR0000138', 'Ecran de salle', 'Rayure sur le coté', null),
    ('ECR0000139', 'Ecran de salle', 'Bon état', 2);

INSERT INTO tag (name) VALUES
    ('détérioré'),
    ('réservé');

INSERT INTO tag_component (component_id, tag_id)  VALUES
    (1, 1),
    (1, 2),
    (2, 2);

INSERT INTO skill (name)  VALUES
      ('developpeur'),
      ('concepteur'),
      ('designer');

INSERT INTO acknowledge (user_id ,skill_id , level )  VALUES
      (2, 1, 5),
      (2, 2, 1),
      (3, 1, 2);
