package com.example.brainupprototipo.materias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.brainupprototipo.InsetsUtil;
import com.example.brainupprototipo.R;
import com.example.brainupprototipo.conteudos.EticaFragment;
import com.example.brainupprototipo.conteudos.FilosofiaIntroducaoFragment;
import com.example.brainupprototipo.conteudos.GeografiaCartografiaFragment;
import com.example.brainupprototipo.conteudos.GeografiaClimaFragment;
import com.example.brainupprototipo.conteudos.GeografiaPopulacaoFragment;
import com.example.brainupprototipo.conteudos.HistoriaColoniaFragment;
import com.example.brainupprototipo.conteudos.HistoriaImperioFragment;
import com.example.brainupprototipo.conteudos.HistoriaMundoFragment;
import com.example.brainupprototipo.conteudos.HistoriaRepublicaFragment;
import com.example.brainupprototipo.conteudos.PensadoresClassicosFragment;
import com.example.brainupprototipo.conteudos.PoliticaAtualFragment;
import com.example.brainupprototipo.conteudos.PoliticaFragment;
import com.example.brainupprototipo.conteudos.SociologiaCulturaFragment;
import com.example.brainupprototipo.conteudos.SociologiaIntroducaoFragment;
import com.example.brainupprototipo.conteudos.SociologiaTrabalhoFragment;

import java.util.function.Supplier;

public class Cienciashumanas extends Fragment {

    public Cienciashumanas() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cienciashumanas, container, false);
        InsetsUtil.aplicar(view);

        alternar(view, R.id.bthistoria, R.id.menuhistoria);
        ligar(view, R.id.bthcolonia, HistoriaColoniaFragment::new);
        ligar(view, R.id.bthimperio, HistoriaImperioFragment::new);
        ligar(view, R.id.bthrepublica, HistoriaRepublicaFragment::new);
        ligar(view, R.id.bthmundo, HistoriaMundoFragment::new);

        alternar(view, R.id.btgeografia, R.id.menugeografia);
        ligar(view, R.id.btgcartografia, GeografiaCartografiaFragment::new);
        ligar(view, R.id.btgclima, GeografiaClimaFragment::new);
        ligar(view, R.id.btgpopulacao, GeografiaPopulacaoFragment::new);

        alternar(view, R.id.btfilosofia, R.id.menufilosofia);
        ligar(view, R.id.btfiloIntro, FilosofiaIntroducaoFragment::new);
        ligar(view, R.id.btpensadores, PensadoresClassicosFragment::new);
        ligar(view, R.id.btetica, EticaFragment::new);
        ligar(view, R.id.btpolitica, PoliticaFragment::new);
        ligar(view, R.id.btpoliticaatual, PoliticaAtualFragment::new);

        alternar(view, R.id.btsociologia, R.id.menusociologia);
        ligar(view, R.id.btsintro, SociologiaIntroducaoFragment::new);
        ligar(view, R.id.btscultura, SociologiaCulturaFragment::new);
        ligar(view, R.id.btstrabalho, SociologiaTrabalhoFragment::new);

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
