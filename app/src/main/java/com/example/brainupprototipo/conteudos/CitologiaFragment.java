package com.example.brainupprototipo.conteudos;

import android.os.Bundle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import androidx.fragment.app.Fragment;

import com.example.brainupprototipo.R;


public class CitologiaFragment extends Fragment {

    public CitologiaFragment() {}

    private LinearLayout[] passos;
    private int passoAtual = 0;
    private Button btproximo;

    private ImageButton btvoltar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_citologia, container, false);

        passos = new LinearLayout[]{
                view.findViewById(R.id.passo1),
                view.findViewById(R.id.passo2),
                view.findViewById(R.id.passo3),
                view.findViewById(R.id.passo3_1),
                view.findViewById(R.id.passo4),
                view.findViewById(R.id.passo4_1),
                view.findViewById(R.id.passo5)

        };

        passos[0].setVisibility(View.VISIBLE);
        passoAtual = 1;

        btproximo = view.findViewById(R.id.btproximo);
        btvoltar = view.findViewById(R.id.btvoltar);
        //btexercicios = view.findViewById(R.id.btexercicios);

        btvoltar.setVisibility(View.INVISIBLE);
        btproximo.setVisibility(View.VISIBLE);

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

                btvoltar.setVisibility(View.VISIBLE);

                if (passoAtual == passos.length) {
                    btproximo.setVisibility(View.INVISIBLE);
                    //btexercicios.setVisibility(View.VISIBLE);
                }

            }

        });

        btvoltar.setOnClickListener(v -> {

            if (passoAtual > 1) {

                LinearLayout atual = passos[passoAtual - 1];

                atual.animate().alpha(0f).setDuration(200).withEndAction(() -> {
                    atual.setVisibility(View.GONE);
                });

                passoAtual--;

                LinearLayout anterior = passos[passoAtual - 1];

                anterior.setAlpha(0f);
                anterior.setVisibility(View.VISIBLE);
                anterior.animate().alpha(1f).setDuration(300);


                if (passoAtual == 1) {
                    btvoltar.setVisibility(View.INVISIBLE);
                } else {
                    btvoltar.setVisibility(View.VISIBLE);
                }

                btproximo.setVisibility(View.VISIBLE);
                //btexercicios.setVisibility(View.INVISIBLE);
            }
        });

        YouTubePlayerView playerView = view.findViewById(R.id.playerView);

        getLifecycle().addObserver(playerView);

        playerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {

                youTubePlayer.cueVideo("rjH2xzCwNx0", 0);

            }});

        return view;
    }
}