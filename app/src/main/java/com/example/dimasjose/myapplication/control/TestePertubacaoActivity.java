package com.example.dimasjose.myapplication.control;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dimasjose.myapplication.R;
import com.example.dimasjose.myapplication.model.Usuario;
import com.example.dimasjose.myapplication.model.UsuarioBD;

import java.io.File;
import java.io.IOException;

public class TestePertubacaoActivity extends AppCompatActivity {
    Button buttonAncora, buttonAmostra, buttonMaior,buttonIgual, buttonMenor;
    private Usuario usuario;
    private UsuarioBD usuarioBD;
    MediaPlayer amostra1,amostra2,amostra3,amostra4,amostra5,amostra6,amostra7,gravacao;
    MediaPlayer[] mediaPlayer;
    int i = 0,j=0;
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

        usuarioBD = new UsuarioBD(this);



        Intent intent = getIntent();
        final String OUTPUT_FILE = intent.getStringExtra("gravacao");
        final Usuario usuario = (Usuario) getIntent().getSerializableExtra("Editing");

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resultado do teste");
        builder.setMessage("A amostra " + Integer.toString(i+1));
        final EditText input = new EditText(this);
        builder.setView(input);



        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                File outFile = new File(OUTPUT_FILE);
                if (outFile.exists())
                    outFile.delete();
                usuario.resultado = input.getText().toString();
                update(usuario.id, usuario.nome, usuario.datadenascimento, usuario.sexo, usuario.ocupacao, usuario.observacao,usuario.resultado);
                Intent inte = new Intent(TestePertubacaoActivity.this, MainActivity.class);
                startActivity(inte);

            }
        });
        builder.setNegativeButton("Voltar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                i=0;
            }
        });
        builder.setCancelable(false);
        final AlertDialog start = builder.create();



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
                Toast.makeText(TestePertubacaoActivity.this, OUTPUT_FILE, Toast.LENGTH_SHORT).show();

            }
        });
        buttonMenor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 2*i+1;
                Toast.makeText(TestePertubacaoActivity.this, Integer.toString(i), Toast.LENGTH_LONG).show();
                j++;
                if(i==3){
                    buttonMenor.setEnabled(false);
                }
                if(j==3)
                    start.show();
            }
        });

        buttonIgual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.show();

                Toast.makeText(TestePertubacaoActivity.this, Integer.toString(i), Toast.LENGTH_LONG).show();


            }
        });

        buttonMaior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 2*i+2;
                j++;
                Toast.makeText(TestePertubacaoActivity.this, Integer.toString(i), Toast.LENGTH_LONG).show();
                if (i==6){
                    buttonMaior.setEnabled(false);
                }
                if(j==3){
                    start.show();
                }
            }
        });

    }
    public void update(Long id, String newEntry, String newEntry2, String newEntry3, String newEntry4, String newEntry5, String newEntry6) {

        boolean insertData = usuarioBD.update(id, newEntry, newEntry2, newEntry3, newEntry4, newEntry5, newEntry6);

        if (insertData == true) {
            //  Toast.makeText(this, "Dado adicionado ao banco com sucesso.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "ERRO dado nao adicionado ao banco.", Toast.LENGTH_LONG).show();
        }
    }

}

