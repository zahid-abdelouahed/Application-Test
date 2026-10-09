-- Insert into Acteur table
INSERT INTO acteur (nom, prenom, date_naissance) VALUES ('Dupont', 'Jean', '1980-05-15');
INSERT INTO acteur (nom, prenom, date_naissance) VALUES ('Martin', 'Marie', '1985-03-22');
INSERT INTO acteur (nom, prenom, date_naissance) VALUES ('Bernard', 'Pierre', '1978-07-10');
INSERT INTO acteur (nom, prenom, date_naissance) VALUES ('Dubois', 'Sophie', '1990-11-08');
INSERT INTO acteur (nom, prenom, date_naissance) VALUES ('Laurent', 'Thomas', '1982-01-25');
INSERT INTO acteur (nom, prenom, date_naissance) VALUES ('Zahid', 'Abdel', '2003-01-25');

-- Insert into Film table
INSERT INTO film (titre, realisateur, date_sortie, genre) VALUES ('Inception', 'Christopher Nolan', '2010-07-16', 'SCIENCE_FICTION');
INSERT INTO film (titre, realisateur, date_sortie, genre) VALUES ('Le Seigneur des Anneaux', 'Peter Jackson', '2001-12-19', 'FANTASY');
INSERT INTO film (titre, realisateur, date_sortie, genre) VALUES ('Pulp Fiction', 'Quentin Tarantino', '1994-10-14', 'CRIME');
INSERT INTO film (titre, realisateur, date_sortie, genre) VALUES ('Forrest Gump', 'Robert Zemeckis', '1994-07-06', 'DRAMA');
INSERT INTO film (titre, realisateur, date_sortie, genre) VALUES ('Interstellar', 'Christopher Nolan', '2014-11-07', 'SCIENCE_FICTION');

-- Insert into Film_Acteur junction table
INSERT INTO film_acteur (film_id, acteur_id) VALUES (1, 1);
INSERT INTO film_acteur (film_id, acteur_id) VALUES (1, 2);
INSERT INTO film_acteur (film_id, acteur_id) VALUES (2, 3);
INSERT INTO film_acteur (film_id, acteur_id) VALUES (2, 4);
INSERT INTO film_acteur (film_id, acteur_id) VALUES (3, 1);
INSERT INTO film_acteur (film_id, acteur_id) VALUES (4, 5);
INSERT INTO film_acteur (film_id, acteur_id) VALUES (5, 2);
