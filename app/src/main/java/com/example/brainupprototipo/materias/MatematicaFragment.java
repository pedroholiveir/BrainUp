package com.example.brainupprototipo.materias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.brainupprototipo.conteudos.AlgebraFragment;
import com.example.brainupprototipo.R;

public class MatematicaFragment extends Fragment {

    public MatematicaFragment() {}

    Button btalgebra;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_matematica, container, false);

        btalgebra = view.findViewById(R.id.btalgebra);

        btalgebra.setOnClickListener(v -> {

                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, new AlgebraFragment())
                            .addToBackStack(null)
                            .commit();
                }
                );

        Window window = requireActivity().getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.azul_app));

        return view;
    }
}
