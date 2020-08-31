package com.example.wdsportz.Adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.wdsportz.MainFragments.Frag_iniTeamSelect_teams;

import java.util.ArrayList;
import java.util.HashMap;

public class iniTeamSelectTabAdapter extends FragmentStateAdapter {

    HashMap<String,String> Leagues;

    public iniTeamSelectTabAdapter(FragmentManager fm, Lifecycle lifecycle, HashMap<String, String> Leagues) {
        super(fm,lifecycle);

        this.Leagues = Leagues;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {

            Fragment fragment = new Frag_iniTeamSelect_teams();
            Bundle args = new Bundle();

            String value = (new ArrayList<String>(Leagues.values())).get(position);

            ArrayList<String> leagueName = new ArrayList<String>();
            leagueName.add(0, value);

            args.putStringArrayList(Frag_iniTeamSelect_teams.ARG_OBJECT, leagueName);
            fragment.setArguments(args);
            return fragment;
        }




    @Override
    public int getItemCount() {
        return Leagues.size();
    }
}


