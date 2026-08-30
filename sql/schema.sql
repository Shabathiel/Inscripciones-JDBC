-- Clase 7 - Cursos e Inscripciones: relacion muchos-a-muchos (N:M)
-- Ejecuta este script ANTES de correr el proyecto Java.

CREATE DATABASE IF NOT EXISTS prog2_db;

USE prog2_db;

CREATE TABLE IF NOT EXISTS estudiantes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    carnet VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS cursos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    creditos INT NOT NULL
);

-- Tabla intermedia: un estudiante puede inscribirse en muchos cursos, y un
-- curso puede tener muchos estudiantes inscritos. Cada FILA de esta tabla
-- representa "este estudiante esta en este curso". La restriccion UNIQUE
-- evita que el mismo estudiante quede inscrito dos veces en el mismo curso.
CREATE TABLE IF NOT EXISTS inscripciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    estudiante_id INT NOT NULL,
    curso_id INT NOT NULL,
    nota DECIMAL(4,2) NULL,
    FOREIGN KEY (estudiante_id) REFERENCES estudiantes(id),
    FOREIGN KEY (curso_id) REFERENCES cursos(id),
    UNIQUE (estudiante_id, curso_id)
);

INSERT IGNORE INTO estudiantes (id, nombre, carnet) VALUES
    (1, 'Ana Lopez', '2024001'),
    (2, 'Carlos Perez', '2024002'),
    (3, 'Maria Gonzalez', '2024003'),
    (4, 'Luis Ramirez', '2024004');

INSERT IGNORE INTO cursos (id, nombre, creditos) VALUES
    (1, 'Programacion 2', 4),
    (2, 'Base de Datos 1', 3),
    (3, 'Matematica Discreta', 3);

-- nota NULL = todavia cursando / sin nota final registrada.
INSERT IGNORE INTO inscripciones (id, estudiante_id, curso_id, nota) VALUES
    (1, 1, 1, 90.00),
    (2, 1, 2, 85.00),
    (3, 2, 1, 78.50),
    (4, 3, 1, 95.00),
    (5, 3, 3, NULL),
    (6, 4, 2, NULL);
