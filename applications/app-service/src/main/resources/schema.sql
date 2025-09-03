CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Tipos de préstamo
CREATE TABLE IF NOT EXISTS loan_type (
   id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
   name VARCHAR(100) NOT NULL,
   description VARCHAR(255),
   amount_min NUMERIC(15,2),
   amount_max NUMERIC(15,2),
   term_min NUMERIC(10,2),
   term_max NUMERIC(10,2),
   rate NUMERIC(10,4),
   automatic_validation NUMERIC(10,4)
);

-- Estados de solicitud
CREATE TABLE IF NOT EXISTS loan_status (
   id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
   name VARCHAR(100) NOT NULL,
   description VARCHAR(255)
);

-- Solicitudes de préstamo
CREATE TABLE IF NOT EXISTS loan_request (
   id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
   document_number VARCHAR(50) NOT NULL,
   email VARCHAR(50) NOT NULL,
   loan_type UUID NOT NULL,
   amount NUMERIC(15,2) NOT NULL,
   term_months INT NOT NULL,
   status_id UUID NOT NULL,
   created_at TIMESTAMP NOT NULL DEFAULT NOW(),
   CONSTRAINT fk_loan_type FOREIGN KEY (loan_type) REFERENCES loan_type(id),
   CONSTRAINT fk_status FOREIGN KEY (status_id) REFERENCES loan_status(id)
);

INSERT INTO loan_status (name, description)
SELECT 'PENDING_REVIEW', 'pendiente revision'
WHERE NOT EXISTS (SELECT 1 FROM loan_status WHERE name = 'PENDING_REVIEW');

INSERT INTO loan_status (name, description)
SELECT 'REJECTED', 'Rechazado'
WHERE NOT EXISTS (SELECT 1 FROM loan_status WHERE name = 'REJECTED');

INSERT INTO loan_status (name, description)
SELECT 'APPROVED', 'Aprovado'
WHERE NOT EXISTS (SELECT 1 FROM loan_status WHERE name = 'APPROVED');

INSERT INTO loan_status (name, description)
SELECT 'MANUAL_REVIEW', 'Revision manual'
WHERE NOT EXISTS (SELECT 1 FROM loan_status WHERE name = 'MANUAL_REVIEW');

INSERT INTO loan_type (name, description, amount_min, amount_max, term_min, term_max, rate, automatic_validation)
SELECT 'Libre inversión', 'Préstamo para gastos personales', 500000, 5000000, 6, 24, 0.0150, 1
WHERE NOT EXISTS (SELECT 1 FROM loan_type WHERE name = 'Libre inversión');

-- Registros prueba soicitudes

INSERT INTO loan_request ( document_number, email, loan_type, amount, term_months, status_id, created_at)
SELECT
'1002003001', 'edvillamil@gmail.com', (SELECT id FROM loan_type WHERE name = 'Libre inversión'),  5000000.00, 12, (SELECT id FROM loan_status WHERE name = 'PENDING_REVIEW'), NOW()
WHERE NOT EXISTS (SELECT 1 FROM loan_request WHERE document_number = '1002003001');

INSERT INTO loan_request ( document_number, email, loan_type, amount, term_months, status_id, created_at)
SELECT
'1002003002', 'edvillamil@gmail.com', (SELECT id FROM loan_type WHERE name = 'Libre inversión'),  5000000.00, 12, (SELECT id FROM loan_status WHERE name = 'REJECTED'), NOW()
WHERE NOT EXISTS (SELECT 1 FROM loan_request WHERE document_number = '1002003002');

INSERT INTO loan_request ( document_number, email, loan_type, amount, term_months, status_id, created_at)
SELECT
'1002003003', 'edvillamil@gmail.com', (SELECT id FROM loan_type WHERE name = 'Libre inversión'),  5000000.00, 12, (SELECT id FROM loan_status WHERE name = 'PENDING_REVIEW'), NOW()
WHERE NOT EXISTS (SELECT 1 FROM loan_request WHERE document_number = '1002003003');

