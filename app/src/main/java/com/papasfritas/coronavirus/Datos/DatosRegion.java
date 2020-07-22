package com.papasfritas.coronavirus.Datos;

public class DatosRegion {

    private String region;
    private String fecha;
    private String confirmados;

    public DatosRegion(String region, String fecha, String confirmados) {
        this.region = region;
        this.fecha = fecha;
        this.confirmados = confirmados;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getConfirmados() {
        return confirmados;
    }

    public void setConfirmados(String confirmados) {
        this.confirmados = confirmados;
    }
}
