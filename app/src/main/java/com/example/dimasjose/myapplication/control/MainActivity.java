package com.example.dimasjose.myapplication.control;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



import com.example.dimasjose.myapplication.R;
import com.example.dimasjose.myapplication.config.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference referenciaFirebase = FirebaseDatabase.getInstance().getReference();


    private Button buttonConsultar; //decclara os botões
    private Button buttonIncluir;
    private Button buttonInicarConsulta;
    private TextView botaoSobreApp;
    private TextView botaoParaQuemTrabalhamos;
    private TextView botaoContato;
    private TextView botaoServico;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DE4A1265")));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);// liga a acticity ao layout activity_main.xml

        referenciaFirebase.child("usuarios").setValue(2200);
        //referenciaFirebase = ConfiguracaoFirebase.getFirebase();

        buttonConsultar = (Button)findViewById(R.id.buttonConsultar); // faz a ligaçao entre o botao no XML e o codigo que determinará sua ação
        buttonIncluir = (Button)findViewById(R.id.buttonIncluir);
        buttonInicarConsulta = findViewById(R.id.buttonIniciarConsulta);
        botaoSobreApp =  (TextView) findViewById(R.id.botaoSobreId);
        botaoServico = (TextView) findViewById(R.id.botaoServicoId);
        botaoContato = (TextView) findViewById(R.id.ContatoId);
        botaoParaQuemTrabalhamos = (TextView) findViewById(R.id.ParaQuemTrabalhamosId);


        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                             Manifest.permission.WRITE_EXTERNAL_STORAGE,
                             Manifest.permission.RECORD_AUDIO},
                1);


        //        buttonIncluir.setOnClickListener(new View.OnClickListener() {
        // Coloca um ouvinte no botão, toda vez que tiver um click ele executa a açao que esta dentro da função


        buttonIncluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CadastroActivity.class);//abre a tela de cadastro
                startActivity(intent);
            }
        });

        buttonConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ConsultaActivity.class);//abre a tela de consulta
                startActivity(intent);
            }
        });
        buttonInicarConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,InicarConsultaActivity.class);
                startActivity(intent);
            }
        });
        botaoSobreApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SobreOAplicativo.class);
                startActivity(intent);

            }
        });

        botaoServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, Servico.class));
            }
        });

        botaoContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(MainActivity.this, Contato.class));
            }
        });

        botaoParaQuemTrabalhamos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ParaQuemTrabalhamos.class));
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permissão Garantida", Toast.LENGTH_LONG).show();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
