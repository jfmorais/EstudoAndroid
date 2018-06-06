package br.com.curymorais.estudoandroid.volleyCEP;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;

import br.com.curymorais.estudoandroid.R;
import br.com.curymorais.estudoandroid.volleyExampleClasses.SingletonRequestQueue;

public class VolleyCEP extends AppCompatActivity {

    TextView logradouro;
    EditText cep;
    String BASE_URL = "https://viacep.com.br/ws/";//18800000/json/
    int numberOfRequestsCompleted;
    Cep cepResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_cep);

        logradouro = findViewById(R.id.end_cep); //endereco completo
        cep = findViewById(R.id.val_cep); //cep digitado
    }

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if (error instanceof NetworkError) {
                Toast.makeText(getApplicationContext(), "No network available", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }
    };

    public void consultaCep (View v){
        String val = cep.getText().toString();
        numberOfRequestsCompleted = 0;
        VolleyLog.DEBUG = true;
        RequestQueue queue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
        String uri = String.format(BASE_URL + val + "/json/");

//        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                VolleyLog.wtf(response, "utf-8");
//                GsonBuilder builder = new GsonBuilder();
//                Gson mGson = builder.create();
//                Cep cepResultado = mGson.fromJson(response, Cep.class);
//                ++numberOfRequestsCompleted;
//
//            }
//        }, errorListener) {
//
//            @Override
//            public Priority getPriority() {
//                return Priority.LOW;
//            }
//
//        };
//
//        queue.add(stringRequest);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(uri, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                VolleyLog.wtf(response.toString(), "utf-8");
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();

                cepResultado = mGson.fromJson(response.toString(), Cep.class);
                ++numberOfRequestsCompleted;

            }
        }, errorListener) {

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Priority getPriority() {
                return Priority.IMMEDIATE;
            }
        };

        queue.add(jsonObjectRequest);

        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {

            @Override
            public void onRequestFinished(Request<Object> request) {
                try {
                    if (request.getCacheEntry() != null) {
                        String cacheValue = new String(request.getCacheEntry().data, "UTF-8");
                        VolleyLog.d(request.getCacheKey() + " " + cacheValue);

                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                if (numberOfRequestsCompleted == 1) {
                    numberOfRequestsCompleted = 0;
                    String resultado =
                            "CEP........: " + cepResultado.getCep() +"\n" +
                            "Logradouro.: " + cepResultado.getLogradouro() +"\n" +
                            "Complemento: " + cepResultado.getComplemento() +"\n" +
                            "Bairro.....: " + cepResultado.getBairro() +"\n" +
                            "Localidade.: " + cepResultado.getLocalidade() +"\n" +
                            "UF.........: " + cepResultado.getUf() +"\n" +
                            "IBGE.......: " + cepResultado.getIbge() +"\n" +
                            "GIA........: " + cepResultado.getGia() +"\n" ;

                    logradouro.setText(resultado);
                }
            }
        });
    }
}
