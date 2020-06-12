package com.example.wdsportz.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.wdsportz.FilterManager;
import com.example.wdsportz.MainFragments.Frag_iniTeamSelect_teams;
import com.example.wdsportz.ObserverFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class iniTeamSelectTabAdapter extends FragmentStatePagerAdapter {

    HashMap<String,String> Leagues;
    private Context context;
    private FilterManager filterManager;

    public iniTeamSelectTabAdapter(FragmentManager fm){
        super(fm);
    }

    public iniTeamSelectTabAdapter(Context context, FragmentManager fm,
                                   FilterManager filterManager,HashMap<String,String> Leagues) {
        super(fm);
        this.context = context;
        this.filterManager = filterManager;
        this.Leagues = Leagues;
    }


//    public iniTeamSelectTabAdapter(FragmentManager fm, ) {
//        super(fm);
//
//    }

////////////////
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Frag_iniTeamSelect_teams();
        Bundle args = new Bundle();
        String leagueName = (new ArrayList<String>(Leagues.values())).get(position);
        args.putString(Frag_iniTeamSelect_teams.ARG_OBJECT, leagueName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ObserverFragment fragment = (ObserverFragment) super.instantiateItem(container, position);
        filterManager.addObserver(fragment); // add the observer
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String value = (new ArrayList<String>(Leagues.values())).get(position);
        String leagueNameSplit = value.split("-")[1];
        return leagueNameSplit;
    }

    @Override
    public int getCount() {
        return Leagues.size();
    }

//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        NestedFragment fragment = (NestedFragment) object; // see (*)
//        filterManager.deleteObserver(fragment); // remove the observer
//        super.destroyItem(container, position, object);
//    }

//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        filterManager.deleteObserver((ObserverFragment) object); // delete the observer
//        super.destroyItem(container, position, object);
//    }

}


