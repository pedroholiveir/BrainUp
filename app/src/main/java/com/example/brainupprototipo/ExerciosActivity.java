package com.example.brainupprototipo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ExerciosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        InsetsUtil.ativarEdgeToEdge(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercios);

        if (savedInstanceState != null) return;

        String arquivo = getIntent().getStringExtra("arquivo");
        String conteudo = getIntent().getStringExtra("conteudo");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ExercioFragment.newInstance(
                        arquivo != null ? arquivo : "matematica.json",
                        conteudo != null ? conteudo : "geral"))
                .commit();
    }
}
