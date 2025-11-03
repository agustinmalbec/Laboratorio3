package com.example.laboratorio3.ui.inmuebles;

import static com.example.laboratorio3.request.ApiClient.URLBASE;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.laboratorio3.R;
import com.example.laboratorio3.databinding.FragmentDetalleinmuebleBinding;

public class DetalleinmuebleFragment extends Fragment {

    private DetalleinmuebleViewModel vm;
    private FragmentDetalleinmuebleBinding binding;

    public static DetalleinmuebleFragment newInstance() {
        return new DetalleinmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(DetalleinmuebleViewModel.class);
        binding = FragmentDetalleinmuebleBinding.inflate(inflater, container, false);

        vm.getMutableInmueble().observe(getViewLifecycleOwner(), inmueble -> {
            binding.tvDireccion.setText(inmueble.getDireccion());
            binding.tvTipo.setText(inmueble.getTipo());
            binding.tvUso.setText(inmueble.getUso());
            binding.tvAmbientes.setText(inmueble.getAmbientes()+"");
            binding.tvSuperficie.setText(inmueble.getSuperficie()+" m2");
            binding.tvLatitud.setText(inmueble.getLatitud()+"");
            binding.tvLongitud.setText(inmueble.getLongitud()+"");
            binding.tvValor.setText("$"+inmueble.getValor());
            Glide.with(this)
                    .load(URLBASE + inmueble.getImagen())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error("null")
                    .into(binding.ivInmueble);
            binding.cbDisponible.setChecked(inmueble.isDisponible());
        });

        vm.obtenerInmueble(getArguments());

        binding.cbDisponible.setOnClickListener(v -> {
                vm.actualizarInmueble(binding.cbDisponible.isChecked());
        });

        return binding.getRoot();
    }
}