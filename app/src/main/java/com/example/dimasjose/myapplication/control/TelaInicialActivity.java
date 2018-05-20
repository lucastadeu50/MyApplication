package com.example.dimasjose.myapplication.control;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.dimasjose.myapplication.R;

public class TelaInicialActivity extends AppCompatActivity {
 Button buttonIniciar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
buttonIniciar = findViewById(R.id.buttonEntrar);
buttonIniciar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent intent = new Intent(TelaInicialActivity.this,MainActivity.class);//abre a tela de cadastro
        startActivity(intent);


    }
});

    }
}
