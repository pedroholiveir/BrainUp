package com.example.brainupprototipo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

/** Tela reutilizável de videoaula: recebe o ID do vídeo e o título por argumento. */
public class VideoAulaFragment extends Fragment {

    private static final String ARG_VIDEO = "videoId";
    private static final String ARG_TITULO = "titulo";

    public static VideoAulaFragment newInstance(String videoId, String titulo) {
        VideoAulaFragment f = new VideoAulaFragment();
        Bundle b = new Bundle();
        b.putString(ARG_VIDEO, videoId);
        b.putString(ARG_TITULO, titulo);
        f.setArguments(b);
        return f;
    }

    public VideoAulaFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videoaula, container, false);
        InsetsUtil.aplicar(view);

        Bundle args = getArguments();
        final String videoId = args != null ? args.getString(ARG_VIDEO) : null;
        String titulo = args != null ? args.getString(ARG_TITULO) : null;
        ((TextView) view.findViewById(R.id.textTituloVideo)).setText(titulo != null ? titulo : "Videoaula");

        YouTubePlayerView player = view.findViewById(R.id.playerVideo);
        getViewLifecycleOwner().getLifecycle().addObserver(player);
        if (videoId != null) {
            player.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.cueVideo(videoId, 0);
                }
            });
        }
        return view;
    }
}
