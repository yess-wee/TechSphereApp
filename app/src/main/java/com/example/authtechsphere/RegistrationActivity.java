package com.example.authtechsphere;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class RegistrationActivity extends AppCompatActivity {


    public static final String RIDER_USERS = "Users";
    public static final String DEFAULT_IMAGE = "default";
    public static final String FILE_SHARED = "fileShared";
    public static final String FILE_RECEIVED = "fileReceived";


    public static final String TAG = "TAG";

    DatabaseReference reference;


    EditText fulln, memail, pwd, cpwd, et_phone;
    Button btnregister;
    TextView btnlogin;

    FirebaseAuth fAuth;
    ProgressBar progressBar;

    FirebaseFirestore fstore;
    //String userID;

    CheckBox isFacultyBox, isStudentBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fulln = findViewById(R.id.fullName);
        memail = findViewById(R.id.Email);
        pwd = findViewById(R.id.password);
        cpwd = findViewById(R.id.confirmPass);
        btnregister = findViewById(R.id.registerBtn);
        btnlogin = findViewById(R.id.createText);
        progressBar = findViewById(R.id.progressBar);
        et_phone = findViewById(R.id.et_phone);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();


        isFacultyBox = findViewById(R.id.isTeacher);
        isStudentBox = findViewById(R.id.isStudent);

        //check box checking logic
        isStudentBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    isFacultyBox.setChecked(false);
                }
            }
        });

        isFacultyBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    isStudentBox.setChecked(false);
                }
            }
        });


        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = memail.getText().toString();
                String password = pwd.getText().toString();
                String fullname = fulln.getText().toString();
                String phone = et_phone.getText().toString();
                String phno = et_phone.getText().toString();
                String confirmpwd = cpwd.getText().toString();

                FirebaseUser firebaseUser = fAuth.getCurrentUser();
//                String userId = firebaseUser.getUid();


                if(!(isFacultyBox.isChecked() || isStudentBox.isChecked())){
                    Toast.makeText(RegistrationActivity.this, "Select login type", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(TextUtils.isEmpty(fullname)){
                    fulln.setError("Full name is required!");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    memail.setError("Email address is required!");
                    return;
                }


                if(TextUtils.isEmpty(password)){
                    pwd.setError("Password is required!");
                    return;
                }

                if(phno.isEmpty() || !password.equals(confirmpwd)){
                    cpwd.setError("Please confirm entered password!");
                    return;
                }

                if(password.length() < 6){
                    pwd.setError("Password should be more than of six characters!");
                    return;
                }

                if(confirmpwd.isEmpty() || !password.equals(confirmpwd)){
                    cpwd.setError("Please confirm entered password!");
                    return;
                }

                if(TextUtils.isEmpty(phone)){
                    pwd.setError("Phone number is required!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser firebaseUser = fAuth.getCurrentUser();
                            if (firebaseUser != null) {
                                String userId = firebaseUser.getUid();

                                Toast.makeText(RegistrationActivity.this, "User Created!", Toast.LENGTH_SHORT).show();
                                startActivitySecond();

                                //verification part

                                FirebaseUser fuser = fAuth.getCurrentUser();
                                fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(RegistrationActivity.this, "Verification Email has been sent!", Toast.LENGTH_SHORT).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: Email not sent!" + e.getMessage());
                                    }
                                });

                                reference = FirebaseDatabase.getInstance().getReference().child(RegistrationActivity.RIDER_USERS).child(fullname);
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("uid", userId);
                                hashMap.put("username", fullname);
                                hashMap.put("email", email);
                                hashMap.put("image", DEFAULT_IMAGE);
                                hashMap.put("phone", phone);
                                hashMap.put("password", password);

                                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            sendUserToMainActivity(fullname);
                                        }
                                    }
                                });
                                reference.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        if (isFacultyBox.isChecked()) {
                                            hashMap.put("isFaculty", "1");
                                        }

                                        if (isStudentBox.isChecked()) {
                                            hashMap.put("isStudent", "1");
                                        }

                                        reference.setValue(hashMap); // Overwrite the data with the updated user object
                                        Log.d(TAG, "onSuccess: Profile created for " + fuser.getUid());
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e.toString());
                                    }
                                });


//                            //made string userID--------------------------------
                                //userID = fAuth.getCurrentUser().getUid();
                                DocumentReference df = fstore.collection("Users").document(fuser.getUid());
                                Map<String, Object> user = new HashMap<>();
                                user.put("fname", fullname);
                                user.put("email", email);
                                user.put("phone no", phone);
                                user.put("pwd", password);
                                df.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void avoid) {
                                        if (isFacultyBox.isChecked()) {
                                            user.put("isFaculty", "1");
                                        }

                                        if (isStudentBox.isChecked()) {
                                            user.put("isStudent", "1");
                                        }

                                        df.set(user);
                                        Log.d(TAG, "onSuccess: Profile created for" + fuser.getUid());
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e.toString());
                                    }
                                });


                            } else {
                                Toast.makeText(RegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    }
                });
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    private void startActivitySecond(){
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void sendUserToMainActivity(String username) {
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        intent.putExtra("username", username);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}