package com.example.brainupprototipo;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.brainupprototipo.conteudos.AlgebraFragment;

public class ConteudoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteudo);

        String conteudo = getIntent().getStringExtra("conteudo");

        Fragment fragment = null;

        switch (conteudo) {
            case "algebra":
                fragment = new AlgebraFragment();
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
