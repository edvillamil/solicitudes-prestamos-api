-- Tipos de préstamo
CREATE TABLE IF NOT EXISTS tipos_prestamo (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT
);

-- Estados de solicitud
CREATE TABLE IF NOT EXISTS estado_solicitud (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(50) NOT NULL
);

-- Solicitudes de préstamo
CREATE TABLE IF NOT EXISTS solicitudes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    documento_cliente VARCHAR(50) NOT NULL,
    tipo_prestamo_id UUID NOT NULL REFERENCES tipos_prestamo(id),
    monto NUMERIC(15,2) NOT NULL,
    plazo_meses INT NOT NULL,
    estado_id UUID NOT NULL REFERENCES estado_solicitud(id),
    fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW()
);