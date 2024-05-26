//package com.example.authtechsphere.dashboard;
//
//import android.content.ActivityNotFoundException;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.authtechsphere.R;
//
//import java.io.File;
//
//public class festiveFragmentActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_festive_fragment);
//
//        Button btnCal = findViewById(R.id.cal);
//
//
//        btnCal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                String pdfFileName = "academic calander.pdf";
////
////                String pdfPath = "assets/6_H.pdf" + pdfFileName;
////
////                try {
////                    Intent intent = new Intent(Intent.ACTION_VIEW);
////                    intent.setDataAndType(Uri.parse(pdfPath), "application/pdf");
////                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////
////                    startActivity(intent);
////                } catch (ActivityNotFoundException e) {
////                    Toast.makeText(festiveFragmentActivity.this, "Not found", Toast.LENGTH_SHORT).show();
////                }
//
//                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/calender.pdf");
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
//                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                startActivity(intent);
//            }
//        });
//
//    }
//}

package com.yash.authtechsphere.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.yash.authtechsphere.R;
import com.yash.authtechsphere.open_pdf_ac_cal;

public class festiveFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festive_fragment);

        Button btnCal = findViewById(R.id.cal);

        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(festiveFragmentActivity.this, open_pdf_ac_cal.class);
                startActivity(i);
            }
        });


//        btnCal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                File file = new File(Environment.getExternalStorageDirectory(), "calender.pdf");
//                Uri fileUri = FileProvider.getUriForFile(festiveFragmentActivity.this, getApplicationContext().getPackageName() + ".provider", file);
//
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(fileUri, "application/pdf");
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Important: Grant temporary read permission
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//
//                try {
//                    startActivity(intent);
//                } catch (ActivityNotFoundException e) {
//                    Toast.makeText(festiveFragmentActivity.this, "No application to view PDF", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
}
