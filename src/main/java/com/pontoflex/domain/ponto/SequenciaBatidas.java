package com.pontoflex.domain.ponto;

import com.pontoflex.domain.exception.BatidaForaDeOrdemException;
import java.util.List;

/**
 * Define a ordem valida das batidas dentro de uma jornada:
 * ENTRADA -> (INICIO_INTERVALO -> FIM_INTERVALO)* -> SAIDA.
 *
 * Classe de dominio pura: sem dependencia de Spring, testavel isoladamente.
 */
public final class SequenciaBatidas {

    private SequenciaBatidas() {}

    /**
     * @param ultimoTipo ultima batida da jornada em aberto, ou {@code null} quando nao ha jornada em aberto
     */
    public static List<TipoRegistro> proximosPermitidos(TipoRegistro ultimoTipo) {
        if (ultimoTipo == null) {
            return List.of(TipoRegistro.ENTRADA);
        }
        return switch (ultimoTipo) {
            case ENTRADA, FIM_INTERVALO -> List.of(TipoRegistro.INICIO_INTERVALO, TipoRegistro.SAIDA);
            case INICIO_INTERVALO -> List.of(TipoRegistro.FIM_INTERVALO);
            case SAIDA -> List.of(TipoRegistro.ENTRADA);
        };
    }

    public static void validar(TipoRegistro ultimoTipo, TipoRegistro novoTipo) {
        List<TipoRegistro> permitidos = proximosPermitidos(ultimoTipo);
        if (!permitidos.contains(novoTipo)) {
            String anterior = ultimoTipo == null ? "nenhuma jornada em aberto" : "ultima batida foi " + ultimoTipo;
            throw new BatidaForaDeOrdemException(
                    novoTipo + " nao e permitida (" + anterior + "; esperado: " + permitidos + ")");
        }
    }
}
