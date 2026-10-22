package com.example.brainupprototipo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MinhaContaFragment extends Fragment {

    public MinhaContaFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_minha_conta, container, false);
        InsetsUtil.topo(view.findViewById(R.id.cabecalho)); // o azul do cabeçalho vai até o topo da tela

        TextView textNome = view.findViewById(R.id.textNome);
        TextView textEmail = view.findViewById(R.id.textEmail);
        TextView textSemHistorico = view.findViewById(R.id.textSemHistorico);
        LinearLayout containerHistorico = view.findViewById(R.id.containerHistorico);
        TextView textSemRedacoes = view.findViewById(R.id.textSemRedacoes);
        LinearLayout containerRedacoes = view.findViewById(R.id.containerRedacoes);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && user.getEmail() != null) textEmail.setText(user.getEmail());

        DesempenhoRepository.carregarNome(nome -> {
            if (getView() == null || nome == null || nome.trim().isEmpty()) return;
            textNome.setText(nome);
        });

        DesempenhoRepository.listar(20, lista -> {
            if (getView() == null) return;
            if (lista == null) {
                textSemHistorico.setText("Não foi possível carregar o histórico. Verifique sua conexão.");
                return;
            }
            mostrarHistorico(lista, containerHistorico, textSemHistorico);
        });

        DesempenhoRepository.listarRedacoes(10, lista -> {
            if (getView() == null) return;
            if (lista == null) {
                textSemRedacoes.setText("Não foi possível carregar as redações. Verifique sua conexão.");
                return;
            }
            mostrarRedacoes(lista, containerRedacoes, textSemRedacoes);
        });

        view.findViewById(R.id.btcreditos).setOnClickListener(v ->
                NavUtil.ir(this, R.id.minhaContaFragment, R.id.creditosFragment));

        view.findViewById(R.id.btsair).setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return view;
    }

    private void mostrarHistorico(List<DesempenhoRepository.Registro> lista,
                                  LinearLayout container, TextView textSem) {
        container.removeAllViews();
        if (lista.isEmpty()) {
            textSem.setText("Você ainda não fez nenhum exercício. Escolha uma matéria e comece!");
            return;
        }
        textSem.setVisibility(View.GONE);

        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("pt", "BR"));
        LayoutInflater inflater = LayoutInflater.from(requireContext());

        for (DesempenhoRepository.Registro r : lista) {
            View item = inflater.inflate(R.layout.item_historico, container, false);
            ((TextView) item.findViewById(R.id.textConteudo)).setText(DesempenhoRepository.nomeConteudo(r.conteudo));
            ((TextView) item.findViewById(R.id.textData)).setText(r.data != null ? fmt.format(r.data) : "agora");

            TextView nota = item.findViewById(R.id.textNota);
            nota.setText(r.porcentagem + "%");
            int cor = r.porcentagem >= 70 ? R.color.verde_acerto
                    : r.porcentagem >= 50 ? R.color.cienciashumanas : R.color.vermelho_erro;
            nota.setTextColor(ContextCompat.getColor(requireContext(), cor));

            ((TextView) item.findViewById(R.id.textDetalhe)).setText(r.acertos + "/" + r.total + " acertos");
            container.addView(item);
        }
    }

    /** Redações corrigidas: toque em uma para rever a correção completa. */
    private void mostrarRedacoes(List<DesempenhoRepository.RedacaoRegistro> lista,
                                 LinearLayout container, TextView textSem) {
        container.removeAllViews();
        if (lista.isEmpty()) {
            textSem.setText("Você ainda não corrigiu nenhuma redação. Vá em Matérias > Português > Redação ENEM.");
            return;
        }
        textSem.setVisibility(View.GONE);

        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("pt", "BR"));
        LayoutInflater inflater = LayoutInflater.from(requireContext());

        for (DesempenhoRepository.RedacaoRegistro r : lista) {
            View item = inflater.inflate(R.layout.item_historico, container, false);
            ((TextView) item.findViewById(R.id.textConteudo)).setText(r.tema != null ? r.tema : "Redação");
            ((TextView) item.findViewById(R.id.textData)).setText(r.data != null ? fmt.format(r.data) : "agora");

            TextView nota = item.findViewById(R.id.textNota);
            nota.setText(String.valueOf(r.total));
            int cor = r.total >= 700 ? R.color.verde_acerto
                    : r.total >= 500 ? R.color.cienciashumanas : R.color.vermelho_erro;
            nota.setTextColor(ContextCompat.getColor(requireContext(), cor));

            ((TextView) item.findViewById(R.id.textDetalhe)).setText("de 1000");
            item.setOnClickListener(v -> abrirRedacao(r));
            container.addView(item);
        }
    }

    private void abrirRedacao(DesempenhoRepository.RedacaoRegistro r) {
        if (r.resultado == null) {
            Toast.makeText(requireContext(), "Resultado indisponível para esta redação", Toast.LENGTH_SHORT).show();
            return;
        }
        NavController c = NavHostFragment.findNavController(this);
        NavDestination atual = c.getCurrentDestination();
        if (atual == null || atual.getId() != R.id.minhaContaFragment) return; // evita toque duplo

        Bundle b = new Bundle();
        b.putString("tema", r.tema);
        b.putString("json", r.resultado);
        c.navigate(R.id.redacaoResultadoFragment, b);
    }
}
