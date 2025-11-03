package com.example.laboratorio3.ui.inmuebles;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.laboratorio3.model.Inmueble;
import com.example.laboratorio3.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleinmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> mutableInmueble = new MutableLiveData<>();
    public DetalleinmuebleViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<Inmueble> getMutableInmueble(){
        return mutableInmueble;
    }

    public void obtenerInmueble(Bundle inmuebleBundle){
        Inmueble inmueble = inmuebleBundle.getSerializable("inmueble", Inmueble.class);
        if (inmueble != null){
            this.mutableInmueble.postValue(inmueble);
        }
    }

    public void actualizarInmueble(Boolean disponible){
        Inmueble inmuebleActual = mutableInmueble.getValue();
        inmuebleActual.setDisponible(disponible);
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
        Call<Inmueble> llamada = api.actualizarInmueble("Bearer " + token, inmuebleActual);
        llamada.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplication(), "Inmueble actualizado", Toast.LENGTH_SHORT).show();
                    mutableInmueble.postValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "Error al actualizar inmueble", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Toast.makeText(getApplication(), "Error del servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}