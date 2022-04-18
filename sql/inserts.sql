INSERT INTO tamanos (nombre) VALUES ('Pequeño'), ('Mediano'), ('Grande');

INSERT INTO colores (nombre) VALUES ('Blanco'), ('Café'), ('Gris'), ('Negro'), ('Atigrado'), ('Bicolor'), ('Con manchas'), ('Varios colores');

INSERT INTO caracteres (nombre) VALUES ('Activo'), ('Independiente'), ('Juguetón'), ('Protector'), ('Ruidoso'), ('Tímido'), ('Tranquilo'), ('Amoroso');

INSERT INTO roles(id, nombre) VALUES (1, 'ROL_ADMINISTRADOR'), (2, 'ROL_VOLUNTARIO'), (3, 'ROL_ADOPTADOR');

-- Configura un administrador
INSERT INTO usuarios (id, nombre, apellidos, habilitado, correo, contrasena, direccion, fecha_registro, telefono) VALUES 
(1, '', '', 1, '', '', '', NOW(), '');

-- Asignale el rol
INSERT INTO usuario_rol (usuario_id, rol_id) VALUES (1, 1);