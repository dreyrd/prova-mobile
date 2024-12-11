package com.example.prova;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.camera2.params.InputConfiguration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prova.API.IBGEService;
import com.example.prova.Model.UF;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView id, sigla, nome;
    TextInputEditText inputSigla;//mudar p textinputlayout
    Retrofit retrofit;

    IBGEService IBGEService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciaComponentes();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://servicodados.ibge.gov.br/api/v1/localidades/estados/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IBGEService = retrofit.create(IBGEService.class);
    }

    private void iniciaComponentes(){
        id = findViewById(R.id.textId);
        sigla = findViewById(R.id.textSigla);
        nome = findViewById(R.id.textNome);
        inputSigla = findViewById(R.id.inputSigla);
    }

    public void recuperarPorSigla(View view){
        Call<UF> call = IBGEService.recuperarPorSigla(inputSigla.getText().toString());

        call.enqueue(new Callback<UF>() {
            @Override
            public void onResponse(Call<UF> call, Response<UF> response) {
                if(response.isSuccessful()){
                    UF uf = response.body();
                    id.setText(uf.getId());
                    sigla.setText(uf.getSigla());
                    nome.setText(uf.getNome());
                }
            }

            @Override
            public void onFailure(Call<UF> call, Throwable t) {

                int siglaDigitida = inputSigla.getText().toString().length();

                String mensagem = "Sigla não foi encontrada";

                if (siglaDigitida == 0){
                    mensagem = "Preencha todos os campos";
                }
                else if (siglaDigitida >= 3){
                    mensagem = "Digite uma sigla válida";
                }

                Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_SHORT).show();
            }
        });
    }

}












