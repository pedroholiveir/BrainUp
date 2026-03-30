package com.example.brainupprototipo;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

public class MinhaContaFragment extends Fragment {

    public MinhaContaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_minha_conta, container, false);

        Window window = requireActivity().getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.azul_app));

        LinearLayout btminhaconta = view.findViewById(R.id.btminhaconta);
        LinearLayout btcreditos = view.findViewById(R.id.btcreditos);

        btminhaconta.setOnClickListener(v ->{
                    Intent intent = new Intent(requireActivity(), MateriaActivity.class);
                    intent.putExtra("materia", "cienciasn");
                    startActivity(intent);

                    requireActivity().overridePendingTransition(
                            R.anim.slide_in_right,
                            R.anim.slide_out_left
                    );
                }
        );

        btcreditos.setOnClickListener(v -> {

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new CreditosFragment())
                    .addToBackStack(null)
                    .commit();

        });




        return view;
    }
}
