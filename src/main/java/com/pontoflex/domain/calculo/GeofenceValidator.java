package com.pontoflex.domain.calculo;

/**
 * Valida se uma coordenada de batida de ponto esta dentro do raio permitido
 * de um local de trabalho, usando a formula de Haversine.
 *
 * Classe de dominio pura: sem dependencia de Spring, testavel isoladamente.
 */
public class GeofenceValidator {

    private static final double RAIO_TERRA_METROS = 6_371_000;

    /**
     * @return distancia em metros entre a coordenada da batida e o centro do geofence
     */
    public double calcularDistanciaMetros(double latCentro, double lonCentro,
                                           double latBatida, double lonBatida) {
        double dLat = Math.toRadians(latBatida - latCentro);
        double dLon = Math.toRadians(lonBatida - lonCentro);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(latCentro)) * Math.cos(Math.toRadians(latBatida))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RAIO_TERRA_METROS * c;
    }

    /**
     * @return resultado da validacao: dentro do raio, fora do raio, ou baixa confianca de GPS
     */
    public ResultadoValidacaoGeofence validar(double latCentro, double lonCentro, int raioPermitidoMetros,
                                               double latBatida, double lonBatida, Double precisaoMetros,
                                               double limiteBaixaConfiancaMetros) {
        double distancia = calcularDistanciaMetros(latCentro, lonCentro, latBatida, lonBatida);
        boolean dentroDoRaio = distancia <= raioPermitidoMetros;
        boolean baixaConfianca = precisaoMetros != null && precisaoMetros > limiteBaixaConfiancaMetros;

        return new ResultadoValidacaoGeofence(dentroDoRaio, baixaConfianca, distancia);
    }

    public record ResultadoValidacaoGeofence(boolean dentroDoRaio, boolean baixaConfianca, double distanciaMetros) {
    }
}
