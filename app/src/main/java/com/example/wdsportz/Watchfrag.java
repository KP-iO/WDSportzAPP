package com.example.wdsportz;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.wdsportz.Adapter.VideoViewAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class Watch extends Fragment {


    public Watch() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_watch, container, false);

        RecyclerView recyclerViewVM = (RecyclerView) view.findViewById(R.id.RecyclerViewVM);
        RecyclerView recyclerViewV = (RecyclerView) view.findViewById(R.id.RecyclerViewV);

        VideoViewAdapter videoViewAdapter = new VideoViewAdapter();
        recyclerViewV.setAdapter(videoViewAdapter);
        recyclerViewV.setLayoutManager(new GridLayoutManager(getActivity(), 2));



        return view;


    }

}
