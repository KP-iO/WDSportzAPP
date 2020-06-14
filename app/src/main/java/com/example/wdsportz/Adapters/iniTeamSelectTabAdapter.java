package com.example.wdsportz.Adapters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.wdsportz.MainFragments.Frag_iniTeamSelect_teams;

import java.util.ArrayList;
import java.util.HashMap;

public class iniTeamSelectTabAdapter extends FragmentStatePagerAdapter {

    HashMap<String,String> Leagues;


    public iniTeamSelectTabAdapter(FragmentManager fm, HashMap<String,String> Leagues) {
        super(fm);
        this.Leagues = Leagues;
    }


////////////////
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Frag_iniTeamSelect_teams();
        Bundle args = new Bundle();
        //Hashmap 'value' to Arraylist and then to string
        String leagueName = (new ArrayList<String>(Leagues.values())).get(position);
        args.putString(Frag_iniTeamSelect_teams.ARG_OBJECT, leagueName);
        fragment.setArguments(args);
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


}


