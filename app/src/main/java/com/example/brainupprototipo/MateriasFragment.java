package com.example.brainupprototipo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

public class MateriasFragment extends Fragment {

    public MateriasFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_materias, container, false);
        InsetsUtil.topo(view);

        view.findViewById(R.id.btmatematica).setOnClickListener(v -> abrirMateria("matematica"));
        view.findViewById(R.id.btportugues).setOnClickListener(v -> abrirMateria("portugues"));
        view.findViewById(R.id.btcienciash).setOnClickListener(v -> abrirMateria("cienciash"));
        view.findViewById(R.id.btcienciasn).setOnClickListener(v -> abrirMateria("cienciasn"));
        view.findViewById(R.id.btredacao).setOnClickListener(v -> abrirMateria("redacao"));

        return view;
    }

    private void abrirMateria(String materia) {
        Intent intent = new Intent(requireActivity(), MateriaActivity.class);
        intent.putExtra("materia", materia);
        ActivityOptionsCompat opcoes = ActivityOptionsCompat.makeCustomAnimation(
                requireContext(), R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(intent, opcoes.toBundle());
    }
}
