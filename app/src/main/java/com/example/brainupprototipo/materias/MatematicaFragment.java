package com.example.brainupprototipo.materias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.brainupprototipo.InsetsUtil;
import com.example.brainupprototipo.R;
import com.example.brainupprototipo.conteudos.MatematicaProporcaoFragment;
import com.example.brainupprototipo.conteudos.MatematicaPorcentagemFragment;
import com.example.brainupprototipo.conteudos.AlgebraFragment;
import com.example.brainupprototipo.conteudos.MatematicaFuncoesFragment;
import com.example.brainupprototipo.conteudos.GeometriaPlanaFragment;
import com.example.brainupprototipo.conteudos.MatematicaEspacialFragment;
import com.example.brainupprototipo.conteudos.MatematicaTrigonometriaFragment;
import com.example.brainupprototipo.conteudos.MatematicaEstatisticaFragment;
import com.example.brainupprototipo.conteudos.MatematicaProbabilidadeFragment;

import java.util.function.Supplier;

public class MatematicaFragment extends Fragment {

    public MatematicaFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matematica, container, false);
        InsetsUtil.aplicar(view);

        ligar(view, R.id.btmproporcao, MatematicaProporcaoFragment::new);
        ligar(view, R.id.btmporcentagem, MatematicaPorcentagemFragment::new);
        ligar(view, R.id.btalgebra, AlgebraFragment::new);
        ligar(view, R.id.btmfuncoes, MatematicaFuncoesFragment::new);
        ligar(view, R.id.btgeometriaplana, GeometriaPlanaFragment::new);
        ligar(view, R.id.btmespacial, MatematicaEspacialFragment::new);
        ligar(view, R.id.btmtrigonometria, MatematicaTrigonometriaFragment::new);
        ligar(view, R.id.btmestatistica, MatematicaEstatisticaFragment::new);
        ligar(view, R.id.btmprobabilidade, MatematicaProbabilidadeFragment::new);
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
