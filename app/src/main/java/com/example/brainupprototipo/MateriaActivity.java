package com.example.brainupprototipo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.brainupprototipo.materias.CienciasNaturezaFragment;
import com.example.brainupprototipo.materias.Cienciashumanas;
import com.example.brainupprototipo.materias.MatematicaFragment;
import com.example.brainupprototipo.materias.PortuguesFragment;

public class MateriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        InsetsUtil.ativarEdgeToEdge(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia);

        // Após rotação o FragmentManager já restaura o fragment; recriar duplicaria a tela.
        if (savedInstanceState != null) return;

        String materia = getIntent().getStringExtra("materia");
        Fragment fragment = null;

        if (materia != null) {           // extra nulo causava NullPointerException no switch
            switch (materia) {
                case "matematica": fragment = new MatematicaFragment(); break;
                case "portugues":  fragment = new PortuguesFragment(); break;
                case "cienciash":  fragment = new Cienciashumanas(); break;
                case "cienciasn":  fragment = new CienciasNaturezaFragment(); break;
                case "redacao":    fragment = new RedacaoFragment(); break;
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
