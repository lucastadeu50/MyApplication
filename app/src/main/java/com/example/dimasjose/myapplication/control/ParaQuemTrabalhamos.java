package com.example.dimasjose.myapplication.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.dimasjose.myapplication.R;

public class ParaQuemTrabalhamos extends Activity {
    private ImageButton botaoVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_para_quem_trabalhamos);

        botaoVoltar = findViewById(R.id.imagemBotaoVoltarId);
        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParaQuemTrabalhamos.this, MainActivity.class));
            }
        });
    }
}
