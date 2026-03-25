package com.example.brainupprototipo.materias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.fragment.app.Fragment;

import com.example.brainupprototipo.R;

public class PortuguesFragment extends Fragment {

    public PortuguesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_portugues, container, false);

        // 🔥 pegando a Activity e mudando a status bar
        Window window = requireActivity().getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.portugues));

        return view;
    }
}