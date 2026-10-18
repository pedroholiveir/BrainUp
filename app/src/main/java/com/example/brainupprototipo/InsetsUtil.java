package com.example.brainupprototipo;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Com targetSdk 35+ o Android desenha o app sob a barra de status e a de navegação.
 * Esta classe liga o modo edge-to-edge e aplica as margens do sistema como padding,
 * para que nada fique escondido atrás das barras.
 */
public final class InsetsUtil {

    private InsetsUtil() {}

    /** Chamar ANTES de super.onCreate(). Ícones escuros (o app usa fundos claros). */
    public static void ativarEdgeToEdge(AppCompatActivity activity) {
        SystemBarStyle estilo = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT);
        EdgeToEdge.enable(activity, estilo, estilo);
    }

    /** Padding no topo e na base (telas sem barra de navegação própria). */
    public static void aplicar(View v) {
        aplicar(v, true, true);
    }

    /** Padding só no topo (telas acima da BottomNavigationView). */
    public static void topo(View v) {
        aplicar(v, true, false);
    }

    /** Aplica no primeiro filho do conteúdo da Activity (ex.: Login e Cadastro). */
    public static void naActivity(Activity activity) {
        View content = activity.findViewById(android.R.id.content);
        if (content instanceof ViewGroup && ((ViewGroup) content).getChildCount() > 0) {
            aplicar(((ViewGroup) content).getChildAt(0));
        }
    }

    private static void aplicar(View v, boolean topo, boolean base) {
        final int l = v.getPaddingLeft();
        final int t = v.getPaddingTop();
        final int r = v.getPaddingRight();
        final int b = v.getPaddingBottom();

        int mascara = WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout();
        if (base) mascara |= WindowInsetsCompat.Type.ime(); // teclado

        final int mascaraFinal = mascara;
        ViewCompat.setOnApplyWindowInsetsListener(v, (view, insets) -> {
            Insets i = insets.getInsets(mascaraFinal);
            view.setPadding(l + i.left, t + (topo ? i.top : 0), r + i.right, b + (base ? i.bottom : 0));
            return insets;
        });
        ViewCompat.requestApplyInsets(v);
    }
}
