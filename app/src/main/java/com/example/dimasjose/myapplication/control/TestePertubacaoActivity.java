package com.example.dimasjose.myapplication.control;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dimasjose.myapplication.R;

public class TestePertubacaoActivity extends AppCompatActivity {
    Button buttonancora, buttoamostra, buttonmaior,buttonigual, buttonmenor;
    MediaPlayer amostra,gravacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_pertubacao);
        buttonancora = findViewById(R.id.buttonPlayAncora);
        buttoamostra = findViewById(R.id.buttonPlayGravacao);
        buttonmaior = findViewById(R.id.buttonMaior);
        buttonmenor = findViewById(R.id.buttonMenor);
        buttonigual = findViewById(R.id.buttonIgual);
        amostra = MediaPlayer.create(TestePertubacaoActivity.this, R.raw.SNR0210J020S000VaF220_1);

        buttonancora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amostra.start();
            }
        });


    }
}
