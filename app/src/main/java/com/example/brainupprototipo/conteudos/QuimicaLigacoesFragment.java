package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class QuimicaLigacoesFragment extends PassosFragment {

    public QuimicaLigacoesFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_quimica_ligacoes;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4};
    }

    @Override
    protected String arquivoExercicios() {
        return "quimica_ligacoes.json";
    }

    @Override
    protected String idConteudo() {
        return "quimica_ligacoes";
    }
}
