package com.example.dimasjose.myapplication.control;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dimasjose.myapplication.R;

import java.io.IOException;

public class TestePertubacaoActivity extends AppCompatActivity {
    Button buttonAncora, buttonAmostra, buttonMaior,buttonIgual, buttonMenor;

    MediaPlayer amostra,gravacao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_pertubacao);
        buttonAncora = findViewById(R.id.buttonPlayAncora);
        buttonAmostra = findViewById(R.id.buttonPlayGravacao);
        buttonMaior = findViewById(R.id.buttonMaior);
        buttonMenor = findViewById(R.id.buttonMenor);
        buttonIgual = findViewById(R.id.buttonIgual);
        amostra = MediaPlayer.create(TestePertubacaoActivity.this, R.raw.snr0210j020s000vaf220_1);


        Intent intent = getIntent();
        final String OUTPUT_FILE = intent.getStringExtra("gravacao");

        Toast.makeText(TestePertubacaoActivity.this, OUTPUT_FILE, Toast.LENGTH_SHORT).show();


        buttonAncora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amostra.start();
            }
        });
        buttonAmostra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            gravacao = new MediaPlayer();
                try {
                    gravacao.setDataSource(OUTPUT_FILE);
                    gravacao.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                gravacao.start();
            }
        });

    }
}
