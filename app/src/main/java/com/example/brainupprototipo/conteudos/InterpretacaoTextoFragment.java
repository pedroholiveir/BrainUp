package com.example.brainupprototipo.conteudos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.fragment.app.Fragment;

import com.example.brainupprototipo.R;

public class InterpretacaoTextoFragment extends Fragment {

    private LinearLayout[] passos;
    private int passoAtual = 0;

    private Button btproximo;
    private ImageButton btvoltar;
    private ScrollView scrollView;

    public InterpretacaoTextoFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_interpretacaotexto, container, false);

        // Scroll
        scrollView = view.findViewById(R.id.meuscrollview);

        // Passos — mesma ordem do XML
        passos = new LinearLayout[]{
                view.findViewById(R.id.passo1),
                view.findViewById(R.id.passo2),
                view.findViewById(R.id.passo3),
                view.findViewById(R.id.passo4),
                view.findViewById(R.id.passo5),
                view.findViewById(R.id.passo6),
                view.findViewById(R.id.passo7)
        };

        // Botões
        btproximo = view.findViewById(R.id.btproximo);
        btvoltar = view.findViewById(R.id.btvoltar);

        // Mostrar primeiro passo
        mostrarPassoInicial();

        // ▶️ BOTÃO PRÓXIMO
        btproximo.setOnClickListener(v -> {
            if (passoAtual < passos.length - 1) {
                navegarPara(passoAtual + 1);
            }
        });

        // ◀️ BOTÃO VOLTAR
        btvoltar.setOnClickListener(v -> {
            if (passoAtual > 0) {
                navegarPara(passoAtual - 1);
            }
        });

        return view;
    }

    private void mostrarPassoInicial() {
        passos[0].setVisibility(View.VISIBLE);
        passoAtual = 0;
        atualizarBotoes();
    }

    private void navegarPara(int destino) {
        LinearLayout atual = passos[passoAtual];
        passoAtual = destino;

        atual.animate()
                .alpha(0f)
                .setDuration(200)
                .withEndAction(() -> {
                    atual.setVisibility(View.GONE);
                    atual.setAlpha(1f);
                    mostrarPasso(passos[passoAtual]);
                });
    }

    private void mostrarPasso(LinearLayout passo) {
        passo.setAlpha(0f);
        passo.setTranslationY(50f);
        passo.setVisibility(View.VISIBLE);

        passo.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(300);

        scrollView.post(() -> scrollView.scrollTo(0, 0));
        atualizarBotoes();
    }

    private void atualizarBotoes() {
        btvoltar.setVisibility(passoAtual == 0 ? View.INVISIBLE : View.VISIBLE);
        btproximo.setVisibility(passoAtual == passos.length - 1 ? View.INVISIBLE : View.VISIBLE);
    }
}