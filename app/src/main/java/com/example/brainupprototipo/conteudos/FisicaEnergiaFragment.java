package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class FisicaEnergiaFragment extends PassosFragment {

    public FisicaEnergiaFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_fisica_energia;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4};
    }

    @Override
    protected String arquivoExercicios() {
        return "fisica_energia.json";
    }

    @Override
    protected String idConteudo() {
        return "fisica_energia";
    }
}
