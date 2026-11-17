package com.example.brainupprototipo;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.FirebaseTooManyRequestsException;
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
        findViewById(R.id.btEsqueciSenha).setOnClickListener(v -> recuperarSenha());
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

    /** Pede o e-mail e envia o link de redefinição de senha do Firebase. */
    private void recuperarSenha() {
        final EditText campo = new EditText(this);
        campo.setHint("Seu e-mail");
        campo.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        campo.setText(edEmail.getText().toString().trim());

        FrameLayout caixa = new FrameLayout(this);
        int p = Math.round(24 * getResources().getDisplayMetrics().density);
        caixa.setPadding(p, 8, p, 0);
        caixa.addView(campo);

        new MaterialAlertDialogBuilder(this)
                .setTitle("Recuperar senha")
                .setMessage("Digite o e-mail da sua conta. Enviaremos um link para você criar uma nova senha.")
                .setView(caixa)
                .setPositiveButton("Enviar link", (d, w) -> enviarLink(campo.getText().toString().trim()))
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void enviarLink(String email) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Digite um e-mail válido", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.setLanguageCode("pt-BR");
        final String aviso = "Se existir uma conta com esse e-mail, enviamos o link. Confira também o spam.";
        auth.sendPasswordResetEmail(email)
                .addOnSuccessListener(v -> Toast.makeText(this, aviso, Toast.LENGTH_LONG).show())
                .addOnFailureListener(e -> {
                    String msg;
                    if (e instanceof FirebaseAuthInvalidUserException) {
                        msg = aviso; // não revela se o e-mail existe
                    } else if (e instanceof FirebaseNetworkException) {
                        msg = "Sem conexão. Verifique sua internet e tente de novo.";
                    } else if (e instanceof FirebaseTooManyRequestsException) {
                        msg = "Muitas tentativas. Aguarde alguns minutos.";
                    } else {
                        msg = "Não foi possível enviar o link agora. Tente novamente.";
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
