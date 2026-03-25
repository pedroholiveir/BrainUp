package com.example.brainupprototipo;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.brainupprototipo.materias.CienciasNaturezaFragment;
import com.example.brainupprototipo.materias.Cienciashumanas;
import com.example.brainupprototipo.materias.MatematicaFragment;
import com.example.brainupprototipo.materias.PortuguesFragment;

public class MateriaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia);

        String materia = getIntent().getStringExtra("materia");

        Fragment fragment = null;

        switch (materia) {
            case "matematica":
                fragment = new MatematicaFragment();
                break;
            case "portugues":
                fragment = new PortuguesFragment();
                break;
            case "cienciash":
                fragment = new Cienciashumanas();
                break;
            case "cienciasn":
                fragment = new CienciasNaturezaFragment();
                break;
        }
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }
}
