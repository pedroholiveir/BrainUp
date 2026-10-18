package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class EvolucaoFragment extends PassosFragment {

    public EvolucaoFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_evolucao;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4, R.id.passo5, R.id.passo6, R.id.passo7};
    }

    @Override
    protected String arquivoExercicios() {
        return "evolucao.json";
    }

    @Override
    protected String idConteudo() {
        return "evolucao";
    }
}
