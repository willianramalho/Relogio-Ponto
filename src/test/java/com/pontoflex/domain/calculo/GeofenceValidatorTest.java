package com.pontoflex.domain.calculo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class GeofenceValidatorTest {

    private final GeofenceValidator validator = new GeofenceValidator();

    // Local de trabalho de exemplo: Joinville/SC
    private static final double LAT_LOCAL = -26.3044;
    private static final double LON_LOCAL = -48.8487;

    @Test
    @DisplayName("Batida no mesmo ponto do local de trabalho deve ter distancia zero e estar dentro do raio")
    void batidaNoMesmoPontoDeveEstarDentroDoRaio() {
        var resultado = validator.validar(LAT_LOCAL, LON_LOCAL, 150, LAT_LOCAL, LON_LOCAL, 10.0, 50.0);

        assertThat(resultado.dentroDoRaio()).isTrue();
        assertThat(resultado.distanciaMetros()).isCloseTo(0.0, org.assertj.core.data.Offset.offset(0.01));
    }

    @Test
    @DisplayName("Batida a mais de 500m do local deve ficar fora do raio de 150m")
    void batidaLongeDoLocalDeveFicarForaDoRaio() {
        // ~0.005 graus de diferenca equivale a aproximadamente 550m de distancia
        double latBatida = LAT_LOCAL + 0.005;

        var resultado = validator.validar(LAT_LOCAL, LON_LOCAL, 150, latBatida, LON_LOCAL, 10.0, 50.0);

        assertThat(resultado.dentroDoRaio()).isFalse();
        assertThat(resultado.distanciaMetros()).isGreaterThan(150);
    }

    @ParameterizedTest(name = "precisao={0}m, limite={1}m -> baixaConfianca={2}")
    @CsvSource({
            "10.0, 50.0, false",
            "60.0, 50.0, true",
            "50.0, 50.0, false"
    })
    @DisplayName("Precisao de GPS acima do limite deve marcar a batida como baixa confianca, sem rejeitar")
    void precisaoRuimDeveMarcarComoBaixaConfianca(double precisao, double limite, boolean esperado) {
        var resultado = validator.validar(LAT_LOCAL, LON_LOCAL, 150, LAT_LOCAL, LON_LOCAL, precisao, limite);

        assertThat(resultado.baixaConfianca()).isEqualTo(esperado);
    }

    @Test
    @DisplayName("Ausencia de precisao informada nao deve gerar baixa confianca")
    void semPrecisaoInformadaNaoDeveMarcarBaixaConfianca() {
        var resultado = validator.validar(LAT_LOCAL, LON_LOCAL, 150, LAT_LOCAL, LON_LOCAL, null, 50.0);

        assertThat(resultado.baixaConfianca()).isFalse();
    }
}
