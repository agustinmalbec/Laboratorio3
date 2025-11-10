package com.example.laboratorio3.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laboratorio3.R;
import com.example.laboratorio3.databinding.FragmentDetalleContratoBinding;
import com.example.laboratorio3.model.Contrato;
import com.example.laboratorio3.model.Inmueble;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DetalleContratoFragment extends Fragment {
    private DetalleContratoViewModel vm;
    private FragmentDetalleContratoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(DetalleContratoViewModel.class);
        binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);

        vm.getMutableContrato().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato contrato) {
                binding.contentGroup.setVisibility(View.VISIBLE);
                binding.tvErrorDetalle.setVisibility(View.GONE);

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String fechaInicio = formatter.format(contrato.getFechaInicio());
                String monto = "$ " + contrato.getMontoAlquiler();
                String vigente = contrato.isVigente() ? "Vigente" : "No Vigente";

                binding.tvFechaInicio.setText(fechaInicio);
                binding.tvMonto.setText(monto);
                binding.tvVigente.setText(vigente);

                binding.btVerPagos.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt("contratoId", contrato.getIdContrato());
                    Navigation.findNavController(v).navigate(R.id.action_detalleContrato_to_pagosFragment, bundle);
                });
            }
        });

        vm.getMutableError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String error) {
                binding.contentGroup.setVisibility(View.GONE);
                binding.tvErrorDetalle.setVisibility(View.VISIBLE);
                binding.tvErrorDetalle.setText(error);
            }
        });

        Bundle bundle = getArguments();
        Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble", Inmueble.class);
        binding.tvInmuebleDireccion.setText(inmueble.getDireccion());
        vm.cargarContrato(bundle);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}