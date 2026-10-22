package com.example.brainupprototipo;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Acompanha uma gravação no Firestore e SEMPRE responde: sucesso, erro com motivo,
 * ou "demorando" após 10 s.
 *
 * Por que isso é necessário: se o banco Firestore não existe ou a API está desativada, o SDK
 * guarda a gravação e fica tentando, sem chamar o listener de falha. Sem o tempo limite,
 * o app parece salvar, mas nada aparece no console.
 */
public final class SalvarUtil {

    private static final String TAG = "SalvarUtil";
    private static final long TIMEOUT_MS = 10_000;

    public interface Resultado {
        /** ok = gravação confirmada pelo servidor; mensagem = motivo quando ok == false. */
        void aoConcluir(boolean ok, String mensagem);
    }

    private SalvarUtil() {}

    public static void acompanhar(Task<?> task, Resultado cb) {
        final AtomicBoolean respondido = new AtomicBoolean(false);
        final Handler handler = new Handler(Looper.getMainLooper());

        final Runnable timeout = () -> {
            if (respondido.compareAndSet(false, true)) {
                Log.w(TAG, "Sem confirmação do Firestore em " + TIMEOUT_MS + " ms");
                if (cb != null) cb.aoConcluir(false,
                        "o salvamento está demorando. Confira a internet e se o Firestore foi criado no console do Firebase.");
            }
        };
        handler.postDelayed(timeout, TIMEOUT_MS);

        task.addOnSuccessListener(r -> {
            handler.removeCallbacks(timeout);
            if (respondido.compareAndSet(false, true) && cb != null) cb.aoConcluir(true, null);
        }).addOnFailureListener(e -> {
            handler.removeCallbacks(timeout);
            Log.e(TAG, "Falha ao gravar no Firestore", e);
            if (respondido.compareAndSet(false, true) && cb != null) cb.aoConcluir(false, mensagemErro(e));
        });
    }

    public static String mensagemErro(Exception e) {
        if (e instanceof FirebaseFirestoreException) {
            switch (((FirebaseFirestoreException) e).getCode()) {
                case PERMISSION_DENIED:
                    return "sem permissão no Firestore. Publique as regras de segurança no console do Firebase.";
                case NOT_FOUND:
                case FAILED_PRECONDITION:
                    return "o banco Firestore ainda não foi criado neste projeto.";
                case UNAVAILABLE:
                    return "sem conexão com o Firebase.";
                case UNAUTHENTICATED:
                    return "entre na conta novamente.";
                default:
                    break;
            }
        }
        return e.getMessage() != null ? e.getMessage() : "erro desconhecido.";
    }
}
