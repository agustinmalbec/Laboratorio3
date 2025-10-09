package com.example.laboratorio3.ui.perfil;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<String> mutableMensaje = new MutableLiveData<>();

    public PerfilViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMutableMensaje() { return mutableMensaje; }

    public void cargar(){
        SharedPreferences sp = getApplication().getSharedPreferences("token", 0);
        String token = sp.getString("token", " ");
        mutableMensaje.setValue(token);
    }
}