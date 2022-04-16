use mascotasintegradora;
select * from usuarios;
select * from usuario_rol;
select * from roles;
select * from bitacora;
select * from blogs;
select * from sesiones;


drop table bitacora;
drop table sesiones;

create table if not exists bitacora (
    fecha_movimiento DATETIME DEFAULT NOW(),
    usuario_id BIGINT NOT NULL,
    dato_antiguo JSON DEFAULT NULL,
    dato_nuevo JSON DEFAULT NULL,
    entidad VARCHAR(50) NOT NULL,
    movimiento VARCHAR(25) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

create table if not exists sesiones (
    fecha_inicio DATETIME DEFAULT NOW(),
    usuario_id BIGINT NOT NULL,
	fecha_fin DATETIME DEFAULT NULL,
	movimiento VARCHAR(25) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

DELIMITER $$
DROP PROCEDURE IF EXISTS registroSesion $$
CREATE PROCEDURE registroSesion(IN usuario_id BIGINT)
BEGIN
    INSERT INTO sesiones(usuario_id, movimiento)
	VALUES (usuario_id, "sesion activa");
	select * from usuarios where id = usuario_id;
END$$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS registroCerrarSesion $$
CREATE PROCEDURE registroCerrarSesion(IN usuario_id BIGINT, fechaFin DATETIME)
BEGIN
 INSERT INTO sesiones(usuario_id, fecha_fin, movimiento)
	VALUES (usuario_id , fechaFin, "sesion inactiva");
	select * from usuarios where id = usuario_id;

END$$
DELIMITER ;
------------------------------------------------------------



DELIMITER $$
DROP PROCEDURE IF EXISTS registroDonacion $$
CREATE PROCEDURE registroDonacion(IN usuario_id BIGINT, autorizacion VARCHAR(20), estado TINYINT,
fechaDonacion DATETIME, monto DOUBLE, donadorId BIGINT)
BEGIN
    INSERT INTO bitacora(usuario_id, dato_antiguo, dato_nuevo, entidad, movimiento )
	VALUES (usuario_id, '{}', JSON_OBJECT("autorizacion", autorizacion, "estado", estado,
    "fechaDonacion", fechaDonacion, "monto", monto, "donadorId", donadorId), "donacion", "registro");
	select * from donaciones;
END$$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS actualizarDonacion $$
CREATE PROCEDURE actualizarDonacion(IN usuario_id BIGINT, autorizacionAnterior VARCHAR(20), estadoAnterior TINYINT,
fechaDonacionAnterior DATETIME, montoAnterior DOUBLE, donadorIdAnterior BIGINT,  autorizacion VARCHAR(20), estado TINYINT,
fechaDonacion DATETIME, monto DOUBLE, donadorId BIGINT)
BEGIN
    INSERT INTO bitacora(usuario_id, dato_antiguo, dato_nuevo, entidad, movimiento )
	VALUES (usuario_id,JSON_OBJECT("autorizacion", autorizacionAnterior, "estado", estadoAnterior,
    "fechaDonacion", fechaDonacionAnterior, "monto", montoAnterior, "donadorId", donadorIdAnterior),
    JSON_OBJECT("autorizacion", autorizacion, "estado", estado, "fechaDonacion", fechaDonacion, "monto", 
    monto, "donadorId", donadorId), "donacion", "actualizacion");
	select * from donaciones;
END$$
DELIMITER ;


DELIMITER $$
DROP PROCEDURE IF EXISTS eliminarDonacion $$
CREATE PROCEDURE eliminarDonacion(IN usuario_id BIGINT, autorizacion VARCHAR(20), estado TINYINT,
fechaDonacion DATETIME, monto DOUBLE, donadorId BIGINT)
BEGIN
    INSERT INTO bitacora(usuario_id, dato_antiguo, dato_nuevo, entidad, movimiento )
	VALUES (usuario_id,JSON_OBJECT("autorizacion", autorizacion, "estado", estado,
    "fechaDonacion", fechaDonacion, "monto", monto, "donadorId", donadorId), "{}", "donacion", "eliminacion");
	select * from donaciones;
END$$
DELIMITER ;


-------------------------------------------------------

DELIMITER $$
DROP PROCEDURE IF EXISTS registroUsuario $$
CREATE PROCEDURE registroUsuario(IN usuario_id BIGINT, apellidos VARCHAR(50), contrasena VARCHAR(255), 
correo VARCHAR(255), direccion VARCHAR(255), fechaRegistro DATETIME, habilitado TINYINT, nombre VARCHAR(50),
telefono VARCHAR(25))
BEGIN
    INSERT INTO bitacora(usuario_id, dato_antiguo, dato_nuevo, entidad, movimiento )
	VALUES (usuario_id, '{}', JSON_OBJECT("apellidos", apellidos, "contrasena", contrasena, "correo", correo
    , "direccion", direccion, "fechaRegistro", fechaRegistro, "habilitado", habilitado, "nombre", nombre,
    "telefono", telefono), 'usuario', 'registro');
	select * from usuarios;
END$$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS actualizarUsuario $$
CREATE PROCEDURE actualizarUsuario(IN usuario_id BIGINT, apellidosAnterior VARCHAR(50), contrasenaAnterior VARCHAR(255), 
correoAnterior VARCHAR(255), direccionAnterior VARCHAR(255), fechaRegistroAnterior DATETIME, habilitadoAnterior TINYINT,
nombreAnterior VARCHAR(50), telefonoAnterior VARCHAR(25), apellidos VARCHAR(50), contrasena VARCHAR(255), 
correo VARCHAR(255), direccion VARCHAR(255), fechaRegistro DATETIME, habilitado TINYINT, nombre VARCHAR(50),
telefono VARCHAR(25))
BEGIN
    INSERT INTO bitacora(usuario_id, dato_antiguo, dato_nuevo, entidad, movimiento )
	VALUES (usuario_id,JSON_OBJECT("apellidos", apellidosAnterior, "contrasena", contrasenaAnterior, "correo", correoAnterior
    , "direccion", direccionAnterior, "fechaRegistro", fechaRegistroAnterior, "habilitado", habilitadoAnterior, "nombre", nombreAnterior,
    "telefono", telefonoAnterior), JSON_OBJECT("apellidos", apellidos, "contrasena", contrasena, "correo", correo
    , "direccion", direccion, "fechaRegistro", fechaRegistro, "habilitado", habilitado, "nombre", nombre,
    "telefono", telefono), 'usuario', 'actualizacion');
	select * from usuarios;
END$$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS eliminarUsuario $$
CREATE PROCEDURE eliminarUsuario(IN usuario_id BIGINT, apellidos VARCHAR(50), contrasena VARCHAR(255), 
correo VARCHAR(255), direccion VARCHAR(255), fechaRegistro DATETIME, habilitado TINYINT, nombre VARCHAR(50),
telefono VARCHAR(25))
BEGIN
    INSERT INTO bitacora(usuario_id, dato_antiguo, dato_nuevo, entidad, movimiento )
	VALUES (usuario_id, '{}', JSON_OBJECT("apellidos", apellidos, "contrasena", contrasena, "correo", correo
    , "direccion", direccion, "fechaRegistro", fechaRegistro, "habilitado", habilitado, "nombre", nombre,
    "telefono", telefono), 'usuario', 'eliminacion');
	select * from usuarios;
END$$
DELIMITER ;

--------------------------------------------------------

DELIMITER $$
DROP PROCEDURE IF EXISTS registroSolicitud $$
CREATE PROCEDURE registroSolicitud(IN usuario_id BIGINT, aprobado TINYINT, fechaSolicitud DATETIME,
adoptadorId BIGINT, mascotaId BIGINT)
BEGIN
    INSERT INTO bitacora(usuario_id, dato_antiguo, dato_nuevo, entidad, movimiento )
	VALUES (usuario_id, '{}', JSON_OBJECT("aprobado", aprobado, "fechaSolicitud", fechaSolicitud ,
    "adoptadorId", adoptadorId, "mascotaId", mascotaId), 'solicitud', 'registro');
	select * from solicitudes;
END$$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS actualizarSolicitud $$
CREATE PROCEDURE actualizarSolicitud(IN usuario_id BIGINT, aprobadoAnterior TINYINT, fechaSolicitudAnterior DATETIME,
adoptadorIdAnterior BIGINT, mascotaIdAnterior BIGINT, aprobado TINYINT, fechaSolicitud DATETIME,
adoptadorId BIGINT, mascotaId BIGINT)
BEGIN
    INSERT INTO bitacora(usuario_id, dato_antiguo, dato_nuevo, entidad, movimiento )
	VALUES (usuario_id, JSON_OBJECT("aprobado", aprobadoAnterior, "fechaSolicitud", fechaSolicitudAnterior ,
    "adoptadorId", adoptadorIdAnterior, "mascotaId", mascotaIdAnterior), JSON_OBJECT("aprobado", aprobado, "fechaSolicitud", fechaSolicitud ,
    "adoptadorId", adoptadorId, "mascotaId", mascotaId), 'solicitud', 'actualizacion');
	select * from solicitudes;
END$$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS eliminarSolicitud $$
CREATE PROCEDURE eliminarSolicitud(IN usuario_id BIGINT, aprobado TINYINT, fechaSolicitud DATETIME,
adoptadorId BIGINT, mascotaId BIGINT)
BEGIN
    INSERT INTO bitacora(usuario_id, dato_antiguo, dato_nuevo, entidad, movimiento )
	VALUES (usuario_id, JSON_OBJECT("aprobado", aprobado, "fechaSolicitud", fechaSolicitud ,
    "adoptadorId", adoptadorId, "mascotaId", mascotaId), '{}','solicitud', 'eliminacion');
	select * from solicitudes;
END$$
DELIMITER ;

------------------------------------------

DELIMITER $$
DROP PROCEDURE IF EXISTS registroMascota $$
CREATE PROCEDURE registroMascota(IN usuario_id BIGINT, aprobadoRegistro VARCHAR(50), detalles LONGTEXT, edad VARCHAR(30),
fechaRegistro DATETIME, imagen VARCHAR(40), nombre VARCHAR(50), sexo TINYINT, tipo TINYINT, caracterId BIGINT,
colorId BIGINT, tamanoId BIGINT, activo TINYINT, disponibleAdopcion TINYINT)
BEGIN
    INSERT INTO bitacora(usuario_id, dato_antiguo, dato_nuevo, entidad, movimiento )
	VALUES (usuario_id, '{}', JSON_OBJECT("aprobadoRegistro", aprobadoRegistro,  "detalles", detalles
    , "edad", edad, "fechaRegistro", fechaRegistro, "imagen", imagen, "nombre", nombre, "sexo", sexo,
    "tipo", tipo, "caracterId", caracterId, "colorId", colorId, "tamanoId", tamanoId, "activo", activo,
    "disponibleAdopcion", disponibleAdopcion), 'mascota', 'registro');
	select * from mascotas;
END$$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS actualizarMascota $$
CREATE PROCEDURE actualizarMascota(IN usuario_id BIGINT, aprobadoRegistroAnterior VARCHAR(50), detallesAnterior LONGTEXT,
 edadAnterior VARCHAR(30), fechaRegistroAnterior DATETIME, imagenAnterior VARCHAR(40), nombreAnterior VARCHAR(50), sexoAnterior TINYINT,
 tipoAnterior TINYINT, caracterIdAnterior BIGINT,
 colorIdAnterior BIGINT, tamanoIdAnterior BIGINT, activoAnterior TINYINT, disponibleAdopcionAnterior TINYINT, 
 aprobadoRegistro VARCHAR(50), detalles LONGTEXT, edad VARCHAR(30),
 fechaRegistro DATETIME, imagen VARCHAR(40), nombre VARCHAR(50), sexo TINYINT, tipo TINYINT, caracterId BIGINT,
 colorId BIGINT, tamanoId BIGINT, activo TINYINT, disponibleAdopcion TINYINT)
BEGIN
    INSERT INTO bitacora(usuario_id, dato_antiguo, dato_nuevo, entidad, movimiento )
	VALUES (usuario_id, JSON_OBJECT("aprobadoRegistro", aprobadoRegistroAnterior,  "detalles", detallesAnterior
    , "edad", edadAnterior, "fechaRegistro", fechaRegistroAnterior, "imagen", imagenAnterior, "nombre", nombreAnterior, 
    "sexo", sexoAnterior,
    "tipo", tipoAnterior, "caracterId", caracterIdAnterior, "colorId", colorIdAnterior, "tamanoId", tamanoIdAnterior, "activo", activoAnterior,
    "disponibleAdopcion", disponibleAdopcionAnterior), JSON_OBJECT("aprobadoRegistro", aprobadoRegistro,  "detalles", detalles
    , "edad", edad, "fechaRegistro", fechaRegistro, "imagen", imagen, "nombre", nombre, "sexo", sexo,
    "tipo", tipo, "caracterId", caracterId, "colorId", colorId, "tamanoId", tamanoId, "activo", activo,
    "disponibleAdopcion", disponibleAdopcion), 'mascota', 'actualizacion');
	select * from mascotas;
END$$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS eliminarMascota $$
CREATE PROCEDURE eliminarMascota(IN usuario_id BIGINT, aprobadoRegistro VARCHAR(50), detalles LONGTEXT, edad VARCHAR(30),
fechaRegistro DATETIME, imagen VARCHAR(40), nombre VARCHAR(50), sexo TINYINT, tipo TINYINT, caracterId BIGINT,
colorId BIGINT, tamanoId BIGINT, activo TINYINT, disponibleAdopcion TINYINT)
BEGIN
    INSERT INTO bitacora(usuario_id, dato_antiguo, dato_nuevo, entidad, movimiento )
	VALUES (usuario_id, JSON_OBJECT("aprobadoRegistro", aprobadoRegistro,  "detalles", detalles
    , "edad", edad, "fechaRegistro", fechaRegistro, "imagen", imagen, "nombre", nombre, "sexo", sexo,
    "tipo", tipo, "caracterId", caracterId, "colorId", colorId, "tamanoId", tamanoId, "activo", activo,
    "disponibleAdopcion", disponibleAdopcion), '{}', 'mascota', 'eliminacion');
	select * from mascotas;
END$$
DELIMITER ;

----------------------------------------

DELIMITER $$
DROP PROCEDURE IF EXISTS registroBlog $$
CREATE PROCEDURE registroBlog(IN usuario_id BIGINT, titulo VARCHAR(50), contenido LONGTEXT, esPrincipal TINYINT,
fechaRegistro DATETIME, imagen VARCHAR(50))
BEGIN
    INSERT INTO bitacora(usuario_id, dato_antiguo, dato_nuevo, entidad, movimiento )
	VALUES (usuario_id, '{}', JSON_OBJECT("titulo", titulo, "contenido", contenido, "esPrincipal", esPrincipal,
    "fechaRegistro",fechaRegistro, "imagen", imagen), 'blog', 'registro');
	select * from blogs;
END$$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS actualizarBlog $$
CREATE PROCEDURE actualizarBlog(IN usuario_id BIGINT, tituloAnterior VARCHAR(50), contenidoAnterior LONGTEXT, esPrincipalAnterior TINYINT,
fechaRegistroAnterior DATETIME, imagenAnterior VARCHAR(50), titulo VARCHAR(50), contenido LONGTEXT, esPrincipal TINYINT,
fechaRegistro DATETIME, imagen VARCHAR(50))
BEGIN
    INSERT INTO bitacora(usuario_id, dato_antiguo, dato_nuevo, entidad, movimiento )
	VALUES (usuario_id, JSON_OBJECT("titulo", tituloAnterior, "contenido", contenidoAnterior, "esPrincipal", esPrincipalAnterior,
    "fechaRegistro",fechaRegistroAnterior, "imagen", imagenAnterior), 
    JSON_OBJECT("titulo", titulo, "contenido", contenido, "esPrincipal", esPrincipal,
    "fechaRegistro",fechaRegistro, "imagen", imagen), 'blog', 'actualizacion');
	select * from blogs;
END$$
DELIMITER ;


DELIMITER $$
DROP PROCEDURE IF EXISTS eliminarBlog $$
CREATE PROCEDURE eliminarBlog(IN usuario_id BIGINT, titulo VARCHAR(50), contenido LONGTEXT, esPrincipal TINYINT,
fechaRegistro DATETIME, imagen VARCHAR(50))
BEGIN
    INSERT INTO bitacora(usuario_id, dato_antiguo, dato_nuevo, entidad, movimiento )
	VALUES (usuario_id, JSON_OBJECT("titulo", titulo, "contenido", contenido, "esPrincipal", esPrincipal,
    "fechaRegistro",fechaRegistro, "imagen", imagen), '{}', 'blog', 'eliminacion');
	select * from blogs;
END$$
DELIMITER ;






