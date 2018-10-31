package com.example.dimasjose.myapplication.control;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.dimasjose.myapplication.R;
import com.example.dimasjose.myapplication.adapter.ListViewAdapter;
import com.example.dimasjose.myapplication.adapter.ListViewAdapterIniciarConsulta;
import com.example.dimasjose.myapplication.model.Usuario;
import com.example.dimasjose.myapplication.model.UsuarioBD;

import java.util.List;

public class InicarConsultaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listViewIniciarConsulta;
    private UsuarioBD usuarioBD;
    public Usuario usuario;
    private ImageButton botaoVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicar_consulta);


        botaoVoltar = findViewById(R.id.imagemBotaoVoltarId);
        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InicarConsultaActivity.this, MainActivity.class));
            }
        });


        listViewIniciarConsulta = findViewById(R.id.listViewIniciarConsulta);
        listViewIniciarConsulta.setOnItemClickListener(this);

        usuario = new Usuario();
        usuarioBD = new UsuarioBD(this);


        carregarListView(usuarioBD.getAll()); //chama a função que carrega os usuarios pra lista
    }

    public void carregarListView(List<Usuario> usuarios) {
        ListViewAdapterIniciarConsulta adapter = new ListViewAdapterIniciarConsulta(this, usuarios);//carrega a lista com os usuarios
        listViewIniciarConsulta.setAdapter(adapter);
    }


    // CRIA A FUNÇÃO DE PODER CLICKAR SOBRE UM ITEM DA LISTA E ABRIR OUTRA TELA
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        usuario = (Usuario) adapterView.getAdapter().getItem(position); // Salva o item que foi clickado na memoria ram
        Intent intent = new Intent(InicarConsultaActivity.this, GravacaowavActivity.class); // chama a classe de edição

        //   PUT EXTRA, serve pra levar os dados do usuario de uma tela pra outra,
        // como se voce tivesse colocando o usuario dentro de um carro e levando ele de uma cidade pra outra

        intent.putExtra("nome", usuario.nome);
        intent.putExtra("datadenascimento", usuario.datadenascimento);
        intent.putExtra("sexo", usuario.sexo);
        intent.putExtra("observacao", usuario.observacao);
        intent.putExtra("ocupacao", usuario.ocupacao);
        intent.putExtra("id", usuario.id);

        intent.putExtra("Editing", usuario);

        startActivity(intent);//inica a tela de ediçao

    }
}