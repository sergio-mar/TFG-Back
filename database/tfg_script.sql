-- Crear base de datos
CREATE DATABASE IF NOT EXISTS servicios_hogar;
USE servicios_hogar;

-- Tabla Usuarios
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    role ENUM('cliente', 'profesional', 'admin') NOT NULL,
    especialidad VARCHAR(255),
    activo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_role (role),
    INDEX idx_activo (activo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla Servicios
CREATE TABLE IF NOT EXISTS services (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    profesional_id BIGINT NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL,
    disponibilidad BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (profesional_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_profesional (profesional_id),
    INDEX idx_categoria (categoria),
    INDEX idx_disponibilidad (disponibilidad)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla Reservas
CREATE TABLE IF NOT EXISTS bookings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    servicio_id BIGINT NOT NULL,
    fecha DATE NOT NULL,
    estado ENUM('pendiente', 'aceptado', 'en_curso', 'finalizado', 'cancelado') DEFAULT 'pendiente',
    valoracion INT CHECK (valoracion >= 1 AND valoracion <= 5),
    comentario TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (servicio_id) REFERENCES services(id) ON DELETE CASCADE,
    INDEX idx_cliente (cliente_id),
    INDEX idx_servicio (servicio_id),
    INDEX idx_fecha (fecha),
    INDEX idx_estado (estado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla Quejas
CREATE TABLE IF NOT EXISTS complaints (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    asunto VARCHAR(255) NOT NULL,
    descripcion TEXT NOT NULL,
    estado ENUM('pendiente', 'en_revision', 'resuelta') DEFAULT 'pendiente',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_usuario (usuario_id),
    INDEX idx_estado (estado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insertar datos de prueba
-- Contraseña para todos: demo123 (hasheada con BCrypt)
INSERT INTO users (email, password, nombre, telefono, role, especialidad, activo) VALUES
('cliente@demo.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IyBGhYI3F5fJIhsLGQHCGcBsQDPy7e', 'Ana García', '600123456', 'cliente', NULL, TRUE),
('profesional@demo.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IyBGhYI3F5fJIhsLGQHCGcBsQDPy7e', 'Carlos Martínez', '600654321', 'profesional', 'Limpieza', TRUE),
('admin@demo.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IyBGhYI3F5fJIhsLGQHCGcBsQDPy7e', 'Admin Sistema', '600999888', 'admin', NULL, TRUE),
('maria@demo.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IyBGhYI3F5fJIhsLGQHCGcBsQDPy7e', 'María López', '611223344', 'profesional', 'Jardinería', TRUE),
('juan@demo.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IyBGhYI3F5fJIhsLGQHCGcBsQDPy7e', 'Juan Pérez', '622334455', 'cliente', NULL, TRUE);

-- Insertar servicios de prueba
INSERT INTO services (profesional_id, titulo, categoria, descripcion, precio, disponibilidad) VALUES
(2, 'Limpieza General del Hogar', 'Limpieza', 'Limpieza completa de viviendas incluyendo cocina, baños y habitaciones', 45.00, TRUE),
(2, 'Limpieza de Oficinas', 'Limpieza', 'Limpieza profesional de espacios de trabajo y áreas comunes', 60.00, TRUE),
(2, 'Limpieza Profunda', 'Limpieza', 'Limpieza intensiva con desinfección completa', 80.00, TRUE),
(4, 'Mantenimiento de Jardín', 'Jardinería', 'Corte de césped, poda y mantenimiento general', 50.00, TRUE),
(4, 'Diseño de Jardines', 'Jardinería', 'Diseño y creación de espacios verdes personalizados', 150.00, TRUE);

-- Insertar reservas de prueba
INSERT INTO bookings (cliente_id, servicio_id, fecha, estado) VALUES
(1, 1, '2026-01-15', 'pendiente'),
(1, 4, '2026-01-20', 'aceptado'),
(5, 2, '2026-01-18', 'pendiente');

-- Insertar una queja de prueba
INSERT INTO complaints (usuario_id, asunto, descripcion, estado) VALUES
(1, 'Retraso en el servicio', 'El profesional llegó 30 minutos tarde a la cita acordada', 'pendiente');