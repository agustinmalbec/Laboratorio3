package com.example.laboratorio3.ui.perfil;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laboratorio3.databinding.FragmentClaveBinding;

public class ClaveFragment extends Fragment {

    private ClaveViewModel vm;
    private FragmentClaveBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(ClaveViewModel.class);
        binding = FragmentClaveBinding.inflate(inflater, container, false);
        binding.btClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String claveActual = binding.etClaveActual.getText().toString();
                String claveNueva = binding.etClaveNueva.getText().toString();
                vm.cambiarClave(claveActual, claveNueva);
            }
        });

        vm.getMutableMensaje().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvClaveError.setText(s.toString());
            }
        });

        return binding.getRoot();
    }
}