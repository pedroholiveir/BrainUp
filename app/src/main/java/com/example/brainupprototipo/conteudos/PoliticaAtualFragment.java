package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class PoliticaAtualFragment extends PassosFragment {

    public PoliticaAtualFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_politica_atual;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4, R.id.passo5};
    }

    @Override
    protected String arquivoExercicios() {
        return "politica_atual.json";
    }

    @Override
    protected String idConteudo() {
        return "politica_atual";
    }
}
