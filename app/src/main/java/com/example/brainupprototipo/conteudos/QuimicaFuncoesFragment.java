package com.example.brainupprototipo.conteudos;

import com.example.brainupprototipo.R;

public class QuimicaFuncoesFragment extends PassosFragment {

    public QuimicaFuncoesFragment() {}

    @Override
    protected int layoutRes() {
        return R.layout.fragment_quimica_funcoes;
    }

    @Override
    protected int[] passoIds() {
        return new int[]{R.id.passo1, R.id.passo2, R.id.passo3, R.id.passo4};
    }

    @Override
    protected String arquivoExercicios() {
        return "quimica_funcoes.json";
    }

    @Override
    protected String idConteudo() {
        return "quimica_funcoes";
    }
}
