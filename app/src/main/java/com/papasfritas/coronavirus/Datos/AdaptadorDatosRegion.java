package com.papasfritas.coronavirus.Datos;

import android.graphics.Region;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.papasfritas.coronavirus.R;

import java.util.ArrayList;


public class AdaptadorDatosRegion extends RecyclerView.Adapter<AdaptadorDatosRegion.exViewHolder>{

    private ArrayList<DatosRegion> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class exViewHolder extends RecyclerView.ViewHolder {
        public TextView region;
        public TextView fecha;
        public TextView confirmados;

        public exViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            region = itemView.findViewById(R.id.region);
            fecha = itemView.findViewById(R.id.fecha);
            confirmados = itemView.findViewById(R.id.confirmados);

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

    public void filtrar(ArrayList<DatosRegion> listaFiltro){
        this.mExampleList = listaFiltro;
        notifyDataSetChanged();

    }

    public AdaptadorDatosRegion(ArrayList<DatosRegion> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public exViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_region, parent, false);
        exViewHolder evh = new exViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(exViewHolder holder, int position) {
        DatosRegion currentItem = mExampleList.get(position);

        holder.region.setText(currentItem.getRegion());
        holder.fecha.setText(currentItem.getFecha());
        holder.confirmados.setText(currentItem.getConfirmados());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
