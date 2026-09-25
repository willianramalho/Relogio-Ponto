package com.pontoflex.domain.exception;

public class EmailJaCadastradoException extends RegraDeNegocioException {

    public EmailJaCadastradoException(String email) {
        super("Ja existe um usuario cadastrado com o email: " + email);
    }

    @Override
    public String getCodigo() {
        return "EMAIL_JA_CADASTRADO";
    }
}
