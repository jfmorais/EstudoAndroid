package br.com.curymorais.estudoandroid.mvpCEP;

import retrofit2.Call;

public interface ICepFinderContract {

    interface Presenter {
        void start();
        void stop();
        Call<Cep> searchCepAPI(String id);
        void searchCep(String id);
    }

    interface View{
        void loadLogradouro(Cep logradouro);
    }
}
