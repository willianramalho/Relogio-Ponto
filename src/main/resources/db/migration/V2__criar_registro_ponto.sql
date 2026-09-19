CREATE TABLE registro_ponto (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    colaborador_id UUID NOT NULL REFERENCES colaborador(id),
    horario_utc TIMESTAMPTZ NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    latitude NUMERIC(10, 7) NOT NULL,
    longitude NUMERIC(10, 7) NOT NULL,
    precisao_metros NUMERIC(6, 2),
    status VARCHAR(30) NOT NULL,
    origem VARCHAR(20) NOT NULL DEFAULT 'APP',
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_registro_ponto_colaborador_horario
    ON registro_ponto (colaborador_id, horario_utc);

CREATE TABLE historico_edicao (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    registro_ponto_id UUID NOT NULL REFERENCES registro_ponto(id),
    editado_por UUID NOT NULL REFERENCES usuario(id),
    editado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    valor_anterior TEXT NOT NULL,
    motivo TEXT NOT NULL
);
