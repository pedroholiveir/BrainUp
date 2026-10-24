package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class LinguagemGenerosFragment extends PassosFragment {

    public LinguagemGenerosFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_linguagem_generos;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4};
    }

    @Override
    protected String arquivoExercicios() {
        return "linguagem_generos.json";
    }

    @Override
    protected String idConteudo() {
        return "linguagem_generos";
    }
}
