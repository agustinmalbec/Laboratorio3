package com.example.laboratorio3.ui.ubicacion;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laboratorio3.R;
import com.example.laboratorio3.databinding.FragmentUbicacionBinding;
import com.google.android.gms.maps.SupportMapFragment;

public class UbicacionFragment extends Fragment {

    private UbicacionViewModel vm;
    private FragmentUbicacionBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        vm = new ViewModelProvider(this).get(UbicacionViewModel.class);
        binding = FragmentUbicacionBinding.inflate(inflater, container, false);

        vm.getMutableMapa().observe(getViewLifecycleOwner(), mapaActual-> {
                SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapa);
                supportMapFragment.getMapAsync(mapaActual);

        });

        vm.cargarMapa();

        return binding.getRoot();
    }
}