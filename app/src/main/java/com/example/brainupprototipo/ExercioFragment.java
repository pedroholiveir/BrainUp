package com.example.brainupprototipo;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExercioFragment extends Fragment {

    private static final String ARG_ARQUIVO = "arquivo";
    private static final String ARG_CONTEUDO = "conteudo";
    private static final String KEY_INDICE = "indice";
    private static final String KEY_RESPOSTAS = "respostas";

    /** Chave usada pelo ResultadoFragment para pedir que o exercício recomece. */
    public static final String RESULT_REFAZER = "exercicio_refazer";

    public static ExercioFragment newInstance(String arquivo, String conteudo) {
        ExercioFragment f = new ExercioFragment();
        Bundle b = new Bundle();
        b.putString(ARG_ARQUIVO, arquivo);
        b.putString(ARG_CONTEUDO, conteudo);
        f.setArguments(b);
        return f;
    }

    public ExercioFragment() {}

    private String conteudo;
    private List<Perguntas> perguntas = new ArrayList<>();
    private int[] respostas = new int[0];   // alternativa escolhida por questão (-1 = nenhuma)
    private int indice = 0;
    private boolean finalizando = false;

    private View conteudoExercicio;
    private TextView textVazio, textexercio, textDificuldade, textpergunta;
    private ImageView imagempergunta;
    private LinearLayout opcoes;
    private LinearProgressIndicator progresso;
    private Button btavancar, btanterior;
    private final List<MaterialButton> botoes = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        String arquivo = args != null ? args.getString(ARG_ARQUIVO, "matematica.json") : "matematica.json";
        conteudo = args != null ? args.getString(ARG_CONTEUDO, "geral") : "geral";

        perguntas = jsonutils.carregarPerguntas(requireContext(), arquivo);
        respostas = new int[perguntas.size()];
        Arrays.fill(respostas, -1);

        if (savedInstanceState != null) {
            int[] salvas = savedInstanceState.getIntArray(KEY_RESPOSTAS);
            if (salvas != null && salvas.length == respostas.length) respostas = salvas;
            indice = savedInstanceState.getInt(KEY_INDICE, 0);
            if (indice < 0 || indice >= perguntas.size()) indice = 0;
        }

        // "Refazer" na tela de resultado
        getParentFragmentManager().setFragmentResultListener(RESULT_REFAZER, this, (chave, bundle) -> {
            Arrays.fill(respostas, -1);
            indice = 0;
            finalizando = false;
            if (getView() != null) mostrarPergunta();
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercio, container, false);
        InsetsUtil.aplicar(view);

        conteudoExercicio = view.findViewById(R.id.conteudoExercicio);
        textVazio = view.findViewById(R.id.textVazio);
        textexercio = view.findViewById(R.id.textexercio);
        textDificuldade = view.findViewById(R.id.textDificuldade);
        textpergunta = view.findViewById(R.id.textpergunta);
        imagempergunta = view.findViewById(R.id.imagempergunta);
        opcoes = view.findViewById(R.id.opcoes);
        progresso = view.findViewById(R.id.progresso);
        btavancar = view.findViewById(R.id.btavancar);
        btanterior = view.findViewById(R.id.btanterior);

        if (perguntas.isEmpty()) {
            conteudoExercicio.setVisibility(View.GONE);
            textVazio.setVisibility(View.VISIBLE);
            return view;
        }

        btavancar.setOnClickListener(v -> avancar());
        btanterior.setOnClickListener(v -> {
            if (indice > 0) {
                indice--;
                mostrarPergunta();
            }
        });

        mostrarPergunta();
        return view;
    }

    private void mostrarPergunta() {
        // NÃO usar getView() aqui: este método roda dentro do onCreateView, quando getView() ainda é null.
        if (opcoes == null || perguntas.isEmpty()) return;

        Perguntas p = perguntas.get(indice);
        int total = perguntas.size();

        textexercio.setText("Exercício " + (indice + 1) + " de " + total);
        progresso.setMax(total);
        progresso.setProgressCompat(indice + 1, true);

        textDificuldade.setText(jsonutils.rotulo(p.dificuldade));
        int corDif;
        switch (jsonutils.peso(p.dificuldade)) {
            case 3: corDif = R.color.vermelho_erro; break;
            case 2: corDif = R.color.cienciashumanas; break;
            default: corDif = R.color.verde_acerto;
        }
        textDificuldade.setTextColor(ContextCompat.getColor(requireContext(), corDif));

        textpergunta.setText(p.pergunta);

        // imagem opcional (nome de um drawable); "sem_imagem" esconde o ImageView
        int resId = 0;
        if (p.imagem != null && !p.imagem.isEmpty() && !p.imagem.equals("sem_imagem")) {
            resId = getResources().getIdentifier(p.imagem, "drawable", requireContext().getPackageName());
        }
        if (resId != 0) {
            imagempergunta.setImageResource(resId);
            imagempergunta.setVisibility(View.VISIBLE);
        } else {
            imagempergunta.setVisibility(View.GONE);
        }

        // alternativas geradas dinamicamente (serve para múltipla escolha e verdadeiro/falso)
        opcoes.removeAllViews();
        botoes.clear();
        boolean multipla = "multipla".equals(p.tipo);
        for (int i = 0; i < p.alternativas.length; i++) {
            final int pos = i;
            MaterialButton b = new MaterialButton(requireContext(), null,
                    com.google.android.material.R.attr.materialButtonOutlinedStyle);
            b.setText(multipla ? ((char) ('A' + i)) + ")  " + p.alternativas[i] : p.alternativas[i]);
            b.setAllCaps(false);
            b.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            b.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            b.setCornerRadius(dp(14));
            b.setStrokeWidth(dp(2));
            b.setMinHeight(dp(56));
            b.setInsetTop(0);
            b.setInsetBottom(0);
            b.setOnClickListener(v -> selecionar(pos));

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.topMargin = dp(10);
            opcoes.addView(b, lp);
            botoes.add(b);
        }
        atualizarSelecao();
    }

    private void selecionar(int pos) {
        // tocar de novo na mesma alternativa desmarca
        respostas[indice] = (respostas[indice] == pos) ? -1 : pos;
        atualizarSelecao();
    }

    private void atualizarSelecao() {
        int azul = ContextCompat.getColor(requireContext(), R.color.azul_app);
        int texto = ContextCompat.getColor(requireContext(), R.color.texto_primario);

        for (int i = 0; i < botoes.size(); i++) {
            boolean sel = respostas[indice] == i;
            MaterialButton b = botoes.get(i);
            b.setBackgroundTintList(ColorStateList.valueOf(sel ? azul : Color.WHITE));
            b.setStrokeColor(ColorStateList.valueOf(azul));
            b.setTextColor(texto);
            b.setTypeface(null, sel ? Typeface.BOLD : Typeface.NORMAL);
        }

        boolean ultima = indice == perguntas.size() - 1;
        btavancar.setText(ultima ? "Finalizar" : "Próxima");
        btavancar.setEnabled(respostas[indice] != -1);
        btavancar.setAlpha(respostas[indice] != -1 ? 1f : 0.5f);
        btanterior.setVisibility(indice > 0 ? View.VISIBLE : View.INVISIBLE);
    }

    private void avancar() {
        if (respostas[indice] == -1) return;
        if (indice < perguntas.size() - 1) {
            indice++;
            mostrarPergunta();
        } else {
            abrirResultado();
        }
    }

    private void abrirResultado() {
        if (finalizando) return; // evita salvar duas vezes com toques rápidos
        finalizando = true;

        int acertos = 0, pontos = 0, pontosMax = 0;
        for (int i = 0; i < perguntas.size(); i++) {
            int peso = jsonutils.peso(perguntas.get(i).dificuldade);
            pontosMax += peso;
            if (respostas[i] == perguntas.get(i).resposta) {
                acertos++;
                pontos += peso;
            }
        }
        int total = perguntas.size();

        // contexto da aplicação: o fragment já terá sido trocado quando a resposta chegar
        final Context app = requireContext().getApplicationContext();
        DesempenhoRepository.salvar(conteudo, acertos, total, pontos, pontosMax, sucesso -> {
            if (Boolean.TRUE.equals(sucesso)) {
                Toast.makeText(app, "Desempenho salvo ✓", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(app, "Não foi possível salvar seu desempenho. Verifique a internet e as regras do Firestore.",
                        Toast.LENGTH_LONG).show();
            }
        });

        Bundle b = new Bundle();
        b.putInt("acertos", acertos);
        b.putInt("total", total);
        b.putInt("pontos", pontos);
        b.putInt("pontosMax", pontosMax);
        ResultadoFragment r = new ResultadoFragment();
        r.setArguments(b);

        int containerId = ((ViewGroup) requireView().getParent()).getId();
        getParentFragmentManager().beginTransaction()
                .replace(containerId, r)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        finalizando = false;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDICE, indice);
        outState.putIntArray(KEY_RESPOSTAS, respostas);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        botoes.clear();
    }

    private int dp(int v) {
        return Math.round(v * getResources().getDisplayMetrics().density);
    }
}
