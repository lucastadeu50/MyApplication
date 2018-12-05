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
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.dimasjose.myapplication.R;
import com.example.dimasjose.myapplication.model.Usuario;
import com.example.dimasjose.myapplication.model.UsuarioBD;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.io.File;
import java.io.IOException;

public class TestePertubacaoActivity extends AppCompatActivity {
    Button  buttonMaior,buttonIgual, buttonMenor;
    Button buttonAncora, buttonAmostra;
    private Usuario usuario;
    private UsuarioBD usuarioBD;
    MediaPlayer amostra1,amostra2,amostra3,amostra4,amostra5,amostra6,amostra7,gravacao;
    MediaPlayer[] mediaPlayer;
    int i = 0,j = 0;
    double resultado=5;
    private ImageButton botaoVoltar;
    String OUTPUT_FILE;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_pertubacao);

        botaoVoltar = findViewById(R.id.imagemBotaoVoltarId);
        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestePertubacaoActivity.this, InicarConsultaActivity.class));

            }
        });


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
        String igual="igual";
        String maior="maior";
        String menor="menor";

        Intent intent = getIntent();
        OUTPUT_FILE = intent.getStringExtra("gravacao");
        final Usuario usuario = (Usuario) getIntent().getSerializableExtra("Editing");








        PushDownAnim.setPushDownAnimTo(buttonAncora)
        .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (i >= 0 && i < 7) {
                        mediaPlayer[i].start();
                      //  Toast.makeText(TestePertubacaoActivity.this, "Reproduzindo ancora", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(TestePertubacaoActivity.this, "Erro ao reproduzir ancora", Toast.LENGTH_SHORT).show();
                    }
                }


        });
        PushDownAnim.setPushDownAnimTo(buttonAmostra)
        .setOnClickListener(new View.OnClickListener() {
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
                  //  Toast.makeText(TestePertubacaoActivity.this, OUTPUT_FILE, Toast.LENGTH_SHORT).show();

                }
        });

        PushDownAnim.setPushDownAnimTo(buttonMenor)
        .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i = 2 * i + 1;
                salvarReultado();
             //   Toast.makeText(TestePertubacaoActivity.this, Double.toString(resultado), Toast.LENGTH_SHORT).show();



               // Toast.makeText(TestePertubacaoActivity.this, Integer.toString(i), Toast.LENGTH_LONG).show();
                j++;
                if (i == 3) {
                    buttonMenor.setEnabled(false);

                }
                if (j == 3) {
                    salvarReultado();
                    resultadoDialog();
                    //start.show();
                }
            }
        });

        PushDownAnim.setPushDownAnimTo(buttonIgual)
        .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarReultado();
             //   start.show();
                resultadoDialog();

                //Toast.makeText(TestePertubacaoActivity.this, Integer.toString(i), Toast.LENGTH_LONG).show();


            }
        });
        PushDownAnim.setPushDownAnimTo(buttonMaior)
        .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 2*i+2;
                salvarReultado();



                j++;
            //    Toast.makeText(TestePertubacaoActivity.this, Integer.toString(i), Toast.LENGTH_LONG).show();
                if (i==6){
                    buttonMaior.setEnabled(false);
                }
                if(j==3){
                    salvarReultado();
                    resultadoDialog();
                  //  start.show();
                }
            }
        });

    }
    public void update(Long id, String newEntry, String newEntry2, String newEntry3, String newEntry4, String newEntry5, String newEntry6, String newEntry7, String newEntry8, String newEntry9, String newEntry10, String newEntry11) {

        boolean insertData = usuarioBD.update(id, newEntry, newEntry2, newEntry3, newEntry4, newEntry5, newEntry6, newEntry7, newEntry8, newEntry9, newEntry10, newEntry11);

        if (insertData == true) {
            //  Toast.makeText(this, "Dado adicionado ao banco com sucesso.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "ERRO dado nao adicionado ao banco.", Toast.LENGTH_LONG).show();
        }
    }
    public void salvarReultado(){
        if(i==0) {
            resultado = 1.5;
        }

        else if(i==1) {
            resultado = 0.5;
        }
        else if(i==2) {
            resultado = 2.5;
        }
        else if(i==3) {
            resultado = 0;
        }
        else if(i==4) {
            resultado = 1.0;
        }
        else if(i==5) {
            resultado = 2.0;
        }
        else if(i==6) {
            resultado = 3.0;
        }
        else if(i==8) {
            resultado = 0.25;
        }

        else if(i==9) {
            resultado = 0.75;
        }
        else if(i==10) {
            resultado = 1.25;
        }

        else if(i==11) {
            resultado = 1.75;
        }

        else if(i==12) {
            resultado = 2.25;
        }

        else if(i==13) {
            resultado = 2.75;
        }
    }
  public void janelaResultado(){

  }
  public void resultadoDialog(){
      final Usuario usuario = (Usuario) getIntent().getSerializableExtra("Editing");
      final AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle("Resultado:");
      salvarReultado();
    //  Toast.makeText(this, Double.toString(resultado), Toast.LENGTH_SHORT).show();
      builder.setMessage("O resultado do teste é igual: " + Double.toString(resultado));
      final EditText input = new EditText(this);
      input.setText(usuario.observacao);

      builder.setView(input);


      builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
              File outFile = new File(OUTPUT_FILE);
              if (outFile.exists())
                  outFile.delete();
              usuario.resultado = String.valueOf(resultado);
              usuario.observacao = input.getText().toString();
              update(usuario.id, usuario.nome, usuario.datadenascimento, usuario.sexo, usuario.ocupacao, usuario.observacao, usuario.resultado, usuario.pitchbreaks, usuario.f0, usuario.jitter, usuario.snr, usuario.shimmerRMS );
              Intent inte = new Intent(TestePertubacaoActivity.this, MainActivity.class);
              startActivity(inte);

          }
      });
      builder.setNegativeButton("Voltar", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
              i=0;
              j=0;
              buttonMaior.setEnabled(true);
              buttonMenor.setEnabled(true);
          }
      });
      builder.setCancelable(false);
      final AlertDialog start = builder.create();
      start.show();
  }

}

