package com.example.authtechsphere;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.authtechsphere.dashboard.fragment_about;
import com.example.authtechsphere.dashboard.fragment_home;
import com.example.authtechsphere.dashboard.fragment_settings;
import com.example.authtechsphere.dashboard.fragment_share;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseAuth mAuth;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_home()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();

        if(id == R.id.nav_home){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_home()).commit();
        }

        else if(id == R.id.nav_privacy){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_settings()).commit();
        }

        else if(id == R.id.nav_support){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_share()).commit();
        }

        else if(id == R.id.nav_about){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_about()).commit();
        }

        else if (id == R.id.profile) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new user_profile()).commit();

        } else if(id == R.id.nav_logout){
            Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            mAuth.getInstance().signOut();
            this.finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}