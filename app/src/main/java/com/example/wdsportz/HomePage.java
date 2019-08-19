package com.example.wdsportz;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomePage extends AppCompatActivity  {
    private BottomNavigationView mMainNav;
    private RelativeLayout mMainFrame;
    private Home homefragment;
    private More moreFragment;
    private Scores scoresFragment;
    private Watch watchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        mMainFrame = (RelativeLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.bottom_nav);
        homefragment = new Home();
        moreFragment = new More();
        scoresFragment = new Scores();
        watchFragment = new Watch();



        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new Home()).commit();


                        return true;

                    case R.id.nav_more:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new More()).commit();

                        return true;

                    case R.id.nav_score:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new Scores()).commit();

                    return true;

                    case R.id.nav_watch:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new Watch()).commit();

                        return true;

                        default:
                            return false;
                }
            }


        });
    }
}