package com.example.laboratorio3.ui.pagos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laboratorio3.R;
import com.example.laboratorio3.model.Pago;

import java.util.List;

public class PagoAdapter extends RecyclerView.Adapter<PagoAdapter.PagoViewHolder> {

    private List<Pago> listaPagos;
    private Context context;
    public PagoAdapter(List<Pago> lista, Context context) {
        this.listaPagos = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public PagoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.pago_card, parent, false);
        return new PagoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull PagoViewHolder holder, int position) {
        Pago pago = listaPagos.get(position);
        String monto = "$ " + pago.getMonto();
        String mes = "Mes: " + pago.getMes();

        holder.tvMontoPago.setText(monto);
        holder.tvMesPago.setText(mes);
        holder.tvDetallePago.setText(pago.getDetalle());
    }

    @Override
    public int getItemCount() {
        return listaPagos.size();
    }

    public class PagoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMontoPago, tvMesPago, tvDetallePago;

        public PagoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMontoPago = itemView.findViewById(R.id.tvMontoPago);
            tvMesPago = itemView.findViewById(R.id.tvMesPago);
            tvDetallePago = itemView.findViewById(R.id.tvDetallePago);
        }
    }
}
