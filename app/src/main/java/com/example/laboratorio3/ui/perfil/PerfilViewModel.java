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

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<String> mutableBoton = new MutableLiveData<>();
    private MutableLiveData<Propietario> mutablePropietario = new MutableLiveData<>();
    private MutableLiveData<Boolean> mutableEstado = new MutableLiveData<>();

    public PerfilViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMutableBoton(){
        return mutableBoton;
    }
    public LiveData<Propietario> getMutablePropietario(){
        return mutablePropietario;
    }
    public LiveData<Boolean> getMutableEstado(){
        return mutableEstado;
    }

    public void editar(String boton, String dni, String nombre, String apellido, String email, String telefono){
        if (boton.equalsIgnoreCase("Editar")){
            mutableBoton.setValue("Guardar");
            mutableEstado.setValue(true);
        }else{
            Propietario editado = new Propietario();
            editado.setIdPropietario(mutablePropietario.getValue().getIdPropietario());
            editado.setDni(Integer.parseInt(dni));
            editado.setNombre(nombre);
            editado.setApellido(apellido);
            editado.setEmail(email);
            editado.setTelefono(Integer.parseInt(telefono));
            editado.setClave(null);
            String token = ApiClient.leerToken(getApplication());
            Call<Propietario> llamada = ApiClient.getInmobiliariaService().actualizarPropietario("Bearer " +token, editado);
            llamada.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getApplication(), "Editado con exito", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplication(), "No se pudo editar", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                    Toast.makeText(getApplication(), "Error del servidor", Toast.LENGTH_SHORT).show();
                }
            });
            mutableBoton.setValue("Editar");
            mutableEstado.setValue(false);
        }
    }
    public void obtenerPropietario(){
        String token = ApiClient.leerToken(getApplication());
        Call<Propietario> llamada = ApiClient.getInmobiliariaService().obtenerPropietario("Bearer " +token);
        llamada.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()){
                    mutablePropietario.postValue(response.body());

                } else {
                    Toast.makeText(getApplication(), "Error al obtener propietario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Toast.makeText(getApplication(), "Error del servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}