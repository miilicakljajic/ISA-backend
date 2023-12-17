INSERT INTO addresses(city, country, "number", street_name) VALUES ('Novi Sad', 'Srbija', '5', 'Bulevar Oslobodjenja');
INSERT INTO addresses(city, country, "number", street_name) VALUES ('Beograd', 'Srbija', '12', 'Kneza Milosa');
INSERT INTO addresses(city, country, "number", street_name) VALUES ('Niš', 'Srbija', '18', 'Bulevar Nemanjića');
INSERT INTO addresses(city, country, "number", street_name) VALUES ('Kragujevac', 'Srbija', '7', 'Kralja Petra I');
INSERT INTO addresses(city, country, "number", street_name) VALUES ('Subotica', 'Srbija', '23', 'Trg Slobode');
INSERT INTO addresses(city, country, "number", street_name) VALUES ('Novi Pazar', 'Srbija', '9', 'Vuka Karadžića');
INSERT INTO addresses(city, country, "number", street_name) VALUES ('Čačak', 'Srbija', '4', 'Cara Lazara');
INSERT INTO addresses(city, country, "number", street_name) VALUES ('Zrenjanin', 'Srbija', '16', 'Kralja Aleksandra');
INSERT INTO addresses(city, country, "number", street_name) VALUES ('Pančevo', 'Srbija', '10', 'Kralja Petra');
INSERT INTO addresses(city, country, "number", street_name) VALUES ('Šabac', 'Srbija', '6', 'Karađorđeva');
INSERT INTO addresses(city, country, "number", street_name) VALUES ('Leskovac', 'Srbija', '3', 'Svetozara Markovića');
INSERT INTO addresses(city, country, "number", street_name) VALUES ('Smederevo', 'Srbija', '14', 'Karađorđeva');
INSERT INTO addresses(city, country, "number", street_name) VALUES ('Užice', 'Srbija', '8', 'Nikole Tesle');
INSERT INTO addresses(city, country, "number", street_name) VALUES ('Kruševac', 'Srbija', '11', 'Kralja Petra I');
INSERT INTO addresses(city, country, "number", street_name) VALUES ('Valjevo', 'Srbija', '17', 'Vuka Karadžića');
INSERT INTO addresses(city, country, "number", street_name) VALUES ('Kraljevo', 'Srbija', '21', 'Kneza Miloša');
INSERT INTO addresses(city, country, "number", street_name) VALUES ('Vranje', 'Srbija', '19', 'Kralja Petra I');
INSERT INTO addresses(city, country, "number", street_name) VALUES ('Žabalj', 'Srbija', '25', 'Trg Republike');
INSERT INTO addresses(city, country, "number", street_name) VALUES ('Bačka Palanka', 'Srbija', '22', 'Jovana Dučića');
INSERT INTO addresses(city, country, "number", street_name) VALUES ('Sombor', 'Srbija', '20', 'Nikole Tesle');

