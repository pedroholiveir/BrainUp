package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class LinguagemVariacaoFragment extends PassosFragment {

    public LinguagemVariacaoFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_linguagem_variacao;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4};
    }

    @Override
    protected String arquivoExercicios() {
        return "linguagem_variacao.json";
    }

    @Override
    protected String idConteudo() {
        return "linguagem_variacao";
    }
}
