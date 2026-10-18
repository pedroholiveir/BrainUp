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
import com.example.brainupprototipo.conteudos.BotanicaFragment;
import com.example.brainupprototipo.conteudos.CitologiaFragment;
import com.example.brainupprototipo.conteudos.EcologiaFragment;
import com.example.brainupprototipo.conteudos.EvolucaoFragment;
import com.example.brainupprototipo.conteudos.FisiologiaHumanaFragment;
import com.example.brainupprototipo.conteudos.GeneticaFragment;

public class CienciasNaturezaFragment extends Fragment {

    public CienciasNaturezaFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ciencias_natureza, container, false);
        InsetsUtil.aplicar(view);

        View menuBiologia = view.findViewById(R.id.menubiologia);
        view.findViewById(R.id.btbiologia).setOnClickListener(v ->
                menuBiologia.setVisibility(menuBiologia.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));

        view.findViewById(R.id.btcitologia).setOnClickListener(v -> abrir(new CitologiaFragment()));
        view.findViewById(R.id.btgenetica).setOnClickListener(v -> abrir(new GeneticaFragment()));
        view.findViewById(R.id.btevolucao).setOnClickListener(v -> abrir(new EvolucaoFragment()));
        view.findViewById(R.id.btecologia).setOnClickListener(v -> abrir(new EcologiaFragment()));
        view.findViewById(R.id.btbotanica).setOnClickListener(v -> abrir(new BotanicaFragment()));
        view.findViewById(R.id.btfisiologia).setOnClickListener(v -> abrir(new FisiologiaHumanaFragment()));

        View.OnClickListener emBreve = v ->
                Toast.makeText(requireContext(), "Conteúdo em breve!", Toast.LENGTH_SHORT).show();
        view.findViewById(R.id.btfisica).setOnClickListener(emBreve);
        view.findViewById(R.id.btquimica).setOnClickListener(emBreve);

        return view;
    }

    private void abrir(Fragment fragment) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
