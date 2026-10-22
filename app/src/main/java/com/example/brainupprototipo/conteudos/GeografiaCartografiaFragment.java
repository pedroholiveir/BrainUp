package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class GeografiaCartografiaFragment extends PassosFragment {

    public GeografiaCartografiaFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_geografia_cartografia;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4, R.id.passo5};
    }

    @Override
    protected String arquivoExercicios() {
        return "geografia_cartografia.json";
    }

    @Override
    protected String idConteudo() {
        return "geografia_cartografia";
    }
}
