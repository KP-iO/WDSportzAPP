package com.example.wdsportz.MainActivities;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;

////////// --------> The below line should get rid of the need to import each fragment.
import com.example.wdsportz.MainFragments.*;
import com.example.wdsportz.MainFragments.Frag_More;
import com.example.wdsportz.MainFragments.Frag_Test_1;
import com.example.wdsportz.MainFragments.Frag_iniTeamSelect;
import com.example.wdsportz.R;
import com.example.wdsportz.SideNav.Frag_About;
import com.example.wdsportz.SideNav.Frag_Explore;
import com.example.wdsportz.SideNav.Frag_LiveGuide;
import com.example.wdsportz.SideNav.Frag_Notifications;
import com.example.wdsportz.SideNav.Frag_Profile;
import com.example.wdsportz.SideNav.Frag_Settings;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// Note: Change name of other classes to 'ClassName'_Fragment

public class Main_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Frag_Test_1.OnFragmentInteractionListener, Frag_iniTeamSelect.OnFragmentInteractionListener, Frag_HomePage.OnFragmentInteractionListener, Frag_Profile.OnFragmentInteractionListener, Frag_Notifications.OnFragmentInteractionListener, Frag_About.OnFragmentInteractionListener, Frag_Explore.OnFragmentInteractionListener, Frag_LiveGuide.OnFragmentInteractionListener, Frag_Settings.OnFragmentInteractionListener, Frag_More.OnFragmentInteractionListener {
    // Collect all listeners in one interface ^^^ and pass through to main activity?

    public Toolbar toolbar;
    public BottomNavigationView bottomNav;
    public NavController navController;
    public NavigationView navigationView;
    public DrawerLayout drawerLayout;
    TextView userName;
    ImageView userImg;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference databaseReference;

    // AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



       setupNavigation();

    }

    public void setupNavigation() {
        databaseReference = firebaseDatabase.getReference("Users");


        toolbar = findViewById(R.id.main_feed_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        navigationView = findViewById(R.id.main_feed_nv_View);
        userName = (TextView)navigationView.getHeaderView(0).findViewById(R.id.usernameText1);
        userImg = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.usernameImg1);

//        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot ds: dataSnapshot.getChildren()){
//                    //get data
//
//
//                    String image = "" + ds.child("image").getValue();
//                    String name = "" + ds.child("name").getValue();
//
//
//
//                    Glide.with(getBaseContext())
//                            .load(image)
//                            .into(userImg);
//
//                    userName.setText(name);
//
//
//
//
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });


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

                userName.setText(name);

                Glide.with(getBaseContext())
                        .load(photoUrl)
                        .into(userImg);


            }
        }





        drawerLayout = findViewById(R.id.main_feed_drawerlayout);
        navigationView = findViewById(R.id.main_feed_nv_View);
        navController = Navigation.findNavController(this, R.id.NavHostFragment);
        navigationView.setNavigationItemSelectedListener(this);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.frag_HomePage,R.id.frag_watch,R.id.scores,R.id.more).setDrawerLayout(drawerLayout).build();
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
                        navController.navigate(R.id.action_global_leagueFragment);
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
            case R.id.frag_Profile:
                navController.navigate(R.id.action_global_frag_Profile);
                break;
            case R.id.Explore:
                navController.navigate(R.id.action_global_Explore);
                break;
            case R.id.LiveGuide:
                navController.navigate(R.id.action_global_LiveGuide);
                break;
            case R.id.Notifications:
                navController.navigate(R.id.action_global_Notifications);
                break;

            case R.id.settingsSegment:
                navController.navigate(R.id.action_global_frag_Settings);
                break;
            case R.id.frag_About:
                navController.navigate(R.id.action_global_frag_About);
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