INSERT INTO public.companies(average_rating, description, name, address_id, working_hours) VALUES (5.5, 'Providing cutting-edge healthcare solutions and medical services.', 'MediCare', 12, '9-16');
INSERT INTO public.companies(average_rating, description, name, address_id, working_hours) VALUES (4.2, 'Innovative technology solutions for medical diagnostics and treatment.', 'MedTech Solutions', 5, '9-16');
INSERT INTO public.companies(average_rating, description, name, address_id, working_hours) VALUES (4.7, 'Specializing in pharmaceutical research and development.', 'Pharma Innovations', 17, '9-16');
INSERT INTO public.companies(average_rating, description, name, address_id, working_hours) VALUES (4.9, 'Offering comprehensive healthcare products and services.', 'HealthCorp', 3, '9-16');
INSERT INTO public.companies(average_rating, description, name, address_id, working_hours) VALUES (4.6, 'Global leader in medical equipment manufacturing and distribution.', 'MedEquip', 14, '9-16');
INSERT INTO public.companies(average_rating, description, name, address_id, working_hours) VALUES (4.3, 'Providing medical expertise and healthcare consulting services.', 'MedConsult', 6, '9-16');
INSERT INTO public.companies(average_rating, description, name, address_id, working_hours) VALUES (3.7, 'Developing state-of-the-art medical software solutions.', 'MediSoft', 4, '9-16');
INSERT INTO public.companies(average_rating, description, name, address_id, working_hours) VALUES (4.8, 'Focused on medical research and innovative therapies.', 'MediResearch', 19, '9-16');
INSERT INTO public.companies(average_rating, description, name, address_id, working_hours) VALUES (4.1, 'Delivering high-quality medical supplies and equipment.', 'MedSupply Co.', 7, '9-16');
INSERT INTO public.companies(average_rating, description, name, address_id, working_hours) VALUES (3.5, 'Specializing in medical training programs and education.', 'MedEdu Solutions', 2, '9-16');
INSERT INTO public.companies(average_rating, description, name, address_id, working_hours) VALUES (4.4, 'Offering advanced medical imaging and diagnostic services.', 'MedImaging', 16, '9-16');
INSERT INTO public.companies(average_rating, description, name, address_id, working_hours) VALUES (3.6, 'Providing healthcare technology solutions and support.', 'MedTech Support', 1, '9-16');
INSERT INTO public.companies(average_rating, description, name, address_id, working_hours) VALUES (4.0, 'Specialized in medical data management and analytics.', 'MediAnalytics', 10, '9-16');
INSERT INTO public.companies(average_rating, description, name, address_id, working_hours) VALUES (4.5, 'Developing cutting-edge medical devices and technologies.', 'MedDevices Inc.', 20, '9-16');
INSERT INTO public.companies(average_rating, description, name, address_id, working_hours) VALUES (3.9, 'Committed to providing accessible healthcare solutions.', 'MedAccess', 15, '9-16');
INSERT INTO public.companies(average_rating, description, name, address_id, working_hours) VALUES (4.2, 'Offering personalized healthcare services and consultations.', 'MediCare Consult', 13, '9-16');
INSERT INTO public.companies(average_rating, description, name, address_id, working_hours) VALUES (3.8, 'Specializing in pharmaceutical manufacturing and distribution.', 'Pharma Solutions', 11, '9-16');
INSERT INTO public.companies(average_rating, description, name, address_id, working_hours) VALUES (4.7, 'Innovative medical research and drug development.', 'MediPharma Research', 9, '9-16');
INSERT INTO public.companies(average_rating, description, name, address_id, working_hours) VALUES (4.0, 'Focused on healthcare technology and patient care.', 'MedTechCare', 18, '9-16');
INSERT INTO public.companies(average_rating, description, name, address_id, working_hours) VALUES (4.3, 'Providing telemedicine solutions and remote healthcare.', 'TeleHealth Solutions', 8, '9-16');


