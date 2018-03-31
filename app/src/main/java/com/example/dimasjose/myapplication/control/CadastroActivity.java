package com.example.dimasjose.myapplication.control;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import  android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dimasjose.myapplication.R;
import com.example.dimasjose.myapplication.adapter.ListViewAdapter;
import com.example.dimasjose.myapplication.model.Usuario;
import com.example.dimasjose.myapplication.model.UsuarioBD;

import java.util.List;

public class CadastroActivity extends AppCompatActivity {

    // declaração de todas variaveis
    private  static final String TAG = "usuariomemoria";
    private Usuario usuario;
    Spinner spinnersexo;
    EditText edtNone, edtDatadeNascimento, edtOcupacao, edtObservacao;
    private UsuarioBD usuarioBD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        //CRIA O SPINNER, PEGA OS ITENS LA DO ARQUIVO DE STRINGS
        spinnersexo  = (Spinner) findViewById(R.id.spinnerSexo);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.spinnersexo, android.R.layout.simple_spinner_item);
        spinnersexo.setAdapter(adapter);

        //CRIA UM USUARIO NA MEMORIA E UM NO BANCO DE DADOS
        usuario = new Usuario();
        usuarioBD = new UsuarioBD(this);

        // LIGA OS EDIT TEXT DO XML COM AS VARIAVEIS CRIADAS ACIMA
        edtNone = findViewById(R.id.edtNome);
        edtDatadeNascimento = findViewById(R.id.edtDatadeNascimento);
        edtOcupacao = findViewById(R.id.edtOcupacao);
        edtObservacao = findViewById(R.id.edtObservacao);



    }

        //CRIA O MENU QUE APARECE NA BARRA AZUL
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cadastro, menu); // INFLA O MENU DE CADASTRO
        return super.onCreateOptionsMenu(menu);
    }

    @Override

    // DIZ O QUE SERA FEITO QUANDO SE CLICK EM SALVAR OU EM CANCELAR.
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.action_ok:

                //SALVA TUDO QUE TIVER NOS EDIT TEXT DENTRO DA VARIAVEL USUARIO
                usuario.nome = edtNone.getText().toString();
                usuario.datadenascimento = edtDatadeNascimento.getText().toString();
                usuario.ocupacao = edtOcupacao.getText().toString();
                usuario.observacao = edtObservacao.getText().toString();
                usuario.sexo = spinnersexo.getSelectedItem().toString();
                //CHAMA A FUNÇAI QUE SERVE PRA VERIFICAR SE TODOS OS DADOS FORAM PREENCHIDOS
                verificadados();
                //Log.d(TAG, usuario.toString());
              //  Log.d(TAG, usuarioBD.toString());
                break;
            case R.id.action_cancelar:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);

    }
    //FUNÇÃO VERIFICA SE OS DA FORAM PREENCHIDOS
    private void  verificadados() {
       boolean verifica = true;
        // SE ALGUM CAMPO ESTA VAZIO ELE VOLTA COM O FOCUS PARA AQUELE CAMPO
        if (usuario.nome.isEmpty()) {
             edtNone.requestFocus();
             Toast.makeText(this, "Campo obrigatorio em branco", Toast.LENGTH_SHORT).show();
             verifica = false;
         }
         else if (usuario.datadenascimento.isEmpty()) {
             edtDatadeNascimento.requestFocus();
             Toast.makeText(this, "Campo obrigatorio em branco", Toast.LENGTH_SHORT).show();
             verifica = false;
         }
        else if (usuario.ocupacao.isEmpty()) {
             edtOcupacao.requestFocus();
             Toast.makeText(this, "Campo obrigatorio em branco", Toast.LENGTH_SHORT).show();
             verifica = false;
         }
         // SE PASSAR NA VERIFICAÇÃO ELE PEGA O USUARIO QUE  ESTA SALVO NA MEMORIA E MANDA PRO BANCO DE DADOS
     if(verifica==true){
         AddData(usuario.nome, usuario.datadenascimento, usuario.sexo, usuario.ocupacao, usuario.observacao );
         finish();
     }
    }
        //FUNÇÃO QUE TRANSFERE OS DADOS MEMORIA RAM PRO BANCO DE DADOS,
        // CADA CAMPO NEW ENTRY É UM DADO DA CLASSE USUARIO QUE SERA SALVO NO CAMPO DE DADOS
    public void AddData(String newEntry, String newEntry2, String newEntry3, String newEntry4, String newEntry5) {

        boolean insertData = usuarioBD.addData(newEntry, newEntry2, newEntry3, newEntry4, newEntry5);

        if(insertData==true){
            Toast.makeText(this, "Dado adicionado ao banco com sucesso.", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "ERRO dado nao adicionado ao banco.", Toast.LENGTH_LONG).show();
        }
    }
}