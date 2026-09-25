package com.pontoflex.domain.exception;

public class CpfJaCadastradoException extends RegraDeNegocioException {

    public CpfJaCadastradoException(String cpf) {
        super("Ja existe um colaborador cadastrado com o CPF informado");
    }

    @Override
    public String getCodigo() {
        return "CPF_JA_CADASTRADO";
    }
}
