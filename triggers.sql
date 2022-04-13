create table if not exists bitacora_blog (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    fecha_movimiento DATETIME DEFAULT NOW(),
    dato_antiguo JSON DEFAULT null,
    dato_nuevo JSON NOT NULL,
    entidad VARCHAR(50) NOT NULL,
    movimiento VARCHAR(25) NOT NULL
);
create table if not exists bitacora_mascota (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    fecha_movimiento DATETIME DEFAULT NOW(),
    dato_antiguo TEXT DEFAULT null,
    daton_nuevo TEXT NOT NULL,
    entidad VARCHAR(50) NOT NULL,
    movimiento VARCHAR(25) NOT NULL
);
create table if not exists bitacora_usuario(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    fecha_movimiento DATETIME DEFAULT NOW(),
    dato_antiguo TEXT DEFAULT null,
    daton_nuevo TEXT NOT NULL,
    entidad VARCHAR(50) NOT NULL,
    movimiento VARCHAR(25) NOT NULL
);

DELIMITER $$
DROP TRIGGER IF EXISTS insertarDatosBlog $$
CREATE TRIGGER insertarDatosBlog
AFTER INSERT ON blogs FOR EACH ROW
BEGIN
INSERT INTO bitacora_blog(dato_antiguo, dato_nuevo, entidad, movimiento )
VALUES ('{}',JSON_OBJECT("id", NEW.id, "titulo", NEW.titulo, "contenido", NEW.contenido, "esPrincipal", NEW.es_principal,
 "fechaRegistro", NEW.fecha_registro, "imagen", NEW.imagen), 'blog', 'registro');
END$$
DELIMITER ;

DELIMITER $$
DROP TRIGGER IF EXISTS actualizarDatosBlog $$
CREATE TRIGGER actualizarDatosBlog
BEFORE UPDATE ON blogs FOR EACH ROW
BEGIN
INSERT INTO bitacora_blog(dato_antiguo, dato_nuevo, entidad, movimiento )
VALUES (JSON_OBJECT("id", OLD.id, "titulo", OLD.titulo, "contenido", OLD.contenido, "esPrincipal", OLD.es_principal,
 "fechaRegistro", OLD.fecha_registro, "imagen", OLD.imagen), 
 JSON_OBJECT("titulo", NEW.titulo, "contenido", NEW.contenido, "esPrincipal", NEW.es_principal,
 "fechaRegistro", NEW.fecha_registro, "imagen", NEW.imagen), 'blog', 'actualizacion');
END$$
DELIMITER ;

DELIMITER $$
DROP TRIGGER IF EXISTS eliminarDatosBlog $$
CREATE TRIGGER eliminarDatosBlog
BEFORE DELETE ON blogs FOR EACH ROW
BEGIN
INSERT INTO bitacora_blog(dato_antiguo, dato_nuevo, entidad, movimiento )
VALUES ('{}', JSON_OBJECT("id", OLD.id, "titulo", OLD.titulo, "contenido", OLD.contenido, "esPrincipal", OLD.es_principal,
 "fechaRegistro", OLD.fecha_registro, "imagen", OLD.imagen), 'blog', 'eliminacion');
END$$
DELIMITER ;

DELIMITER $$
DROP TRIGGER IF EXISTS insertarDatosMascota $$
CREATE TRIGGER insertarDatosMascota
AFTER INSERT ON mascotas FOR EACH ROW
BEGIN
INSERT INTO bitacora_mascota(dato_antiguo, dato_nuevo, entidad, movimiento )
VALUES ('{}',JSON_OBJECT("id", NEW.id, "aprobadoRegistro", NEW.aprobado_registro, "detalles", NEW.detalles, "edad", NEW.edad,
 "fechaRegistro", NEW.fecha_registro, "imagen", NEW.imagen, "nombre", NEW.nombre, "sexo", NEW.sexo, "tipo", NEW.tipo,
 "caracterId", NEW.caracter_id, "colorId", NEW.color_id, "tamanoId", NEW.tamano_id, "activo", NEW.activo,
 "disponibleAdopcion", NEW.disponible_adopcion), 'mascota', 'registro');
END$$
DELIMITER ;

