package com.example.laboratorio3.ui.pagos;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.laboratorio3.model.Pago;
import com.example.laboratorio3.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {

    private MutableLiveData<List<Pago>> mutablePagos = new MutableLiveData<>();
    private MutableLiveData<String> mutableError = new MutableLiveData<>();

    public PagosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Pago>> getMutablePagos() {
        return mutablePagos;
    }

    public LiveData<String> getMutableError() {
        return mutableError;
    }

    public void cargarPagos(Bundle bundle) {
        int contratoId = bundle.getInt("contratoId");

        if (contratoId == 0) {
            mutableError.postValue("Error: No se recibió un ID de contrato válido.");
            return;
        }

        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();

        Call<List<Pago>> llamada = api.obtenerPagosPorContrato("Bearer " + token, contratoId);

        llamada.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isEmpty()) {
                        mutableError.postValue("Este contrato aún no tiene pagos registrados.");
                    } else {
                        mutablePagos.postValue(response.body());
                    }
                } else {
                    mutableError.postValue("Error al obtener los pagos: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                mutableError.postValue("Error de conexión: " + t.getMessage());
            }
        });
    }
}