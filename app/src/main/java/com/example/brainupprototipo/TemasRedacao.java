package com.example.brainupprototipo;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/** Lê os temas de redação do ENEM em assets/temas_redacao.json. */
public final class TemasRedacao {

    public static class Tema {
        public final int ano;
        public final String titulo;

        Tema(int ano, String titulo) {
            this.ano = ano;
            this.titulo = titulo;
        }

        /** Enunciado no padrão da prova do ENEM. */
        public String proposta() {
            return "Redija um texto dissertativo-argumentativo em modalidade escrita formal da língua "
                    + "portuguesa sobre o tema “" + titulo + "”, apresentando proposta de intervenção "
                    + "que respeite os direitos humanos.";
        }
    }

    private TemasRedacao() {}

    public static List<Tema> carregar(Context context) {
        List<Tema> lista = new ArrayList<>();
        try (InputStream is = context.getAssets().open("temas_redacao.json")) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[4096];
            int n;
            while ((n = is.read(buf)) != -1) out.write(buf, 0, n);
            JSONArray arr = new JSONArray(new String(out.toByteArray(), StandardCharsets.UTF_8));
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                String titulo = o.optString("tema", "").trim();
                if (!titulo.isEmpty()) lista.add(new Tema(o.optInt("ano", 0), titulo));
            }
        } catch (Exception e) {
            Log.e("TemasRedacao", "Erro ao ler temas", e);
        }
        return lista;
    }
}
