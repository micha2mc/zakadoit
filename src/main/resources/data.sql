INSERT INTO "AREA" (ID, NAME) VALUES
(1, 'Estrategía y Consultoría'),
(2, 'Desarrollo Software'),
(3, 'Sistemas e Infraestructuras'),
(4, 'Ciberseguridad'),
(5, 'Service Desk');

INSERT INTO "COMPANIES" (ID, NAME) VALUES
(1, 'ING'),
(2, 'BBVA'),
(3, 'SANTANDER'),
(4, 'SANITAS'),
(5, 'MINISTERIO DEFENSA'),
(6, 'MUTUA MADRILEÑA');

INSERT INTO "LANGUAGE" (ID, NAME, ISOCODE) VALUES
(1, 'Español', 'ES'),
(2, 'Francés', 'FR'),
(3, 'Inglés', 'EN'),
(4, 'Chino', 'ZH');


-- Insertar Employees
INSERT INTO "EMPLOYEE" (id, corporate_key, full_name, year_of_experience, salary_per_year, email, date_of_birth, career, company_id, area_id) VALUES
(1000, 'ZJ95NS', 'Juan Pérez García', 8, 55000.00, 'juan.perez@techinnovations.com', '1985-03-15', 'Software Developer', 1, 2),
(1001, 'HR76DB', 'María López Fernández', 12, 75000.00, 'maria.lopez@techinnovations.com', '1980-07-22', 'Project Manager', 1, 1),
(1002, 'EM03PQ', 'Carlos Rodríguez Martínez', 10, 65000.00, 'carlos.rodriguez@globalsolutions.com', '1983-11-30', 'HR Manager', 2, 2),
(1003, 'BN04ST', 'Ana Gómez Sánchez', 6, 48000.00, 'ana.gomez@datasystems.com', '1990-05-18', 'Financial Analyst', 3, 3),
(1004, 'WN05KN', 'David Hernández Ruiz', 4, 42000.00, 'david.hernandez@globalsolutions.com', '1992-09-25', 'Sales Executive', 2, 4),
(1005, 'LT85GR', 'Antonio Ndong  Aznar', 3, 40000.00, 'antonio.ndong@zakadoit.com', '1998-09-27', 'Software Developer', 4, 2);

-- Insertar relaciones Employee-Language
INSERT INTO employee_language (employee_id, language_id) VALUES
(1000, 1), (1000, 2),
(1001, 1), (1001, 2), (1001, 3),
(1002, 1), (1002, 2),
(1003, 1), (1003, 2), (1003, 4),
(1004, 1), (1004, 3),
(1005, 1), (1005, 2), (1005, 3), (1005, 4);