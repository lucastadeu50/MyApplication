package com.example.dimasjose.myapplication.config;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenciasAndroid {
    private Context context;
    private SharedPreferences preferencess;
    private String NOME_ARQUIVO = "projetoFirebase.preferencias";
    private int MODE = 0;
    private SharedPreferences.Editor editor;

    private  final String CHAVE_INDENTIFICADOR = "identificarUsuarioLogado";
    private final String CHAVE_NOME = "nomeUsuarioLogado";


    public PreferenciasAndroid (Context context) {
        this.context = context;
        preferencess = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferencess.edit();
    }
    public void salvarUsuarioPreferencias(String identifacadorUsuario, String nomeUsuario){
        editor.putString(CHAVE_INDENTIFICADOR, identifacadorUsuario);
        editor.putString(CHAVE_NOME, nomeUsuario);
        editor.commit();
    }
    public String getIdentificador(){
        return preferencess.getString(CHAVE_INDENTIFICADOR, null);
    }
    public String getNome(){
        return preferencess.getString(CHAVE_NOME, null);
    }
}
