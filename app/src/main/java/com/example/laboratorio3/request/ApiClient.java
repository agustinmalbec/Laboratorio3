package com.example.laboratorio3.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.laboratorio3.model.Inmueble;
import com.example.laboratorio3.model.Propietario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public class ApiClient {

    public static final String URLBASE = "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";
    public static void guardarToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static String leerToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        return sp.getString("token", null);
    }

    public static InmobiliariaService getInmobiliariaService(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-mm-dd't'hh:mm:ss").create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(InmobiliariaService.class);
    }

    public interface InmobiliariaService{
        @FormUrlEncoded
        @POST("api/propietarios/login")
        Call<String> login(@Field("Usuario") String u, @Field("Clave") String c);

        @GET("api/propietarios")
        Call<Propietario> obtenerPropietario(@Header("Authorization") String token);

        @PUT("api/propietarios/actualizar")
        Call<Propietario> actualizarPropietario(@Header("Authorization") String token, @Body Propietario propietario);

        @PUT("api/propietarios/changePassword")
        Call<Propietario> cambiarContraseña(@Header("Authorization") String token, @Field("currentPassword") String cp, @Field("newPassword") String np);

        @PUT("api/propietarios/email")
        Call<Propietario> olvideContraseña(@Header("Authorization") String token, @Field("email") String e);

        @GET("api/inmuebles")
        Call<List<Inmueble>> obtenerInmuebles(@Header("Authorization") String token);

        @PUT("api/inmuebles/actualizar")
        Call<Inmueble> actualizarInmueble(@Header("Authorization") String token, @Body Inmueble inmueble);

        @Multipart
        @POST("api/inmuebles/cargar")
        Call<Inmueble> cargarInmueble(@Header("Authorization") String token,
                                      @Part MultipartBody.Part imagen,
                                      @Part("inmueble")RequestBody inmueble);
    }
}
