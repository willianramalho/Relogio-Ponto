package com.pontoflex.domain.ponto;

/**
 * Status de uma batida de ponto apos as validacoes de negocio.
 */
public enum StatusRegistro {
    VALIDO,
    PENDENTE_APROVACAO,
    AGUARDANDO_JUSTIFICATIVA
}
