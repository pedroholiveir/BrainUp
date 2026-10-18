package com.example.brainupprototipo;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** Envia a redação ao backend (Worker) e devolve o JSON da correção na thread principal. */
public class RedacaoService {

    public interface Callback {
        void onSucesso(String json);
        void onErro(String mensagem);
    }

    private static final ExecutorService EXEC = Executors.newSingleThreadExecutor();
    private static final Handler MAIN = new Handler(Looper.getMainLooper());

    public static final String[] CHAVES = {"c1", "c2", "c3", "c4", "c5"};

    public static void corrigir(String tema, String texto, Callback cb) {
        EXEC.execute(() -> {
            HttpURLConnection c = null;
            try {
                JSONObject body = new JSONObject();
                body.put("tema", tema);
                body.put("texto", texto);

                c = (HttpURLConnection) new URL(RedacaoConfig.URL).openConnection();
                c.setRequestMethod("POST");
                c.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                c.setConnectTimeout(15000);
                c.setReadTimeout(60000);
                c.setDoOutput(true);
                c.getOutputStream().write(body.toString().getBytes(StandardCharsets.UTF_8));

                int code = c.getResponseCode();
                InputStream is = code < 400 ? c.getInputStream() : c.getErrorStream();
                String resp = is == null ? "" : lerTudo(is);

                if (code == 429) {
                    erro(cb, "Muitas correções agora. Tente novamente em alguns minutos.");
                    return;
                }
                if (code >= 400) {
                    erro(cb, "Não foi possível corrigir agora (erro " + code + ").");
                    return;
                }

                JSONObject obj = new JSONObject(limpar(resp));
                normalizar(obj);
                String finalJson = obj.toString();
                MAIN.post(() -> cb.onSucesso(finalJson));

            } catch (JSONException e) {
                erro(cb, "A IA devolveu uma resposta inválida. Tente novamente.");
            } catch (IOException e) {
                erro(cb, "Sem conexão ou o servidor demorou demais. Verifique sua internet.");
            } finally {
                if (c != null) c.disconnect();
            }
        });
    }

    /** Garante notas entre 0 e 200 e recalcula o total (não confia na soma do modelo). */
    static void normalizar(JSONObject obj) throws JSONException {
        int total = 0;
        for (String k : CHAVES) {
            JSONObject comp = obj.getJSONObject(k);
            int nota = Math.max(0, Math.min(200, comp.getInt("nota")));
            comp.put("nota", nota);
            total += nota;
        }
        obj.put("total", total);
    }

    /** Remove cercas ```json caso o modelo as envie mesmo assim. */
    static String limpar(String s) {
        String t = s.trim();
        if (t.startsWith("```")) {
            int ini = t.indexOf('\n');
            int fim = t.lastIndexOf("```");
            if (ini >= 0 && fim > ini) t = t.substring(ini + 1, fim).trim();
        }
        return t;
    }

    public static String lerTudo(InputStream is) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        int n;
        while ((n = is.read(buf)) != -1) out.write(buf, 0, n);
        is.close();
        return new String(out.toByteArray(), StandardCharsets.UTF_8);
    }

    private static void erro(Callback cb, String msg) {
        MAIN.post(() -> cb.onErro(msg));
    }
}
