package com.example.brainupprototipo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CadastroActivity extends AppCompatActivity {

    private EditText edNome, edEmail, edSenha;
    private Button btCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InsetsUtil.ativarEdgeToEdge(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        InsetsUtil.naActivity(this);

        edNome = findViewById(R.id.edNome);
        edEmail = findViewById(R.id.edEmail);
        edSenha = findViewById(R.id.edSenha);
        btCadastrar = findViewById(R.id.btCadastrar);

        btCadastrar.setOnClickListener(v -> cadastrar());
        findViewById(R.id.btVoltarLogin).setOnClickListener(v -> finish());
    }

    private void cadastrar() {
        String nome = edNome.getText().toString().trim();
        String email = edEmail.getText().toString().trim();
        String senha = edSenha.getText().toString();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "E-mail inválido", Toast.LENGTH_SHORT).show();
            return;
        }
        if (senha.length() < 6) {
            Toast.makeText(this, "A senha precisa ter pelo menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        btCadastrar.setEnabled(false);

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                .addOnSuccessListener(r -> {
                    Map<String, Object> usuario = new HashMap<>();
                    usuario.put("nome", nome);
                    usuario.put("email", email);

                    FirebaseFirestore.getInstance()
                            .collection("usuarios")
                            .document(r.getUser().getUid())
                            .set(usuario)
                            .addOnCompleteListener(t -> abrirApp());
                })
                .addOnFailureListener(e -> {
                    btCadastrar.setEnabled(true);
                    String msg = (e instanceof FirebaseAuthUserCollisionException)
                            ? "Este e-mail já está cadastrado"
                            : "Erro ao cadastrar. Verifique sua conexão.";
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
