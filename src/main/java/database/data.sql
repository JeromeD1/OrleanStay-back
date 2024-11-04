-- Insertion des informations personnelles
INSERT INTO Personal_information (firstname, lastname, email, phone, address, city, country, zipcode) VALUES
('John', 'Doe', 'john.doe@example.com', '1234567890', '123 Main St', 'Vancouver', 'Canada', '45 500'),
('Jane', 'Smith', 'jane.smith@example.com', '0987654321', '456 Elm St', 'Toronto', 'Canada', '45 500'),
('Alice', 'Johnson', 'alice.johnson@example.com', '1122334455', '789 Oak St', 'Montreal', 'Canada', '45 500'),
('Bob', 'Brown', 'bob.brown@example.com', '5544332211', '321 Pine St', 'Calgary', 'Canada', '45 500'),
('Jerome', 'Bigs', 'jerome.bigs@example.com', '06 20 45 50 24', '18 rue des haricots', 'Rouen', 'France', '45 500'),
('Albert', 'Mousquetaire', 'albert.mousquetaire@example.com', '07 50 24 78 32', '25 rue des Schmits', 'Paris', 'France', '45 500');

-- Insertion des utilisateurs avec les rôles OWNER, ADMIN et USER
INSERT INTO Utilisateur (role, login, password, creation_date, personal_information_id) VALUES
('OWNER', 'owner1@example.com', 'password1', CURRENT_TIMESTAMP, (SELECT id FROM Personal_information WHERE email = 'john.doe@example.com')),
('OWNER', 'owner2@example.com', 'password2', CURRENT_TIMESTAMP, (SELECT id FROM Personal_information WHERE email = 'jane.smith@example.com')),
('ADMIN', 'admin@example.com', 'password3', CURRENT_TIMESTAMP, (SELECT id FROM Personal_information WHERE email = 'alice.johnson@example.com')),
('USER', 'user@example.com', 'password4', CURRENT_TIMESTAMP, (SELECT id FROM Personal_information WHERE email = 'bob.brown@example.com'));

--Insertion de travellers
INSERT INTO Traveller (personal_information_id, utilisateur_id) VALUES
((SELECT id FROM Personal_information WHERE email = 'john.doe@example.com'), (SELECT id FROM Utilisateur WHERE login = 'owner1@example.com')),
((SELECT id FROM Personal_information WHERE email = 'jane.smith@example.com'), (SELECT id FROM Utilisateur WHERE login = 'owner2@example.com')),
((SELECT id FROM Personal_information WHERE email = 'alice.johnson@example.com'), (SELECT id FROM Utilisateur WHERE login = 'admin@example.com')),
((SELECT id FROM Personal_information WHERE email = 'bob.brown@example.com'), (SELECT id FROM Utilisateur WHERE login = 'user@example.com')),
((SELECT id FROM Personal_information WHERE email = 'jerome.bigs@example.com'), NULL),
((SELECT id FROM Personal_information WHERE email = 'albert.mousquetaire@example.com'), NULL);


-- Creation de 3 discounts
INSERT INTO Discount (week, month, is_week_activated, is_month_activated) VALUES (0.95, 0.90, TRUE, TRUE);
INSERT INTO Discount (week, month, is_week_activated, is_month_activated) VALUES (0.95, 0.90, FALSE, FALSE);
INSERT INTO Discount (week, month, is_week_activated, is_month_activated) VALUES (0.95, 0.90, FALSE, TRUE);

