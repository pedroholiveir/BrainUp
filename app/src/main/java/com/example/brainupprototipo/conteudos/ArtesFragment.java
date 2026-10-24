package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class ArtesFragment extends PassosFragment {

    public ArtesFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_artes;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4};
    }

    @Override
    protected String arquivoExercicios() {
        return "artes.json";
    }

    @Override
    protected String idConteudo() {
        return "artes";
    }
}
