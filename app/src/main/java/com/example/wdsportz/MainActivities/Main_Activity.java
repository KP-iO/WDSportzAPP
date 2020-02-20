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
import com.example.wdsportz.MainFragments.Frag_HomePage;
import com.example.wdsportz.MainFragments.Frag_IniTeamSelection;
import com.example.wdsportz.MainFragments.Frag_Test_1;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

// Note: Change name of other classes to 'ClassName'_Fragment

public class Main_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Frag_Test_1.OnFragmentInteractionListener, Frag_IniTeamSelection.OnFragmentInteractionListener, Frag_HomePage.OnFragmentInteractionListener, Frag_Profile.OnFragmentInteractionListener, Frag_Notifications.OnFragmentInteractionListener, Frag_About.OnFragmentInteractionListener, Frag_Explore.OnFragmentInteractionListener, Frag_LiveGuide.OnFragmentInteractionListener, Frag_Settings.OnFragmentInteractionListener {
    // Collect all listeners in one interface ^^^ and pass through to main activity?

    public Toolbar toolbar;
    public BottomNavigationView bottomNav;
    public NavController navController;
    public NavigationView navigationView;
    public DrawerLayout drawerLayout;
    // AppBarConfiguration appBarConfiguration;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    DatabaseReference databaseReference;
    ImageView profileImg;
    TextView profileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        navInformation();

       setupNavigation();

    }

//    private void navInformation() {
//        firebaseAuth = firebaseAuth.getInstance();
//        firebaseUser = firebaseAuth.getCurrentUser();
//        firebaseDatabase = firebaseDatabase.getInstance();
//
//        String uimg = firebaseUser.getPhotoUrl().toString();
//
//        TextView profileText = findViewById(R.id.usernameText);
//        profileImg = findViewById(R.id.usernameImg);
//        Glide.with(this)
//                .load(uimg)
//                .into(profileImg);
//
//
//
//
//
//    }

    public void setupNavigation() {
        databaseReference = firebaseDatabase.getReference("Users");


        toolbar = findViewById(R.id.main_feed_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);


        drawerLayout = findViewById(R.id.main_feed_drawerlayout);
        navigationView = findViewById(R.id.main_feed_nv_View);
        profileName = (TextView)navigationView.getHeaderView(0).findViewById(R.id.usernameText);
        profileImg = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.usernameImg);
        navController = Navigation.findNavController(this, R.id.NavHostFragment);
        navigationView.setNavigationItemSelectedListener(this);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.frag_HomePage,R.id.frag_watch,R.id.scores,R.id.more, R.id.frag_About,R.id.Explore, R.id.LiveGuide, R.id.Notifications, R.id.settingsSegment ).setDrawerLayout(drawerLayout).build();
        //AppBarConfiguration.Builder(navController.getGraph()).build(); <--- Drawer only on homepage (replace above)
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);


        bottomNav = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(bottomNav, navController);

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    //get data


                    String image = "" + ds.child("image").getValue();
                    String name = "" + ds.child("name").getValue();



                    Glide.with(getBaseContext())
                .load(image)
                .into(profileImg);

                    profileName.setText(name);





                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

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

