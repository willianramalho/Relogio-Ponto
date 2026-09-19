CREATE TABLE lancamento_banco_horas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    colaborador_id UUID NOT NULL REFERENCES colaborador(id),
    data_referencia DATE NOT NULL,
    minutos INTEGER NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    data_expiracao DATE,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_lancamento_banco_horas_colaborador
    ON lancamento_banco_horas (colaborador_id, data_referencia);

CREATE TABLE feriado (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    data DATE NOT NULL,
    descricao VARCHAR(150) NOT NULL,
    escopo VARCHAR(20) NOT NULL,
    municipio VARCHAR(100),
    estado VARCHAR(2)
);
