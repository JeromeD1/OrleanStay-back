-- Insertion des informations personnelles
INSERT INTO Personal_information (firstname, lastname, email, phone, address, city, country) VALUES
('John', 'Doe', 'john.doe@example.com', '1234567890', '123 Main St', 'Vancouver', 'Canada'),
('Jane', 'Smith', 'jane.smith@example.com', '0987654321', '456 Elm St', 'Toronto', 'Canada'),
('Alice', 'Johnson', 'alice.johnson@example.com', '1122334455', '789 Oak St', 'Montreal', 'Canada'),
('Bob', 'Brown', 'bob.brown@example.com', '5544332211', '321 Pine St', 'Calgary', 'Canada'),
('Jerome', 'Bigs', 'jerome.bigs@example.com', '06 20 45 50 24', '18 rue des haricots', 'Rouen', 'France'),
('Albert', 'Mousquetaire', 'albert.mousquetaire@example.com', '07 50 24 78 32', '25 rue des Schmits', 'Paris', 'France');

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
('Appartement Lumineux', 'Un bel appartement avec vue sur la ville', '123 rue de la Paix', '75001', 'Paris', 'https://maps.google.com/?q=123+rue+de+la+Paix', '500m', '1km', '200m', 120, 300, 50, 70, 90, 'SAISONNIER', true, 1, 1),
('Studio Moderne', 'Studio moderne parfait pour les courts séjours', '45 avenue du Général', '75002', 'Paris', 'https://maps.google.com/?q=45+avenue+du+Général', '1km', '500m', '100m', 80, 200, 30, 40, 60, 'LONGUE_DUREE', true, 2, 2),
('Chambre Cosy', 'Petite chambre cosy près des commodités', '78 boulevard Magenta', '75010', 'Paris', 'https://maps.google.com/?q=78+boulevard+Magenta', '2km', '1.5km', '300m', 60, 150, 20, 30, 40, 'SAISONNIER', false, 3, 2);


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
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('monImageUrl1', 1, 1);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('monImageUrl2', 2, 1);

INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('monImageUrl3', 1, 2);
INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('monImageUrl4', 2, 2);

INSERT INTO Appartment_photo (img_url, position_order, Appartment_id) VALUES ('monImageUrl5', 1, 3);


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