package com.example.laboratorio3.ui.ubicacion;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UbicacionViewModel extends AndroidViewModel {
    private MutableLiveData<Mapa> mutableMapa = new MutableLiveData<>();
    public UbicacionViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Mapa> getMutableMapa() { return mutableMapa; };

    public void cargarMapa(){
        Mapa mapaActual = new Mapa();
        mutableMapa.setValue(mapaActual);
    }

    public class Mapa implements OnMapReadyCallback {
        private LatLng ulp = new LatLng(-33.150720, -66.306864);
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            MarkerOptions marcadorUlp = new MarkerOptions();
            marcadorUlp.position(ulp);
            marcadorUlp.title("Ubicacion");

            googleMap.addMarker(marcadorUlp);

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(ulp)
                    .build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            googleMap.animateCamera(cameraUpdate);
        }
    }
}