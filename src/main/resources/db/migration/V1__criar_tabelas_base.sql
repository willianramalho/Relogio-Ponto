CREATE TABLE escala_trabalho (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tipo VARCHAR(30) NOT NULL,
    horas_diarias_previstas INTEGER NOT NULL,
    dias_trabalho JSONB NOT NULL
);

CREATE TABLE local_trabalho (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(150) NOT NULL,
    latitude NUMERIC(10, 7) NOT NULL,
    longitude NUMERIC(10, 7) NOT NULL,
    raio_metros INTEGER NOT NULL,
    municipio VARCHAR(100) NOT NULL,
    estado VARCHAR(2) NOT NULL
);

CREATE TABLE colaborador (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(150) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    matricula VARCHAR(30) NOT NULL UNIQUE,
    escala_id UUID NOT NULL REFERENCES escala_trabalho(id),
    local_trabalho_id UUID NOT NULL REFERENCES local_trabalho(id),
    gestor_id UUID REFERENCES colaborador(id),
    data_admissao DATE NOT NULL
);

CREATE TABLE role (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE usuario (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    colaborador_id UUID NOT NULL REFERENCES colaborador(id),
    email VARCHAR(150) NOT NULL UNIQUE,
    senha_hash VARCHAR(255) NOT NULL
);

CREATE TABLE usuario_role (
    usuario_id UUID NOT NULL REFERENCES usuario(id),
    role_id UUID NOT NULL REFERENCES role(id),
    PRIMARY KEY (usuario_id, role_id)
);

INSERT INTO role (nome) VALUES ('COLABORADOR'), ('GESTOR'), ('RH_ADMIN');
