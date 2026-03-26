INSERT INTO role ( name ) VALUES
     ('ADMIN'),
     ('USER'),
     ('SUPPLIER');

INSERT INTO app_user ( email, password, pseudo, role_id ) VALUES
    ('a@a.com', 'root', 'Utilisateur A', 1),
    ('b@b.com', 'root', 'Utilisateur B', 3),
    ('c@c.com', 'root', 'Utilisateur C', 2);

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
