package com.example.dimasjose.myapplication.control;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dimasjose.myapplication.R;
//TESTE
public class MainActivity extends AppCompatActivity {

    private Button buttonConsultar; //decclara os botões
    private Button buttonIncluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);// liga a acticity ao layout activity_main.xml

        buttonConsultar = (Button)findViewById(R.id.buttonConsultar); // faz a ligaçao entre o botao no XML e o codigo que determinará sua ação
        buttonIncluir = (Button)findViewById(R.id.buttonIncluir);

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
    }
}
