package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class FisiologiaHumanaFragment extends PassosFragment {

    public FisiologiaHumanaFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_fisiologia_humana;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4, R.id.passo5, R.id.passo6, R.id.passo7, R.id.passo8, R.id.passo9, R.id.passo10, R.id.passo11, R.id.passo12};
    }

    @Override
    protected String arquivoExercicios() {
        return "fisiologia_humana.json";
    }

    @Override
    protected String idConteudo() {
        return "fisiologia_humana";
    }
}
