package com.papasfritas.coronavirus.Datos;

public class DatosComuna {

    private String comuna;
    private String fecha;
    private String confirmados;
    private String poblacion;
    private String porcentaje;

    public DatosComuna(String comuna, String fecha, String confirmados, String poblacion, String porcentaje) {
        this.comuna = comuna;
        this.fecha = fecha;
        this.confirmados = confirmados;
        this.poblacion = poblacion;
        this.porcentaje = porcentaje;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
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

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(String porcentaje) {
        this.porcentaje = porcentaje;
    }
}
