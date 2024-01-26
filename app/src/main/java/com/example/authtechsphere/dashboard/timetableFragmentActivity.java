package com.example.authtechsphere.dashboard;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.authtechsphere.R;

public class timetableFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_fragment);

        Button btnITH = findViewById(R.id.h_tt);
        Button btnITI = findViewById(R.id.h_tt);

        btnITH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pdfFileName = "6_H.pdf";

                String pdfPath = "assets/6_H.pdf" + pdfFileName;

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(pdfPath), "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(timetableFragmentActivity.this, "Not found", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnITI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pdfFileName = "6_I.pdf";

                String pdfPath = "assets/6_I.pdf" + pdfFileName;

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(pdfPath), "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(timetableFragmentActivity.this, "Not found", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
