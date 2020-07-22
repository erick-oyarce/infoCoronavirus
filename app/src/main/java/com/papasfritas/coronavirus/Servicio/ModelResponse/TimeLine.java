package com.papasfritas.coronavirus.Servicio.ModelResponse;

import com.google.gson.JsonObject;

import java.lang.reflect.Array;

public class TimeLine {
    private JsonObject deaths;
    private JsonObject recovered;
    private JsonObject cases;

    public JsonObject getCases() {
        return cases;
    }

    public void setCases(JsonObject cases) {
        this.cases = cases;
    }

    public JsonObject getDeaths() {
        return deaths;
    }

    public void setDeaths(JsonObject deaths) {
        this.deaths = deaths;
    }

    public JsonObject getRecovered() {
        return recovered;
    }

    public void setRecovered(JsonObject recovered) {
        this.recovered = recovered;
    }


}
