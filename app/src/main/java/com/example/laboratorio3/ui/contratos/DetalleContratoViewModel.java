package com.example.laboratorio3.ui.contratos;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.laboratorio3.model.Contrato;
import com.example.laboratorio3.model.Inmueble;
import com.example.laboratorio3.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleContratoViewModel extends AndroidViewModel {

    private MutableLiveData<Contrato> mutableContrato = new MutableLiveData<>();
    private MutableLiveData<String> mutableError = new MutableLiveData<>();

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Contrato> getMutableContrato() {
        return mutableContrato;
    }

    public LiveData<String> getMutableError() {
        return mutableError;
    }

    public void cargarContrato(Bundle bundle) {
        Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble", Inmueble.class);
        if (inmueble == null) {
            mutableError.postValue("Error al cargar el inmueble.");
            return;
        }

        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();

        Call<Contrato> llamada = api.obtenerContratoPorInmueble("Bearer " + token, inmueble.getIdInmueble());
        llamada.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if (response.isSuccessful()) {
                    mutableContrato.postValue(response.body());
                } else {
                    if (response.code() == 404) {
                        mutableError.postValue("Este inmueble no tiene un contrato asociado.");
                    } else {
                        mutableError.postValue("Error al obtener el contrato: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                mutableError.postValue("Error de conexión: " + t.getMessage());
            }
        });
    }
}