package com.pontoflex.domain.calculo;

import com.pontoflex.domain.ponto.Batida;
import com.pontoflex.domain.ponto.TipoRegistro;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;

/**
 * Calcula horas trabalhadas, saldo e horas extras de uma jornada a partir das suas batidas.
 *
 * Periodos trabalhados sao os pares ENTRADA/FIM_INTERVALO -> INICIO_INTERVALO/SAIDA.
 * Adicional noturno e percentuais de hora extra (50%/100%) ficam para uma fase posterior.
 *
 * Classe de dominio pura: sem dependencia de Spring, testavel isoladamente.
 */
public class CalculoJornada {

    /** CLT art. 59: no maximo 2 horas extras por dia (o sistema apenas alerta, nao bloqueia). */
    public static final int LIMITE_EXTRAS_DIARIAS_MINUTOS = 120;

    /**
     * @param agora usado para contabilizar um periodo ainda em aberto (jornada em andamento)
     */
    public ResultadoCalculoJornada calcular(List<Batida> batidas, int minutosPrevistos, Instant agora) {
        List<Batida> ordenadas = batidas.stream().sorted(Comparator.comparing(Batida::horario)).toList();

        long minutosTrabalhados = 0;
        Instant inicioPeriodo = null;
        for (Batida batida : ordenadas) {
            if (abrePeriodo(batida.tipo())) {
                inicioPeriodo = batida.horario();
            } else if (inicioPeriodo != null) {
                minutosTrabalhados += Duration.between(inicioPeriodo, batida.horario()).toMinutes();
                inicioPeriodo = null;
            }
        }

        boolean emAndamento = inicioPeriodo != null;
        if (emAndamento && agora.isAfter(inicioPeriodo)) {
            minutosTrabalhados += Duration.between(inicioPeriodo, agora).toMinutes();
        }

        boolean encerrada = !ordenadas.isEmpty() && ordenadas.getLast().tipo() == TipoRegistro.SAIDA;
        int trabalhados = Math.toIntExact(minutosTrabalhados);
        int saldo = trabalhados - minutosPrevistos;
        int extras = Math.max(saldo, 0);

        return new ResultadoCalculoJornada(
                trabalhados, minutosPrevistos, saldo, extras, extras > LIMITE_EXTRAS_DIARIAS_MINUTOS, encerrada);
    }

    private static boolean abrePeriodo(TipoRegistro tipo) {
        return tipo == TipoRegistro.ENTRADA || tipo == TipoRegistro.FIM_INTERVALO;
    }

    public record ResultadoCalculoJornada(
            int minutosTrabalhados,
            int minutosPrevistos,
            int saldoMinutos,
            int minutosExtras,
            boolean excedeLimiteExtras,
            boolean encerrada) {}
}
