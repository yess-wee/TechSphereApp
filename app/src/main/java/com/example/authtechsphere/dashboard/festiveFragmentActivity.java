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

public class festiveFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festive_fragment);

        Button btnCal = findViewById(R.id.cal);


        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pdfFileName = "academic calander.pdf";

                String pdfPath = "assets/6_H.pdf" + pdfFileName;

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(pdfPath), "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(festiveFragmentActivity.this, "Not found", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}