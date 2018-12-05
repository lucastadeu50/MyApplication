package com.example.dimasjose.myapplication.control;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dimasjose.myapplication.R;
import com.example.dimasjose.myapplication.model.Usuario;
import com.example.dimasjose.myapplication.model.UsuarioBD;

public class EdicaoActivity extends AppCompatActivity {
    UsuarioBD usuarioBD;
    Usuario usuario;
    EditText edtNone, edtDatadeNascimento, edtOcupacao, edtObservacao;
    Spinner spinnersexo;
    private ImageButton botaoVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicao);


        botaoVoltar = findViewById(R.id.imagemBotaoVoltarId);
        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EdicaoActivity.this, MainActivity.class));
            }
        });



        // MESMA COISA EXPLICAÇÃO DAS OUTRAS CLASSES
        usuarioBD = new UsuarioBD(this);
        spinnersexo = (Spinner) findViewById(R.id.spinnerSexo);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.spinnersexo, android.R.layout.simple_spinner_item);
        spinnersexo.setAdapter(adapter);
        edtNone = findViewById(R.id.edtNome);
        edtDatadeNascimento = findViewById(R.id.edtDatadeNascimento);
        edtOcupacao = findViewById(R.id.edtOcupacao);
        edtObservacao = findViewById(R.id.edtObservacao);

        // RECEBE O OBJETO USUARIO DA CLASSE DE CONSULTA
        final Usuario usuario = (Usuario) getIntent().getSerializableExtra("Editing");

        // RECEBE O OBJETO USUARIO QUE FOI SALVO NO INTENT LA NA CLASSE DE CONSULTA
        Intent intent = getIntent();
        edtNone.setText(intent.getStringExtra("nome"));// PREENCHE O NOME NA TELA
        edtDatadeNascimento.setText(intent.getStringExtra("datadenascimento")); // PRRENCHE A DATA
        String sexo = intent.getStringExtra("sexo");

        // SELECIONA EM QUAL POSIÇÃO O SPINNER DEVE FICAR QUANDO FOR ABERTA A TELA DE EDIÇÃO
        if (sexo.equals("Masculino")) {
            spinnersexo.setSelection(1);
        }
        if (sexo.equals("Feminino")) {
            spinnersexo.setSelection(0);
        }
        edtOcupacao.setText(intent.getStringExtra("ocupacao"));
        edtObservacao.setText(intent.getStringExtra("observacao"));
        edtNone.requestFocus();// COLOCA O FOCUS NO NOME QUANDO A TELA É ABERTA



    }
    // É PRATICAMENTE IGUAL A CLASSE DE CADASTRO
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edicao, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int ida = item.getItemId();
        final Usuario usuario = (Usuario) getIntent().getSerializableExtra("Editing");

        Intent intent = new Intent(EdicaoActivity.this, ConsultaActivity.class);

        switch (ida) {
            case R.id.item_salvar:
                usuario.nome = edtNone.getText().toString();
                usuario.datadenascimento = edtDatadeNascimento.getText().toString();
                usuario.ocupacao = edtOcupacao.getText().toString();
                usuario.observacao = edtObservacao.getText().toString();
                usuario.sexo = spinnersexo.getSelectedItem().toString();
                update(usuario.id, usuario.nome, usuario.datadenascimento, usuario.sexo, usuario.ocupacao, usuario.observacao, usuario.resultado, usuario.pitchbreaks, usuario.f0, usuario.jitter, usuario.snr, usuario.shimmerRMS );
                startActivity(intent);
                break;
            case R.id.item_voltar:
                finish();
                break;
            case R.id.item_apagar:
                usuarioBD.delete(usuario);// CHAMA A FUNÇAO DE APAGAR
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    public void update(Long id, String newEntry, String newEntry2, String newEntry3, String newEntry4, String newEntry5, String newEntry6, String newEntry7, String newEntry8, String newEntry9, String newEntry10, String newEntry11) {

        boolean insertData = usuarioBD.update(id, newEntry, newEntry2, newEntry3, newEntry4, newEntry5, newEntry6, newEntry7, newEntry8, newEntry9, newEntry10, newEntry11);

        if (insertData == true) {
          //  Toast.makeText(this, "Dado adicionado ao banco com sucesso.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "ERRO dado nao adicionado ao banco.", Toast.LENGTH_LONG).show();
        }
    }
}
