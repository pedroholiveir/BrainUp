package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class MatematicaEstatisticaFragment extends PassosFragment {

    public MatematicaEstatisticaFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_matematica_estatistica;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4};
    }

    @Override
    protected String arquivoExercicios() {
        return "matematica_estatistica.json";
    }

    @Override
    protected String idConteudo() {
        return "matematica_estatistica";
    }
}
