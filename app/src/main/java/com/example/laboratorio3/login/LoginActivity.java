package com.example.laboratorio3.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.laboratorio3.MainActivity;
import com.example.laboratorio3.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel vm;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vm = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = binding.etMailLogin.getText().toString();
                String clave = binding.etClaveLogin.getText().toString();
                vm.login(mail, clave);
            }
        });

        vm.getLoginExitoso().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean exitoso) {
                if (Boolean.TRUE.equals(exitoso)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        vm.getMutableMensaje().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String mensaje) {
                binding.tvMensajeLogin.setText(mensaje);
            }
        });
    }
}
