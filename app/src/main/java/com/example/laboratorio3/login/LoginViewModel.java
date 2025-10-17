package com.example.laboratorio3.login;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.laboratorio3.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<String> mutableMensaje = new MutableLiveData<>();
    private MutableLiveData<Boolean> loginExitoso = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMutableMensaje() { return mutableMensaje; }
    public LiveData<Boolean> getLoginExitoso() { return loginExitoso; }
    public void login(String mail, String clave){
        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
        Call<String> llamada = api.login(mail, clave);
        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    String token = response.body();
                    Context context = getApplication().getApplicationContext();
                    ApiClient.guardarToken(context, token);
                    Toast.makeText(getApplication(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    loginExitoso.postValue(true);
                } else {
                    mutableMensaje.postValue("Usuario y/o contraseña incorrecta");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mutableMensaje.postValue("Error de conexión");
            }
        });
    }
}
