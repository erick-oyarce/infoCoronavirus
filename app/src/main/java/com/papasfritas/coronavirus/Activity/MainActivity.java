package com.papasfritas.coronavirus.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.papasfritas.coronavirus.Datos.DatosRegion;
import com.papasfritas.coronavirus.Memory.Local;
import com.papasfritas.coronavirus.R;
import com.papasfritas.coronavirus.Servicio.Ajax;
import com.papasfritas.coronavirus.Servicio.ApiService;
import com.papasfritas.coronavirus.Servicio.ModelResponse.DataResponse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements Callback<DataResponse> {

    TextView confirmado, muertos, recuperados, fecha, nuevos, activos, nuevosMuertos, criticos, test;
    ImageButton actualizar;

    Call <DataResponse> call;

    private PieChart chart;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    startActivity(new Intent(MainActivity.this, MainDashBoardActivity.class));
                    return true;

                case R.id.navigation_notifications:
                    startActivity(new Intent(MainActivity.this, MainDatosRegionActivity.class));
                    return true;

            }
            return false;
        }
    };

    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                System.out.println(initializationStatus);
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_home);
        init();
        peticionHttp();

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
               System.out.println("onAdloaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                System.out.println(errorCode);
            }

            @Override
            public void onAdOpened() {
                System.out.println("onAdOpened");
            }

            @Override
            public void onAdClicked() {
                System.out.println("onAdClicked");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                System.out.println("onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                System.out.println("onAdClosed");
            }
        });
// TODO: Add adView to your view hierarchy.


    }
    public void init(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        fecha = findViewById(R.id.fecha);
        fecha.setText(dateFormat.format(date));
        confirmado = findViewById(R.id.confirmado);
        muertos = findViewById(R.id.muertos);
        recuperados = findViewById(R.id.recuperado);
        nuevos = findViewById(R.id.nuevos);
        activos = findViewById(R.id.Activos);
        nuevosMuertos = findViewById(R.id.nuevosMuertos);
        criticos = findViewById(R.id.criticos);
        test = findViewById(R.id.tests);

        actualizar = findViewById(R.id.actualizar);
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peticionHttp();
               // new InitTask().execute();
            }
        });

    }

    public void peticionHttp(){
        call = ApiService.getApiService("https://corona.lmao.ninja/").getDataPais();
        call.enqueue(this);
    }


    @Override
    public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
        if(response.isSuccessful()){
            DataResponse dataResponse = response.body();
            confirmado.setText(Integer.toString(dataResponse.getCases()));
            muertos.setText(Integer.toString(dataResponse.getDeaths()));
            recuperados.setText(Integer.toString(dataResponse.getRecovered()));
            nuevos.setText(Integer.toString(dataResponse.getTodayCases()));
            activos.setText(Integer.toString(dataResponse.getActive()));
            nuevosMuertos.setText(Integer.toString(dataResponse.getTodayDeaths()));
            criticos.setText(Integer.toString(dataResponse.getCritical()));
            test.setText(Integer.toString(dataResponse.getTests()));
            Toast.makeText(getApplicationContext(),"Datos Actualizados", Toast.LENGTH_SHORT).show();

          //  consultaInfo();

        }
    }

    @Override
    public void onFailure(Call<DataResponse> call, Throwable t) {
        Toast.makeText(getApplicationContext(), "Error al obtener la informacion",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            super.onBackPressed(); finishAffinity(); System.exit(0);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void consultaInfo(){
        final ProgressDialog progressDialog;

        JSONObject json = new JSONObject();
        //se envian los datos
        final Ajax ajax = new Ajax(getApplicationContext(),"https://chile-coronapi1.p.rapidapi.com");
        ajax.addHeader("x-rapidapi-key", "8d2f4baabfmsh784f1eccce1e3f2p19831ejsn5697b84dd14a");
        ajax.addHeader("useQueryString", "true");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");
        progressDialog.setCancelable(false);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ajax.AjaxCancel();

                progressDialog.hide();
            }
        });
        progressDialog.show();
        ajax.client(Request.Method.GET, "/v3/latest/nation", "application/json; charset=utf-8", json, new com.android.volley.Response.Listener() {
            @Override
            public void onResponse(Object response) {
                try {
                    JSONObject objectRes = new JSONObject(response.toString());

                    progressDialog.hide();
                    progressDialog.dismiss();


                } catch (JSONException e) {
                    progressDialog.hide();
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                progressDialog.dismiss();
                try {
                    if (error.networkResponse != null) {
                        //String statusCode = String.valueOf(error.networkResponse.statusCode);
                        //Toast.makeText(getApplicationContext(), statusCode, Toast.LENGTH_SHORT).show();
                        String body = new String(error.networkResponse.data, "UTF-8");
                        //Toast.makeText(getApplicationContext(), body, Toast.LENGTH_SHORT).show();
                        JSONObject object = new JSONObject(body);

                    } else {

                    }
                } catch (JSONException ex) {

                } catch (UnsupportedEncodingException e) {

                }
            }
        });
    }
}
