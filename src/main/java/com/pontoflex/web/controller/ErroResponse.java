package com.pontoflex.web.controller;

import java.time.Instant;
import java.util.List;

public record ErroResponse(String codigo, String mensagem, Instant timestamp, List<CampoInvalido> camposInvalidos) {

    public static ErroResponse de(String codigo, String mensagem) {
        return new ErroResponse(codigo, mensagem, Instant.now(), List.of());
    }

    public static ErroResponse deValidacao(List<CampoInvalido> camposInvalidos) {
        return new ErroResponse("ERRO_DE_VALIDACAO", "Um ou mais campos sao invalidos", Instant.now(), camposInvalidos);
    }

    public record CampoInvalido(String campo, String mensagem) {}
}
