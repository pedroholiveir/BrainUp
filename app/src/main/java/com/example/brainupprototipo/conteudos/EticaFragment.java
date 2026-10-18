package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class EticaFragment extends PassosFragment {

    public EticaFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_etica;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4, R.id.passo5, R.id.passo6};
    }

    @Override
    protected String arquivoExercicios() {
        return "etica.json";
    }

    @Override
    protected String idConteudo() {
        return "etica";
    }
}
