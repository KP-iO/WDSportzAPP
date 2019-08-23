package com.example.wdsportz;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    private DrawerLayout drawer;
    private NavigationView.OnNavigationItemSelectedListener NavItemListen;

    MyRecyclerViewAdapter adapter;
    MyRecyclerViewAdapter.ItemClickListener adapterlistening;
    private ArrayList<RecyclerViewModel> ArticleArrayList;

    private BottomNavigationView mMainNav;
    private RelativeLayout mMainFrame;
    private Home homefragment;
    private More moreFragment;
    private Scores scoresFragment;
    private Watch watchFragment;


    // Temporary Feed Population Arrays

    private int[] NewsImages = new int[]{R.drawable.arsenal_team_logo,R.drawable.bowers___fc,R.drawable.barking_fv,R.drawable.chestnut_fv,R.drawable.enfield_fc, R.drawable.ware_fc,R.drawable.folkestone_fc,R.drawable.molesey_fc};
    private String[] NewsTitles = new String[]{"Coventry Dominates in a 4 -1 Win Over Nottingham","New Manager Decided For Bowers FC","Lorem ipsum dolor sit amet."," Consectetur Adipiscing Elit, Sed Do Eiusmod Tempor Incididunt","Sed Do Eiusmod Tempor Incididunt","Duis aute irure dolor in reprehenderit in voluptate"," sunt in culpa qui officia deserunt mollit"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);


        // Toolbar define and set up
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Drawer and Navigation View Define
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Navigation View Listener Setup
        navigationView.setNavigationItemSelectedListener(NavItemListen);

        //
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        RecyclerView recyclerView = findViewById(R.id.Main_feed);
        ArticleArrayList = eatFruits();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, ArticleArrayList);
        adapter.setClickListener(adapterlistening);
        recyclerView.setAdapter(adapter);


        mMainFrame = (RelativeLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.bottom_nav);
        homefragment = new Home();
        moreFragment = new More();
        scoresFragment = new Scores();
        watchFragment = new Watch();


        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Home()).commit();


                        return true;

                    case R.id.nav_more:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new More()).commit();

                        return true;

                    case R.id.nav_score:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Scores()).commit();

                        return true;

                    case R.id.nav_watch:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Watch()).commit();

                        return true;

                    default:
                        return false;
                }
            }
        });

    }


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_message:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SettingsSegment()).commit();
                break;
            case R.id.nav_share:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileSegment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }



    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private ArrayList<RecyclerViewModel> eatFruits() {

        ArrayList<RecyclerViewModel> list = new ArrayList<>();

        for (int i = 0; i < 7 ; i++) {
            RecyclerViewModel FullArticle = new RecyclerViewModel();
            FullArticle.setName(NewsTitles[i]);
            FullArticle.setImage_drawable(NewsImages[i]);
            list.add(FullArticle);
        }

        return list;
    }





}



