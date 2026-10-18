package com.example.brainupprototipo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProvasEnemFragment extends Fragment {

    private PhotoView imageView;
    private View menu, visualizador;
    private TextView textPagina;
    private ProgressBar progressoPdf;
    private ImageButton btVoltar, btAvancar;
    private OnBackPressedCallback voltarAoMenuCallback;

    // Todo acesso ao PdfRenderer acontece nesta thread única (o PdfRenderer não é thread-safe)
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler principal = new Handler(Looper.getMainLooper());

    private PdfRenderer pdfRenderer;
    private ParcelFileDescriptor descritor;
    private int paginaAtual = 0;
    private int totalPaginas = 0;

    public ProvasEnemFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provas_enem, container, false);
        InsetsUtil.topo(view);

        imageView = view.findViewById(R.id.imageViewPdf);
        menu = view.findViewById(R.id.menupdf);
        visualizador = view.findViewById(R.id.visualizador);
        textPagina = view.findViewById(R.id.textPagina);
        progressoPdf = view.findViewById(R.id.progressoPdf);
        btVoltar = view.findViewById(R.id.btpdfvoltar);
        btAvancar = view.findViewById(R.id.btpdfavancar);

        view.findViewById(R.id.btprova21).setOnClickListener(v -> abrirPdf("prova1_21.pdf"));
        view.findViewById(R.id.btprova22).setOnClickListener(v -> abrirPdf("prova1_22.pdf"));
        btVoltar.setOnClickListener(v -> mudarPagina(-1));
        btAvancar.setOnClickListener(v -> mudarPagina(+1));

        // "voltar" dentro da prova leva ao menu de provas, não para fora da tela
        voltarAoMenuCallback = new OnBackPressedCallback(false) {
            @Override
            public void handleOnBackPressed() {
                voltarAoMenu();
            }
        };
        requireActivity().getOnBackPressedDispatcher()
                .addCallback(getViewLifecycleOwner(), voltarAoMenuCallback);

        return view;
    }

    private void abrirPdf(String nomeArquivo) {
        menu.setVisibility(View.GONE);
        visualizador.setVisibility(View.VISIBLE);
        progressoPdf.setVisibility(View.VISIBLE);
        imageView.setImageDrawable(null);
        textPagina.setText("Carregando...");
        voltarAoMenuCallback.setEnabled(true);

        final Context app = requireContext().getApplicationContext();
        executor.execute(() -> {
            try {
                fecharRenderer(); // fecha a prova anterior antes de abrir outra

                File arquivo = new File(app.getCacheDir(), nomeArquivo);
                if (!arquivo.exists() || arquivo.length() == 0) copiarDoAssets(app, nomeArquivo, arquivo);

                descritor = ParcelFileDescriptor.open(arquivo, ParcelFileDescriptor.MODE_READ_ONLY);
                pdfRenderer = new PdfRenderer(descritor);
                totalPaginas = pdfRenderer.getPageCount();
                paginaAtual = 0;
                renderizar(0);
            } catch (Exception e) {
                principal.post(() -> {
                    if (getView() == null) return;
                    Toast.makeText(requireContext(), "Não foi possível abrir a prova.", Toast.LENGTH_LONG).show();
                    voltarAoMenu();
                });
            }
        });
    }

    private void mudarPagina(int delta) {
        executor.execute(() -> {
            int nova = paginaAtual + delta;
            if (pdfRenderer == null || nova < 0 || nova >= totalPaginas) return;
            paginaAtual = nova;
            renderizar(nova);
        });
    }

    /** Roda na thread do executor. */
    private void renderizar(int indice) {
        if (pdfRenderer == null || indice < 0 || indice >= totalPaginas) return;

        Bitmap bitmap;
        PdfRenderer.Page pagina = pdfRenderer.openPage(indice);
        try {
            // escala maior = texto nítido ao dar zoom (limitada para não estourar a memória)
            float escala = Math.max(1f, Math.min(2.5f, 2400f / pagina.getHeight()));
            bitmap = Bitmap.createBitmap(
                    (int) (pagina.getWidth() * escala),
                    (int) (pagina.getHeight() * escala),
                    Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(Color.WHITE);
            pagina.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        } finally {
            pagina.close();
        }

        final int mostrada = indice;
        final int total = totalPaginas;
        principal.post(() -> {
            if (getView() == null) {
                bitmap.recycle();
                return;
            }
            imageView.setImageBitmap(bitmap);
            progressoPdf.setVisibility(View.GONE);
            textPagina.setText("Página " + (mostrada + 1) + " de " + total);
            btVoltar.setAlpha(mostrada > 0 ? 1f : 0.3f);
            btAvancar.setAlpha(mostrada < total - 1 ? 1f : 0.3f);
        });
    }

    private void voltarAoMenu() {
        if (getView() == null) return;
        visualizador.setVisibility(View.GONE);
        menu.setVisibility(View.VISIBLE);
        voltarAoMenuCallback.setEnabled(false);
        executor.execute(this::fecharRenderer);
    }

    private void copiarDoAssets(Context ctx, String nome, File destino) throws IOException {
        File temp = new File(destino.getParentFile(), nome + ".tmp"); // evita arquivo pela metade
        try (InputStream in = ctx.getAssets().open(nome);
             FileOutputStream out = new FileOutputStream(temp)) {
            byte[] buffer = new byte[8192];
            int n;
            while ((n = in.read(buffer)) > 0) out.write(buffer, 0, n);
        }
        if (!temp.renameTo(destino)) throw new IOException("Falha ao salvar " + nome);
    }

    private void fecharRenderer() {
        try {
            if (pdfRenderer != null) pdfRenderer.close();
            if (descritor != null) descritor.close();
        } catch (Exception ignorada) {
        }
        pdfRenderer = null;
        descritor = null;
        totalPaginas = 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        executor.execute(this::fecharRenderer);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executor.shutdown(); // a tarefa de fechar já enfileirada ainda executa
    }
}
