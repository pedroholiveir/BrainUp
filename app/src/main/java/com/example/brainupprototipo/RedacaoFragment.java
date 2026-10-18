package com.example.brainupprototipo;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RedacaoFragment extends Fragment {

    private EditText edTema, edRedacao;
    private TextView textContador, textAguarde;
    private Button btSortear, btCorrigir;
    private ProgressBar progress;

    private final List<String> temas = new ArrayList<>();
    private final Random random = new Random();

    public RedacaoFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_redacao, container, false);

        // Edge-to-edge: setStatusBarColor não tem efeito no Android 15+. As margens do sistema
        // (barra de status, navegação e teclado) são aplicadas como padding.
        // Se a tela estiver sob a barra inferior do app (NavHost), só o topo precisa de margem.
        if (container != null && container.getId() == R.id.nav_host_fragment) {
            InsetsUtil.topo(view);
        } else {
            InsetsUtil.aplicar(view);
        }

        edTema = view.findViewById(R.id.edTema);
        edRedacao = view.findViewById(R.id.edRedacao);
        textContador = view.findViewById(R.id.textContador);
        textAguarde = view.findViewById(R.id.textAguarde);
        btSortear = view.findViewById(R.id.btSortearTema);
        btCorrigir = view.findViewById(R.id.btCorrigir);
        progress = view.findViewById(R.id.progressCorrecao);

        carregarTemas();

        btSortear.setOnClickListener(v -> {
            if (temas.isEmpty()) {
                Toast.makeText(requireContext(), "Não foi possível carregar os temas", Toast.LENGTH_SHORT).show();
                return;
            }
            edTema.setText(temas.get(random.nextInt(temas.size())));
        });

        edRedacao.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int a, int b, int c) {}
            @Override public void onTextChanged(CharSequence s, int a, int b, int c) {}
            @Override public void afterTextChanged(Editable s) {
                atualizarContador(s.toString());
            }
        });
        // o texto restaurado após girar a tela não dispara o TextWatcher
        atualizarContador(edRedacao.getText().toString());

        btCorrigir.setOnClickListener(v -> corrigir());

        return view;
    }

    private void atualizarContador(String texto) {
        String t = texto.trim();
        int palavras = t.isEmpty() ? 0 : t.split("\\s+").length;
        textContador.setText(palavras + " palavras");
    }

    private void carregarTemas() {
        temas.clear(); // sem isto, cada recriação da tela duplicava os temas
        try {
            String json = RedacaoService.lerTudo(requireContext().getAssets().open("temas_redacao.json"));
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                temas.add(o.getString("tema"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void corrigir() {
        String tema = edTema.getText().toString().trim();
        String texto = edRedacao.getText().toString().trim();

        if (tema.isEmpty()) {
            Toast.makeText(requireContext(), "Informe o tema da redação", Toast.LENGTH_SHORT).show();
            return;
        }
        if (texto.length() < RedacaoConfig.MIN_CHARS) {
            Toast.makeText(requireContext(),
                    "Escreva mais um pouco (mínimo de " + RedacaoConfig.MIN_CHARS + " caracteres)",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (texto.length() > RedacaoConfig.MAX_CHARS) {
            Toast.makeText(requireContext(), "Texto muito longo para a correção", Toast.LENGTH_LONG).show();
            return;
        }

        esconderTeclado();
        setCarregando(true);

        // contexto da aplicação: a correção demora e o usuário pode sair da tela antes de terminar
        final Context app = requireContext().getApplicationContext();

        RedacaoService.corrigir(tema, texto, new RedacaoService.Callback() {
            @Override
            public void onSucesso(String json) {
                // salva SEMPRE, mesmo que o usuário tenha saído da tela (antes o resultado se perdia)
                salvar(app, tema, texto, json);

                if (!isAdded() || getView() == null) return;
                setCarregando(false);
                abrirResultado(tema, json);
            }

            @Override
            public void onErro(String mensagem) {
                if (!isAdded() || getView() == null) return;
                setCarregando(false);
                Toast.makeText(requireContext(), mensagem, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void esconderTeclado() {
        View foco = requireActivity().getCurrentFocus();
        if (foco == null) foco = edRedacao;
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) imm.hideSoftInputFromWindow(foco.getWindowToken(), 0);
        foco.clearFocus();
    }

    private void setCarregando(boolean carregando) {
        btCorrigir.setEnabled(!carregando);
        btSortear.setEnabled(!carregando);
        progress.setVisibility(carregando ? View.VISIBLE : View.GONE);
        textAguarde.setVisibility(carregando ? View.VISIBLE : View.GONE);
    }

    private static final String TAG = "Redacao";

    /** Salva em usuarios/{uid}/redacoes. Todo caminho de falha avisa o usuário e registra no Logcat. */
    private void salvar(Context app, String tema, String texto, String json) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Log.w(TAG, "Sem usuário logado: redação não salva");
            Toast.makeText(app, "Faça login novamente para salvar a redação no histórico", Toast.LENGTH_LONG).show();
            return;
        }

        Map<String, Object> doc = new HashMap<>();
        try {
            JSONObject obj = new JSONObject(json);
            doc.put("tema", tema);
            doc.put("texto", texto);
            doc.put("total", obj.getInt("total"));
            for (String k : RedacaoService.CHAVES) {
                doc.put(k, obj.getJSONObject(k).getInt("nota"));
            }
        } catch (Exception e) {
            Log.e(TAG, "Resposta da correção em formato inesperado", e);
            Toast.makeText(app, "Correção feita, mas o resultado veio em formato inesperado e não foi salvo",
                    Toast.LENGTH_LONG).show();
            return;
        }
        doc.put("resultado", json);
        doc.put("data", FieldValue.serverTimestamp());

        FirebaseFirestore.getInstance()
                .collection("usuarios")
                .document(user.getUid())
                .collection("redacoes")
                .add(doc)
                .addOnSuccessListener(ref -> {
                    Log.d(TAG, "Redação salva em " + ref.getPath());
                    Toast.makeText(app, "Redação salva no histórico ✓", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Falha ao salvar redação", e);
                    String motivo = "";
                    if (e instanceof FirebaseFirestoreException) {
                        FirebaseFirestoreException.Code c = ((FirebaseFirestoreException) e).getCode();
                        if (c == FirebaseFirestoreException.Code.PERMISSION_DENIED) {
                            motivo = " (sem permissão: confira as regras do Firestore)";
                        } else if (c == FirebaseFirestoreException.Code.UNAVAILABLE) {
                            motivo = " (sem conexão)";
                        } else {
                            motivo = " (" + c.name() + ")";
                        }
                    }
                    Toast.makeText(app, "Correção feita, mas não foi possível salvar no histórico" + motivo,
                            Toast.LENGTH_LONG).show();
                });
    }

    private void abrirResultado(String tema, String json) {
        RedacaoResultadoFragment f = new RedacaoResultadoFragment();
        Bundle b = new Bundle();
        b.putString("tema", tema);
        b.putString("json", json);
        f.setArguments(b);

        // Troca no mesmo container em que esta tela está. Antes usava R.id.nav_host_fragment,
        // que não existe dentro da MateriaActivity: o resultado nunca aparecia.
        int containerId = ((ViewGroup) requireView().getParent()).getId();
        getParentFragmentManager()
                .beginTransaction()
                .replace(containerId, f)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}