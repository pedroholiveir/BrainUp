package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class HistoriaMundoFragment extends PassosFragment {

    public HistoriaMundoFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_historia_mundo;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4, R.id.passo5};
    }

    @Override
    protected String arquivoExercicios() {
        return "historia_mundo.json";
    }

    @Override
    protected String idConteudo() {
        return "historia_mundo";
    }
}
