package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class QuimicaOrganicaFragment extends PassosFragment {

    public QuimicaOrganicaFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_quimica_organica;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4};
    }

    @Override
    protected String arquivoExercicios() {
        return "quimica_organica.json";
    }

    @Override
    protected String idConteudo() {
        return "quimica_organica";
    }
}
