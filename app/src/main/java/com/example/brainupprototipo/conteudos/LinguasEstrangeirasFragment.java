package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class LinguasEstrangeirasFragment extends PassosFragment {

    public LinguasEstrangeirasFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_linguas_estrangeiras;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4};
    }

    @Override
    protected String arquivoExercicios() {
        return "linguas_estrangeiras.json";
    }

    @Override
    protected String idConteudo() {
        return "linguas_estrangeiras";
    }
}
