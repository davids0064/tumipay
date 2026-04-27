-- Script de inicialización para PostgreSQL
-- Crea rol/usuario, base de datos y asigna privilegios.
-- Modifica el nombre de usuario y la contraseña según sea necesario.

-- Eliminar si existen (útil para re-ejecutar en entorno de desarrollo)
DROP DATABASE IF EXISTS tumipaydb;
DROP ROLE IF EXISTS tumipay_user;

-- Crear el usuario con contraseña (reemplaza 'CHANGE_ME_PASSWORD')
CREATE ROLE tumipay_user WITH LOGIN PASSWORD 'tUm1p4y_s3cr3t';

-- Crear la base de datos y asignarle el propietario
CREATE DATABASE tumipaydb OWNER tumipay_user;

-- Dar todos los privilegios sobre la base de datos al usuario
GRANT ALL PRIVILEGES ON DATABASE tumipaydb TO tumipay_user;

-- Nota: en producción guarda las credenciales en un secreto seguro y no en texto plano.
