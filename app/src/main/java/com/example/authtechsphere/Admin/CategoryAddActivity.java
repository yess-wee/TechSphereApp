package com.example.authtechsphere.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.authtechsphere.R;
import com.example.authtechsphere.databinding.ActivityCategoryAddBinding;

public class CategoryAddActivity extends AppCompatActivity {

    ActivityCategoryAddBinding binding;

    Button addCategorybtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);

        addCategorybtn = findViewById(R.id.addCategorybtn);

        addCategorybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CategoryAddActivity.this, Categories.class);
                startActivity(i);

            }
        });

//        binding.addCategorybtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(CategoryAddActivity.this,Categories.class));
//            }
//        });

    }
}