package com.example.wdsportz;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;

/**
 * Created by khrishawn
 */
public class NavBarJetpackX {


    NavController navController;
    DrawerLayout drawerLayout;

    AppBarConfiguration appBarConfiguration =
            new AppBarConfiguration.Builder(navController.getGraph())
                    .setDrawerLayout(drawerLayout)
                    .build();

}
