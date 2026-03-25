package com.example.brainupprototipo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class ResultadoFragment extends Fragment {

    TextView textAcertos, textTotal, textPorcentagem;
    Button btRefazer, btiniciore;

    int acertos, total;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_resultado, container, false);

        textAcertos = view.findViewById(R.id.textAcertos);
        textTotal = view.findViewById(R.id.textTotal);
        textPorcentagem = view.findViewById(R.id.textPorcentagem);
        btRefazer = view.findViewById(R.id.btRefazer);
        btiniciore = view.findViewById(R.id.btiniciore);

        // RECEBER DADOS
        acertos = getArguments().getInt("acertos");
        total = getArguments().getInt("total");

        // MOSTRAR
        textAcertos.setText("Acertos: " + acertos);
        textTotal.setText("Total: " + total);

        int porcentagem = (acertos * 100) / total;
        textPorcentagem.setText(porcentagem + "%");

        // BOTÃO REFAZER
        btRefazer.setOnClickListener(v -> {

            ExercioFragment fragment = new ExercioFragment();

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();

        });

            btiniciore.setOnClickListener(v ->{
                        Intent intent = new Intent(requireActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                    );

        return view;
    }
}
