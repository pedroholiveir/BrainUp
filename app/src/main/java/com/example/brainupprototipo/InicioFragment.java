package com.example.brainupprototipo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;

public class InicioFragment extends Fragment {

    public InicioFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        InsetsUtil.topo(view);

        TextView textOla = view.findViewById(R.id.textOla);
        TextView textFeitos = view.findViewById(R.id.textFeitos);
        TextView textMedia = view.findViewById(R.id.textMedia);

        // abre dentro do NavHost (antes o NavHost inteiro era substituído, quebrando a barra inferior)
        view.findViewById(R.id.btprovas).setOnClickListener(v ->
                NavUtil.ir(this, R.id.inicioFragment, R.id.provasEnemFragment));

        DesempenhoRepository.carregarNome(nome -> {
            if (getView() == null || nome == null || nome.trim().isEmpty()) return;
            String primeiro = nome.trim().split("\\s+")[0];
            textOla.setText("Olá, " + primeiro + "!");
        });

        DesempenhoRepository.listar(100, lista -> {
            if (getView() == null || lista == null) return;
            textFeitos.setText(String.valueOf(lista.size()));
            textMedia.setText(media(lista));
        });

        return view;
    }

    private String media(List<DesempenhoRepository.Registro> lista) {
        if (lista.isEmpty()) return "--%";
        int soma = 0;
        for (DesempenhoRepository.Registro r : lista) soma += r.porcentagem;
        return Math.round(soma / (float) lista.size()) + "%";
    }
}
