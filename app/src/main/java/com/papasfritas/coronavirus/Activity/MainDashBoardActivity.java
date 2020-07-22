package com.papasfritas.coronavirus.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.papasfritas.coronavirus.R;
import com.papasfritas.coronavirus.Servicio.ApiService;
import com.papasfritas.coronavirus.Servicio.ModelResponse.DataResponse;
import com.papasfritas.coronavirus.Servicio.ModelResponse.ModelAllCountry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainDashBoardActivity extends AppCompatActivity implements Callback<ModelAllCountry>{

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(MainDashBoardActivity.this, MainActivity.class));
                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:
                    startActivity(new Intent(MainDashBoardActivity.this, MainDatosRegionActivity.class));
                    return true;
            }
            return false;
        }
    };

    private BarChart chart, chart2;
    Call <ModelAllCountry> call;
    ArrayList<BarEntry> values = new ArrayList<>();
    ArrayList<BarEntry> values2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dash_board);
        getSupportActionBar().setTitle("Datos Acumulados en el país");
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_dashboard);

        chart = findViewById(R.id.chart1);

        chart.getDescription().setEnabled(false);

        chart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);
        chart.getAxisLeft().setDrawGridLines(false);


        // add a nice and smooth animation
        chart.animateY(1500);

        chart.getLegend().setEnabled(false);


        /*****************************/
        chart2 = findViewById(R.id.chart2);

        chart2.getDescription().setEnabled(false);

        chart2.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chart2.setPinchZoom(false);

        chart2.setDrawBarShadow(false);
        chart2.setDrawGridBackground(false);
        chart2.getAxisLeft().setDrawGridLines(false);


        // add a nice and smooth animation
        chart2.animateY(1500);

        chart2.getLegend().setEnabled(false);


        peticionHttp();

    }
    public void peticionHttp(){
        call = ApiService.getApiService("https://corona.lmao.ninja/").getAllData();
        call.enqueue(this);
    }
    @Override
    public void onResponse(Call<ModelAllCountry> call, Response<ModelAllCountry> response) {
        if (response.isSuccessful()) {
            System.out.println(response.body());
            ModelAllCountry allData = response.body();
            String[] casos = allData.getTimeline().getCases().toString().split(",");
            String[] muertos = allData.getTimeline().getDeaths().toString().split(",");
            for (int i = 0; casos.length > i; i++) {
                    String[] aux = casos[i].replace("{", "").replace("}", "").replace("\"", "").split(":");
                    int dato = Integer.parseInt(aux[1]);
                    values.add(new BarEntry(i, dato));



            }
            for (int i = 0; muertos.length > i; i++) {
                    String[] aux = muertos[i].replace("{", "").replace("}", "").replace("\"", "").split(":");
                    int dato = Integer.parseInt(aux[1]);
                    values2.add(new BarEntry(i, dato));

            }
            BarDataSet set1;
            BarDataSet set2;

            if (chart.getData() != null &&
                    chart.getData().getDataSetCount() > 0) {
                set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
                set1.setValues(values);
                chart.getData().notifyDataChanged();
                chart.notifyDataSetChanged();
            } else {
                set1 = new BarDataSet(values, "Data Set");
                set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
                set1.setDrawValues(false);

                ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1);

                BarData data = new BarData(dataSets);
                chart.setData(data);
                chart.setFitBars(true);
            }

            chart.invalidate();


            if (chart2.getData() != null &&
                    chart2.getData().getDataSetCount() > 0) {
                set2 = (BarDataSet) chart2.getData().getDataSetByIndex(0);
                set2.setValues(values2);
                chart2.getData().notifyDataChanged();
                chart2.notifyDataSetChanged();
            } else {
                set2 = new BarDataSet(values2, "Data Set");
                set2.setColors(ColorTemplate.VORDIPLOM_COLORS);
                set2.setDrawValues(false);

                ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                dataSets.add(set2);

                BarData data = new BarData(dataSets);
                chart2.setData(data);
                chart2.setFitBars(true);
            }

            chart2.invalidate();
        }
    }

    @Override
    public void onFailure(Call<ModelAllCountry> call, Throwable t) {

    }

}
