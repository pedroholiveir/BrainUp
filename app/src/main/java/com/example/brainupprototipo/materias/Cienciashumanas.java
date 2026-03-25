package com.example.brainupprototipo.materias;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.brainupprototipo.R;

public class Cienciashumanas extends Fragment {

    public Cienciashumanas() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cienciashumanas, container, false);

        Window window = requireActivity().getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.cienciashumanas));

        return view;
    }
}