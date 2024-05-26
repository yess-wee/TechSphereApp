package com.yash.authtechsphere.dashboard;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.yash.authtechsphere.R;


public class cpiFragmentActivity extends AppCompatActivity {

    private EditText editTextSessional1, editTextSessional2, editTextSessional3,
            editTextPracticalViva, editTextExpectedCPI;
    private RadioButton radioButton100, radioButton150;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpifragment);

        // Initialize UI elements
        editTextSessional1 = findViewById(R.id.editTextSessional1);
        editTextSessional2 = findViewById(R.id.editTextSessional2);
        editTextSessional3 = findViewById(R.id.editTextSessional3);
        editTextPracticalViva = findViewById(R.id.editTextPracticalViva);
        editTextExpectedCPI = findViewById(R.id.editTextExpectedCPI);
        radioButton100 = findViewById(R.id.radioButton100);
        radioButton150 = findViewById(R.id.radioButton150);
        textViewResult = findViewById(R.id.textViewResult);

        // Set onClick listener for the Submit button
        Button buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateExternalMarks();
            }
        });
    }

    // Function to calculate external marks based on user input
    private void calculateExternalMarks() {
        int sessional1 = Integer.parseInt(editTextSessional1.getText().toString());
        int sessional2 = Integer.parseInt(editTextSessional2.getText().toString());
        int sessional3 = Integer.parseInt(editTextSessional3.getText().toString());
        int practicalViva = Integer.parseInt(editTextPracticalViva.getText().toString());
        float expectedCPI = Float.parseFloat(editTextExpectedCPI.getText().toString());
        int maxMarks = radioButton100.isChecked() ? 100 : 150; // Assuming maximum marks is selected by the user

        // Total sessional marks
        int totalSessionals = sessional1 + sessional2 + sessional3;

        // Calculate total marks based on sessionals
        double totalMarks = ((totalSessionals / 36.0) * maxMarks);

        // Adjust the marks according to practical viva
        double practicalVivaAdjustment = ((practicalViva / 50.0) * 10);
        totalMarks += practicalVivaAdjustment;

        // Adjust the marks according to expected CPI
        double expectedCPIAdjustment = 0;
        if (expectedCPI >= 8.0)
            expectedCPIAdjustment = 10;
        else if (expectedCPI >= 7.0)
            expectedCPIAdjustment = 5;
        totalMarks += expectedCPIAdjustment;

        // Apply the scaling factor to fit within the range of 0 to 60
        double scaleFactor = 0.1181; // Maximum marks + practical viva + expected CPI
        totalMarks *= scaleFactor;

        // Round the total marks to nearest integer
        int externalMarks = (int) Math.round(totalMarks);

        // Display the calculated external marks
        textViewResult.setText("Total external marks out of 60: " + externalMarks);
    }


}