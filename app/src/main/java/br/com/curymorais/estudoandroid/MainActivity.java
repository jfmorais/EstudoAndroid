package br.com.curymorais.estudoandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.curymorais.estudoandroid.asynctaskExample.AsyncTaskExample;
import br.com.curymorais.estudoandroid.mvpCEP.CepFinderActivity;
import br.com.curymorais.estudoandroid.volleyCEP.VolleyCEP;
import br.com.curymorais.estudoandroid.volleyExampleClasses.VolleyExample;

public class MainActivity extends AppCompatActivity {
    private Button buttonAsyncTask;
    private Button buttonVolley;
//    private Button buttonAsyncTask;
    private EditText time;
    private TextView finalResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAsyncTask = (Button) findViewById(R.id.bt_async_task);
        buttonVolley = (Button) findViewById(R.id.bt_volley);


    }

    public void startAsyncTask(View v){
        Intent i = new Intent(this, AsyncTaskExample.class);
        startActivity(i);
    }

    public void startVolley(View v){
        Intent i = new Intent(this, VolleyExample.class);
        startActivity(i);
    }

    public void startVolleyCEP(View v){
        Intent i = new Intent(this, VolleyCEP.class);
        startActivity(i);
    }

    public void startRetrofitCEP(View v){
        Intent i = new Intent(this, CepFinderActivity.class);
        startActivity(i);
    }

}