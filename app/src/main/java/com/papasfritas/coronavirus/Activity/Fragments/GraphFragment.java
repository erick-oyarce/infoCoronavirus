package com.papasfritas.coronavirus.Activity.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.papasfritas.coronavirus.R;
import com.papasfritas.coronavirus.Servicio.ApiService;
import com.papasfritas.coronavirus.Servicio.ModelResponse.ModelAllCountry;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GraphFragment extends Fragment  implements Callback<ModelAllCountry> {

    public GraphFragment() {
        // Required empty public constructor
    }
    private BarChart chart, chart2;
    Call<ModelAllCountry> call;
    ArrayList<BarEntry> values = new ArrayList<>();
    ArrayList<BarEntry> values2 = new ArrayList<>();


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_graph, container, false);
       init();
        return view;
    }

    public void init(){

        chart = view.findViewById(R.id.chart1);

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
        chart2 = view.findViewById(R.id.chart2);

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
