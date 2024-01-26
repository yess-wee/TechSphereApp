package com.example.authtechsphere.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.authtechsphere.LoginActivity;
import com.example.authtechsphere.MainActivity;
import com.example.authtechsphere.R;
import com.google.firebase.auth.FirebaseAuth;

public class Admin extends AppCompatActivity {

    Button btnLogout;

    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnLogout = findViewById(R.id.btn_logout);



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