package com.example.prova.API;

import com.example.prova.Model.UF;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IBGEService {

    @GET("{sigla}")
    Call<UF> recuperarPorSigla(@Path("sigla") String sigla);

}
