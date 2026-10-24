package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class MatematicaProbabilidadeFragment extends PassosFragment {

    public MatematicaProbabilidadeFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_matematica_probabilidade;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4};
    }

    @Override
    protected String arquivoExercicios() {
        return "matematica_probabilidade.json";
    }

    @Override
    protected String idConteudo() {
        return "matematica_probabilidade";
    }
}
