package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class SociologiaTrabalhoFragment extends PassosFragment {

    public SociologiaTrabalhoFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_sociologia_trabalho;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4};
    }

    @Override
    protected String arquivoExercicios() {
        return "sociologia_trabalho.json";
    }

    @Override
    protected String idConteudo() {
        return "sociologia_trabalho";
    }
}
