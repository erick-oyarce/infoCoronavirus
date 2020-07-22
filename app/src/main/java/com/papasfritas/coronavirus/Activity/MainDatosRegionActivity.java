package com.papasfritas.coronavirus.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.papasfritas.coronavirus.Datos.AdaptadorDatosComuna;
import com.papasfritas.coronavirus.Datos.AdaptadorDatosRegion;
import com.papasfritas.coronavirus.Datos.DatosComuna;
import com.papasfritas.coronavirus.Datos.DatosRegion;
import com.papasfritas.coronavirus.R;
import com.papasfritas.coronavirus.Servicio.Ajax;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;

public class MainDatosRegionActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(MainDatosRegionActivity.this, MainActivity.class));
                    return true;
                case R.id.navigation_dashboard:
                    startActivity(new Intent(MainDatosRegionActivity.this, MainDashBoardActivity.class));
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    private ArrayList<DatosRegion> listaRegiones = new ArrayList<>();
    ArrayList<DatosRegion> listaFiltro;

    private ArrayList<DatosComuna> listaComunas = new ArrayList<>();
    ArrayList<DatosComuna> listaFiltroComunas;
    private EditText buscar;

    private RecyclerView mRecyclerView;
    private AdaptadorDatosRegion mAdapter;
    private AdaptadorDatosComuna mAdapterComuna;
    private RecyclerView.LayoutManager mLayoutManager;

    ProgressDialog progressDialog;

    Spinner spinnerSelect;
    ArrayList<String> select = new ArrayList<>();
    String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_datos_region);
        getSupportActionBar().setTitle("Total de casos");

        spinnerSelect = findViewById(R.id.spinnerSelect);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_notifications);
        initSpinner();
        spinnerSelect.setSelection(0);
        buscar = findViewById(R.id.buscar);
        buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               // filtrarItems(String.valueOf(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filtrarItems(String.valueOf(editable));
            }
        });

    }
    public void initSpinner(){
        select = new ArrayList<>();
        select.add("Región");
        select.add("Comunas");

        ArrayAdapter<String> adaptadorCliente = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, select);
        adaptadorCliente.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSelect.setAdapter(adaptadorCliente);
        spinnerSelect.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {

            @Override public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                item = String.valueOf(parent.getItemAtPosition(position));
                if(item.equals("Región")){
                    if (listaRegiones.size() == 0) {
                        consultaInfo();
                    }else{
                        buildRecyclerView();
                      //  mAdapter.filtrar(listaRegiones);

                    }
                }
                if(item.equals("Comunas")){
                    if(listaComunas.size() == 0) {
                        consultaInfoComunas();
                    }else{
                        buildRecyclerViewComuna();
                       // mAdapterComuna.filtrar(listaComunas);
                    }
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
                //   Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void filtrarItems(String buscar){
        listaFiltro = new ArrayList<>();
        listaFiltroComunas = new ArrayList<>();
        if(item.equals("Región")){
            if(!buscar.equals("")) {
                for (DatosRegion datos : listaRegiones) {
                    if (datos.getRegion().toLowerCase().contains(buscar.toLowerCase())) {
                        listaFiltro.add(datos);
                    }
                }
            }else{
                listaFiltro = listaRegiones;
            }
            mAdapter.filtrar(listaFiltro);
        }
        if(item.equals("Comunas")){
            if(!buscar.equals("")) {
                for (DatosComuna datos : listaComunas) {
                    if (datos.getComuna().toLowerCase().contains(buscar.toLowerCase())) {
                        listaFiltroComunas.add(datos);
                    }
                }
            }else{
                listaFiltroComunas = listaComunas;
            }
            mAdapterComuna.filtrar(listaFiltroComunas);
        }
    }

    public void consultaInfo(){

        JSONObject json = new JSONObject();
        //se envian los datos
        final Ajax ajax = new Ajax(getApplicationContext(),"https://chile-coronapi1.p.rapidapi.com");
        ajax.addHeader("x-rapidapi-key", "8d2f4baabfmsh784f1eccce1e3f2p19831ejsn5697b84dd14a");
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
        ajax.client(Request.Method.GET, "/v3/latest/regions", "application/json; charset=utf-8", json, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                try {
                    JSONObject objectRes = new JSONObject(response.toString());
                    listaRegiones = new ArrayList<>();
                    for(int i = 0; i < objectRes.names().length(); i++){
                        int region = objectRes.names().getInt(i);
                        String valueDat = objectRes.getString(String.valueOf(region));
                        JSONObject objDept = new JSONObject(valueDat);
                        String regionName = objDept.getString("region");
                        String data = objDept.getString("regionData").replace("{","").replace("}","").replace("\"","").replace(",",":").replace("\\","");
                        String[] partsData = data.split(":");
                        listaRegiones.add(new DatosRegion(regionName,partsData[0],partsData[2]));


                    }
                    buildRecyclerView();
                    progressDialog.hide();
                    progressDialog.dismiss();


                } catch (JSONException e) {
                    progressDialog.hide();
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
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


    public void consultaInfoComunas(){

        JSONObject json = new JSONObject();
        //se envian los datos
        final Ajax ajax = new Ajax(getApplicationContext(),"https://chile-coronapi1.p.rapidapi.com");
        ajax.addHeader("x-rapidapi-key", "8d2f4baabfmsh784f1eccce1e3f2p19831ejsn5697b84dd14a");
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
        ajax.client(Request.Method.GET, "/v3/latest/communes", "application/json; charset=utf-8", json, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                try {
                    JSONObject objectRes = new JSONObject(response.toString());
                    listaComunas = new ArrayList<>();
                    for(int i = 0; i < objectRes.names().length(); i++){
                        int region = objectRes.names().getInt(i);
                        String valueDat = objectRes.getString(String.valueOf(region));
                        JSONObject objDept = new JSONObject(valueDat);
                        String comuna = objDept.getString("commune");
                        String[] confirmados = objDept.getString("confirmed").replace("\\","").replace("\"","").replace("{","").replace("}","").split(":");
                        String comunaInfo = objDept.getString("communeInfo");
                        JSONObject infoComuna = new JSONObject(comunaInfo);
                        int poblacion = infoComuna.getInt("population");
                        System.out.println(poblacion);

                        float porcentajeFloat = (float)(Integer.valueOf(confirmados[1]) * 100) / poblacion;
                        BigDecimal bd = new BigDecimal(Float.toString(porcentajeFloat));
                        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                        String porcentaje = String.valueOf(bd);
                        listaComunas.add(new DatosComuna(comuna,confirmados[0], confirmados[1], poblacion+" hab",  porcentaje+"%"));
                    }
                    buildRecyclerViewComuna();
                    progressDialog.hide();
                    progressDialog.dismiss();


                } catch (JSONException e) {
                    progressDialog.hide();
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
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



    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new AdaptadorDatosRegion(listaRegiones);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AdaptadorDatosRegion.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

        });
    }

    public void buildRecyclerViewComuna() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapterComuna = new AdaptadorDatosComuna(listaComunas);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapterComuna);

        mAdapterComuna.setOnItemClickListener(new AdaptadorDatosComuna.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

        });
    }
}
