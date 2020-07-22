package com.papasfritas.coronavirus.Servicio;

import android.content.Context;

import androidx.annotation.Nullable;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Ajax {

    private RequestQueue request;
    private StringRequest jsonRequest;
    private String urlBase;
    private Map<String, String> headers;

    public Ajax(Context context, String url) {
        this.request = Volley.newRequestQueue(context);
        this.urlBase = url;
        headers = new HashMap<String, String>();
        addHeader("content-type", "application/json");
    }

    public void client(int method, String url, final String BodyContentType, final JSONObject jsonBody, Response.Listener listener, @Nullable Response.ErrorListener errorListener) {

        jsonRequest = new StringRequest(method, this.urlBase + url, listener, errorListener) {

            @Override
            public byte[] getBody() {
                return jsonBody.toString().getBytes();
            }

            @Override
            public String getBodyContentType()
            {
                return BodyContentType;
            }

            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }
        };
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 2000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.add(jsonRequest);

        request.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> requestObject) {
                request.getCache().clear();
            }
        });
    }

    public void clientArray(int method, String url, final String BodyContentType, final JSONArray jsonBody, Response.Listener listener, @Nullable Response.ErrorListener errorListener) {

        jsonRequest = new StringRequest(method, this.urlBase + url, listener, errorListener) {

            @Override
            public byte[] getBody() {
                return jsonBody.toString().getBytes();
            }

            @Override
            public String getBodyContentType()
            {
                return BodyContentType;
            }

            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }
        };
        request.add(jsonRequest);

        request.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> requestObject) {
                request.getCache().clear();
            }
        });
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public void AjaxCancel() {
        request.cancelAll(jsonRequest);
    }

}
