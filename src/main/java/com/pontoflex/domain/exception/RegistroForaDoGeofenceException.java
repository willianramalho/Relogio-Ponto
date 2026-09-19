package com.pontoflex.domain.exception;

public class RegistroForaDoGeofenceException extends RegraDeNegocioException {

    private final double distanciaMetros;
    private final double raioPermitidoMetros;

    public RegistroForaDoGeofenceException(double distanciaMetros, double raioPermitidoMetros) {
        super("Batida fora do raio permitido do local de trabalho");
        this.distanciaMetros = distanciaMetros;
        this.raioPermitidoMetros = raioPermitidoMetros;
    }

    public double getDistanciaMetros() {
        return distanciaMetros;
    }

    public double getRaioPermitidoMetros() {
        return raioPermitidoMetros;
    }

    @Override
    public String getCodigo() {
        return "REGISTRO_FORA_DO_GEOFENCE";
    }
}
