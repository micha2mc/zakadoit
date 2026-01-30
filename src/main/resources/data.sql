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
INSERT INTO employee (id, salary_per_year, career, corporate_key, date_of_birth, email, full_name, year_of_experience, area_id, company_id) VALUES
(1000, 55000.00, 'Software Developer', 'IH44ZV', '1987-03-15', 'obama.watson@zakadoit.com', 'Juan Obama Watson', 8, 2, 1),
(1001, 24000.00, 'Aprendiz de Ciberseguridad con la integración de IA', 'KO96PZ', '1999-02-25', 'MBULITO.SANTANDER@ZAKADOIT.COM', 'Paloma Santander Mbulito', 2, 4, 4),
(1002, 68000.00, 'Experto en Sistemas e Infraestucturas aeronaáticas ', 'UH88QP', '1989-05-30', 'carretero.gonzales@zakadoit.com', 'Luis Carretero Gonzáles', 10, 3, 5),
(1003, 35000.00, 'Servicio de resolución de incidencias', 'KT10ZJ', '2002-12-12', 'mbasogo.mikue@zakadoit.com', 'Teodora Mbasogo Mikue', 4, 5, 6),
(1004, 45000.00, 'Encargado de las estrategías y consultoría', 'AT63OT', '2003-01-15', 'zhang.wei@zakado.com', 'Zhang Wei', 6, 1, 3),
(1005, 35000.00, 'Desarrollo de Aplicaciones Web', 'VR21YS', '2000-01-20', 'wang.fang@zakadoit.com', 'Wang Fang', 2, 2, 2),
(1006, 50000.00, 'Experto en desarrollo y DevOps', 'EA21BU', '1995-06-25', 'nakamura.haruto@zakadoit.com', 'Nakamura Haruto', 7, 2, 4),
(1007, 75000.00, 'Experto en Ciberseguridad', 'TJ31LH', '1990-07-03', 'suzuki.sakura@zakadoit.com', 'Suzuki Sakura', 10, 4, 5),
(1018, 100000.00, 'Estrategía, Gestión, Contabilidad y consultoría', 'KV96UY', '1985-06-06', 'cho.yeon-woo@zakadoit.com', 'Cho Yeon-woo', 15, 1, 2),
(1019, 30000.00, 'Incidencias', 'MK30UY', '1990-09-25', 'mohamed.ali@zakadoit.com', 'Mohamed Ali', 2, 5, 3),
(1020, 66000.00, 'Experta en Arquitectura y desarrollo.', 'BX43PF', '2001-12-24', 'priya.sharma@zakadoit.com', 'Priya Sharma', 8, 2, 4),
(1022, 55000.00, 'Software Developer', 'OY40XE', '1985-03-15', 'juan.perez@zakadoit.com', 'Juan Pérez García', 8, 2, 5),
(1023, 75000.00, 'Project Manager', 'PN68RD', '1980-07-22', 'maria.lopez@zakadoit.com', 'María López Fernández', 12, 1, 4),
(1024, 40000.00, 'Software Developer', 'FU42RN', '1998-09-27', 'antonio.ndong@zakadoit.com', 'Antonio Ndong  Aznar', 3, 2, 1);

-- Insertar relaciones Employee-Language
INSERT INTO employee_language (employee_id, language_id) VALUES
(1000, 1), (1000, 2), (1000, 3), (1000, 4),
(1001, 1), (1001, 2), (1001, 3),
(1002, 1), (1002, 3),
(1003, 1), (1003, 3),
(1004, 1), (1004, 3), (1004, 4),
(1005, 1), (1005, 3), (1005, 4),
(1006, 1), (1006, 2), (1006, 3),
(1007, 1), (1007, 2), (1007, 3),
(1018, 1), (1018, 2), (1018, 3),
(1019, 1), (1019, 2), (1019, 3),
(1020, 1), (1020, 2), (1020, 3),
(1022, 1), (1022, 3),
(1023, 1), (1023, 2), (1023, 3), (1023, 4),
(1024, 1), (1024, 2), (1024, 3), (1024, 4);











