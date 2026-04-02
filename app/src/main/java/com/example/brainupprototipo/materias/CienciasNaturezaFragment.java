package com.example.brainupprototipo.materias;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.brainupprototipo.R;
import com.example.brainupprototipo.conteudos.AlgebraFragment;
import com.example.brainupprototipo.conteudos.CitologiaFragment;


public class CienciasNaturezaFragment extends Fragment {

    public CienciasNaturezaFragment() {
      // Required empty public constructor
    }

    Button btcitologia, btbiologia;

    LinearLayout menubiologia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ciencias_natureza, container, false);

        Window window = requireActivity().getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.cienciasnatureza));

        btbiologia = view.findViewById(R.id.btbiologia);
        menubiologia = view.findViewById(R.id.menubiologia);
        btcitologia = view.findViewById(R.id.btcitologia);

        btbiologia.setOnClickListener(v ->{

            if(menubiologia.getVisibility() == View.GONE) {
                menubiologia.setVisibility(View.VISIBLE);
            }
            else {
                menubiologia.setVisibility(View.GONE);
            }
        });

        btcitologia.setOnClickListener(v ->{

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new CitologiaFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}