package com.example.laboratorio3.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
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
    private SensorManager manager;
    private Sensor acelerometro;
    private Manejador manejador;
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

    public void activarLecturas(){
        if (manager == null){
            manager = (SensorManager) getApplication().getSystemService(Context.SENSOR_SERVICE);
            acelerometro = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            manejador = new Manejador();
        }
        if (acelerometro != null){
            manager.registerListener(manejador, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
        }
        manager.registerListener(manejador, acelerometro, SensorManager.SENSOR_DELAY_UI);
    }
    public void desactivarLecturas(){
        if (manager != null && manejador != null) {
            manager.unregisterListener(manejador);
        }
    }

    private void lanzarMarcador() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:2664123456"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(intent);
    }
    private class Manejador implements SensorEventListener {

        private float accel;
        private float accelCurrent;
        private float accelLast;
        private long lastShakeTime;
        private static final int SHAKE_THRESHOLD = 12;

        Manejador() {
            accel = 0.00f;
            accelCurrent = SensorManager.GRAVITY_EARTH;
            accelLast = SensorManager.GRAVITY_EARTH;
            lastShakeTime = 0;
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            accelLast = accelCurrent;
            accelCurrent = (float) Math.sqrt(x * x + y * y + z * z);
            float delta = accelCurrent - accelLast;
            accel = accel * 0.9f + delta;

            if (accel > SHAKE_THRESHOLD) {
                long now = System.currentTimeMillis();
                if (now - lastShakeTime > 2000) {
                    lastShakeTime = now;
                    lanzarMarcador();
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }
}
