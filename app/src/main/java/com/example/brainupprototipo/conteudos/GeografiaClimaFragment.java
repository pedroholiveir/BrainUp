package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class GeografiaClimaFragment extends PassosFragment {

    public GeografiaClimaFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_geografia_clima;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4};
    }

    @Override
    protected String arquivoExercicios() {
        return "geografia_clima.json";
    }

    @Override
    protected String idConteudo() {
        return "geografia_clima";
    }
}
