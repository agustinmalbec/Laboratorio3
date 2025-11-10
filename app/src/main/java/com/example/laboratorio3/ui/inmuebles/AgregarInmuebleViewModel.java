package com.example.laboratorio3.ui.inmuebles;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.laboratorio3.model.Inmueble;
import com.example.laboratorio3.request.ApiClient;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Uri> mutableUri = new MutableLiveData<>();
    private MutableLiveData<String> mutableMensaje = new MutableLiveData<>();
    public AgregarInmuebleViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<Uri> getMutableUri(){
        return mutableUri;
    }
    public LiveData<String> getMutableMensaje() {
        return mutableMensaje;
    }

    public void cargarInmueble(String direccion, String precio,String uso, String tipo, String superficie, String ambientes){
        if (direccion.isEmpty() || precio.isEmpty() || uso.isEmpty() || tipo.isEmpty() || superficie.isEmpty() || ambientes.isEmpty()){
            mutableMensaje.postValue("Todos los campos son obligatorios");
            return;
        }

        Inmueble inmueble = new Inmueble();

        // --- 4. SOLUCIÓN: Bloque try-catch para evitar NumberFormatException ---
        try {
            inmueble.setValor(Double.parseDouble(precio));
            inmueble.setSuperficie(Integer.parseInt(superficie));
            inmueble.setAmbientes(Integer.parseInt(ambientes));
        } catch (NumberFormatException e) {
            mutableMensaje.postValue("Error: 'Precio', 'Superficie' y 'Ambientes' deben ser números.");
            return; // Detenemos la ejecución si los números son inválidos
        }
        // --- FIN DE LA SOLUCIÓN ---

        inmueble.setDireccion(direccion);
        inmueble.setUso(uso);
        inmueble.setTipo(tipo);
        inmueble.setDisponible(false);

        byte[] imagen = transformarImagen();

        // 5. Comprobamos que la imagen no sea nula (transformarImagen la controla)
        if (imagen == null) {
            mutableMensaje.postValue("Debe seleccionar una imagen válida.");
            return;
        }

        String token = ApiClient.leerToken(getApplication());
        String inmuebleJson = new Gson().toJson(inmueble);

        RequestBody inmuebleBody = RequestBody.create(inmuebleJson, MediaType.parse("application/json; charset=utf-8"));
        RequestBody requestFile = RequestBody.create(imagen, MediaType.parse("image/jpeg"));
        MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", requestFile);

        Call<Inmueble> llamada = ApiClient.getInmobiliariaService().cargarInmueble("Bearer " +token, imagenPart, inmuebleBody);
        llamada.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplication(), "Inmueble agregado con exito", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplication(), "No se pudo agregar el inmueble", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Toast.makeText(getApplication(), "Error del servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void recibirFoto(ActivityResult result){
        if (result.getResultCode() == RESULT_OK){
            Intent data = result.getData();
            Uri uri = data.getData();
            mutableUri.setValue(uri);
        }
    }
    private byte[] transformarImagen(){
        try {
            Uri uri = mutableUri.getValue();
            if (uri == null) {
                return null;
            }
            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }catch (FileNotFoundException er){
            Toast.makeText(getApplication(), "No se selecciono una foto", Toast.LENGTH_SHORT).show();
            return new byte[]{};
        }
    }

}