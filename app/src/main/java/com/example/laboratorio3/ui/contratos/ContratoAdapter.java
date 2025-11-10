package com.example.laboratorio3.ui.contratos;

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
import com.example.laboratorio3.request.ApiClient;

import java.util.List;

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ContratoViewHolder> {

    private List<Inmueble> listaInmueblesConContrato;
    private Context context;

    public ContratoAdapter(List<Inmueble> lista, Context context) {
        this.listaInmueblesConContrato = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public ContratoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.inmueble_card, parent, false);
        return new ContratoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ContratoViewHolder holder, int position) {
        Inmueble inmueble = listaInmueblesConContrato.get(position);

        holder.tvDireccion.setText(inmueble.getDireccion());
        holder.tvTipo.setText(inmueble.getTipo());

        String urlImagen = ApiClient.URLBASE + inmueble.getImagen();
        Glide.with(context)
                .load(urlImagen)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.ivImagen);

        holder.cardView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("inmueble", inmueble);

            Navigation.findNavController((Activity) v.getContext(), R.id.nav_host_fragment_content_main)
                    .navigate(R.id.action_nav_contratos_to_detalleContratoFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return listaInmueblesConContrato.size();
    }

    public class ContratoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDireccion, tvTipo;
        private ImageView ivImagen;
        private CardView cardView;

        public ContratoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvCardDireccion);
            tvTipo = itemView.findViewById(R.id.tvCardTipo);
            ivImagen = itemView.findViewById(R.id.ivCardImagen);
            cardView = itemView.findViewById(R.id.idCard);
        }
    }
}