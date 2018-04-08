package com.example.dimasjose.myapplication.control;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dimasjose.myapplication.R;

import java.io.IOException;

public class TestePertubacaoActivity extends AppCompatActivity {
    Button buttonAncora, buttonAmostra, buttonMaior,buttonIgual, buttonMenor;

    MediaPlayer amostra1,amostra2,amostra3,amostra4,amostra5,amostra6,amostra7,gravacao;
    MediaPlayer[] mediaPlayer;
    int i = 0;
    AlertDialog resultado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_pertubacao);
        buttonAncora = findViewById(R.id.buttonPlayAncora);
        buttonAmostra = findViewById(R.id.buttonPlayGravacao);
        buttonMaior = findViewById(R.id.buttonMaior);
        buttonMenor = findViewById(R.id.buttonMenor);
        buttonIgual = findViewById(R.id.buttonIgual);
        amostra1 = MediaPlayer.create(TestePertubacaoActivity.this, R.raw.snr0210j020s000vaf220_1);
        amostra2 = MediaPlayer.create(TestePertubacaoActivity.this, R.raw.snr0350j020s000vaf220_1);
        amostra3 = MediaPlayer.create(TestePertubacaoActivity.this, R.raw.snr0070j020s000vaf220_1);
        amostra4 = MediaPlayer.create(TestePertubacaoActivity.this, R.raw.snr0420j020s000vaf220_1);
        amostra5 = MediaPlayer.create(TestePertubacaoActivity.this, R.raw.snr0280j020s000vaf220_1);
        amostra6 = MediaPlayer.create(TestePertubacaoActivity.this, R.raw.snr0140j020s000vaf220_1);
        amostra7 = MediaPlayer.create(TestePertubacaoActivity.this, R.raw.snr0010j020s000vaf220_1);
        mediaPlayer = new MediaPlayer[]{amostra1, amostra2, amostra3, amostra4, amostra5, amostra6, amostra7};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resultado do teste");
        builder.setMessage("A amostra" + Integer.toString(i));






        Intent intent = getIntent();
        final String OUTPUT_FILE = intent.getStringExtra("gravacao");



        buttonAncora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i>=0 && i<7) {
                    mediaPlayer[i].start();
                }else{
                    Toast.makeText(TestePertubacaoActivity.this, "Erro ao reproduzir ancora", Toast.LENGTH_SHORT).show();
                }
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
        buttonMenor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 2*i+1;
                //Toast.makeText(TestePertubacaoActivity.this, Integer.toString(i), Toast.LENGTH_LONG).show();
                if(i==4){
                    buttonMenor.setEnabled(false);
                }
            }
        });

        buttonIgual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestePertubacaoActivity.this, Integer.toString(i), Toast.LENGTH_LONG).show();

            }
        });

        buttonMaior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 2*i+2;
                Toast.makeText(TestePertubacaoActivity.this, Integer.toString(i), Toast.LENGTH_LONG).show();
                if (i==13){
                    buttonMaior.setEnabled(false);
                }
            }
        });

    }
}

