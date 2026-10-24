package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class EducacaoFisicaFragment extends PassosFragment {

    public EducacaoFisicaFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_educacao_fisica;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4};
    }

    @Override
    protected String arquivoExercicios() {
        return "educacao_fisica.json";
    }

    @Override
    protected String idConteudo() {
        return "educacao_fisica";
    }
}
