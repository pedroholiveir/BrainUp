package com.example.brainupprototipo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class MateriasFragment extends Fragment {

    public MateriasFragment() {}

    Button btmatematica,btportugues,btcienciash,btcienciasn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_materias, container, false);

        btmatematica = view.findViewById(R.id.btmatematica);

        btmatematica.setOnClickListener(v -> {

                    Intent intent = new Intent(requireActivity(), MateriaActivity.class);
                    intent.putExtra("materia", "matematica");
                    startActivity(intent);

                    requireActivity().overridePendingTransition(
                            R.anim.slide_in_right,
                            R.anim.slide_out_left
                    );
                });

        btportugues = view.findViewById(R.id.btportugues);

        btportugues.setOnClickListener(v -> {

                    Intent intent = new Intent(requireActivity(), MateriaActivity.class);
                    intent.putExtra("materia", "portugues");
                    startActivity(intent);

            requireActivity().overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
            );
                }
        );

        btcienciash = view.findViewById(R.id.btcienciash);

        btcienciash.setOnClickListener(v -> {

                    Intent intent = new Intent(requireActivity(), MateriaActivity.class);
                    intent.putExtra("materia", "cienciash");
                    startActivity(intent);

            requireActivity().overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
            );
                }
        );

        btcienciasn = view.findViewById(R.id.btcienciasn);

        btcienciasn.setOnClickListener(v -> {

                    Intent intent = new Intent(requireActivity(), MateriaActivity.class);
                    intent.putExtra("materia", "cienciasn");
                    startActivity(intent);

            requireActivity().overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
            );
                }
        );



        return view;
    }
}
