package com.example.dimasjose.myapplication.model;

import com.example.dimasjose.myapplication.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class UsuarioLogin {
    private String id;
    private String nome;
    private String email;
    private String senha;

    public UsuarioLogin(){

    }
    public void salvar(){
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();//precisamos criar uma referencia do firebase
        referenciaFirebase.child("Usuarios").child( getId() ).setValue( this );//utilizamos a referencia do firebase criada para salvar dados no nosso banco de dados, recuperamos o id setado na classe cadastroUsuario
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
