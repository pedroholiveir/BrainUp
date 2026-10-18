package com.example.brainupprototipo.conteudos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.brainupprototipo.ExercioFragment;
import com.example.brainupprototipo.InsetsUtil;
import com.example.brainupprototipo.R;
import com.example.brainupprototipo.DesempenhoRepository;
import com.example.brainupprototipo.VideoAulaFragment;
import com.example.brainupprototipo.VideoAulas;
import com.google.android.material.button.MaterialButton;
import android.content.res.ColorStateList;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Base dos conteúdos "passo a passo". Antes cada conteúdo repetia ~100 linhas iguais.
 *
 * Correções em relação à versão anterior:
 *  - toques rápidos em "Próximo" não embaralham mais os passos (flag de animação);
 *  - o passo atual sobrevive à rotação de tela;
 *  - o último passo leva aos exercícios (quando houver) ou conclui o conteúdo.
 */
public abstract class PassosFragment extends Fragment {

    private static final String KEY_PASSO = "passo";

    protected abstract int layoutRes();

    protected abstract int[] passoIds();

    /** Arquivo de questões em assets exibido ao final do conteúdo (ou null). */
    protected String arquivoExercicios() {
        return null;
    }

    /** Identificador usado para salvar o desempenho. */
    protected String idConteudo() {
        return "geral";
    }

    /** Gancho para configurações extras (ex.: player de vídeo). */
    protected void onViewCriada(@NonNull View view) {}

    private LinearLayout[] passos = new LinearLayout[0];
    private int passoAtual = 0;
    private boolean animando = false;
    private Button btproximo;
    private ImageButton btvoltar;
    private ScrollView scrollView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) passoAtual = savedInstanceState.getInt(KEY_PASSO, 0);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(layoutRes(), container, false);
        InsetsUtil.aplicar(view);
        animando = false;

        scrollView = view.findViewById(R.id.meuscrollview);
        btproximo = view.findViewById(R.id.btproximo);
        btvoltar = view.findViewById(R.id.btvoltar);

        List<LinearLayout> lista = new ArrayList<>();
        for (int id : passoIds()) {
            LinearLayout passo = view.findViewById(id);
            if (passo != null) lista.add(passo);
        }
        passos = lista.toArray(new LinearLayout[0]);
        if (passoAtual >= passos.length) passoAtual = 0;

        for (LinearLayout p : passos) {
            p.setVisibility(View.GONE);
            p.setAlpha(1f);
            p.setTranslationY(0f);
        }
        if (passos.length > 0) passos[passoAtual].setVisibility(View.VISIBLE);
        atualizarBotoes();

        btproximo.setOnClickListener(v -> {
            if (passoAtual < passos.length - 1) navegarPara(passoAtual + 1);
            else concluir();
        });
        btvoltar.setOnClickListener(v -> navegarPara(passoAtual - 1));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewCriada(view);
        adicionarBotaoVideo(view);
    }

    /**
     * Se o conteúdo tiver uma videoaula em assets/videoaulas.json, mostra um botão 🎥
     * no centro da barra inferior. (A Citologia já tem o player embutido no primeiro passo.)
     */
    private void adicionarBotaoVideo(@NonNull View view) {
        if (view.findViewById(R.id.playerView) != null) return;
        if (!(view instanceof ConstraintLayout)) return;

        final String videoId = VideoAulas.idPara(requireContext(), idConteudo());
        if (videoId == null) return;

        final float d = getResources().getDisplayMetrics().density;
        int tamanho = Math.round(56 * d);

        MaterialButton b = new MaterialButton(requireContext(), null,
                com.google.android.material.R.attr.materialButtonOutlinedStyle);
        b.setText("🎥");
        b.setTextSize(22);
        b.setPadding(0, 0, 0, 0);
        b.setMinWidth(0);
        b.setMinimumWidth(0);
        b.setInsetTop(0);
        b.setInsetBottom(0);
        b.setCornerRadius(tamanho / 2);
        b.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        b.setStrokeColor(ColorStateList.valueOf(
                androidx.core.content.ContextCompat.getColor(requireContext(), R.color.azul_app)));
        b.setContentDescription("Assistir videoaula");

        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(tamanho, tamanho);
        lp.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        lp.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        lp.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        lp.bottomMargin = Math.round(37 * d);
        ((ConstraintLayout) view).addView(b, lp);

        b.setOnClickListener(v -> {
            int containerId = ((ViewGroup) requireView().getParent()).getId();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(containerId, VideoAulaFragment.newInstance(
                            videoId, DesempenhoRepository.nomeConteudo(idConteudo())))
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void navegarPara(int destino) {
        if (animando || destino < 0 || destino >= passos.length) return;
        animando = true;

        final LinearLayout atual = passos[passoAtual];
        final LinearLayout proximo = passos[destino];
        passoAtual = destino;
        atualizarBotoes();

        atual.animate().alpha(0f).setDuration(150).withEndAction(() -> {
            atual.setVisibility(View.GONE);
            atual.setAlpha(1f);

            proximo.setAlpha(0f);
            proximo.setTranslationY(40f);
            proximo.setVisibility(View.VISIBLE);
            if (scrollView != null) scrollView.scrollTo(0, 0);

            proximo.animate().alpha(1f).translationY(0f).setDuration(250)
                    .withEndAction(() -> animando = false);
        });
    }

    private void atualizarBotoes() {
        boolean ultimo = passoAtual >= passos.length - 1;
        btvoltar.setVisibility(passoAtual == 0 ? View.INVISIBLE : View.VISIBLE);
        btproximo.setVisibility(View.VISIBLE);
        if (ultimo) {
            btproximo.setText(arquivoExercicios() != null ? "Exercícios" : "Concluir");
        } else {
            btproximo.setText("Próximo");
        }
    }

    private void concluir() {
        String arquivo = arquivoExercicios();
        if (arquivo == null) {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
            return;
        }
        int containerId = ((ViewGroup) requireView().getParent()).getId();
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(containerId, ExercioFragment.newInstance(arquivo, idConteudo()))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_PASSO, passoAtual);
    }
}
