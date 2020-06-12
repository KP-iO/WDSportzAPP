package com.example.wdsportz.MainActivities;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.wdsportz.MainFragments.Frag_HomePage;
import com.example.wdsportz.MainFragments.Frag_Test_1;
import com.example.wdsportz.MainFragments.Frag_iniTeamSelect;
import com.example.wdsportz.MainFragments.Frag_socialWebOpen;
import com.example.wdsportz.R;
import com.example.wdsportz.SideNav.Frag_About;
import com.example.wdsportz.SideNav.Frag_Explore;
import com.example.wdsportz.SideNav.Frag_LiveGuide;
import com.example.wdsportz.SideNav.Frag_Notifications;
import com.example.wdsportz.SideNav.Frag_Profile;
import com.example.wdsportz.SideNav.Frag_Settings;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

////////// --------> The below line should get rid of the need to import each fragment.

// Note: Change name of other classes to 'ClassName'_Fragment

//Change majority of 'px' dimensions settings to 'dp'

public class Main_Activity extends AppCompatActivity implements Frag_Test_1.OnFragmentInteractionListener, Frag_iniTeamSelect.OnFragmentInteractionListener, Frag_HomePage.OnFragmentInteractionListener, Frag_Profile.OnFragmentInteractionListener, Frag_Notifications.OnFragmentInteractionListener, Frag_About.OnFragmentInteractionListener, Frag_Explore.OnFragmentInteractionListener, Frag_LiveGuide.OnFragmentInteractionListener, Frag_Settings.OnFragmentInteractionListener, Frag_socialWebOpen.OnFragmentInteractionListener {
    // Collect all listeners in one interface ^^^ and pass through to main activity?

    public Toolbar toolbar;
    public BottomNavigationView bottomNav;
    public NavController navController;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme1);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupNavigation();
    }

    public void setupNavigation() {

        databaseReference = firebaseDatabase.getReference("Users");

            toolbar = findViewById(R.id.main_feed_toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                String email = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();


            }
        }


        navController = Navigation.findNavController(this, R.id.NavHostFragment);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.frag_HomePage,R.id.frag_watch,R.id.frag_scores,R.id.frag_more).build();

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
    public void onFragmentInteraction(Uri uri) {

    }


    }

