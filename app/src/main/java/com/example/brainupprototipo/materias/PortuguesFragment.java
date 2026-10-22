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

public class PortuguesFragment extends Fragment {

    public PortuguesFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portugues, container, false);
        InsetsUtil.aplicar(view);

        view.findViewById(R.id.btinterpretacao).setOnClickListener(v -> abrir(new InterpretacaoTextoFragment()));
        view.findViewById(R.id.btredacao).setOnClickListener(v -> abrir(new RedacaoFragment()));
        return view;
    }

    private void abrir(Fragment fragment) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
