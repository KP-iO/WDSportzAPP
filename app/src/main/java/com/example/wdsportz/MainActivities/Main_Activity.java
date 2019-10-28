package com.example.wdsportz.MainActivities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.wdsportz.R;
import com.example.wdsportz.Watch;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

// Note: Change name of other classes to 'ClassName'_Fragment



public class Main_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Frag_Test_1.OnFragmentInteractionListener, Frag_IniTeamSelection.OnFragmentInteractionListener, Frag_HomePage.OnFragmentInteractionListener {


    // Collect all listeners in one interface ^^^ and pass through to main activity?

    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    BottomNavigationView bottomNav;
    Toolbar toolbar;
    NavController navController;

    AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navController = Navigation.findNavController(this, R.id.NavHostFragment);

       toolbar = findViewById(R.id.toolbar1);
       setSupportActionBar(toolbar);

        appBarConfiguration = new AppBarConfiguration.Builder(R.id.frag_HomePage).build();
        NavigationUI.setupActionBarWithNavController(this, navController,appBarConfiguration);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout1);
        //appBarConfiguration = new AppBarConfiguration.Builder(R.id.frag_HomePage).setDrawerLayout(drawerLayout).build();
        //NavigationView navView = findViewById(R.id.nv_View);
        //NavigationUI.setupWithNavController(navView, navController);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navController = Navigation.findNavController(this, R.id.NavHostFragment);
        bottomNav = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(bottomNav, navController);

       visibiltyNavElements(navController);
    }


    public void visibiltyNavElements (NavController navController) {

         navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
             @Override
             public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                 if (destination.getId() == R.id.frag_HomePage) {

                     toolbar.setVisibility(View.VISIBLE);
                     bottomNav.setVisibility(View.VISIBLE);
                     Log.d("Bottom Nav visible Test", ":VISIBLE");

                 }else {

                     toolbar.setVisibility(View.GONE);
                     bottomNav.setVisibility(View.GONE);
                 }

             }

         });

     }

        @Override
       public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return false;
        }

        @Override
        public void onFragmentInteraction(Uri uri) {
        }

    }

