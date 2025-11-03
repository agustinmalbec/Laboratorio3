package com.example.laboratorio3.ui.inmuebles;

import static com.example.laboratorio3.request.ApiClient.URLBASE;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.laboratorio3.R;
import com.example.laboratorio3.model.Inmueble;

import java.util.List;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.InmuebleViewHolder> {
        private List<Inmueble> lista;
        private Context context;

        public InmuebleAdapter(List<Inmueble> lista, Context context){
            this.lista = lista;
            this.context = context;
        }


    @NonNull
    @Override
    public InmuebleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.inmueble_card, parent, false);
        return new InmuebleViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull InmuebleViewHolder holder, int position) {
        Inmueble inmueble = lista.get(position);
        holder.tvDireccion.setText(inmueble.getDireccion());
        holder.tvTipo.setText(inmueble.getTipo());
        Glide.with(context)
                .load(URLBASE + inmueble.getImagen())
                .placeholder(R.drawable.ic_launcher_background)
                .error("null")
                .into(holder.ivImagen);
        holder.cardView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("inmueble", inmueble);
            Navigation.findNavController((Activity)v.getContext(), R.id.nav_host_fragment_content_main).navigate(R.id.detalleinmuebleFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class InmuebleViewHolder extends RecyclerView.ViewHolder{
            private TextView tvDireccion, tvTipo;
            private ImageView ivImagen;
            private CardView cardView;

        public InmuebleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvCardDireccion);
            tvTipo = itemView.findViewById(R.id.tvCardTipo);
            ivImagen = itemView.findViewById(R.id.ivCardImagen);
            cardView = itemView.findViewById(R.id.idCard);
        }
    }
}
