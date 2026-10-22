package com.example.brainupprototipo.materias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.brainupprototipo.InsetsUtil;
import com.example.brainupprototipo.R;
import com.example.brainupprototipo.conteudos.BotanicaFragment;
import com.example.brainupprototipo.conteudos.CitologiaFragment;
import com.example.brainupprototipo.conteudos.EcologiaFragment;
import com.example.brainupprototipo.conteudos.EvolucaoFragment;
import com.example.brainupprototipo.conteudos.FisicaCinematicaFragment;
import com.example.brainupprototipo.conteudos.FisicaEletricidadeFragment;
import com.example.brainupprototipo.conteudos.FisicaEnergiaFragment;
import com.example.brainupprototipo.conteudos.FisicaNewtonFragment;
import com.example.brainupprototipo.conteudos.FisiologiaHumanaFragment;
import com.example.brainupprototipo.conteudos.GeneticaFragment;
import com.example.brainupprototipo.conteudos.QuimicaAtomisticaFragment;
import com.example.brainupprototipo.conteudos.QuimicaFuncoesFragment;
import com.example.brainupprototipo.conteudos.QuimicaLigacoesFragment;
import com.example.brainupprototipo.conteudos.QuimicaOrganicaFragment;

import java.util.function.Supplier;

public class CienciasNaturezaFragment extends Fragment {

    public CienciasNaturezaFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ciencias_natureza, container, false);
        InsetsUtil.aplicar(view);

        alternar(view, R.id.btfisica, R.id.menufisica);
        ligar(view, R.id.btfcinematica, FisicaCinematicaFragment::new);
        ligar(view, R.id.btfnewton, FisicaNewtonFragment::new);
        ligar(view, R.id.btfenergia, FisicaEnergiaFragment::new);
        ligar(view, R.id.btfeletricidade, FisicaEletricidadeFragment::new);

        alternar(view, R.id.btquimica, R.id.menuquimica);
        ligar(view, R.id.btqatomistica, QuimicaAtomisticaFragment::new);
        ligar(view, R.id.btqligacoes, QuimicaLigacoesFragment::new);
        ligar(view, R.id.btqfuncoes, QuimicaFuncoesFragment::new);
        ligar(view, R.id.btqorganica, QuimicaOrganicaFragment::new);

        alternar(view, R.id.btbiologia, R.id.menubiologia);
        ligar(view, R.id.btcitologia, CitologiaFragment::new);
        ligar(view, R.id.btgenetica, GeneticaFragment::new);
        ligar(view, R.id.btevolucao, EvolucaoFragment::new);
        ligar(view, R.id.btecologia, EcologiaFragment::new);
        ligar(view, R.id.btbotanica, BotanicaFragment::new);
        ligar(view, R.id.btfisiologia, FisiologiaHumanaFragment::new);

        return view;
    }

    /** Abre/fecha o submenu de uma disciplina. */
    private void alternar(View raiz, int botaoId, int menuId) {
        View menu = raiz.findViewById(menuId);
        raiz.findViewById(botaoId).setOnClickListener(v ->
                menu.setVisibility(menu.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));
    }

    private void ligar(View raiz, int botaoId, Supplier<Fragment> fabrica) {
        raiz.findViewById(botaoId).setOnClickListener(v -> abrir(fabrica.get()));
    }

    private void abrir(Fragment fragment) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
