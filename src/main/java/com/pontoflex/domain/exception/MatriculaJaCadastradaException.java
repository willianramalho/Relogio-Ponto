package com.pontoflex.domain.exception;

public class MatriculaJaCadastradaException extends RegraDeNegocioException {

    public MatriculaJaCadastradaException(String matricula) {
        super("Ja existe um colaborador cadastrado com a matricula: " + matricula);
    }

    @Override
    public String getCodigo() {
        return "MATRICULA_JA_CADASTRADA";
    }
}
