CREATE TABLE tipo (
    id BIGSERIAL PRIMARY KEY,
    tipo VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE calendario (
    id BIGSERIAL PRIMARY KEY,
    fecha DATE,
    id_tipo BIGINT NOT NULL REFERENCES tipo(id),
    descripcion VARCHAR(255)
);
