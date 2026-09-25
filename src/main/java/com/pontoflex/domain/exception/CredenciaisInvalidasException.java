package com.pontoflex.domain.exception;

public class CredenciaisInvalidasException extends RegraDeNegocioException {

    public CredenciaisInvalidasException() {
        super("Email ou senha invalidos");
    }

    @Override
    public String getCodigo() {
        return "CREDENCIAIS_INVALIDAS";
    }
}
