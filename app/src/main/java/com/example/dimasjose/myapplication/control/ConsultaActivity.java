package com.example.dimasjose.myapplication.control;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.dimasjose.myapplication.R;
import com.example.dimasjose.myapplication.adapter.ListViewAdapter;
import com.example.dimasjose.myapplication.model.Usuario;
import com.example.dimasjose.myapplication.model.UsuarioBD;

import java.util.ArrayList;
import java.util.List;

public class ConsultaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView; // declara a listview
    private UsuarioBD usuarioBD; // declara um objeto da classe usuarioBD
    public Usuario usuario; // declara um objeto da classe usuario
    Button voltar; // declara o botao voltar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);


        usuarioBD = new UsuarioBD(this); // cria um novo Usuario no banco de dados
        usuario = new Usuario(); // cria um novo usuario na memoria ram do celular
        listView = (ListView) findViewById(R.id.listView); // conecta a listview com o xml
        listView.setOnItemClickListener(this); // cria um ouvinte na listview, ativa  a função de clickar em um item da lista
        voltar = findViewById(R.id.buttonvoltar); // conecta o voltar com xml
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsultaActivity.this, MainActivity.class);//volta pro menu principal quando clicka em voltar
                startActivity(intent);
            }
        });


        carregarListView(usuarioBD.getAll()); //chama a função que carrega os usuarios pra lista
    }
    public void carregarListView(List<Usuario> usuarios){
        ListViewAdapter adapter = new ListViewAdapter(this, usuarios);//carrega a lista com os usuarios
        listView.setAdapter(adapter);
    }


    // CRIA A FUNÇÃO DE PODER CLICKAR SOBRE UM ITEM DA LISTA E ABRIR OUTRA TELA
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        usuario = (Usuario) adapterView.getAdapter().getItem(position); // Salva o item que foi clickado na memoria ram
        Intent intent = new Intent(this,EdicaoActivity.class); // chama a classe de edição

       //   PUT EXTRA, serve pra levar os dados do usuario de uma tela pra outra,
        // como se voce tivesse colocando o usuario dentro de um carro e levando ele de uma cidade pra outra

        intent.putExtra("nome", usuario.nome);
        intent.putExtra("datadenascimento", usuario.datadenascimento);
        intent.putExtra("sexo",usuario.sexo);
        intent.putExtra("observacao", usuario.observacao);
        intent.putExtra("ocupacao", usuario.ocupacao);
        intent.putExtra("id", usuario.id);

        intent.putExtra("Editing", usuario);

        startActivity(intent);//inica a tela de ediçao

    }
}