DELIMITER $$
DROP TRIGGER IF EXISTS actualizarDatosMascota $$
CREATE TRIGGER actualizarDatosMascota
BEFORE UPDATE ON mascotas FOR EACH ROW
BEGIN
INSERT INTO bitacora_mascota(dato_antiguo, dato_nuevo, entidad, movimiento )
VALUES (JSON_OBJECT("id", OLD.id, "aprobadoRegistro", OLD.aprobado_registro, "detalles", OLD.detalles, "edad", OLD.edad,
 "fechaRegistro", OLD.fecha_registro, "imagen", OLD.imagen, "nombre", OLD.nombre, "sexo", OLD.sexo, "tipo", OLD.tipo,
 "caracterId", OLD.caracter_id, "colorId", OLD.color_id, "tamanoId", OLD.tamano_id, "activo", OLD.activo,
 "disponibleAdopcion", OLD.disponible_adopcion), JSON_OBJECT("id", OLD.id, "aprobadoRegistro", NEW.aprobado_registro, "detalles", 
 NEW.detalles, "edad", NEW.edad, "fechaRegistro", NEW.fecha_registro, "imagen", NEW.imagen, "nombre", 
 NEW.nombre, "sexo", NEW.sexo, "tipo", NEW.tipo, "caracterId", NEW.caracter_id, "colorId", NEW.color_id,
 "tamanoId", NEW.tamano_id, "activo", NEW.activo, "disponibleAdopcion", NEW.disponible_adopcion), 'mascota', 'actualizacion');
END$$
DELIMITER ;


DELIMITER $$
DROP TRIGGER IF EXISTS eliminarDatosMascota $$
CREATE TRIGGER eliminarDatosMascota
BEFORE DELETE ON mascotas FOR EACH ROW
BEGIN
INSERT INTO bitacora_mascota(dato_antiguo, dato_nuevo, entidad, movimiento )
VALUES ('{}', JSON_OBJECT("id", OLD.id, "aprobadoRegistro", OLD.aprobado_registro, "detalles", 
 OLD.detalles, "edad", OLD.edad, "fechaRegistro", OLD.fecha_registro, "imagen", OLD.imagen, "nombre", 
 OLD.nombre, "sexo", OLD.sexo, "tipo", OLD.tipo, "caracterId", OLD.caracter_id, "colorId", OLD.color_id,
 "tamanoId", OLD.tamano_id, "activo", OLD.activo, "disponibleAdopcion", OLD.disponible_adopcion), 'mascota', 'eliminacion');
END$$
DELIMITER ;


DELIMITER $$
DROP TRIGGER IF EXISTS insertarDatosSolicitud $$
CREATE TRIGGER insertarDatosSolicitud
AFTER INSERT ON solicitudes FOR EACH ROW
BEGIN
INSERT INTO bitacora_mascota(dato_antiguo, dato_nuevo, entidad, movimiento )
VALUES ('{}',JSON_OBJECT("id", NEW.id, "aprobado", NEW.aprobado, "fechaSolicitud", NEW.fechaSolicitud, "adoptadorId", NEW.adoptador_id,
 "mascotaId", NEW.mascota_id), 'solicitud', 'registro');
END$$
DELIMITER ;

DELIMITER $$
DROP TRIGGER IF EXISTS actualizarDatosSolicitud $$
CREATE TRIGGER actualizarDatosSolicitud
BEFORE UPDATE ON solicitudes FOR EACH ROW
BEGIN
INSERT INTO bitacora_mascota(dato_antiguo, dato_nuevo, entidad, movimiento )
VALUES (JSON_OBJECT("id", OLD.id,"aprobado", OLD.aprobado, "fechaSolicitud", OLD.fechaSolicitud, "adoptadorId", OLD.adoptador_id,
 "mascotaId", OLD.mascota_id), JSON_OBJECT("id", OLD.id, "aprobado", NEW.aprobado, "fechaSolicitud", NEW.fechaSolicitud, "adoptadorId", NEW.adoptador_id,
 "mascotaId", NEW.mascota_id), 'solicitud', 'actualizacion');
END$$
DELIMITER ;


DELIMITER $$
DROP TRIGGER IF EXISTS eliminarDatosSolicitud $$
CREATE TRIGGER eliminarDatosSolicitud
BEFORE DELETE ON solicitudes FOR EACH ROW
BEGIN
INSERT INTO bitacora_mascota(dato_antiguo, dato_nuevo, entidad, movimiento )
VALUES ('{}',JSON_OBJECT("id", OLD.id, "aprobado", OLD.aprobado, "fechaSolicitud", OLD.fechaSolicitud, "adoptadorId", OLD.adoptador_id,
 "mascotaId", OLD.mascota_id), 'solicitud', 'actualizacion');
END$$
DELIMITER ;