-- Création de 3 appartements
INSERT INTO Appartment (name, description, address, zipcode, city, google_map_url, distance_city_center, distance_train, distance_tram, night_price, caution, menage_court_sejour, menage_long_sejour, menage_longue_duree, type, is_active, discount_id, owner_id) VALUES
('Ambert 1', 'Studio (28m²) avec mezzanine et terrasse', '59 rue d Ambert', '45000', 'Orléans', 'https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d10632.483433538391!2d1.9153119798149716!3d47.90269288714803!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x47e4fb548918cbb3%3A0x9f97d3eb2f127df7!2s59%20Rue%20d%27Ambert%2C%2045000%20Orl%C3%A9ans!5e0!3m2!1sfr!2sfr!4v1707226894953!5m2!1sfr!2sfr', 'A 10 minutes en tram', 'A 10 minutes en tram', 'A 50m du tram', 50, 300, 30, 50, 50, 'SAISONNIER', true, 1, 1),
('Ambert 4', 'Grand studio (32m²) + mezzanine', '59 rue d Ambert', '45000', 'Orléans', 'https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d10632.483433538391!2d1.9153119798149716!3d47.90269288714803!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x47e4fb548918cbb3%3A0x9f97d3eb2f127df7!2s59%20Rue%20d%27Ambert%2C%2045000%20Orl%C3%A9ans!5e0!3m2!1sfr!2sfr!4v1707226894953!5m2!1sfr!2sfr', 'A 10 minutes en tram', 'A 10 minutes en tram', 'A 50m du tram', 50, 300, 30, 50, 50, 'SAISONNIER', true, 1, 1),
('Ambert 5', 'Studio (20m²) + mezzanine', '59 rue d Ambert', '45000', 'Orléans', 'https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d10632.483433538391!2d1.9153119798149716!3d47.90269288714803!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x47e4fb548918cbb3%3A0x9f97d3eb2f127df7!2s59%20Rue%20d%27Ambert%2C%2045000%20Orl%C3%A9ans!5e0!3m2!1sfr!2sfr!4v1707226894953!5m2!1sfr!2sfr', 'A 10 minutes en tram', 'A 10 minutes en tram', 'A 50m du tram', 50, 300, 30, 50, 50, 'SAISONNIER', true, 1, 1);


-- Création d'infos liées aux appartements
INSERT INTO Appartment_info (info, Appartment_id, position_order) VALUES ('Vue sur la mer', 1, 1);
INSERT INTO Appartment_info (info, Appartment_id, position_order) VALUES ('Wi-Fi gratuit', 1, 2);
INSERT INTO Appartment_info (info, Appartment_id, position_order) VALUES ('Parking inclus', 1, 3);
INSERT INTO Appartment_info (info, Appartment_id, position_order) VALUES ('Animaux acceptés', 1, 4);

INSERT INTO Appartment_info (info, Appartment_id, position_order) VALUES ('Proche du métro', 2, 1);
INSERT INTO Appartment_info (info, Appartment_id, position_order) VALUES ('Cuisine équipée', 2, 2);
INSERT INTO Appartment_info (info, Appartment_id, position_order) VALUES ('Balcon', 2, 3);
INSERT INTO Appartment_info (info, Appartment_id, position_order) VALUES ('Salle de sport', 2, 4);
INSERT INTO Appartment_info (info, Appartment_id, position_order) VALUES ('Piscine', 2, 5);

INSERT INTO Appartment_info (info, Appartment_id, position_order) VALUES ('Service de ménage', 3, 1);
INSERT INTO Appartment_info (info, Appartment_id, position_order) VALUES ('Climatisation', 3, 2);
INSERT INTO Appartment_info (info, Appartment_id, position_order) VALUES ('Sécurité 24/7', 3, 3);
INSERT INTO Appartment_info (info, Appartment_id, position_order) VALUES ('Terrasse privée', 3, 4);
INSERT INTO Appartment_info (info, Appartment_id, position_order) VALUES ('Chauffage central', 3, 5);
INSERT INTO Appartment_info (info, Appartment_id, position_order) VALUES ('Isolation phonique', 3, 6);


-- Création de photos liées aux appartements
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_horizontal-w1000/v1720443884/image0_knsnk4.jpg', 1, 1);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_horizontal-w1000/v1720444200/image1_rphzfz.jpg', 2, 1);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_horizontal-w1000/v1720444417/image2_mjgajz.jpg', 3, 1);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_horizontal-w1000/v1720444466/image3_b4fnfu.jpg', 4, 1);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_vertical-w1000/v1720444523/image4_uy8iij.jpg', 5, 1);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_horizontal-w1000/v1720444998/image5_gdvh2y.jpg', 6, 1);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_horizontal-w1000/v1720445144/image6_emqf3w.jpg', 7, 1);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_horizontal-w1000/v1720445194/image7_lqyt1k.jpg', 8, 1);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_vertical-w1000/v1720445268/image11_aokkmy.jpg', 9, 1);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_vertical-w1000/v1720445343/image12_gw9pnu.jpg', 10, 1);

INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_horizontal-w1000/v1720446069/image0_e5hsku.jpg', 1, 2);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_horizontal-w1000/v1720446129/image1_agdm20.jpg', 2, 2);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_horizontal-w1000/v1720446167/image2_itswcn.jpg', 3, 2);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_vertical-w1000/v1720446234/image4_fu87bi.jpg', 4, 2);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_vertical-w1000/v1720446292/image5_xzgvrp.jpg', 5, 2);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_horizontal-w1000/v1720446365/image6_qs92fn.jpg', 6, 2);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_horizontal-w1000/v1720446396/image7_vcf1hq.jpg', 7, 2);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_vertical-w1000/v1720446433/image8_pgk4ak.jpg', 8, 2);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_horizontal-w1000/v1720446467/image9_qsu7vd.jpg', 9, 2);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_horizontal-w1000/v1720446528/image10_xiin1g.jpg', 10, 2);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_vertical-w1000/v1720445268/image11_aokkmy.jpg', 11, 2);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_vertical-w1000/v1720445343/image12_gw9pnu.jpg', 12, 2);



INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_horizontal-w1000/v1720446790/image1_fyj4tj.jpg', 1, 3);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_horizontal-w1000/v1720446819/image2_uv23cn.jpg', 2, 3);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_horizontal-w1000/v1720446846/image3_sqgysp.jpg', 3, 3);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_vertical-w1000/v1720446877/image4_hhkyh3.jpg', 4, 3);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_vertical-w1000/v1720446908/image5_gegzwc.jpg', 5, 3);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_horizontal-w1000/v1720446467/image9_qsu7vd.jpg', 6, 3);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_horizontal-w1000/v1720446528/image10_xiin1g.jpg', 7, 3);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_vertical-w1000/v1720445268/image11_aokkmy.jpg', 8, 3);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('https://res.cloudinary.com/dqizqxdgn/image/upload/t_vertical-w1000/v1720445343/image12_gw9pnu.jpg', 9, 3);


-- Création de réservations liees aux appartements et aux travellers
INSERT INTO Reservation (appartment_id, traveller_id, checkin_date, checkout_date, nb_adult, nb_child, nb_baby, reservation_price, is_accepted, is_cancelled, is_deposit_asked, is_deposit_received, traveller_message) VALUES
(1, 1, '2024-07-01 14:00:00', '2024-07-10 10:00:00', 2, 1, 0, 1200.00, FALSE, FALSE, TRUE, FALSE, 'Message pour le voyageur 1'),
(2, 2, '2024-07-05 16:00:00', '2024-07-12 09:00:00', 1, 0, 0, 800.00, FALSE, FALSE, TRUE, FALSE, 'Message pour le voyageur 2'),
(3, 3, '2024-07-08 13:00:00', '2024-07-15 11:00:00', 2, 2, 1, 1500.00, TRUE, FALSE, FALSE, TRUE, 'Message pour le voyageur 3'),
(1, 4, '2024-07-15 15:00:00', '2024-07-20 10:00:00', 1, 1, 0, 1000.00, TRUE, FALSE, FALSE, TRUE, 'Message pour le voyageur 4'),
(2, 5, '2024-07-20 12:00:00', '2024-07-25 10:00:00', 3, 0, 0, 1600.00, FALSE, TRUE, FALSE, FALSE, 'Message pour le voyageur 5');

-- création de feedback liés aux appartements
INSERT INTO feedback (appartment_id, utilisateur_id, comment, creation_date, modification_date)
VALUES
(1, 1, 'Premier commentaire pour appartement 1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 'Deuxième commentaire pour appartement 1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 'Troisième commentaire pour appartement 1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 'Premier commentaire pour appartement 2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 'Deuxième commentaire pour appartement 2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 3, 'Troisième commentaire pour appartement 2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 'Premier commentaire pour appartement 3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 'Deuxième commentaire pour appartement 3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, 'Troisième commentaire pour appartement 3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);