package com.example.dimasjose.myapplication.control;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dimasjose.myapplication.R;
import com.example.dimasjose.myapplication.config.ConfiguracaoFirebase;
//import com.example.dimasjose.myapplication.model.UsuarioLogin;
import com.example.dimasjose.myapplication.model.UsuarioLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class LoginUsuarioActivity extends AppCompatActivity {



    private EditText email;
    private EditText senha;
    private Button botaoLogar;
    private UsuarioLogin usuario;
    private FirebaseAuth autenticacao;
    private DatabaseReference referenciaFirebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuario);


        permissoes();
        verificarUsuarioLogado();

        email = findViewById(R.id.edit_login_email_id);
        senha = findViewById(R.id.edit_senha_id);
        botaoLogar = findViewById(R.id.botao_logar_id);

        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!email.getText().toString().equals("") && !email.getText().toString().equals("")){
                    usuario = new UsuarioLogin(); //instanciando a classe usuariio
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                validarLogin();//método que vou criar para fazer a validação do usuario
            }else {
                    email.requestFocus();
                    Toast.makeText(LoginUsuarioActivity.this, "Digite o email e a senha", Toast.LENGTH_SHORT).show();
                }
            }
            }
            );

    }

    private void verificarUsuarioLogado(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if (autenticacao.getCurrentUser()!= null){//caso ja tenha um usuario logado vamos enviar o usuario para a tela principal
            abrirTelaPrincipal();// metodo que ja estava criado nas linhas abaixo

        }

    }
    private void validarLogin(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    abrirTelaPrincipal();//se o login for realizado com sucesso o app vai para a tela principal
                 //   Toast.makeText(LoginUsuarioActivity.this, "Login efetuado", Toast.LENGTH_SHORT).show();;

                }else{
                    Toast.makeText(LoginUsuarioActivity.this, "Email não cadastro ou senha errada", Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }
     private void abrirTelaPrincipal(){

    Intent intent = new Intent(LoginUsuarioActivity.this, MainActivity.class);
    startActivity( intent );

}

    public void abrirCadastroUsuario (View view){

        Intent intent = new Intent(LoginUsuarioActivity.this, CadastroUsuarioActivity.class);
        startActivity( intent );
    }

    @AfterPermissionGranted(123)
    private void permissoes() {
        String[] perms = {Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
        } else {
            EasyPermissions.requestPermissions(this, "O aplicativo Voice Analysis precisa de permissão para gravar audio e ler e escrever arquivos na memoria",
                    123, perms);
        }
    }

}