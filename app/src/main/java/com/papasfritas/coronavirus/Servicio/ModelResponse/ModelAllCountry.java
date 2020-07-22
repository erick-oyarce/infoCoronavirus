package com.papasfritas.coronavirus.Servicio.ModelResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.lang.reflect.Array;

public class ModelAllCountry {

    private String country;
    private JsonArray provinces;
    private TimeLine timeline;

    public TimeLine getTimeline() {
        return timeline;
    }

    public void setTimeline(TimeLine timeline) {
        this.timeline = timeline;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public JsonArray getProvinces() {
        return provinces;
    }

    public void setProvinces(JsonArray provinces) {
        this.provinces = provinces;
    }


}
