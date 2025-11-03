package com.example.laboratorio3.ui.inmuebles;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laboratorio3.databinding.FragmentAgregarInmuebleBinding;

public class AgregarInmuebleFragment extends Fragment {

    private AgregarInmuebleViewModel vm;
    private FragmentAgregarInmuebleBinding binding;
    private ActivityResultLauncher<Intent> arl;
    private Intent intent;

    public static AgregarInmuebleFragment newInstance() {
        return new AgregarInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(AgregarInmuebleViewModel.class);
        binding = FragmentAgregarInmuebleBinding.inflate(getLayoutInflater());
        abrirGaleria();
        binding.btAgregarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arl.launch(intent);
            }
        });

        vm.getMutableUri().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.ivAgregarInmueble.setImageURI(uri);
            }
        });

        binding.btAgregarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.cargarInmueble(
                        binding.etDireccion.getText().toString(),
                        binding.etPrecio.getText().toString(),
                        binding.etUso.getText().toString(),
                        binding.etTipo.getText().toString(),
                        binding.etSuperficie.getText().toString(),
                        binding.etAmbientes.getText().toString());
            }
        });

        return binding.getRoot();
    }

    private void abrirGaleria(){
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                vm.recibirFoto(result);
            }
        });
    }
}