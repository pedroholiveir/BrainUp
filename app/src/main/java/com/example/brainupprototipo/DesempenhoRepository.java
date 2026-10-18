package com.example.brainupprototipo;

import android.util.Log;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Salva e lê o desempenho do usuário em usuarios/{uid}/desempenho. */
public class DesempenhoRepository {

    /** Recebe o resultado; em caso de erro (offline, sem login) recebe null. */
    public interface Callback<T> {
        void onResultado(T resultado);
    }

    public static class Registro {
        public String conteudo;
        public int acertos, total, pontos, pontosMax, porcentagem;
        public Date data;
    }

    private static FirebaseUser usuario() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    private static final String TAG = "Desempenho";

    public static void salvar(String conteudo, int acertos, int total, int pontos, int pontosMax) {
        salvar(conteudo, acertos, total, pontos, pontosMax, null);
    }

    /** O callback recebe true se o Firestore confirmou a gravação e false se falhou. */
    public static void salvar(String conteudo, int acertos, int total, int pontos, int pontosMax,
                              Callback<Boolean> cb) {
        FirebaseUser u = usuario();
        if (u == null) {
            Log.w(TAG, "Sem usuário logado: desempenho não salvo");
            if (cb != null) cb.onResultado(false);
            return;
        }

        Map<String, Object> d = new HashMap<>();
        d.put("conteudo", conteudo);
        d.put("acertos", acertos);
        d.put("total", total);
        d.put("pontos", pontos);
        d.put("pontosMax", pontosMax);
        d.put("porcentagem", total == 0 ? 0 : Math.round(acertos * 100f / total));
        d.put("data", FieldValue.serverTimestamp());

        FirebaseFirestore.getInstance()
                .collection("usuarios").document(u.getUid())
                .collection("desempenho").add(d)
                .addOnSuccessListener(ref -> {
                    Log.d(TAG, "Desempenho salvo em " + ref.getPath());
                    if (cb != null) cb.onResultado(true);
                })
                .addOnFailureListener(e -> {
                    // Se for PERMISSION_DENIED, as regras do Firestore estão bloqueando a escrita
                    Log.e(TAG, "Falha ao salvar desempenho", e);
                    if (cb != null) cb.onResultado(false);
                });
    }

    public static void listar(int limite, Callback<List<Registro>> cb) {
        FirebaseUser u = usuario();
        if (u == null) {
            cb.onResultado(null);
            return;
        }
        FirebaseFirestore.getInstance()
                .collection("usuarios").document(u.getUid())
                .collection("desempenho")
                .orderBy("data", Query.Direction.DESCENDING)
                .limit(limite)
                .get()
                .addOnSuccessListener(snap -> {
                    List<Registro> lista = new ArrayList<>();
                    for (DocumentSnapshot d : snap.getDocuments()) {
                        Registro r = new Registro();
                        r.conteudo = d.getString("conteudo");
                        r.acertos = inteiro(d.getLong("acertos"));
                        r.total = inteiro(d.getLong("total"));
                        r.pontos = inteiro(d.getLong("pontos"));
                        r.pontosMax = inteiro(d.getLong("pontosMax"));
                        r.porcentagem = inteiro(d.getLong("porcentagem"));
                        Timestamp ts = d.getTimestamp("data");
                        r.data = ts == null ? null : ts.toDate();
                        lista.add(r);
                    }
                    cb.onResultado(lista);
                })
                .addOnFailureListener(e -> cb.onResultado(null));
    }

    public static void carregarNome(Callback<String> cb) {
        FirebaseUser u = usuario();
        if (u == null) {
            cb.onResultado(null);
            return;
        }
        FirebaseFirestore.getInstance().collection("usuarios").document(u.getUid()).get()
                .addOnSuccessListener(d -> cb.onResultado(d.getString("nome")))
                .addOnFailureListener(e -> cb.onResultado(null));
    }

    public static String nomeConteudo(String chave) {
        if (chave == null) return "Exercícios";
        switch (chave) {
            case "algebra": return "Álgebra";
            case "geometria_plana": return "Geometria Plana";
            case "interpretacao_texto": return "Interpretação de Texto";
            case "citologia": return "Citologia";
            case "genetica": return "Genética";
            case "evolucao": return "Evolução";
            case "ecologia": return "Ecologia";
            case "botanica": return "Botânica";
            case "fisiologia_humana": return "Fisiologia Humana";
            case "filosofia_introducao": return "Introdução à Filosofia";
            case "pensadores_classicos": return "Pensadores Clássicos";
            case "etica": return "Ética";
            case "politica": return "Política";
            case "politica_atual": return "Política Atual";
            default: return "Exercícios";
        }
    }

    private static int inteiro(Long v) {
        return v == null ? 0 : v.intValue();
    }
}
