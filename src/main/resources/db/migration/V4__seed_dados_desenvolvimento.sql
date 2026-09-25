-- Dados de desenvolvimento: escala e local de trabalho padrao usados pelo fluxo de
-- autoregistro (POST /api/auth/registrar) enquanto nao existem cadastros proprios de
-- escala/local de trabalho. Revisar quando esses modulos forem implementados.

INSERT INTO escala_trabalho (id, tipo, horas_diarias_previstas, dias_trabalho)
VALUES (
    '11111111-1111-1111-1111-111111111111',
    '5X2',
    8,
    '["SEG","TER","QUA","QUI","SEX"]'::jsonb
);

INSERT INTO local_trabalho (id, nome, latitude, longitude, raio_metros, municipio, estado)
VALUES (
    '22222222-2222-2222-2222-222222222222',
    'Matriz (padrao de desenvolvimento)',
    -26.3044000,
    -48.8487000,
    150,
    'Joinville',
    'SC'
);