INSERT INTO loan_request ( document_number, email, loan_type, amount, term_months, status_id, created_at)
SELECT
'1002003004', 'edvillamil@gmail.com', (SELECT id FROM loan_type WHERE name = 'Libre inversión'),  5000000.00, 12, (SELECT id FROM loan_status WHERE name = 'MANUAL_REVIEW'), NOW()
WHERE NOT EXISTS (SELECT 1 FROM loan_request WHERE document_number = '1002003004');

INSERT INTO loan_request ( document_number, email, loan_type, amount, term_months, status_id, created_at)
SELECT
'1002003005', 'edvillamil@gmail.com', (SELECT id FROM loan_type WHERE name = 'Libre inversión'),  5000000.00, 12, (SELECT id FROM loan_status WHERE name = 'MANUAL_REVIEW'), NOW()
WHERE NOT EXISTS (SELECT 1 FROM loan_request WHERE document_number = '1002003005');

INSERT INTO loan_request ( document_number, email, loan_type, amount, term_months, status_id, created_at)
SELECT
'1002003006', 'edvillamil@gmail.com', (SELECT id FROM loan_type WHERE name = 'Libre inversión'),  5000000.00, 12, (SELECT id FROM loan_status WHERE name = 'PENDING_REVIEW'), NOW()
WHERE NOT EXISTS (SELECT 1 FROM loan_request WHERE document_number = '1002003006');

INSERT INTO loan_request ( document_number, email, loan_type, amount, term_months, status_id, created_at)
SELECT
'1002003007', 'edvillamil@gmail.com', (SELECT id FROM loan_type WHERE name = 'Libre inversión'),  5000000.00, 12, (SELECT id FROM loan_status WHERE name = 'APPROVED'), NOW()
WHERE NOT EXISTS (SELECT 1 FROM loan_request WHERE document_number = '1002003007');

INSERT INTO loan_request ( document_number, email, loan_type, amount, term_months, status_id, created_at)
SELECT
'1002003008', 'asesor2@gmail.com', (SELECT id FROM loan_type WHERE name = 'Libre inversión'),  5000000.00, 12, (SELECT id FROM loan_status WHERE name = 'APPROVED'), NOW()
WHERE NOT EXISTS (SELECT 1 FROM loan_request WHERE document_number = '1002003008');

INSERT INTO loan_request ( document_number, email, loan_type, amount, term_months, status_id, created_at)
SELECT
'1002003009', 'edvillamil@gmail.com', (SELECT id FROM loan_type WHERE name = 'Libre inversión'),  5000000.00, 12, (SELECT id FROM loan_status WHERE name = 'PENDING_REVIEW'), NOW()
WHERE NOT EXISTS (SELECT 1 FROM loan_request WHERE document_number = '1002003009');

INSERT INTO loan_request ( document_number, email, loan_type, amount, term_months, status_id, created_at)
SELECT
'1002003010', 'edvillamil@gmail.com', (SELECT id FROM loan_type WHERE name = 'Libre inversión'),  5000000.00, 12, (SELECT id FROM loan_status WHERE name = 'PENDING_REVIEW'), NOW()
WHERE NOT EXISTS (SELECT 1 FROM loan_request WHERE document_number = '1002003010');

INSERT INTO loan_request ( document_number, email, loan_type, amount, term_months, status_id, created_at)
SELECT
'1002003011', 'edvillamil@gmail.com', (SELECT id FROM loan_type WHERE name = 'Libre inversión'),  5000000.00, 12, (SELECT id FROM loan_status WHERE name = 'PENDING_REVIEW'), NOW()
WHERE NOT EXISTS (SELECT 1 FROM loan_request WHERE document_number = '1002003011');

INSERT INTO loan_request ( document_number, email, loan_type, amount, term_months, status_id, created_at)
SELECT
'1002003012', 'edvillamil@gmail.com', (SELECT id FROM loan_type WHERE name = 'Libre inversión'),  5000000.00, 12, (SELECT id FROM loan_status WHERE name = 'PENDING_REVIEW'), NOW()
WHERE NOT EXISTS (SELECT 1 FROM loan_request WHERE document_number = '1002003012');