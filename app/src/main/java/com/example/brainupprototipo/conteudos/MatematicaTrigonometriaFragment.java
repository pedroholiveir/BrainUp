package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class MatematicaTrigonometriaFragment extends PassosFragment {

    public MatematicaTrigonometriaFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_matematica_trigonometria;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4};
    }

    @Override
    protected String arquivoExercicios() {
        return "matematica_trigonometria.json";
    }

    @Override
    protected String idConteudo() {
        return "matematica_trigonometria";
    }
}