INSERT INTO public.equipment(description, name, type, company_id) VALUES ('Stethoscope for auscultation', 'Stethoscope', 'Medical Equipment', 1);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('MRI machine for diagnostic imaging', 'MRI Machine', 'Diagnostic Equipment', 1);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('Defibrillator for cardiac emergencies', 'Defibrillator', 'Emergency Equipment', 1);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('Ultrasound device for imaging', 'Ultrasound Machine', 'Imaging Equipment', 1);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('Infusion pump for drug delivery', 'Infusion Pump', 'Medical Device', 1);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('Electrocardiogram machine for heart monitoring', 'ECG Machine', 'Monitoring Equipment', 1);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('Pulse oximeter for oxygen saturation measurement', 'Pulse Oximeter', 'Diagnostic Tool', 1);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('Nebulizer for respiratory therapy', 'Nebulizer', 'Respiratory Equipment', 1);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('Surgical microscope for precise visualization', 'Surgical Microscope', 'Surgical Equipment', 2);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('Anesthesia machine for administering anesthesia', 'Anesthesia Machine', 'Anesthesia Equipment', 2);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('Blood pressure monitor for hypertension evaluation', 'Blood Pressure Monitor', 'Diagnostic Device', 2);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('X-ray machine for radiographic imaging', 'X-ray Machine', 'Radiology Equipment', 3);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('Ophthalmoscope for eye examinations', 'Ophthalmoscope', 'Diagnostic Tool', 3);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('Dental chair for dental procedures', 'Dental Chair', 'Dental Equipment', 3);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('Ventilator for respiratory support', 'Ventilator', 'Life Support Equipment', 4);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('Surgical scalpel for precise incisions', 'Surgical Scalpel', 'Surgical Instrument', 4);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('CT scanner for detailed imaging', 'CT Scanner', 'Diagnostic Equipment', 4);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('Blood glucose meter for monitoring sugar levels', 'Glucose Meter', 'Diagnostic Device', 4);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('Dialysis machine for renal therapy', 'Dialysis Machine', 'Renal Equipment', 3);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('Orthopedic drill for bone surgeries', 'Orthopedic Drill', 'Surgical Instrument', 3);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('Stethoscope for auscultation', 'Stethoscope', 'Medical Equipment', 3);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('MRI machine for diagnostic imaging', 'MRI Machine', 'Diagnostic Equipment', 3);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('Defibrillator for cardiac emergencies', 'Defibrillator', 'Emergency Equipment', 3);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('Ultrasound device for imaging', 'Ultrasound Machine', 'Imaging Equipment', 3);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('Infusion pump for drug delivery', 'Infusion Pump', 'Medical Device', 3);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('Electrocardiogram machine for heart monitoring', 'ECG Machine', 'Monitoring Equipment', 3);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('Pulse oximeter for oxygen saturation measurement', 'Pulse Oximeter', 'Diagnostic Tool', 3);
INSERT INTO public.equipment(description, name, type, company_id) VALUES ('Nebulizer for respiratory therapy', 'Nebulizer', 'Respiratory Equipment', 3);


INSERT INTO public.ROLE (name) VALUES ('ROLE_USER');
INSERT INTO public.ROLE (name) VALUES ('ROLE_SYSTEM_ADMIN');
INSERT INTO public.ROLE (name) VALUES ('ROLE_COMPANY_ADMIN');


INSERT INTO public.users(
    id, company_info, email, enabled, first_name, last_name, last_password_reset_date, occupation, password, phone_number, picture_link, company_id, address_id)
VALUES (21, 'Changing genes - changing future', 'usr@m.com', true, 'User', 'Peric', to_timestamp(1639619463), 'Programmer', '$2a$12$yaElZaO65G7HhHNYcn8HkusyQtTt0lN.WL6GUtWR9RO8d5dSNosOq', '+381 62 111 1111', 'https://m.media-amazon.com/images/I/51DBd7O6GEL._AC_SL1500_.jpg', null, 1);

INSERT INTO public.users(
    id, company_info, email, enabled, first_name, last_name, last_password_reset_date, occupation, password, phone_number, picture_link, company_id, address_id)
VALUES (22,'Changing genes - changing future', 'sys@m.com', true, 'Sys', 'Peric', to_timestamp(1639619463), 'Programmer', '$2a$12$yaElZaO65G7HhHNYcn8HkusyQtTt0lN.WL6GUtWR9RO8d5dSNosOq', '+381 62 111 1111', 'https://m.media-amazon.com/images/I/51DBd7O6GEL._AC_SL1500_.jpg', null, 1);

INSERT INTO public.users(
    id, company_info, email, enabled, first_name, last_name, last_password_reset_date, occupation, password, phone_number, picture_link, company_id, address_id)
VALUES (23, 'Changing genes - changing future', 'comp@m.com', true, 'Admin1', 'Kompanijski1', to_timestamp(1639619463), 'Programmer', '$2a$12$yaElZaO65G7HhHNYcn8HkusyQtTt0lN.WL6GUtWR9RO8d5dSNosOq', '+381 62 111 1111', 'https://m.media-amazon.com/images/I/51DBd7O6GEL._AC_SL1500_.jpg', 1, 1);

