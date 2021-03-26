package com.papasfritas.coronavirus.Activity.Fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.papasfritas.coronavirus.R;
import com.papasfritas.coronavirus.Servicio.ApiService;
import com.papasfritas.coronavirus.Servicio.ModelResponse.DataResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment implements Callback<DataResponse> {

    public InfoFragment() {
        // Required empty public constructor
    }

   
    TextView confirmado, muertos, recuperados, fecha, nuevos, activos, nuevosMuertos, criticos, test;
    //ImageButton actualizar;

    Call<DataResponse> call;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_info, container, false);

        init();
        peticionHttp();

        return view;
    }
    public void init(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        fecha = view.findViewById(R.id.fecha);
        fecha.setText(dateFormat.format(date));
        confirmado = view.findViewById(R.id.confirmado);
        muertos = view.findViewById(R.id.muertos);
        recuperados = view.findViewById(R.id.recuperado);
        nuevos = view.findViewById(R.id.nuevos);
        activos = view.findViewById(R.id.Activos);
        nuevosMuertos = view.findViewById(R.id.nuevosMuertos);
        criticos = view.findViewById(R.id.criticos);
        test = view.findViewById(R.id.tests);

//        actualizar = view.findViewById(R.id.actualizar);
//        actualizar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                peticionHttp();
//                // new InitTask().execute();
//            }
//        });

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
           // Toast.makeText(getContext(),"Datos Actualizados", Toast.LENGTH_SHORT).show();

            //  consultaInfo();

        }else{
            Log.d("ERROR", String.valueOf(response.code()));
        }
    }

    @Override
    public void onFailure(Call<DataResponse> call, Throwable t) {
        Toast.makeText(getContext(), "Error al obtener la informacion",Toast.LENGTH_LONG).show();
    }




}
