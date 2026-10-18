package com.example.brainupprototipo.materias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.brainupprototipo.InsetsUtil;
import com.example.brainupprototipo.R;
import com.example.brainupprototipo.conteudos.EticaFragment;
import com.example.brainupprototipo.conteudos.FilosofiaIntroducaoFragment;
import com.example.brainupprototipo.conteudos.PensadoresClassicosFragment;
import com.example.brainupprototipo.conteudos.PoliticaAtualFragment;
import com.example.brainupprototipo.conteudos.PoliticaFragment;

public class Cienciashumanas extends Fragment {

    public Cienciashumanas() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cienciashumanas, container, false);
        InsetsUtil.aplicar(view);

        // Filosofia abre um submenu com os 5 conteúdos
        View menuFilosofia = view.findViewById(R.id.menufilosofia);
        view.findViewById(R.id.btfilosofia).setOnClickListener(v ->
                menuFilosofia.setVisibility(menuFilosofia.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));

        view.findViewById(R.id.btfiloIntro).setOnClickListener(v -> abrir(new FilosofiaIntroducaoFragment()));
        view.findViewById(R.id.btpensadores).setOnClickListener(v -> abrir(new PensadoresClassicosFragment()));
        view.findViewById(R.id.btetica).setOnClickListener(v -> abrir(new EticaFragment()));
        view.findViewById(R.id.btpolitica).setOnClickListener(v -> abrir(new PoliticaFragment()));
        view.findViewById(R.id.btpoliticaatual).setOnClickListener(v -> abrir(new PoliticaAtualFragment()));

        // ainda sem material enviado
        View.OnClickListener emBreve = v ->
                Toast.makeText(requireContext(), "Conteúdo em breve!", Toast.LENGTH_SHORT).show();
        view.findViewById(R.id.bthistoria).setOnClickListener(emBreve);
        view.findViewById(R.id.btgeografia).setOnClickListener(emBreve);
        view.findViewById(R.id.btsociologia).setOnClickListener(emBreve);

        return view;
    }

    private void abrir(Fragment fragment) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
