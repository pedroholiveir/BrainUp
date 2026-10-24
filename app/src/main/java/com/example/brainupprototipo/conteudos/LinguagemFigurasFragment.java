package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class LinguagemFigurasFragment extends PassosFragment {

    public LinguagemFigurasFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_linguagem_figuras;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4};
    }

    @Override
    protected String arquivoExercicios() {
        return "linguagem_figuras.json";
    }

    @Override
    protected String idConteudo() {
        return "linguagem_figuras";
    }
}
