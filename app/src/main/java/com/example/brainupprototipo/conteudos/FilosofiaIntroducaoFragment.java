package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class FilosofiaIntroducaoFragment extends PassosFragment {

    public FilosofiaIntroducaoFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_filosofia_introducao;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3};
    }

    @Override
    protected String arquivoExercicios() {
        return "filosofia_introducao.json";
    }

    @Override
    protected String idConteudo() {
        return "filosofia_introducao";
    }
}
