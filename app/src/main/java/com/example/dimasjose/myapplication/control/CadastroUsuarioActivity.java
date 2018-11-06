package com.example.dimasjose.myapplication.control;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//import com.example.dimasjose.myapplication.control.UsuarioLogin
import com.example.dimasjose.myapplication.R;
import com.example.dimasjose.myapplication.config.Base64Custom;
import com.example.dimasjose.myapplication.config.ConfiguracaoFirebase;
import com.example.dimasjose.myapplication.config.PreferenciasAndroid;
import com.example.dimasjose.myapplication.model.UsuarioLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class CadastroUsuarioActivity extends AppCompatActivity {


    private EditText nome;
    private EditText email;
    private EditText senha1, senha2;
    private Button botaoCadastrar;
    private UsuarioLogin usuario; //criando o atributo usuario da classe que eu criei
    private FirebaseAuth autenticacao; //atributo do metodo criado



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        nome = findViewById(R.id.edit_cadastro_nome_id);
        email = findViewById(R.id.edit_cadastro_email_id);
        senha1 = findViewById(R.id.edit_cadastro_senha_id);
        senha2 = findViewById(R.id.edit_cadastro_senha2_id);

        botaoCadastrar =findViewById(R.id.botao_cadastrar_cadastro_id);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(senha1.getText().toString().equals(senha2.getText().toString()) && !email.getText().toString().equals("") && !nome.getText().toString().equals(""))  {
                    usuario = new UsuarioLogin();
                    usuario.setNome(nome.getText().toString());
                    usuario.setEmail(email.getText().toString());
                    usuario.setSenha(senha1.getText().toString());
                    cadastrarUsuario();
                } else if (!senha1.getText().toString().equals(senha2.getText().toString())){
                    senha1.requestFocus();
                    Toast.makeText(CadastroUsuarioActivity.this, "As senhas não correspondem", Toast.LENGTH_SHORT).show();
                    }

                 else if (nome.getText().toString().equals("")){
                    nome.requestFocus();
                    Toast.makeText(CadastroUsuarioActivity.this, "Campo obrigatório", Toast.LENGTH_SHORT).show();
                }
                else if (email.getText().toString().equals("")){
                    email.requestFocus();
                    Toast.makeText(CadastroUsuarioActivity.this, "Campo obrigatório", Toast.LENGTH_SHORT).show();
                }
                 else if (senha1.getText().toString().equals("")){
                    senha1.requestFocus();
                    Toast.makeText(CadastroUsuarioActivity.this, "Campo obrigatório", Toast.LENGTH_SHORT).show();
                }
                else if (senha2.getText().toString().equals("")){
                    senha2.requestFocus();
                    Toast.makeText(CadastroUsuarioActivity.this, "Campo obrigatório", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public void cadastrarUsuario() {

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {//verificando se realmente foi feito o cadastro do usuario
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CadastroUsuarioActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                    String identicadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuario.setId(identicadorUsuario);
                    usuario.salvar();


                    PreferenciasAndroid preferenciasAndroid = new PreferenciasAndroid(CadastroUsuarioActivity.this);
                    preferenciasAndroid.salvarUsuarioPreferencias(identicadorUsuario, usuario.getNome());

                   // autenticacao.signOut();
                    finish();

                } else {
                   //TRATAMENTO DE EXCESSÔES
                    String erroExcessao = "";// a string vai receber as menssagens

                    try {//utlizando a estrutura try catch para tratamento de erros no cadastro
                        throw task.getException(); //throw lança uma excessão, objeto task tem todos os dados referentes ao usuario e get exception recupera a excessão para que seja tratada com o catch

                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcessao = "Digite uma senha com pelo menos 6 caracteres";
                        senha1.requestFocus();

                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcessao = "Email invalido!";
                        email.requestFocus();
                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcessao = "Este email ja esta em uso no app!";
                        email.requestFocus();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroUsuarioActivity.this, "Erro: " + erroExcessao, Toast.LENGTH_LONG).show();

                }

            }
        });
    }
}

