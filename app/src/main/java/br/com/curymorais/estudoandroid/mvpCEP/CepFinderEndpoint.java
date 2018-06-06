package br.com.curymorais.estudoandroid.mvpCEP;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CepFinderEndpoint {

    @GET("ws/{valor}/json/")
    Call<Cep> searchCep(@Path(value = "valor", encoded = false) String valor);
}
