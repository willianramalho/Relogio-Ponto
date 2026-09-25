package com.pontoflex.domain.ponto;

import java.time.Instant;

/**
 * Visao minima de uma batida usada pelas regras de calculo, independente da persistencia.
 */
public record Batida(TipoRegistro tipo, Instant horario) {}
