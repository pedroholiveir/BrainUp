package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class SociologiaIntroducaoFragment extends PassosFragment {

    public SociologiaIntroducaoFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_sociologia_introducao;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4, R.id.passo5};
    }

    @Override
    protected String arquivoExercicios() {
        return "sociologia_introducao.json";
    }

    @Override
    protected String idConteudo() {
        return "sociologia_introducao";
    }
}
