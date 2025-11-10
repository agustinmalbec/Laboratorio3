package com.example.laboratorio3.ui.perfil;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.laboratorio3.model.Propietario;
import com.example.laboratorio3.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClaveViewModel extends AndroidViewModel {

    private MutableLiveData<String> mutableMensaje = new MutableLiveData();
    private MutableLiveData<String> mutableBoton = new MutableLiveData<>();
    private MutableLiveData<Propietario> mutablePropietario = new MutableLiveData<>();

    public ClaveViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<String> getMutableBoton(){
        return mutableBoton;
    }
    public LiveData<String> getMutableMensaje(){
        return mutableMensaje;
    }
    public LiveData<Propietario> getMutablePropietario(){
        return mutablePropietario;
    }

    public void cambiarClave(String claveActual, String claveNueva){
            String token = ApiClient.leerToken(getApplication());
            Call<Propietario> llamada = ApiClient.getInmobiliariaService().cambiarContraseña("Bearer " + token, claveActual, claveNueva);
            llamada.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getApplication(), "Clave cambiada con exito", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplication(), "No se pudo cambiar la clave", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                    Toast.makeText(getApplication(), "Error del servidor", Toast.LENGTH_SHORT).show();
                }
            });
    }
}