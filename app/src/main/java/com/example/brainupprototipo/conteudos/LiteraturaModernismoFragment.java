package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class LiteraturaModernismoFragment extends PassosFragment {

    public LiteraturaModernismoFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_literatura_modernismo;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4};
    }

    @Override
    protected String arquivoExercicios() {
        return "literatura_modernismo.json";
    }

    @Override
    protected String idConteudo() {
        return "literatura_modernismo";
    }
}
