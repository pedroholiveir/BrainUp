package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class SociologiaCulturaFragment extends PassosFragment {

    public SociologiaCulturaFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_sociologia_cultura;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4};
    }

    @Override
    protected String arquivoExercicios() {
        return "sociologia_cultura.json";
    }

    @Override
    protected String idConteudo() {
        return "sociologia_cultura";
    }
}
