package com.yash.authtechsphere;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link User_profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User_profile extends Fragment {

    private TextView profileName, profileEmail, profilePhone, profilePwd;
    private TextView titleName, titleUsername;

    private DatabaseReference userRef;





    private FirebaseFirestore db;
    private FirebaseUser currentUser;



//    private FirebaseAuth mAuth;
//    private FirebaseUser fuser;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public User_profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment user_profile.
     */
    // TODO: Rename and change types and number of parameters
    public static User_profile newInstance(String param1, String param2) {
        User_profile fragment = new User_profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
//
//        mAuth = FirebaseAuth.getInstance();
//        fuser = mAuth.getCurrentUser();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_user_profile, container, false);

        // Find TextView elements in the layout
//        TextView nameTextView = rootView.findViewById(R.id.username);
//        TextView emailTextView = rootView.findViewById(R.id.emailadd);
//
//        // Get the current Firebase user
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        // Check if the user is not null
//        if (user != null) {
//            // Get user's name and email
//            String name = user.getDisplayName();
//            String email = user.getEmail();
//
//            // Set user's name and email to TextView elements
//            if (nameTextView != null) {
//                nameTextView.setText(name != null ? "Name: " + name : "Name not available");
//            } else {
//                Log.e(TAG, "Name TextView not found in layout");
//            }
//
//            if (emailTextView != null) {
//                emailTextView.setText(email != null ? "Email: " + email : "Email not available");
//            } else {
//                Log.e(TAG, "Email TextView not found in layout");
//            }
//        } else {
//            // Handle case when user is null
//            if (nameTextView != null) {
//                nameTextView.setText("User not logged in");
//            }
//            if (emailTextView != null) {
//                emailTextView.setText("User not logged in");
//            }
//        }
//
//        return rootView;

        profileName = rootView.findViewById(R.id.profileName);
        profileEmail = rootView.findViewById(R.id.profileEmail);

        profilePhone = rootView.findViewById(R.id.profilePhone);
        profilePwd = rootView.findViewById(R.id.profilePassword);
        titleName = rootView.findViewById(R.id.titleName);

        showUserData();

//        editProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                passUserData();
//            }
//        });

        return rootView;
    }


    public void showUserData(){

//        Intent intent = getActivity().getIntent();
//
//        String nameUser = intent.getStringExtra("name");
//        String emailUser = intent.getStringExtra("email");
//        String usernameUser = intent.getStringExtra("username");
//        String passwordUser = intent.getStringExtra("password");
//
//        titleName.setText(nameUser);
//        titleUsername.setText(usernameUser);
//        profileName.setText(nameUser);
//        profileEmail.setText(emailUser);
//        profileUsername.setText(usernameUser);
//        profilePassword.setText(passwordUser);


        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            db.collection("Users").document(userId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String name = document.getString("fname");
                        String email = document.getString("email");
                       // String isStudentFaculty = document.getString("isStudent/isFaculty");
                        String phone = document.getString("phone no");
                        String pwd = document.getString("pwd");

                        // Update UI with fetched data
                        titleName.setText(name);
                        profileName.setText(name);
                        profileEmail.setText(email);
                        //profileIsStudentFaculty.setText(isStudentFaculty);
                       profilePhone.setText(phone);
                        profilePwd.setText(pwd);
                    } else {
                        Toast.makeText(getContext(), "User data not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "User not authenticated", Toast.LENGTH_SHORT).show();
        }



    }


}