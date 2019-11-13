package com.example.wdsportz.MainActivities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.wdsportz.MainFragments.Frag_HomePage;
import com.example.wdsportz.MainFragments.Frag_IniTeamSelection;
import com.example.wdsportz.MainFragments.frag_login;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.wdsportz.R;

public class Auth_Activity extends AppCompatActivity implements Frag_IniTeamSelection.OnFragmentInteractionListener,Frag_HomePage.OnFragmentInteractionListener, frag_login.OnFragmentInteractionListener  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_main);

       //  navController = Navigation.findNavController(this, R.id.);

        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

       // FloatingActionButton fab = findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View view) {
          //      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //            .setAction("Action", null).show();
            //}
       /// });
    }



    public void goToMainFeed() {
        Intent intent = new Intent(this, Main_Activity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }



    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

}
