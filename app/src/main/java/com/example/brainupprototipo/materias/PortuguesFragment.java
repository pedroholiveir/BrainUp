package com.example.brainupprototipo.materias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.brainupprototipo.InsetsUtil;
import com.example.brainupprototipo.R;
import com.example.brainupprototipo.RedacaoFragment;
import com.example.brainupprototipo.conteudos.InterpretacaoTextoFragment;
import com.example.brainupprototipo.conteudos.LinguagemGenerosFragment;
import com.example.brainupprototipo.conteudos.LinguagemFuncoesFragment;
import com.example.brainupprototipo.conteudos.LinguagemFigurasFragment;
import com.example.brainupprototipo.conteudos.LinguagemVariacaoFragment;
import com.example.brainupprototipo.conteudos.LinguagemCoesaoFragment;
import com.example.brainupprototipo.conteudos.LiteraturaEscolasFragment;
import com.example.brainupprototipo.conteudos.LiteraturaModernismoFragment;
import com.example.brainupprototipo.conteudos.ArtesFragment;
import com.example.brainupprototipo.conteudos.EducacaoFisicaFragment;
import com.example.brainupprototipo.conteudos.LinguasEstrangeirasFragment;

import java.util.function.Supplier;

public class PortuguesFragment extends Fragment {

    public PortuguesFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portugues, container, false);
        InsetsUtil.aplicar(view);

        alternar(view, R.id.btlingport, R.id.menulingport);
        ligar(view, R.id.btinterpretacao, InterpretacaoTextoFragment::new);
        ligar(view, R.id.btgeneros, LinguagemGenerosFragment::new);
        ligar(view, R.id.btfuncoes, LinguagemFuncoesFragment::new);
        ligar(view, R.id.btfiguras, LinguagemFigurasFragment::new);
        ligar(view, R.id.btvariacao, LinguagemVariacaoFragment::new);
        ligar(view, R.id.btcoesao, LinguagemCoesaoFragment::new);
        alternar(view, R.id.btliteratura, R.id.menuliteratura);
        ligar(view, R.id.btlitescolas, LiteraturaEscolasFragment::new);
        ligar(view, R.id.btlitmodernismo, LiteraturaModernismoFragment::new);
        ligar(view, R.id.btredacao, RedacaoFragment::new);
        ligar(view, R.id.btartes, ArtesFragment::new);
        ligar(view, R.id.bteducacaofisica, EducacaoFisicaFragment::new);
        ligar(view, R.id.btlinguas, LinguasEstrangeirasFragment::new);
        return view;
    }

    /** Abre/fecha o submenu de uma área. */
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
