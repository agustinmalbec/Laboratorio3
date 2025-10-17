package com.example.laboratorio3.ui.perfil;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.laboratorio3.R;
import com.example.laboratorio3.databinding.FragmentPerfilBinding;
import com.example.laboratorio3.model.Propietario;

public class PerfilFragment extends Fragment {
    private PerfilViewModel vm;
    private FragmentPerfilBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(PerfilViewModel.class);
        binding = FragmentPerfilBinding.inflate(inflater, container, false);

        vm.getMutablePropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                binding.etPerfilDni.setText(propietario.getDni());
                binding.etPerfilNombre.setText(propietario.getNombre());
                binding.etPerfilApellido.setText(propietario.getApellido());
                binding.etPerfilEmail.setText(propietario.getEmail());
                binding.etPerfilTelefono.setText(propietario.getTelefono());
            }
        });

        vm.getMutableEstado().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.etPerfilDni.setEnabled(aBoolean);
                binding.etPerfilNombre.setEnabled(aBoolean);
                binding.etPerfilApellido.setEnabled(aBoolean);
                binding.etPerfilEmail.setEnabled(aBoolean);
                binding.etPerfilTelefono.setEnabled(aBoolean);
            }
        });

        vm.obtenerPropietario();

        binding.btPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dni = binding.etPerfilDni.getText().toString();
                String nombre = binding.etPerfilNombre.getText().toString();
                String apellido = binding.etPerfilApellido.getText().toString();
                String email = binding.etPerfilEmail.getText().toString();
                String telefono = binding.etPerfilTelefono.getText().toString();
                vm.editar(binding.btPerfil.getText().toString(), dni, nombre, apellido, email, telefono);
            }
        });

        vm.getMutableBoton().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btPerfil.setText(s.toString());
            }
        });

        binding.btPerfilClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("propietario", vm.getMutablePropietario().getValue());
                Navigation.findNavController(v).navigate(R.id.action_nav_perfil_to_nav_clave, bundle);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}