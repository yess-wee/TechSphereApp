package com.example.authtechsphere.dashboard;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.authtechsphere.R;

import java.util.ArrayList;
import java.util.List;


public class cpiFragmentActivity extends AppCompatActivity {

    private EditText[] gradeEditTexts;
    private EditText[] creditEditTexts;
    private TextView textViewCPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpifragment);

        gradeEditTexts = new EditText[]{
                findViewById(R.id.g1),
                findViewById(R.id.g2),
                findViewById(R.id.g3),
                findViewById(R.id.g4),
                findViewById(R.id.g5),
                findViewById(R.id.g6),
        };

        creditEditTexts = new EditText[]{
                findViewById(R.id.c1),
                findViewById(R.id.c2),
                findViewById(R.id.c3),
                findViewById(R.id.c4),
                findViewById(R.id.c5),
                findViewById(R.id.c6),
        };

        Button buttonCalculate = findViewById(R.id.btn_calculate);
        textViewCPI = findViewById(R.id.result);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCPI();
            }
        });
    }

    private void calculateCPI() {
        List<Double> grades = new ArrayList<>();
        List<Integer> credits = new ArrayList<>();


        for (EditText editText : gradeEditTexts) {
            String gradeText = editText.getText().toString().trim();
            if (!gradeText.isEmpty()) {
                double grade = Double.parseDouble(gradeText);
                grades.add(grade);
            }
        }

        for (EditText editText : creditEditTexts) {
            String creditText = editText.getText().toString().trim();
            if (!creditText.isEmpty()) {
                int credit = Integer.parseInt(creditText);
                credits.add(credit);
            }
        }


        double cpi = calculateCPI(grades, credits);

        textViewCPI.setVisibility(View.VISIBLE);
        textViewCPI.setText(String.format("CPI: %.2f", cpi));
    }

    private double calculateCPI(List<Double> grades, List<Integer> credits) {

        double totalGradeCredits = 0;
        int totalCredits = 0;
        int result = 0;

        for (int i = 0; i < grades.size(); i++) {
            totalGradeCredits += grades.get(i) * credits.get(i);
            totalCredits += credits.get(i);
            result = (int) (totalGradeCredits/totalCredits);
        }

        return result;
    }
}



