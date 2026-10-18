package com.example.brainupprototipo;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class jsonutils {

    /** Carrega as questões do arquivo em assets, ordenadas da mais fácil para a mais difícil. */
    public static List<Perguntas> carregarPerguntas(Context context, String arquivo) {
        List<Perguntas> lista = new ArrayList<>();
        if (arquivo == null) return lista;

        try (InputStream is = context.getAssets().open(arquivo)) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[4096];
            int n;
            while ((n = is.read(buf)) != -1) out.write(buf, 0, n);

            JSONArray array = new JSONArray(new String(out.toByteArray(), StandardCharsets.UTF_8));

            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject obj = array.getJSONObject(i);
                    JSONArray altArray = obj.getJSONArray("alternativas");
                    String[] alternativas = new String[altArray.length()];
                    for (int j = 0; j < altArray.length(); j++) alternativas[j] = altArray.getString(j);

                    int resposta = obj.getInt("resposta");
                    // ignora questão inválida em vez de quebrar o app
                    if (alternativas.length < 2 || resposta < 0 || resposta >= alternativas.length) continue;

                    lista.add(new Perguntas(
                            obj.optString("assunto", ""),
                            obj.getString("pergunta"),
                            obj.optString("imagem", "sem_imagem"),
                            obj.optString("tipo", "multipla"),
                            obj.optString("dificuldade", "facil"),
                            alternativas,
                            resposta));
                } catch (Exception e) {
                    Log.w("jsonutils", "Questão " + i + " inválida em " + arquivo, e);
                }
            }
        } catch (Exception e) {
            Log.e("jsonutils", "Erro ao ler " + arquivo, e);
        }

        Collections.sort(lista, (a, b) -> Integer.compare(peso(a.dificuldade), peso(b.dificuldade)));
        return lista;
    }

    /** Peso da questão na pontuação: fácil = 1, médio = 2, difícil = 3. */
    public static int peso(String d) {
        if (d == null) return 1;
        switch (d.toLowerCase()) {
            case "dificil":
            case "difícil":
                return 3;
            case "medio":
            case "médio":
                return 2;
            default:
                return 1;
        }
    }

    public static String rotulo(String d) {
        switch (peso(d)) {
            case 3: return "Difícil";
            case 2: return "Médio";
            default: return "Fácil";
        }
    }
}
