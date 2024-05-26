package com.yash.authtechsphere;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.yash.authtechsphere.Admin.Admin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashActivity extends AppCompatActivity {

    TextView appname;
    LottieAnimationView lottie;

    FirebaseUser currentUser;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FirebaseApp.initializeApp(this);

        appname = findViewById(R.id.appname);
        lottie = findViewById(R.id.lottie);

        appname.animate().translationY(-1200).setDuration(2700).setStartDelay(0);
        lottie.animate().translationX(2000).setDuration(2000).setStartDelay(2900);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        fStore = FirebaseFirestore.getInstance();


        ImageView imageView = findViewById(R.id.splash_background);

        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(1000);
        animationDrawable.start();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if (currentUser != null) {
//                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
//                    startActivity(i);
                    checkUserRole(currentUser.getUid());
                } else {
                    // User not authenticated, redirect to LoginActivity
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }

            private void checkUserRole(String userId) {
                DocumentReference userRef = fStore.collection("Users").document(userId);
                userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            if (documentSnapshot.getString("isFaculty") != null) {
                                // User is a faculty, redirect to Admin activity
                                Intent i = new Intent(getApplicationContext(), Admin.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            } else if (documentSnapshot.getString("isStudent") != null) {
                                // User is a student, redirect to MainActivity

                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            } else {
                                // User role not specified, redirect to appropriate screen or handle accordingly
                                Log.d("SplashActivity", "User role not specified");
                                // For example, redirect to a generic home activity
                                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
                            }
                            finish();
                        } else {
                            Log.d("SplashActivity", "User document does not exist");
                            // User document does not exist, handle accordingly (e.g., redirect to LoginActivity)
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("SplashActivity", "Error checking user role: " + e.getMessage());
                        // Handle failure, redirect to appropriate screen (e.g., LoginActivity)
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                });
            }
        },5000);

    }
}