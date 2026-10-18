package com.example.brainupprototipo.conteudos;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.brainupprototipo.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class CitologiaFragment extends PassosFragment {

    public CitologiaFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_citologia;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo3_1, R.id.passo4, R.id.passo4_1, R.id.passo5, R.id.passo5_1, R.id.passo5_2, R.id.passo6, R.id.passo6_1, R.id.passo7, R.id.passo8, R.id.passo9, R.id.passo10};
    }

    @Override
    protected String arquivoExercicios() {
        return "citologia.json";
    }

    @Override
    protected String idConteudo() {
        return "citologia";
    }

    @Override
    protected void onViewCriada(@NonNull View view) {
        YouTubePlayerView playerView = view.findViewById(R.id.playerView);
        if (playerView == null) return;
        getViewLifecycleOwner().getLifecycle().addObserver(playerView);

        playerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                // cueVideo prepara o vídeo sem reproduzir automaticamente
                youTubePlayer.cueVideo("rjH2xzCwNx0", 0);
            }
        });
    }
}
