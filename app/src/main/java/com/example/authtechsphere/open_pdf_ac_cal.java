package com.example.authtechsphere;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class open_pdf_ac_cal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_pdf);

        PDFView pdfView = findViewById(R.id.calpdf);

        pdfView.fromAsset("ac_cal.pdf").load();

    }
}