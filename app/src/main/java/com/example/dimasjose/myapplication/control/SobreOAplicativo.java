package com.example.dimasjose.myapplication.control;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.dimasjose.myapplication.R;

public class SobreOAplicativo extends Activity {

    private ImageButton botaoVoltar;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre_oaplicativo);

        botaoVoltar = findViewById(R.id.imagemBotaoVoltarId);
        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SobreOAplicativo.this, MainActivity.class));
            }
        });
    }
}
