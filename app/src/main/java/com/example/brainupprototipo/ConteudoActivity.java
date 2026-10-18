package com.example.brainupprototipo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.brainupprototipo.conteudos.AlgebraFragment;

public class ConteudoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        InsetsUtil.ativarEdgeToEdge(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteudo);

        if (savedInstanceState != null) return;

        String conteudo = getIntent().getStringExtra("conteudo");
        Fragment fragment = null;

        if (conteudo != null) {
            switch (conteudo) {
                case "algebra": fragment = new AlgebraFragment(); break;
            }
        }

        if (fragment == null) {
            finish();
            return;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
