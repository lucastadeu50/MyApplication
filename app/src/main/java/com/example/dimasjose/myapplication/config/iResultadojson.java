package com.example.dimasjose.myapplication.config;

import com.example.dimasjose.myapplication.model.ResultadoJson;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface iResultadojson {
    @GET("/{resultado}")
    Call<ResultadoJson> getResultado(@Path("resultado") String resultado);
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.2.15/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
