package br.com.curymorais.estudoandroid.mvpCEP;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.curymorais.estudoandroid.R;

public class CepFinderActivity extends AppCompatActivity implements ICepFinderContract.View {

    private CepFinderPresenter mPresenter;
    private TextView logradouro;
    private EditText cep;
    private Button procurarCep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp_cep);

        logradouro = findViewById(R.id.text_view_logradouroId);
        cep = findViewById(R.id.edit_text_cepId);
        procurarCep = findViewById(R.id.button_consultarCepId);

        procurarCep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.searchCep(cep.getText().toString());
            }
        });

        mPresenter=new CepFinderPresenter(this);
        mPresenter.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.stop();
    }

    @Override
    public void loadLogradouro(Cep logradouro) {
        String resultado =
                "CEP........: " + logradouro.getCep() +"\n" +
                "Logradouro.: " + logradouro.getLogradouro() +"\n" +
                "Complemento: " + logradouro.getComplemento() +"\n" +
                "Bairro.....: " + logradouro.getBairro() +"\n" +
                "Localidade.: " + logradouro.getLocalidade() +"\n" +
                "UF.........: " + logradouro.getUf() +"\n" +
                "IBGE.......: " + logradouro.getIbge() +"\n" +
                "GIA........: " + logradouro.getGia() +"\n" ;

        this.logradouro.setText(resultado);
    }
}