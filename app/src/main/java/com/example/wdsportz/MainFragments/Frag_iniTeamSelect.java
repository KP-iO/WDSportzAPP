package com.example.wdsportz.MainFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.wdsportz.Adapters.SelectTeamsRecyclerViewAdapter;
import com.example.wdsportz.Adapters.iniTeamSelectTabAdapter;
import com.example.wdsportz.MainActivities.Auth_Activity;
import com.example.wdsportz.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Frag_iniTeamSelect.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Frag_iniTeamSelect#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_iniTeamSelect extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    // When requested, this adapter returns a Frag_iniTeamSelect_teams,
    // representing an object in the collection.
    iniTeamSelectTabAdapter iniTeamSelectTabAdapter;
    ViewPager2 viewPager;
    SelectTeamsRecyclerViewAdapter selectTeamsRecyclerViewAdapter;
    public ArrayList<String> teamsSelected = new ArrayList<>();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    StorageReference storageReference;


    public Frag_iniTeamSelect() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_iniTeamSelect.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag_iniTeamSelect newInstance(String param1, String param2) {
        Frag_iniTeamSelect fragment = new Frag_iniTeamSelect();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        teamsSelected =SelectTeamsRecyclerViewAdapter.getArrayList();


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).findViewById(R.id.my_toolbar).setVisibility(View.VISIBLE);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_initeamselection, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        databaseReference = firebaseDatabase.getReference("Users");
        storageReference = FirebaseStorage.getInstance().getReference();


        TabLayout tabLayout = view.findViewById(R.id.tab_layout);

        iniTeamSelectTabAdapter = new iniTeamSelectTabAdapter(this);
        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(iniTeamSelectTabAdapter);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                tab.setText("Tab" + (position + 1));

            }
        }).attach();

        NameTabs(view);

        Button button = view.findViewById(R.id.btn_finish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
//                selectTeamsRecyclerViewAdapter = new SelectTeamsRecyclerViewAdapter();
//                teamsSelected = selectTeamsRecyclerViewAdapter.getArrayList();

                Log.d("CLICK1", Arrays.toString(teamsSelected.toArray()) + "  Clicked");




                ((Auth_Activity)getActivity()).goToMainFeed();
            }

        });

    }



    public void NameTabs (@NonNull View view){

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);

        tabLayout.getTabAt(0).setText("Suggested");
        tabLayout.getTabAt(1).setText("Bostik League");
        tabLayout.getTabAt(2).setText("Essex Senior League");
        tabLayout.getTabAt(3).setText("Universities (BUCS) ");

    }


    //private iniTeamSelectAdapter createCardAdapter() {
      //  iniTeamSelectAdapter adapter = new iniTeamSelectAdapter(this);
       // return adapter;
    //}


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}



