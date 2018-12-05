package com.example.dimasjose.myapplication.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimas jose on 20/03/2018.
 */

//CLASSE QUE TRATA AS AÇOES NO BANCO DE DE DADOS


public class UsuarioBD extends SQLiteOpenHelper{
    //
    public static final String DATABASE_NAME = "mylist.db";
    public static final String TABLE_NAME = "mylist_data";
    public static final String COL1 = "_ID";
    public static final String COL2 = "NOME";
    public static final String COL3 = "DATADENASCIMENTO";
    public static final String COL4 = "SEXO";
    public static final String COL5 = "OCUPACAO";
    public static final String COL6 = "OBSERVACAO";
    public static final String COL7 =  "RESULTADO";
    public static final String COL8 =  "PITCHBREAKS";
    public static final String COL9 =  "F0";
    public static final String COL10 =  "JITTER";
    public static final String COL11 =  "SNR";
    public static final String COL12 =  "SHIMMERRMS";




    public UsuarioBD(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    //CRIA A TABELA
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " NOME, " + " DATADENASCIMENTO, " + " SEXO, " + " OCUPACAO, " + " OBSERVACAO, " + " RESULTADO, " + "PITCHBREAKS, " + "F0, " + "JITTER, " + "SNR, " + "SHIMMERRMS )";
        db.execSQL(createTable);
    }

    // ESSA FUNÇAO NAO FAZ DIFERENÇA NENHUMA
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // FUNÇAO QUE PEGA OS DADOS NA MEMORIA RAM E SALVA NO BANCO DE DADOS,
    // PEGA OS DADOS PREECHIDOS NO CAMPO DE CAMPO DE CADSTRO E TRANFERE PRO BANCO
    // FUNÇÃO RECEBE COMO PARAMENTRO NOME, DATADEJNASCIMENTO .... QUE ESTAVA EM USUARIO NA MEMORIA
    public boolean addData(String nome,String datadenascimento, String sexo,String ocupacao, String obsservacao) {

        SQLiteDatabase db = this.getWritableDatabase();//ABRE A CONEXAO COM O BANCO DE DADOS
        ContentValues contentValues = new ContentValues(); // CRIA UMA VARIAVEL DO TIPO CONTENT VALUES
        // (SERVE PRA GUARDAR VALORES RECEBIDOS NA FUNÇAO EM UMA UNICA VARIAVEL)
        // COLOCA TUDO QUE FOI RECEBIDO DENTRO DA VARIAVEL CONTENT VALUES
        contentValues.put(COL2, nome);
        contentValues.put(COL3, datadenascimento);
        contentValues.put(COL4, sexo);
        contentValues.put(COL5, ocupacao);
        contentValues.put(COL6, obsservacao);

        long result = db.insert(TABLE_NAME, null, contentValues); // DB.INSERT INSERE A VARIAVEL CONTENT VALUES NO BANCO DE DADOS

        //se os dados nao forem adicionados certos retorna -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    //FUNÇÃO NAO USADA PRA NADA
    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    // FUNÇAO QUE RETORNA TODOS USUARIOS SALVOS NO BANCO PRA LISTVIEW
    public List<Usuario> getAll(){
        //SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        try {

          ////  return toList(sqLiteDatabase.rawQuery("select * from TABLE_NAME",null));//olhar o igual
          return  toList(sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null));

        }finally {
            sqLiteDatabase.close();
        }
    }

    //FUNÇÃO PRA DELETAR UM USUARIO DO BANCO, VOCE RECEBE O USUARIO QUE VAI SER DELETADO COM PARAMETRO E APAGA ELE PELO CODIGO.
    public long delete(Usuario usuario){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {

            return sqLiteDatabase.delete(TABLE_NAME, "_ID=?", new String[]{String.valueOf(usuario.id)});

        }finally {
            sqLiteDatabase.close();
        }
    }

    // EDITA UM USUARIO QUE JA ESTA SALVO, FUNCIONA IGUAL A FUNÇÃO DE SALVAR
    public boolean update(Long id, String nome,String datadenascimento, String sexo,String ocupacao, String obsservacao, String resultado, String pitchbreaks, String f0, String jitter, String snr, String shimmerRMS){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, id);
        contentValues.put(COL2, nome);
        contentValues.put(COL3, datadenascimento);
        contentValues.put(COL4, sexo);
        contentValues.put(COL5, ocupacao);
        contentValues.put(COL6, obsservacao);
        contentValues.put(COL7, resultado);
        contentValues.put(COL8, pitchbreaks);
        contentValues.put(COL9, f0);
        contentValues.put(COL10, jitter);
        contentValues.put(COL11, snr);
        contentValues.put(COL12, shimmerRMS);

        try {
              sqLiteDatabase.update(TABLE_NAME, contentValues, "_ID=?", new String[]{String.valueOf(id)});
        }finally {
            sqLiteDatabase.close();
        }
        return true;
    }

    // PEGA UM USUARIO POR UM E VAI COLOCANDO NA LISTA, ATÉ O CURSOR CHEGAR NO FIM DA TABELA
    public  List<Usuario> toList(Cursor cursor){

        List<Usuario> usuarios = new ArrayList<>();
        if(cursor!=null && cursor.getCount() > 0) {

            if (cursor.moveToFirst()) {
                do {
                    Usuario usuario = new Usuario();
                    usuario.id = cursor.getLong(cursor.getColumnIndex("_ID"));
                    usuario.nome = cursor.getString(cursor.getColumnIndex("NOME"));
                    usuario.datadenascimento = cursor.getString(cursor.getColumnIndex("DATADENASCIMENTO"));
                    usuario.sexo = cursor.getString(cursor.getColumnIndex("SEXO"));
                    usuario.ocupacao = cursor.getString(cursor.getColumnIndex("OCUPACAO"));
                    usuario.observacao = cursor.getString(cursor.getColumnIndex("OBSERVACAO"));
                    usuario.resultado = cursor.getString(cursor.getColumnIndex("RESULTADO"));
                    usuario.pitchbreaks = cursor.getString(cursor.getColumnIndex("PITCHBREAKS"));
                    usuario.f0 = cursor.getString(cursor.getColumnIndex("F0"));
                    usuario.jitter = cursor.getString(cursor.getColumnIndex("JITTER"));
                    usuario.snr = cursor.getString(cursor.getColumnIndex("SNR"));
                    usuario.shimmerRMS = cursor.getString(cursor.getColumnIndex("SHIMMERRMS"));


                    usuarios.add(usuario);
                } while (cursor.moveToNext());
            }
        }
        return usuarios;
    }
}