INSERT INTO public.users(
    id, company_info, email, enabled, first_name, last_name, last_password_reset_date, occupation, password, phone_number, picture_link, company_id, address_id)
VALUES (24, 'Changing genes - changing future', 'comp2@m.com', true, 'Admin2', 'Kompanijski2', to_timestamp(1639619463), 'Programmer', '$2a$12$yaElZaO65G7HhHNYcn8HkusyQtTt0lN.WL6GUtWR9RO8d5dSNosOq', '+381 62 111 1111', 'https://m.media-amazon.com/images/I/51DBd7O6GEL._AC_SL1500_.jpg', 1, 1);

INSERT INTO public.users(
    id, company_info, email, enabled, first_name, last_name, last_password_reset_date, occupation, password, phone_number, picture_link, company_id, address_id)
VALUES (25, 'Changing genes - changing future', 'comp2@m.com', true, 'Admin3', 'Kompanijski3', to_timestamp(1639619463), 'Programmer', '$2a$12$yaElZaO65G7HhHNYcn8HkusyQtTt0lN.WL6GUtWR9RO8d5dSNosOq', '+381 62 111 1111', 'https://m.media-amazon.com/images/I/51DBd7O6GEL._AC_SL1500_.jpg', 1, 1);


INSERT INTO public.users(
    id, company_info, email, enabled, first_name, last_name, last_password_reset_date, occupation, password, phone_number, picture_link, company_id, address_id)
VALUES (26, 'Changing genes - changing future', 'ognjenmilojevic2001@gmail.com', true, 'Ognjen', 'Milojevic', to_timestamp(1639619463), 'Programmer', '$2a$12$yaElZaO65G7HhHNYcn8HkusyQtTt0lN.WL6GUtWR9RO8d5dSNosOq', '+381 62 111 1111', 'https://m.media-amazon.com/images/I/51DBd7O6GEL._AC_SL1500_.jpg', null, 1);


INSERT INTO public.users(
    id, company_info, email, enabled, first_name, last_name, last_password_reset_date, occupation, password, phone_number, picture_link, company_id, address_id)
VALUES (27, 'Changing genes - changing future', 'milicakljajic1@gmail.com', true, 'Milica', 'Kljajic', to_timestamp(1639619463), 'Programmer', '$2a$12$yaElZaO65G7HhHNYcn8HkusyQtTt0lN.WL6GUtWR9RO8d5dSNosOq', '+381 62 111 1111', 'https://m.media-amazon.com/images/I/51DBd7O6GEL._AC_SL1500_.jpg', null, 1);



INSERT INTO public.user_role(
    user_id, role_id)
VALUES (21, 1);

INSERT INTO public.user_role(
    user_id, role_id)
VALUES (22, 2);

INSERT INTO public.user_role(
    user_id, role_id)
VALUES (23, 3);

INSERT INTO public.user_role(
    user_id, role_id)
VALUES (24, 3);

INSERT INTO public.user_role(
    user_id, role_id)
VALUES (25, 3);

INSERT INTO public.user_role(
    user_id, role_id)
VALUES (26, 1);

INSERT INTO public.user_role(
    user_id, role_id)
VALUES (27, 1);

INSERT INTO public.appointments(
    admin_firstname, admin_lastname, date, duration, reserved, company_id, user_id)
VALUES ('Admin', 'Peric', '2023-12-23T11:30:00', '30', true, 1, 26);

INSERT INTO public.appointments(
    admin_firstname, admin_lastname, date, duration, reserved, company_id)
VALUES ('Admin', 'Peric', '2023-12-23T10:30:00', '30', false, 3);

INSERT INTO public.appointments(
    admin_firstname, admin_lastname, date, duration, reserved, company_id)
VALUES ('Admin', 'Peric', '2023-12-24T11:00:00', '30', false, 3);

INSERT INTO public.appointments(
    admin_firstname, admin_lastname, date, duration, reserved, company_id)
VALUES ('Admin', 'Peric', '2023-12-25T11:30:00', '30', false, 3);

