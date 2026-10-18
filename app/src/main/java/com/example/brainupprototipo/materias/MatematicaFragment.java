package com.example.brainupprototipo.materias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.brainupprototipo.InsetsUtil;
import com.example.brainupprototipo.R;
import com.example.brainupprototipo.conteudos.AlgebraFragment;
import com.example.brainupprototipo.conteudos.GeometriaPlanaFragment;

public class MatematicaFragment extends Fragment {

    public MatematicaFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matematica, container, false);
        InsetsUtil.aplicar(view);

        view.findViewById(R.id.btalgebra).setOnClickListener(v -> abrir(new AlgebraFragment()));
        view.findViewById(R.id.btgeometriaplana).setOnClickListener(v -> abrir(new GeometriaPlanaFragment()));
        return view;
    }

    private void abrir(Fragment fragment) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