DELIMITER $$
DROP TRIGGER IF EXISTS insertarDatosUsuario $$
CREATE TRIGGER insertarDatosUsuario
AFTER INSERT ON usuarios FOR EACH ROW
BEGIN
INSERT INTO bitacora_usuario(dato_antiguo, dato_nuevo, entidad, movimiento )
VALUES ('{}',JSON_OBJECT("id", NEW.id, "nombre", NEW.nombre, "apellidos", NEW.apellidos, "contrasena", NEW.contrasena,
 "correo", NEW.correo, "dirreccion", NEW.direccion, "fechaRegistro", NEW.fecha_registro, "habilitado", NEW.habilitado,
 "telefono", NEW.telefono), 'usuario', 'registro');
END$$
DELIMITER ;

DELIMITER $$
DROP TRIGGER IF EXISTS actualizarDatosUsuario $$
CREATE TRIGGER actualizarDatosUsuario
BEFORE UPDATE ON usuarios FOR EACH ROW
BEGIN
INSERT INTO bitacora_usuario(dato_antiguo, dato_nuevo, entidad, movimiento )
VALUES (JSON_OBJECT("id", OLD.id, "nombre", OLD.nombre, "apellidos", OLD.apellidos, "contrasena", OLD.contrasena,
 "correo", OLD.correo, "dirreccion", OLD.direccion, "fechaRegistro", OLD.fecha_registro, "habilitado", OLD.habilitado,
 "telefono", OLD.telefono),JSON_OBJECT("id", OLD.id, "nombre", NEW.nombre, "apellidos", NEW.apellidos, "contrasena", NEW.contrasena,
 "correo", NEW.correo, "dirreccion", NEW.direccion, "fechaRegistro", NEW.fecha_registro, "habilitado", NEW.habilitado,
 "telefono", NEW.telefono), 'usuario', 'actualizacion');
END$$
DELIMITER ;

DELIMITER $$
DROP TRIGGER IF EXISTS eliminarDatosUsuario $$
CREATE TRIGGER eliminarDatosUsuario
BEFORE DELETE ON usuarios FOR EACH ROW
BEGIN
INSERT INTO bitacora_usuario(dato_antiguo, dato_nuevo, entidad, movimiento )
VALUES ('{}',JSON_OBJECT("id", OLD.id, "nombre", OLD.nombre, "apellidos", OLD.apellidos, "contrasena", OLD.contrasena,
 "correo", OLD.correo, "dirreccion", OLD.direccion, "fechaRegistro", OLD.fecha_registro, "habilitado", OLD.habilitado,
 "telefono", OLD.telefono),'usuario', 'eliminacion');
END$$
DELIMITER ;

DELIMITER $$
DROP TRIGGER IF EXISTS insertarDatosDonacion $$
CREATE TRIGGER insertarDatosDonacion
AFTER INSERT ON donaciones FOR EACH ROW
BEGIN
INSERT INTO bitacora_usuario(dato_antiguo, dato_nuevo, entidad, movimiento )
VALUES ('{}',JSON_OBJECT("id", NEW.id"autorizacion", NEW.autorizacion, "estado", NEW.estado, "fechaDonacion", NEW.fecha_donacion,
 "monto", NEW.monto, "donadorId", NEW.donador_id),'donacion', 'registro');
END$$
DELIMITER ;

DELIMITER $$
DROP TRIGGER IF EXISTS actualizarDatosDonacion $$
CREATE TRIGGER actualizarDatosDonacion
BEFORE UPDATE ON donaciones FOR EACH ROW
BEGIN
INSERT INTO bitacora_usuario(dato_antiguo, dato_nuevo, entidad, movimiento )
VALUES (JSON_OBJECT("id", OLD.id, "autorizacion", OLD.autorizacion, "estado", OLD.estado, "fechaDonacion", OLD.fecha_donacion,
 "monto", OLD.monto, "donadorId", OLD.donador_id), JSON_OBJECT("id", OLD.id, "autorizacion", NEW.autorizacion, "estado", NEW.estado, 
 "fechaDonacion", NEW.fecha_donacion, "monto", NEW.monto, "donadorId", NEW.donador_id),'donacion', 'actualizacion');
END$$
DELIMITER ;

DELIMITER $$
DROP TRIGGER IF EXISTS eliminarDatosDonacion $$
CREATE TRIGGER eliminarDatosDonacion
BEFORE DELETE ON donaciones FOR EACH ROW
BEGIN
INSERT INTO bitacora_usuario(dato_antiguo, dato_nuevo, entidad, movimiento )
VALUES ('{}',JSON_OBJECT("id", OLD.id, "autorizacion", OLD.autorizacion, "estado", OLD.estado, "fechaDonacion", OLD.fecha_donacion,
 "monto", OLD.monto, "donadorId", OLD.donador_id),'donacion', 'eliminacion');
END$$
DELIMITER ;