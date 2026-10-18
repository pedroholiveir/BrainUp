package com.example.brainupprototipo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ResultadoFragment extends Fragment {

    public ResultadoFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resultado, container, false);
        InsetsUtil.aplicar(view);

        Bundle args = getArguments();
        int acertos = args != null ? args.getInt("acertos") : 0;
        int total = args != null ? args.getInt("total") : 0;
        int pontos = args != null ? args.getInt("pontos") : 0;
        int pontosMax = args != null ? args.getInt("pontosMax") : 0;

        // evita divisão por zero
        int pct = total > 0 ? Math.round(acertos * 100f / total) : 0;

        String emoji, mensagem;
        if (pct >= 70) {
            emoji = "🎉";
            mensagem = "Excelente! Você domina bem esse conteúdo.";
        } else if (pct >= 50) {
            emoji = "👏";
            mensagem = "Bom trabalho! Mais um pouco de prática e você chega lá.";
        } else {
            emoji = "💪";
            mensagem = "Continue praticando! Revise o conteúdo e tente de novo.";
        }

        ((TextView) view.findViewById(R.id.textEmoji)).setText(emoji);
        ((TextView) view.findViewById(R.id.textMensagem)).setText(mensagem);
        ((TextView) view.findViewById(R.id.textPorcentagem)).setText(pct + "%");
        ((TextView) view.findViewById(R.id.textAcertos)).setText("Acertos: " + acertos);
        ((TextView) view.findViewById(R.id.textTotal)).setText("Total: " + total);
        ((TextView) view.findViewById(R.id.textPontos)).setText("Pontuação: " + pontos + " de " + pontosMax);

        // Refazer: volta para o exercício (que recomeça) em vez de criar outro por cima
        view.findViewById(R.id.btRefazer).setOnClickListener(v -> {
            getParentFragmentManager().setFragmentResult(ExercioFragment.RESULT_REFAZER, new Bundle());
            getParentFragmentManager().popBackStack();
        });

        // Início: fecha a Activity de matéria e volta para a MainActivity que já estava aberta
        view.findViewById(R.id.btiniciore).setOnClickListener(v -> requireActivity().finish());

        return view;
    }
}
