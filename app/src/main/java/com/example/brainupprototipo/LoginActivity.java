package com.example.brainupprototipo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText edEmail, edSenha;
    private Button btEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InsetsUtil.ativarEdgeToEdge(this);
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        // Já logado: vai direto para o app
        if (auth.getCurrentUser() != null) {
            abrirApp();
            return;
        }

        setContentView(R.layout.activity_login);
        InsetsUtil.naActivity(this);

        edEmail = findViewById(R.id.edEmail);
        edSenha = findViewById(R.id.edSenha);
        btEntrar = findViewById(R.id.btEntrar);

        btEntrar.setOnClickListener(v -> entrar());
        findViewById(R.id.btIrCadastro).setOnClickListener(v ->
                startActivity(new Intent(this, CadastroActivity.class)));
    }

    private void entrar() {
        String email = edEmail.getText().toString().trim();
        String senha = edSenha.getText().toString();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        btEntrar.setEnabled(false);

        auth.signInWithEmailAndPassword(email, senha)
                .addOnSuccessListener(r -> abrirApp())
                .addOnFailureListener(e -> {
                    btEntrar.setEnabled(true);
                    String msg;
                    if (e instanceof FirebaseAuthInvalidUserException
                            || e instanceof FirebaseAuthInvalidCredentialsException) {
                        msg = "E-mail ou senha inválidos";
                    } else {
                        msg = "Erro ao entrar. Verifique sua conexão.";
                    }
                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                });
    }

    private void abrirApp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
