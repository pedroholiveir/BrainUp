package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class HistoriaRepublicaFragment extends PassosFragment {

    public HistoriaRepublicaFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_historia_republica;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4, R.id.passo5};
    }

    @Override
    protected String arquivoExercicios() {
        return "historia_republica.json";
    }

    @Override
    protected String idConteudo() {
        return "historia_republica";
    }
}
