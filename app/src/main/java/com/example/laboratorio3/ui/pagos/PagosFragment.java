package com.example.laboratorio3.ui.pagos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.laboratorio3.databinding.FragmentPagosBinding;
import com.example.laboratorio3.model.Pago;

import java.util.List;

public class PagosFragment extends Fragment {

    private PagosViewModel vm;
    private FragmentPagosBinding binding;
    private PagoAdapter adapter;
    private TextView tvErrorPagos;
    private RecyclerView rvPagos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(PagosViewModel.class);
        binding = FragmentPagosBinding.inflate(inflater, container, false);

        rvPagos = binding.rvPagos;
        tvErrorPagos = binding.tvErrorPagos;

        rvPagos.setLayoutManager(new LinearLayoutManager(getContext()));

        vm.getMutablePagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
                rvPagos.setVisibility(View.VISIBLE);
                tvErrorPagos.setVisibility(View.GONE);

                adapter = new PagoAdapter(pagos, getContext());
                rvPagos.setAdapter(adapter);
            }
        });

        vm.getMutableError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String error) {
                rvPagos.setVisibility(View.GONE);
                tvErrorPagos.setVisibility(View.VISIBLE);
                tvErrorPagos.setText(error);
            }
        });

        vm.cargarPagos(getArguments());

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}