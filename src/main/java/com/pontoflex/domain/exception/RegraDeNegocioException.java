package com.pontoflex.domain.exception;

/**
 * Excecao base para violacoes de regra de negocio do dominio de ponto/jornada.
 * Toda subclasse deve carregar um codigo estavel para o cliente da API tratar
 * programaticamente (ver GlobalExceptionHandler).
 */
public abstract class RegraDeNegocioException extends RuntimeException {

    protected RegraDeNegocioException(String mensagem) {
        super(mensagem);
    }

    public abstract String getCodigo();
}
