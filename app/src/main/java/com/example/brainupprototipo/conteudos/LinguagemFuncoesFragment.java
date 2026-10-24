package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class LinguagemFuncoesFragment extends PassosFragment {

    public LinguagemFuncoesFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_linguagem_funcoes;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4};
    }

    @Override
    protected String arquivoExercicios() {
        return "linguagem_funcoes.json";
    }

    @Override
    protected String idConteudo() {
        return "linguagem_funcoes";
    }
}
