package com.example.brainupprototipo;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;

public final class NavUtil {

    private NavUtil() {}

    /** Navega só se ainda estivermos na tela de origem (evita duplicar telas com toque duplo). */
    public static void ir(Fragment fragment, int origemId, int destinoId) {
        NavController c = NavHostFragment.findNavController(fragment);
        NavDestination atual = c.getCurrentDestination();
        if (atual != null && atual.getId() == origemId) {
            c.navigate(destinoId);
        }
    }
}
