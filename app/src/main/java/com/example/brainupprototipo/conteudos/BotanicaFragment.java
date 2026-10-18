package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class BotanicaFragment extends PassosFragment {

    public BotanicaFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_botanica;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4, R.id.passo5, R.id.passo6, R.id.passo7, R.id.passo8, R.id.passo9};
    }

    @Override
    protected String arquivoExercicios() {
        return "botanica.json";
    }

    @Override
    protected String idConteudo() {
        return "botanica";
    }
}
