package com.example.brainupprototipo;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import org.json.JSONArray;
import org.json.JSONObject;

public class RedacaoResultadoFragment extends Fragment {

    private static final String[] NOMES = {
            "C1 — Norma culta",
            "C2 — Tema e tipo textual",
            "C3 — Argumentação",
            "C4 — Coesão",
            "C5 — Proposta de intervenção"
    };

    public RedacaoResultadoFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_redacao_resultado, container, false);

        // Edge-to-edge: setStatusBarColor não tem efeito no Android 15+; usamos as margens do sistema.
        // Sob a barra inferior do app (NavHost) só o topo precisa de margem.
        if (container != null && container.getId() == R.id.nav_host_fragment) {
            InsetsUtil.topo(view);
        } else {
            InsetsUtil.aplicar(view);
        }

        TextView textTema = view.findViewById(R.id.textTemaResultado);
        TextView textTotal = view.findViewById(R.id.textNotaTotal);
        TextView textFortes = view.findViewById(R.id.textFortes);
        TextView textMelhorias = view.findViewById(R.id.textMelhorias);
        LinearLayout lista = view.findViewById(R.id.listaCompetencias);

        // Aberto pelo histórico (Minha Conta) o fragment é um destino do NavHost;
        // aberto logo após a correção ele está dentro da MateriaActivity.
        final boolean viaNav = getParentFragment() instanceof NavHostFragment;

        Button btVoltar = view.findViewById(R.id.btVoltarTexto);
        if (viaNav) btVoltar.setText("Voltar");
        btVoltar.setOnClickListener(v -> {
            if (viaNav) NavHostFragment.findNavController(this).popBackStack();
            else getParentFragmentManager().popBackStack(); // o texto digitado continua nos campos
        });

        // "Ir para o início": dentro da MateriaActivity, fecha a matéria e volta ao app.
        // (popBackStack inclusive só levava de volta ao menu de Português.)
        view.findViewById(R.id.btInicioRedacao).setOnClickListener(v -> {
            if (viaNav) {
                NavHostFragment.findNavController(this).popBackStack(R.id.inicioFragment, false);
            } else if (requireActivity() instanceof MateriaActivity) {
                requireActivity().finish();
            } else {
                getParentFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

        Bundle args = getArguments();
        if (args == null || args.getString("json") == null) {
            Toast.makeText(requireContext(), "Resultado indisponível", Toast.LENGTH_SHORT).show();
            return view;
        }

        textTema.setText(args.getString("tema", ""));

        try {
            JSONObject obj = new JSONObject(args.getString("json"));
            textTotal.setText(obj.getInt("total") + " / 1000");

            for (int i = 0; i < RedacaoService.CHAVES.length; i++) {
                JSONObject comp = obj.getJSONObject(RedacaoService.CHAVES[i]);
                int nota = comp.getInt("nota");

                View item = inflater.inflate(R.layout.item_competencia, lista, false);
                ((TextView) item.findViewById(R.id.textCompTitulo)).setText(NOMES[i]);
                ((TextView) item.findViewById(R.id.textCompNota)).setText(nota + "/200");
                ((TextView) item.findViewById(R.id.textCompComentario))
                        .setText(comp.optString("comentario", ""));

                ProgressBar pb = item.findViewById(R.id.progressComp);
                pb.setProgress(nota);
                int cor = nota >= 160 ? R.color.btselecionada
                        : nota >= 100 ? R.color.azul_app
                        : R.color.portugues;
                pb.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), cor)));

                lista.addView(item);
            }

            textFortes.setText(marcadores(obj.optJSONArray("pontos_fortes")));
            textMelhorias.setText(marcadores(obj.optJSONArray("melhorias")));

        } catch (Exception e) {
            Toast.makeText(requireContext(), "Não foi possível exibir o resultado", Toast.LENGTH_LONG).show();
        }

        return view;
    }

    private String marcadores(JSONArray arr) {
        if (arr == null || arr.length() == 0) return "—";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length(); i++) {
            if (i > 0) sb.append("\n");
            sb.append("• ").append(arr.optString(i));
        }
        return sb.toString();
    }
}
