package com.pontoflex.domain.exception;

public class BatidaForaDeOrdemException extends RegraDeNegocioException {

    public BatidaForaDeOrdemException(String detalhe) {
        super("Batida de ponto fora de ordem: " + detalhe);
    }

    @Override
    public String getCodigo() {
        return "BATIDA_FORA_DE_ORDEM";
    }
}
