-- =========================================================
-- Creación de la base de datos
-- =========================================================

CREATE DATABASE IF NOT EXISTS zakadoit
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE zakadoit;

-- =========================================================
-- Tablas de emulación de secuencias (MySQL < 8.0)
-- =========================================================

CREATE TABLE idgenerator_area (
    next_val BIGINT
) ENGINE=InnoDB;
INSERT INTO idgenerator_area VALUES (1);

CREATE TABLE idgenerator_company (
    next_val BIGINT
) ENGINE=InnoDB;
INSERT INTO idgenerator_company VALUES (1);

CREATE TABLE idgenerator_language (
    next_val BIGINT
) ENGINE=InnoDB;
INSERT INTO idgenerator_language VALUES (1);

CREATE TABLE employee_id_seq (
    next_val BIGINT
) ENGINE=InnoDB;
INSERT INTO employee_id_seq VALUES (1000);

-- =========================================================
-- Tablas de catálogo
-- =========================================================

CREATE TABLE area (
    id   BIGINT NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE companies (
    id   BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE language (
    id      BIGINT NOT NULL,
    name    VARCHAR(255) NOT NULL,
    isocode VARCHAR(2)   NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY UK_language_name (name),
    UNIQUE KEY UK_language_isocode (isocode)
) ENGINE=InnoDB;

-- =========================================================
-- Tabla principal
-- =========================================================

CREATE TABLE employee (
    id                 BIGINT NOT NULL,
    corporate_key      VARCHAR(255) NOT NULL,
    full_name          VARCHAR(255) NOT NULL,
    year_of_experience INT NOT NULL,
    salary_per_year    DECIMAL(38,2),
    email              VARCHAR(255) NOT NULL,
    date_of_birth      DATE NOT NULL,
    career             VARCHAR(255),
    company_id         BIGINT NOT NULL,
    area_id            BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_employee_company FOREIGN KEY (company_id) REFERENCES companies (id),
    CONSTRAINT FK_employee_area    FOREIGN KEY (area_id)    REFERENCES area (id)
) ENGINE=InnoDB;

-- =========================================================
-- Tabla intermedia (relación muchos-a-muchos)
-- =========================================================

CREATE TABLE employee_language (
    employee_id BIGINT NOT NULL,
    language_id BIGINT NOT NULL,
    PRIMARY KEY (employee_id, language_id),
    CONSTRAINT FK_emplang_employee FOREIGN KEY (employee_id) REFERENCES employee (id),
    CONSTRAINT FK_emplang_language FOREIGN KEY (language_id) REFERENCES language (id)
) ENGINE=InnoDB;