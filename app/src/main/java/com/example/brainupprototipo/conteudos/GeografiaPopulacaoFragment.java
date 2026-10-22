package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class GeografiaPopulacaoFragment extends PassosFragment {

    public GeografiaPopulacaoFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_geografia_populacao;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4};
    }

    @Override
    protected String arquivoExercicios() {
        return "geografia_populacao.json";
    }

    @Override
    protected String idConteudo() {
        return "geografia_populacao";
    }
}
