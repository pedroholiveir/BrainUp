package com.example.brainupprototipo;

import android.content.Context;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/** Consulta assets/videoaulas.json: conteúdo -> ID do vídeo no YouTube. */
public final class VideoAulas {

    private VideoAulas() {}

    /** Retorna o ID do vídeo ou null se o conteúdo não tiver videoaula cadastrada. */
    public static String idPara(Context context, String conteudo) {
        try (InputStream is = context.getAssets().open("videoaulas.json")) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[2048];
            int n;
            while ((n = is.read(buf)) != -1) out.write(buf, 0, n);
            JSONObject json = new JSONObject(new String(out.toByteArray(), StandardCharsets.UTF_8));
            String id = json.optString(conteudo, "").trim();
            return id.isEmpty() ? null : id;
        } catch (Exception e) {
            return null;
        }
    }
}
