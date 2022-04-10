create table if not exists bitacora_blog (
    fecha_movimiento DATETIME DEFAULT NOW(),
    usuario_id BIGINT NOT NULL,
    dato_antiguo TEXT DEFAULT null,
    dato_nuevo TEXT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    movimiento VARCHAR(25) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
create table if not exists bitacora_mascota (
    fecha_movimiento DATETIME DEFAULT NOW(),
    usuario_id BIGINT NOT NULL,
    dato_antiguo TEXT DEFAULT null,
    daton_nuevo TEXT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    movimiento VARCHAR(25) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
create table if not exists bitacora_usuario(
    fecha_movimiento DATETIME DEFAULT NOW(),
    usuario_id BIGINT NOT NULL,
    dato_antiguo TEXT DEFAULT null,
    daton_nuevo TEXT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    movimiento VARCHAR(25) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);