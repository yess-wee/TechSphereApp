package com.example.authtechsphere.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.authtechsphere.LoginActivity;
import com.example.authtechsphere.MainActivity;
import com.example.authtechsphere.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;

public class Admin extends AppCompatActivity {

    Button btnLogout,btn_doc_locker,btn_doc_share;

    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        btnLogout = findViewById(R.id.btn_logout);
        btn_doc_locker = findViewById(R.id.btn_doc_locker);
        btn_doc_share = findViewById(R.id.btn_docsh);

        btn_doc_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DocShare.class);
                startActivity(i);
            }
        });

        BiometricManager biometricManager = androidx.biometric.BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {

            // this means we can use biometric sensor
            case BiometricManager.BIOMETRIC_SUCCESS:
               // msgtex.setText("You can use the fingerprint sensor to login");
               // msgtex.setTextColor(Color.parseColor("#fafafa"));
               // Toast.makeText(this, "use the fingerprint sensor for access", Toast.LENGTH_SHORT).show();
                break;

            // this means that the device doesn't have fingerprint sensor
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
               // msgtex.setText("This device doesnot have a fingerprint sensor");
                Toast.makeText(this, "This device doesnot have a fingerprint sensor", Toast.LENGTH_SHORT).show();
                btn_doc_locker.setVisibility(View.GONE);
                break;

            // this means that biometric sensor is not available
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                //msgtex.setText("The biometric sensor is currently unavailable");
                Toast.makeText(this, "The biometric sensor is currently unavailable", Toast.LENGTH_SHORT).show();
                btn_doc_locker.setVisibility(View.GONE);
                break;

            // this means that the device doesn't contain your fingerprint
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                //msgtex.setText("Your device doesn't have fingerprint saved,please check your security settings");
                Toast.makeText(this, "Your device doesn't have fingerprint saved,please check your security settings", Toast.LENGTH_SHORT).show();
                btn_doc_locker.setVisibility(View.GONE);
                break;
        }
        // creating a variable for our Executor
        Executor executor = ContextCompat.getMainExecutor(this);
        // this will give us result of AUTHENTICATION
        final BiometricPrompt biometricPrompt = new BiometricPrompt(Admin.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            // THIS METHOD IS CALLED WHEN AUTHENTICATION IS SUCCESS
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Access!", Toast.LENGTH_SHORT).show();
                btn_doc_locker.setText("CHECK");
                Intent i = new Intent(getApplicationContext(), DocLock.class);
                startActivity(i);
            }
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });
        // creating a variable for our promptInfo
        // BIOMETRIC DIALOG
        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("TechSphere - DocLocker")
                .setDescription("Use your fingerprint for access ").setNegativeButtonText("Cancel").build();
        btn_doc_locker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                biometricPrompt.authenticate(promptInfo);


            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Admin.this, LoginActivity.class);
                startActivity(i);
                fAuth.getInstance().signOut();
                finish();


            }
        });

    }
}