package com.example.wdsportz.MainActivities;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.wdsportz.MainFragments.Frag_HomePage;
import com.example.wdsportz.MainFragments.Frag_IniTeamSelection;
import com.example.wdsportz.MainFragments.Frag_Profile;
import com.example.wdsportz.MainFragments.Frag_Test_1;
import com.example.wdsportz.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

// Note: Change name of other classes to 'ClassName'_Fragment

public class Main_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Frag_Test_1.OnFragmentInteractionListener, Frag_IniTeamSelection.OnFragmentInteractionListener, Frag_HomePage.OnFragmentInteractionListener, Frag_Profile.OnFragmentInteractionListener {
    // Collect all listeners in one interface ^^^ and pass through to main activity?

    public Toolbar toolbar;
    public BottomNavigationView bottomNav;
    public NavController navController;
    public NavigationView navigationView;
    public DrawerLayout drawerLayout;
    // AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       setupNavigation();

    }

    public void setupNavigation() {


        toolbar = findViewById(R.id.main_feed_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerLayout = findViewById(R.id.main_feed_drawerlayout);
        navigationView = findViewById(R.id.main_feed_nv_View);
        navController = Navigation.findNavController(this, R.id.NavHostFragment);
        navigationView.setNavigationItemSelectedListener(this);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.frag_HomePage,R.id.frag_watch).setDrawerLayout(drawerLayout).build();
        //AppBarConfiguration.Builder(navController.getGraph()).build(); <--- Drawer only on homepage (replace above)
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);


        bottomNav = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(bottomNav, navController);


        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        navController.navigate(R.id.action_global_frag_HomePage);
                        Log.d("Bottom Nav Test", "nav_home");

                        break;

                    case R.id.nav_watch:
                        Log.d("Bottom Nav Test", "nav_watch");
                        navController.navigate(R.id.action_global_frag_watch);

                        break;


                    case R.id.nav_score:
                        navController.navigate(R.id.action_global_scores);
                        Log.d("Bottom Nav Test", "nav_score");

                        break;

                    case R.id.nav_more:
                        navController.navigate(R.id.action_global_more);
                        Log.d("Bottom Nav Test", "nav_more");

                        break;
                }
                return true;
            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Frag_Profile:
                navController.navigate(R.id.action_frag_HomePage_to_frag_Profile);
                break;

           // case R.id.Frag_Saved:
             //   navController.navigate(R.id.action_frag_HomePage_to_frag_Profile);
              //  break;

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


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    }

