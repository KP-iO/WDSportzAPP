package com.example.wdsportz.Adapters;

import android.os.Bundle;
import android.util.Log;

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
        Log.d("Fragment Position", String.valueOf(position));

        if (position == 0) {
            Log.d("Fragment Position", "POSSS 0");
            Fragment fragment = new Frag_iniTeamSelect_teams();
            Bundle args = new Bundle();
            ArrayList<String> allLeagues = (new ArrayList<String>(Leagues.values()));
            args.putStringArrayList(Frag_iniTeamSelect_teams.ARG_OBJECT, allLeagues);
            Log.d("all leagues: ", String.valueOf(allLeagues));
            fragment.setArguments(args);
            //for first tab

            return fragment;

        } else {

            Log.d("Fragment Position", "POSSS > 1");
            Fragment fragment = new Frag_iniTeamSelect_teams();
            Bundle args = new Bundle();

            String value = (new ArrayList<String>(Leagues.values())).get(position -1);

            ArrayList<String> leagueName = new ArrayList<String>();
            leagueName.add(0, value);
            args.putStringArrayList(Frag_iniTeamSelect_teams.ARG_OBJECT, leagueName);
            fragment.setArguments(args);
            return fragment;
        }
    }


    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {

            String PageTitleAllTeams = "All Teams";
            return PageTitleAllTeams;

        } else {

            String value = (new ArrayList<String>(Leagues.values())).get(position -1);
            String leagueNameSplit = value.split("-")[1];
            return leagueNameSplit;

        }

    }

    @Override
    public int getCount() {
        //Plus one for all teams tab
        return Leagues.size() + 1;
    }


}


