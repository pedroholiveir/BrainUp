package com.example.brainupprototipo;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;

import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ProvasEnemFragment extends Fragment {

    private PhotoView imageView;
    private ImageButton btpdfavancar, btpdfvoltar;

    private PdfRenderer pdfRenderer;
    private PdfRenderer.Page currentPage;
    private int currentPageIndex = 0;

    public ProvasEnemFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_provas_enem, container, false);

        Window window = requireActivity().getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.fundoApp));

        imageView = view.findViewById(R.id.imageViewPdf);
        btpdfavancar = view.findViewById(R.id.btpdfavancar);
        btpdfvoltar = view.findViewById(R.id.btpdfvoltar);

        openPdf();

        btpdfavancar.setOnClickListener(v -> showPage(currentPageIndex + 1));
        btpdfvoltar.setOnClickListener(v -> showPage(currentPageIndex - 1));

        return view;
    }

    private void openPdf() {
        try {
            File file = new File(requireContext().getCacheDir(), "prova1_21.pdf");

            if (!file.exists()) {
                InputStream asset = requireContext().getAssets().open("prova1_21.pdf");
                FileOutputStream output = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = asset.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }

                output.close();
                asset.close();
            }

            ParcelFileDescriptor fileDescriptor =
                    ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);

            pdfRenderer = new PdfRenderer(fileDescriptor);

            showPage(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPage(int index) {
        if (pdfRenderer == null || index < 0 || index >= pdfRenderer.getPageCount()) return;

        if (currentPage != null) {
            currentPage.close();
        }

        currentPage = pdfRenderer.openPage(index);

        Bitmap bitmap = Bitmap.createBitmap(
                currentPage.getWidth(),
                currentPage.getHeight(),
                Bitmap.Config.ARGB_8888
        );

        bitmap.eraseColor(android.graphics.Color.WHITE);

        currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        imageView.setImageBitmap(bitmap);

        currentPageIndex = index;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (currentPage != null) currentPage.close();
            if (pdfRenderer != null) pdfRenderer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}