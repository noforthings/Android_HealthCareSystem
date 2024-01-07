        CREATE TABLE MAJOR (
            id INTEGER PRIMARY KEY,
            name TEXT UNIQUE,
            description TEXT,
            image TEXT
        );

        CREATE TABLE DISEASE (
            id INTEGER PRIMARY KEY,
            name TEXT UNIQUE,
            description TEXT,
            major_id INTEGER,
            image TEXT,
            FOREIGN KEY (major_id) REFERENCES MAJOR(id)
        );

        CREATE TABLE USER (
            id INTEGER PRIMARY KEY,
            username TEXT UNIQUE,
            password TEXT,
            fullname TEXT,
            email TEXT,
            phone TEXT,
            major_id INTEGER,
            description TEXT,
            image TEXT,
            FOREIGN KEY (major_id) REFERENCES MAJOR(id)
        );

        CREATE TABLE SLOT (
            id INTEGER PRIMARY KEY,
            dayinweek TEXT,
            time_slot TEXT,
            user_id INTEGER,
            is_booked INTEGER,
            FOREIGN KEY (user_id) REFERENCES USER(id)
        );


        CREATE TABLE APPOINTMENT (
            id INTEGER PRIMARY KEY,
            patient_id INTEGER,
            doctor_id INTEGER,
            slot_id INTEGER,
            appointment_time TEXT,
            status INTEGER,
            description TEXT,
            FOREIGN KEY (patient_id) REFERENCES USER(id),
            FOREIGN KEY (doctor_id) REFERENCES USER(id),
            FOREIGN KEY (slot_id) REFERENCES SLOT(id)
        );

        INSERT INTO MAJOR (id, name, description) VALUES
            (0, 'Patient', 'Use this for patient account'),
            (1, 'Cardiology', 'Deals with heart-related issues'),
            (2, 'Dermatology', 'Focuses on skin conditions'),
            (3, 'Neurology', 'Study of the nervous system and its disorders'),
            (9999, 'Admin', 'Use this for admin account'),
            (10, 'Orthopedics', 'Deals with musculoskeletal system'),
            (11, 'Ophthalmology', 'Focuses on eye-related issues'),
            (12, 'Gastroenterology', 'Study of the digestive system'),
            (13, 'Pulmonology', 'Deals with respiratory system'),
            (14, 'Urology', 'Focuses on urinary system');



        INSERT INTO DISEASE (id, name, description, major_id) VALUES
            (1, 'Coronary', 'Problem related to Coronary Artery Disease', 1),
            (2, 'Strokes', 'Problem related to Stroke', 1),
            (3, 'Fibrillation', 'Problem related to Fibrillation', 1),
            (4, 'Scabies', 'Problem related to Scabies', 2),
            (5, 'Itchy', 'Problem related to Itchy', 2),
            (6, 'Shingles', 'Problem related to shingles', 2),
            (7, 'Alzheimer', 'Problem related to Alzheimer', 3),
            (8, 'Parkinson', 'Problem related to Parkinson', 3),
            (9, 'Sclerosis', 'Problem related to Sclerosis', 3),
            (25, 'Osteoarthritis', 'Degeneration of joint cartilage and bones', 10),
            (26, 'Fracture', 'Broken bone', 10),
            (27, 'Scoliosis', 'Abnormal curvature of the spine', 10),
            (28, 'Tendinitis', 'Inflammation of a tendon', 10),
            (29, 'Rheumatoid Arthritis', 'Chronic inflammatory joint disorder', 10),
            (30, 'Glaucoma', 'Increased pressure in the eye', 11),
            (31, 'Macular Degeneration', 'Deterioration of the macular', 11),
            (32, 'Conjunctivitis', 'Pink eye, inflammation of the conjunctiva', 11),
            (33, 'Retinal Detachment', 'Separation of the retina from the eye', 11),
            (34, 'Astigmatism', 'Irregular curvature of the cornea or lens', 11),
            (35, 'Gastric Ulcer', 'Sores in the lining of the stomach', 12),
            (36, 'Crowns Disease', 'Inflammatory bowel disease', 12),
            (37, 'Gallstones', 'Hardened deposits in the gallbladder', 12),
            (38, 'Celiac Disease', 'Immune reaction to gluten', 12),
            (39, 'Hepatitis', 'Inflammation of the liver', 12),
            (40, 'Chronic Bronchitis', 'Persistent inflammation of the bronchial tubes', 13),
            (41, 'Pneumonia', 'Inflammation of lung tissue', 13),
            (42, 'COPD (Chronic Obstructive Pulmonary Disease)', 'Progressive lung disease', 13),
            (43, 'Pulmonary Embolism', 'Blockage in one of the pulmonary arteries', 13),
            (44, 'Atelectasis', 'Collapsed lung', 13),
            (45, 'Kidney Stones', 'Hard deposits in the kidneys', 14),
            (46, 'Urinary Incontinence', 'Inability to control urination', 14),
            (47, 'Bladder Cancer', 'Cancer in the bladder', 14),
            (48, 'Prostatic', 'Inflammation of the prostate gland', 14),
            (49, 'Renal Failure', 'Impaired kidney function', 14),
            (75, 'Heart Attack', 'Blockage of blood flow to the heart', 1),
            (76, 'Arrhythmia', 'Irregular heart rhythm', 1),
            (77, 'Heart Failure', 'Inability of the heart to pump blood effectively', 1),
            (78, 'Atherosclerosis', 'Hardening and narrowing of the arteries', 1),
            (79, 'Hypertensive Heart Disease', 'Heart disease due to high blood pressure', 1),
            (80, 'Acne', 'Skin condition causing pimples and blemishes', 2),
            (81, 'Psoriasis', 'Chronic skin condition with red, scaly patches', 2),
            (82, 'Eczema', 'Inflammatory skin condition causing itching', 2),
            (83, 'Melanoma', 'Deadly form of skin cancer', 2),
            (84, 'Rosacea', 'Chronic skin condition causing facial redness', 2),
            (85, 'Migraine', 'Severe headaches with sensory disturbances', 3),
            (86, 'Epilepsy', 'Neurological disorder causing seizures', 3),
            (87, 'Multiple Sclerosis', 'Autoimmune disease affecting the central nervous system', 3),
            (88, 'Stroke', 'Disruption of blood flow to the brain', 3),
            (89, 'Parkinson Disease', 'Progressive nervous system disorder', 3);


        INSERT INTO USER (id, username, password, fullname, email, phone, major_id, description) VALUES
            (1, 'admin', 'admin', NULL, NULL, NULL, 9999, NULL),
            (2, 'demo', '12345678', 'Demo Account', 'demo@demo.com', '000000000', 0, NULL),
            (3, 'panh0706', '12345678', 'Pham Trinh The Anh', 'anhcoc0706@gmail.com', '0866123xxx', 0, NULL),
            (4, 'ndat1234', '12345678', 'Nguyen Huu Dat', 'datpro1234@gmail.com', '0912345xxx', 0, NULL),
            (5, 'nsac1234', '12345678', 'Nguyen Hong Sac', 'sacduck1234@gmail.com', '0866123xxx', 0, NULL),
            (6, 'htruong1234', '12345678', 'Hoang Xuan Truong', 'truongnro1234@gmail.com', '0223456xxx', 0, NULL),
            (7, 'doctor1', 'admin123', 'Dr. Jame Smith', 'drjame@dphospital.com', '0965526712', 1, 'A professional doctor with more than 10 years in this major.'),
            (8, 'doctor2', 'admin123', 'Dr. Harry Kane', 'drkane@dphospital.com', '0965526712', 2, 'A professional doctor with more than 10 years in this major.'),
            (9, 'doctor3', 'admin123', 'Dr. Jude Bellingham', 'drjude@dphospital.com', '0965526712', 3, 'A professional doctor with more than 10 years in this major.'),
            (10, 'doctor10', 'password123', 'Dr. Cristiano Ronaldo', 'cardio1@dphospital.com', '1234567890', 1, 'Cardiologist with expertise in heart health.'),
            (11, 'doctor11', 'password123', 'Dr. Lionel Messi', 'cardio2@dphospital.com', '1234567890', 1, 'Experienced cardiologist specializing in cardiovascular care.'),
            (12, 'doctor12', 'password123', 'Dr. Kilian Mbappe', 'cardio3@dphospital.com', '1234567890', 1, 'Dedicated to diagnosing and treating heart conditions.'),
            (13, 'doctor13', 'password123', 'Dr. Andres Iniesta', 'derma1@dphospital.com', '1234567890', 2, 'Expert in diagnosing and treating various skin conditions.'),
            (14, 'doctor14', 'password123', 'Dr. Zlatan Ibrahimovic', 'derma2@dphospital.com', '1234567890', 2, 'Specializing in dermatological care and skin health.'),
            (15, 'doctor15', 'password123', 'Dr. Luka Modric', 'derma3@dphospital.com', '1234567890', 2, 'Skilled dermatologist with a focus on patient well-being.'),
            (16, 'doctor16', 'password123', 'Dr. Vinicius Junior', 'neuro1@dphospital.com', '1234567890', 3, 'Neurologist specializing in disorders of the nervous system.'),
            (17, 'doctor17', 'password123', 'Dr. Robert Lewandowski', 'neuro2@dphospital.com', '1234567890', 3, 'Expertise in diagnosing and treating neurological conditions.'),
            (18, 'doctor18', 'password123', 'Dr. Karim Benzema', 'neuro3@dphospital.com', '1234567890', 3, 'Dedicated to improving the lives of patients with neurological disorders.'),
            (19, 'doctor19', 'password123', 'Dr. Harry Kane', 'ortho1@dphospital.com', '1234567890', 10, 'Orthopedic specialist focusing on musculoskeletal health.'),
            (20, 'doctor20', 'password123', 'Dr. Kevin De Bruyne', 'ortho2@dphospital.com', '1234567890', 10, 'Skilled in diagnosing and treating orthopedic conditions.'),
            (21, 'doctor21', 'password123', 'Dr. Phil Foden', 'ortho3@dphospital.com', '1234567890', 10, 'Orthopedic surgeon committed to patient well-being.'),
            (22, 'doctor22', 'password123', 'Dr. Marcus Rashford', 'ophtho1@dphospital.com', '1234567890', 11, 'Ophthalmologist specializing in eye care and vision health.'),
            (23, 'doctor23', 'password123', 'Dr. Harry Marguire', 'ophtho2@dphospital.com', '1234567890', 11, 'Expert in diagnosing and treating eye conditions.'),
            (24, 'doctor24', 'password123', 'Dr. Son', 'ophtho3@dphospital.com', '1234567890', 11, 'Dedicated to preserving and enhancing patient vision.'),
            (25, 'doctor25', 'password123', 'Dr. Luke Shaw', 'gastro1@dphospital.com', '1234567890', 12, 'Gastroenterologist specializing in digestive system health.'),
            (26, 'doctor26', 'password123', 'Dr. Lukaku', 'gastro2@dphospital.com', '1234567890', 12, 'Expertise in diagnosing and treating gastrointestinal conditions.'),
            (27, 'doctor27', 'password123', 'Dr. Xavi', 'gastro3@dphospital.com', '1234567890', 12, 'Dedicated to improving patients digestive health.'),
            (28, 'doctor28', 'password123', 'Dr. Mourinho', 'pulmo1@dphospital.com', '1234567890', 13, 'Pulmonologist specializing in respiratory system health.'),
            (29, 'doctor29', 'password123', 'Dr. Virgil van Dijk', 'pulmo2@dphospital.com', '1234567890', 13, 'Expertise in diagnosing and treating respiratory conditions.'),
            (30, 'doctor30', 'password123', 'Dr. Darwin Nunez', 'pulmo3@dphospital.com', '1234567890', 13, 'Dedicated to improving patients respiratory well-being.'),
            (31, 'doctor31', 'password123', 'Dr. Mohamed Salah', 'uro1@dphospital.com', '1234567890', 14, 'Urologist specializing in urinary system health.'),
            (32, 'doctor32', 'password123', 'Dr. Andre Onana', 'uro2@dphospital.com', '1234567890', 14, 'Expertise in diagnosing and treating urological conditions.'),
            (33, 'doctor33', 'password123', 'Dr. Professional', 'uro3@dphospital.com', '1234567890', 14, 'Dedicated to improving patients urinary health.');

        CREATE TABLE MATERIAL(
            idd INTEGER PRIMARY KEY,
            dayinweek TEXT,
            time_slot TEXT,
            is_booked INTEGER
        );
        INSERT INTO MATERIAL(dayinweek, time_slot, is_booked) VALUES
              ('Monday', 'Morning', 0),
              ('Monday', 'Afternoon', 0),
             ('Tuesday', 'Morning', 0),
             ('Tuesday', 'Afternoon', 0),
             ('Wednesday', 'Morning', 0),
             ('Wednesday', 'Afternoon', 0),
             ('Thursday', 'Morning', 0),
             ('Thursday', 'Afternoon', 0),
             ('Friday', 'Morning', 0),
             ('Friday', 'Afternoon', 0),
             ('Saturday', 'Morning', 0),
             ('Saturday', 'Afternoon', 0);


        INSERT INTO SLOT (id, dayinweek, time_slot, user_id, is_booked) VALUES
            (1, 'Monday', 'Morning', 7, 1),
            (2, 'Monday', 'Afternoon', 7, 1),
            (3, 'Wednesday', 'Morning', 7, 0),
            (4, 'Friday', 'Afternoon', 7, 0),
            (5, 'Friday', 'Morning', 7, 0),
            (6, 'Monday', 'Morning', 8, 0),
            (7, 'Tuesday', 'Afternoon', 8, 0),
            (8, 'Thursday', 'Morning', 8, 0),
            (9, 'Friday', 'Afternoon', 8, 0),
            (10, 'Friday', 'Morning', 8, 1),
            (11, 'Tuesday', 'Morning', 9, 0),
            (12, 'Tuesday', 'Afternoon', 9, 0),
            (13, 'Thursday', 'Morning', 9, 0),
            (14, 'Friday', 'Afternoon', 9, 0),
            (15, 'Friday', 'Morning', 9, 0);

        INSERT INTO SLOT (dayinweek, time_slot, user_id, is_booked)
        SELECT DISTINCT s.dayinweek, s.time_slot, d.id, 0
        FROM MATERIAL s
        JOIN USER d ON d.id > 9;


        INSERT INTO APPOINTMENT (patient_id, doctor_id, slot_id, appointment_time, status, description)
        VALUES
            (2, 7, 1, 'Monday Morning', 0, 'Consultation with specialist.'),
            (2, 7, 2, 'Monday Afternoon', 0, 'Physical examination.'),
            (2, 7, 1, 'Monday Morning', 1, 'Consultation with specialist.'),
            (2, 9, 10, 'Friday Morning', 0, 'Regular checkup.');





