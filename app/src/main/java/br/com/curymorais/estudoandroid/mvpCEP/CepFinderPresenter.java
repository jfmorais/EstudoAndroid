package br.com.curymorais.estudoandroid.mvpCEP;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.io.IOException;

import br.com.curymorais.estudoandroid.R;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CepFinderPresenter  implements  ICepFinderContract.Presenter{
    private final ICepFinderContract.View mView;
    CepFinderPresenter.InitialTask mInitialTask;

    public CepFinderPresenter(ICepFinderContract.View view) {
        this.mView=view;
    }
    @Override
    public void start() {
        //todo preparation if required. Create instance of your member variables
    }

    @Override
    public void stop() {
        /*Call this method on onDestroy() of your Activity/Fragment.
        Stop any running Async task here*/
        if (mInitialTask != null && mInitialTask.getStatus() == AsyncTask.Status.RUNNING) {
            mInitialTask.cancel(true);
        }
    }

    @Override
    public void searchCep (String id){
        if (mInitialTask == null || mInitialTask.getStatus() != AsyncTask.Status.RUNNING) {
            mInitialTask = new CepFinderPresenter.InitialTask(id, this, mView);
            mInitialTask.execute();
        }
    }


    @Override
    public Call<Cep> searchCepAPI(String id) {
        Retrofit r = new Retrofit
                .Builder()
                .baseUrl("https://viacep.com.br/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CepFinderEndpoint cepFinder = r.create(CepFinderEndpoint.class);
        return cepFinder.searchCep(id);

    }
    //AsyncTask
    public static class InitialTask extends AsyncTask<Void, Integer, Cep>{

        private final String id;
        private final ICepFinderContract.Presenter presenter;
        private final ICepFinderContract.View view;

        public InitialTask(String id, ICepFinderContract.Presenter presenter, ICepFinderContract.View view){
            this.id=id;
            this.presenter = presenter;
            this.view=view;
        }


        @Override
        protected Cep doInBackground(Void... voids) {

            try {
                Response<Cep> response= presenter.searchCepAPI(id).execute();

                if(response.isSuccessful()){
                    Log.i("CURY_serv", response.body().getLocalidade());
                    return response.body();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
//            switch (values[0]) {
//                case STATE_LOADING:
//                    view.loadDownloadingScene();
//                    break;
//                case STATE_EMPTY:
//                    view.loadEmptyScreen();
//                    break;
//            }
        }

        @Override
        protected void onPostExecute(Cep logradouro) {
            super.onPostExecute(logradouro);
            if(logradouro!=null){
                view.loadLogradouro(logradouro);
            }
        }
    }
}
