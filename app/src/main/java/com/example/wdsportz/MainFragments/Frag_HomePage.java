package com.example.wdsportz.MainFragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wdsportz.Adapters.MainFeedRecyclerViewAdapter;
import com.example.wdsportz.R;
import com.example.wdsportz.RecyclerViewModel;

import java.util.ArrayList;


public class Frag_HomePage extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    MainFeedRecyclerViewAdapter adapter;
    MainFeedRecyclerViewAdapter.ItemClickListener adapterlistening;
    private ArrayList<RecyclerViewModel> ArticleArrayList;

    // Temporary Feed Population Arrays
    private int[] NewsImages = new int[]{R.drawable.arsenal_team_logo,R.drawable.bowers___fc,R.drawable.barking_fv,R.drawable.chestnut_fv,R.drawable.enfield_fc, R.drawable.ware_fc,R.drawable.folkestone_fc,R.drawable.molesey_fc};
    private String[] NewsTitles = new String[]{"Coventry Dominates in a 4 -1 Win Over Nottingham","New Manager Decided For Bowers FC","Lorem ipsum dolor sit amet."," Consectetur Adipiscing Elit, Sed Do Eiusmod Tempor Incididunt","Sed Do Eiusmod Tempor Incididunt","Duis aute irure dolor in reprehenderit in voluptate"," sunt in culpa qui officia deserunt mollit"};


    public Frag_HomePage(){

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_Test_1.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag_HomePage newInstance(String param1, String param2) {
        Frag_HomePage fragment = new Frag_HomePage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homepage, container, false);

    }


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

                /// Move feed load to external function
        final Context context = view.getContext();
        final String Tag = "FEED TEST";

        Log.d("RecyclerTest" , "onViewCreated: ");

        RecyclerView recyclerView = getView().findViewById(R.id.Main_feed);
        ArticleArrayList = populateFeed();

        Log.d(Tag,  ArticleArrayList.toString());

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new MainFeedRecyclerViewAdapter(context, ArticleArrayList);
        adapter.setClickListener(adapterlistening);
        recyclerView.setAdapter(adapter);

        Log.d("RecyclerTest" , "onViewCreated:111 ");

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    // Called when the fragment is no longer attached to activity
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private ArrayList<RecyclerViewModel> populateFeed() {

        ArrayList<RecyclerViewModel> list = new ArrayList<>();

        for (int i = 0; i < 7 ; i++) {
            RecyclerViewModel FullArticle = new RecyclerViewModel();
            FullArticle.setName(NewsTitles[i]);
            FullArticle.setImage_drawable(NewsImages[i]);
            list.add(FullArticle);
        }

        return list;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}



