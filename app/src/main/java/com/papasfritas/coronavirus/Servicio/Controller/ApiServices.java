package com.papasfritas.coronavirus.Servicio.Controller;

import com.google.gson.JsonObject;
import com.papasfritas.coronavirus.Servicio.ModelResponse.DataResponse;
import com.papasfritas.coronavirus.Servicio.ModelResponse.ModelAllCountry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiServices {

    @GET("v2/countries/Chile")
    Call<DataResponse> getDataPais();

    @GET("v2/historical/Chile?lastdays=30")
    Call<ModelAllCountry> getAllData();
}
