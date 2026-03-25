package com.example.brainupprototipo;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class jsonutils {

    public static List<Perguntas> carregarPerguntas(Context context, String arquivo){

        List<Perguntas> lista = new ArrayList<>();

        try {

            InputStream is = context.getAssets().open(arquivo);

            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONArray array = new JSONArray(json);

            for(int i = 0; i < array.length(); i++){

                JSONObject obj = array.getJSONObject(i);

                String assunto = obj.getString("assunto");
                String pergunta = obj.getString("pergunta");
                String imagem = obj.getString("imagem");
                String tipo = obj.getString("tipo");
                int resposta = obj.getInt("resposta");

                JSONArray altArray = obj.getJSONArray("alternativas");
                String[] alternativas = new String[altArray.length()];

                for(int j = 0; j < altArray.length(); j++){
                    alternativas[j] = altArray.getString(j);
                }

                Perguntas p = new Perguntas(
                        assunto,
                        pergunta,
                        imagem,
                        tipo,
                        alternativas,
                        resposta
                );

                lista.add(p);
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return lista;
    }
}