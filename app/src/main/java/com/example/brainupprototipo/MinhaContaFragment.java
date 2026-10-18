package com.example.brainupprototipo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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
}
