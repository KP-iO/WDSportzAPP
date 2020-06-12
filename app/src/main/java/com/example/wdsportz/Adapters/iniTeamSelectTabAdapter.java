package com.example.wdsportz.Adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.wdsportz.MainFragments.Frag_iniTeamSelect_teams;

// extend FragmentPagerAdapter instead of FragmentStatePagerAdapter (Better implementation) >

public class iniTeamSelectTabAdapter extends FragmentStateAdapter {

    public iniTeamSelectTabAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        Fragment fragment = new Frag_iniTeamSelect_teams();
        Bundle args = new Bundle();
        // Our object is just an integer /
        args.putInt(Frag_iniTeamSelect_teams.ARG_OBJECT, position + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}


