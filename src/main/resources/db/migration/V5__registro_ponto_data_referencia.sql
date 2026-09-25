-- Data da jornada a que a batida pertence (fuso do local de trabalho). Uma jornada que
-- cruza a meia-noite continua na data da ENTRADA, o que simplifica o calculo diario.
ALTER TABLE registro_ponto ADD COLUMN data_referencia DATE;
UPDATE registro_ponto SET data_referencia = (horario_utc AT TIME ZONE 'America/Sao_Paulo')::date;
ALTER TABLE registro_ponto ALTER COLUMN data_referencia SET NOT NULL;

CREATE INDEX idx_registro_ponto_colaborador_data
    ON registro_ponto (colaborador_id, data_referencia);

-- Navegadores em desktop costumam reportar precisao de varios km (geolocalizacao por IP);
-- NUMERIC(6,2) estourava acima de 9999.99m.
ALTER TABLE registro_ponto ALTER COLUMN precisao_metros TYPE NUMERIC(10, 2);
