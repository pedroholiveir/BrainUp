package com.example.brainupprototipo.conteudos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.brainupprototipo.ExercioFragment;
import com.example.brainupprototipo.R;

public class AlgebraFragment extends Fragment {

    private LinearLayout[] passos;
    private int passoAtual = 0;
    private Button btproximo;

    public AlgebraFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_algebra, container, false);

        passos = new LinearLayout[]{
                view.findViewById(R.id.passo1),
                view.findViewById(R.id.passo2),
                view.findViewById(R.id.passo3),
                view.findViewById(R.id.passo4),
                view.findViewById(R.id.passo5),
                view.findViewById(R.id.passo5_1),
                view.findViewById(R.id.passo5_2),
                view.findViewById(R.id.passo5_3),
                view.findViewById(R.id.passo5_4),
                view.findViewById(R.id.passo6),
                view.findViewById(R.id.passo6_1)
        };

        passos[0].setVisibility(View.VISIBLE);
        passoAtual = 1;

        btproximo = view.findViewById(R.id.btproximo);

        btproximo.setOnClickListener(v -> {

            if (passoAtual < passos.length) {

                LinearLayout anterior = passos[passoAtual - 1];

                anterior.animate().alpha(0f).setDuration(200).withEndAction(() -> {
                    anterior.setVisibility(View.GONE);
                });

                LinearLayout passo = passos[passoAtual];

                passo.setAlpha(0f);
                passo.setVisibility(View.VISIBLE);
                passo.animate().alpha(1f).setDuration(300);

                passoAtual++;

                if (passoAtual == passos.length) {
                    btproximo.setText("Exercicios");
                }

            } else {

                // troca para tela de exercícios
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new ExercioFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}