package com.papasfritas.coronavirus.Datos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.papasfritas.coronavirus.R;

import java.util.ArrayList;


public class AdaptadorDatosComuna extends RecyclerView.Adapter<AdaptadorDatosComuna.exViewHolder>{

    private ArrayList<DatosComuna> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class exViewHolder extends RecyclerView.ViewHolder {
        public TextView comuna;
        public TextView fecha;
        public TextView confirmados;
        public TextView poblacion;
        public TextView porcentaje;

        public exViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            comuna = itemView.findViewById(R.id.comuna);
            fecha = itemView.findViewById(R.id.fecha);
            confirmados = itemView.findViewById(R.id.confirmados);
            poblacion = itemView.findViewById(R.id.poblacion);
            porcentaje = itemView.findViewById(R.id.porcentaje);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });


        }
    }

    public void filtrar(ArrayList<DatosComuna> listaFiltro){
        this.mExampleList = listaFiltro;
        notifyDataSetChanged();

    }

    public AdaptadorDatosComuna(ArrayList<DatosComuna> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public exViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_comuna, parent, false);
        exViewHolder evh = new exViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(exViewHolder holder, int position) {
        DatosComuna currentItem = mExampleList.get(position);

        if(currentItem.getComuna().length() > 12){
            holder.comuna.setText(currentItem.getComuna().substring(0,12)+"...");
        }else {
            holder.comuna.setText(currentItem.getComuna());
        }
        holder.fecha.setText(currentItem.getFecha());
        holder.confirmados.setText(currentItem.getConfirmados());
        holder.poblacion.setText(currentItem.getPoblacion());
        holder.porcentaje.setText(currentItem.getPorcentaje());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
