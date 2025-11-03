package com.example.laboratorio3.ui.inmuebles;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.laboratorio3.model.Inmueble;
import com.example.laboratorio3.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmueblesViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Inmueble>> mutableInmueble = new MutableLiveData<>();

    public InmueblesViewModel(@NonNull Application application) {
        super(application);
        leerInmueble();
    }


    public LiveData<List<Inmueble>> getMutableInmueble(){
        return mutableInmueble;
    }

    public void leerInmueble(){
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
        Call<List<Inmueble>> llamada = api.obtenerInmuebles("Bearer " +token);
        llamada.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()){
                    mutableInmueble.postValue((List<Inmueble>) response.body());
                } else {
                    Toast.makeText(getApplication(), "Error al obtener inmuebles", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Toast.makeText(getApplication(), "Error del servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}