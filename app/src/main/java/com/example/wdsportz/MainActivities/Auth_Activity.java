package com.example.wdsportz.MainActivities;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.wdsportz.Adapters.iniTeamSelectTabAdapter;
import com.example.wdsportz.FilterManager;
import com.example.wdsportz.MainFragments.Frag_HomePage;
import com.example.wdsportz.MainFragments.Frag_iniTeamSelect;
import com.example.wdsportz.MainFragments.frag_Register;
import com.example.wdsportz.MainFragments.frag_login;
import com.example.wdsportz.R;

public class Auth_Activity extends AppCompatActivity implements SearchView.OnQueryTextListener,Frag_iniTeamSelect.OnFragmentInteractionListener,Frag_HomePage.OnFragmentInteractionListener, frag_login.OnFragmentInteractionListener, frag_Register.OnFragmentInteractionListener  {

    //Change majority of 'px' dimensions settings to 'dp'
    Toolbar myToolbar;
    private static Auth_Activity instance;
    private FilterManager filterManager;
    private ViewPager pager;
    private iniTeamSelectTabAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme1_NoActionBar);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_main);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setVisibility(View.GONE);

        instance = this;
        filterManager = new FilterManager();

    }

    public static FilterManager getFilterManager() {
        return instance.filterManager; // return the observable class
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filterManager.setQuery(newText); // update the observable value
        return true;
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

       SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
       searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        // Associate searchable configuration with the SearchView
        //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        //SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        return super.onCreateOptionsMenu(menu);
    }

    private void changeSearchViewTextColor(View view) {  }

    @Override
    public boolean onQueryTextSubmit(String query) { return false; }



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
