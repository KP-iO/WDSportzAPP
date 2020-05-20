package com.example.wdsportz.MainActivities;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.example.wdsportz.MainFragments.Frag_HomePage;
import com.example.wdsportz.MainFragments.Frag_iniTeamSelect;
import com.example.wdsportz.MainFragments.frag_Register;
import com.example.wdsportz.MainFragments.frag_login;
import com.example.wdsportz.R;

public class Auth_Activity extends AppCompatActivity implements Frag_iniTeamSelect.OnFragmentInteractionListener,Frag_HomePage.OnFragmentInteractionListener, frag_login.OnFragmentInteractionListener, frag_Register.OnFragmentInteractionListener  {

    //Change majority of 'px' dimensions settings to 'dp'
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       setTheme(R.style.AppTheme1_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_main);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setVisibility(View.GONE);


    }

    @Override
    public void onResume(){
        super.onResume();
        myToolbar.setVisibility(View.GONE);
    }


/// SEARCH ->
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

       SearchView searchView = (SearchView)menu.findItem(R.id.search).getActionView();
       searchView.setMaxWidth(Integer.MAX_VALUE);

        // Associate searchable configuration with the SearchView
        //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        //SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        return true;
    }


    public void goToMainFeed() {
        Intent intent = new Intent(this, Main_Activity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //process Cursor and display results
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

}
