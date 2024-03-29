package ypw.app.wdsportz.MainActivities;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import ypw.app.wdsportz.MainFragments.Frag_HomePage;
import ypw.app.wdsportz.MainFragments.Frag_iniTeamSelect;
import ypw.app.wdsportz.MainFragments.frag_Register;
import ypw.app.wdsportz.MainFragments.frag_login;
import com.example.wdsportz.R;

public class Auth_Activity extends AppCompatActivity implements Frag_iniTeamSelect.OnFragmentInteractionListener,Frag_HomePage.OnFragmentInteractionListener, frag_login.OnFragmentInteractionListener, frag_Register.OnFragmentInteractionListener  {

    Toolbar myToolbar;
    private static Auth_Activity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_main);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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

       SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
       searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }


    public void goToMainFeed() {
        Intent intent = new Intent(this, Main_Activity.class);
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
