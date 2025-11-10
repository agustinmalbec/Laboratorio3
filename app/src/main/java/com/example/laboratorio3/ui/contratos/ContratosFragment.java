package com.example.laboratorio3.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laboratorio3.databinding.FragmentContratosBinding;
import com.example.laboratorio3.model.Inmueble;

import java.util.List;

public class ContratosFragment extends Fragment {

    private ContratosViewModel vm;
    private FragmentContratosBinding binding;
    private ContratoAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(ContratosViewModel.class);
        binding = FragmentContratosBinding.inflate(inflater, container, false);

        RecyclerView rv = binding.rvContratos;
        GridLayoutManager glm = new GridLayoutManager(getContext(), 2);
        rv.setLayoutManager(glm);

        vm.getMutableInmuebles().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                adapter = new ContratoAdapter(inmuebles, getContext());
                rv.setAdapter(adapter);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        vm.cargarInmueblesConContrato();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}