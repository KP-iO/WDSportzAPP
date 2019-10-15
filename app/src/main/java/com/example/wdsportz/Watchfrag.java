package com.example.wdsportz;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.example.wdsportz.Adapter.VideoViewAdapter;
import com.example.wdsportz.viewmodels.VideoViewModel;
import com.example.wdsportz.Watch;


/**
 * A simple {@link Fragment} subclass.
 */
public class Watchfrag extends Fragment {


    public Watchfrag() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_watch, container, false);
        Intent firstpage= new Intent(getActivity(), Watch.class);
      startActivity(firstpage);
////        RecyclerView recyclerViewVM = (RecyclerView) view.findViewById(R.id.RecyclerViewVM);
//        RecyclerView recyclerViewV = (RecyclerView) view.findViewById(R.id.RecyclerViewV);
//
//        VideoViewAdapter videoViewAdapter = new VideoViewAdapter();
//        recyclerViewV.setAdapter(videoViewAdapter);
//        recyclerViewV.setLayoutManager(new GridLayoutManager(getActivity(), 2));



        return view;


    }

}
