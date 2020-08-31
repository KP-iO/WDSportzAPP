package com.example.wdsportz.MainFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.wdsportz.Adapters.SelectTeamsRecyclerViewAdapter;
import com.example.wdsportz.Adapters.iniTeamSelectTabAdapter;
import com.example.wdsportz.MainActivities.Auth_Activity;
import com.example.wdsportz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Frag_iniTeamSelect extends Fragment {

    public static final String TAG = "Frag_iniTeamSelect";

    FirebaseFirestore fireStoreDB = FirebaseFirestore.getInstance();

    ArrayList<String> teamsPrefs;
    iniTeamSelectTabAdapter iniTeamSelectTabAdapter;
    ViewPager2 viewPager;
    SelectTeamsRecyclerViewAdapter selectTeamsRecyclerViewAdapter;

    public ArrayList<String> teamsSelected = new ArrayList<String>();
    TabLayout tabLayout;
    View view1;
    Map<String, String> leagues = new LinkedHashMap<>();

    private OnFragmentInteractionListener mListener;


    public Frag_iniTeamSelect() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().findViewById(R.id.my_toolbar).setVisibility(View.VISIBLE);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_initeamselection, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().findViewById(R.id.my_toolbar).setVisibility(View.VISIBLE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        getActivity().findViewById(R.id.my_toolbar).setVisibility(View.VISIBLE);

        viewPager = view.findViewById(R.id.iniSelectview_pager);
        tabLayout = view.findViewById(R.id.tab_layout);
        //Here the pager and tablayout are assigned adapters in 'nameTabs function' Called in getLeaguesID
        getLeaguesID();


        Button button = view.findViewById(R.id.btn_finish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTeamsRecyclerViewAdapter = new SelectTeamsRecyclerViewAdapter();
                teamsSelected = selectTeamsRecyclerViewAdapter.getArrayList();

                adFavourite();
                ((Auth_Activity) getActivity()).goToMainFeed();

            }

        });

    }

    public void getLeaguesID() {

        try {
            fireStoreDB.collection("Leagues")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    String leagueID = String.valueOf(document.get("leagueId"));
                                    String leagueName = document.getId();

                                    leagues.put(leagueID, leagueName);

                                }

                                NameTabs();

                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        } catch (Exception error) {
            Log.d(TAG, "getLeaguesID: FIREBASE FAILURE ==  " + error);

        }

    }


    public void NameTabs() {

        //SORT LEAGUES
        LinkedHashMap<String, String> sortedMap = new LinkedHashMap<>();

        leagues.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

        ///
        Log.d(TAG, String.valueOf(sortedMap.size()));
        Log.d(TAG + "League name + id",sortedMap.toString());

        iniTeamSelectTabAdapter = new iniTeamSelectTabAdapter(getChildFragmentManager(), getLifecycle(),(LinkedHashMap<String, String>) sortedMap);
        viewPager.setAdapter(iniTeamSelectTabAdapter);
        setPageTitle(sortedMap);

    }


    public void setPageTitle(LinkedHashMap<String, String> sortedMap) {

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            for (int i = 0; i < sortedMap.size(); i++) {
                Log.d(TAG, "setPageTitle: " + position);
                String value = (new ArrayList<String>(sortedMap.values())).get(position);
                Log.d(TAG, "value "+value);

                String s1 = value.substring(value.indexOf("-")+1);
                s1.trim();

                tab.setText(s1);
            }
        }).attach();
    }


    private void adFavourite(){

        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user1.getUid();
        selectTeamsRecyclerViewAdapter = new SelectTeamsRecyclerViewAdapter();
        teamsSelected = selectTeamsRecyclerViewAdapter.getArrayList();
        teamsPrefs = SelectTeamsRecyclerViewAdapter.getArrayList();
        Log.d("CLICK1", Arrays.toString(teamsSelected.toArray())+ "  Clicked");

        HashMap<String, ArrayList<String>> teams = new HashMap<>();

        teams.put("Favourite Teams", teamsPrefs);

        // firebase datatabase instance
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        //path to store data named "Users"

        database.collection("Users").document(uid)
                .set(teams, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(MotionScene.TAG, "DocumentSnapshot successfully written!");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(MotionScene.TAG, "Error writing document", e);

                    }
                });

        // Used to make FirebaseProfile for user
    }



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



