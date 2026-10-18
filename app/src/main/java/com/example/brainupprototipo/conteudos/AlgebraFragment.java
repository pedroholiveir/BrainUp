package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class AlgebraFragment extends PassosFragment {

    public AlgebraFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_algebra;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4, R.id.passo5, R.id.passo5_1, R.id.passo5_2, R.id.passo5_3, R.id.passo5_4, R.id.passo6, R.id.passo6_1};
    }

    @Override
    protected String arquivoExercicios() {
        return "algebra.json";
    }

    @Override
    protected String idConteudo() {
        return "algebra";
    }
}
