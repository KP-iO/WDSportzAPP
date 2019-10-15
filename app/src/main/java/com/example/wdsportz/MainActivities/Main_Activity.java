package com.example.wdsportz.MainActivities;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.wdsportz.R;
import com.google.android.material.navigation.NavigationView;


//Create Another Navigation Graph For Signin/ Register /Auth
    //https://developer.android.com/guide/navigation/navigation-design-graph

// Note: Change name of other classes to 'ClassName'_Fragment



public class Main_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Frag_Test_1.OnFragmentInteractionListener, Frag_IniTeamSelection.OnFragmentInteractionListener, Frag_HomePage.OnFragmentInteractionListener {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        NavController navController1 = Navigation.findNavController(this, R.id.NavHostFragment);
        DrawerLayout drawerLayout = findViewById(R.id.MainActivity);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController1.getGraph())
                .setDrawerLayout(drawerLayout)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.NavHostFragment);
        NavigationView navView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navView, navController);

        NavigationUI.setupWithNavController(toolbar, navController);
    }


        @Override
       public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return false;
        }

        @Override
        public void onFragmentInteraction(Uri uri) {
        }

    }

