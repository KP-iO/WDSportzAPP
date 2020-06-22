package com.example.wdsportz.MainFragments;

import android.app.ProgressDialog;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Frag_iniTeamSelect.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Frag_iniTeamSelect#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_iniTeamSelect extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    String postKey;
    ArrayList<String> teamsPrefs;
    private OnFragmentInteractionListener mListener;
    FirebaseUser firebaseUser;
    FirebaseFirestore fireStoreDB = FirebaseFirestore.getInstance();
    iniTeamSelectTabAdapter iniTeamSelectTabAdapter;
    ViewPager2 viewPager;
    SelectTeamsRecyclerViewAdapter selectTeamsRecyclerViewAdapter;
    public ArrayList<String> teamsSelected = new ArrayList<String>();
    ProgressDialog pd;
    String storagePath = "Users_Profile_Cover_Imgs/";
    TabLayout tabLayout;
    View view1;
    Map<String, String> leagues = new LinkedHashMap<>();
//    private ViewPager pager;
//    private iniTeamSelectTabAdapter pagerAdapter;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    DatabaseReference databaseReference;
    StorageReference storageReference;
    String profileOrCoverPhoto;

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

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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


//        databaseReference = firebaseDatabase.getReference("Users");
//        storageReference = FirebaseStorage.getInstance().getReference();

        viewPager = view.findViewById(R.id.iniSelectview_pager);
        tabLayout = view.findViewById(R.id.tab_layout);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);

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
            Log.d(TAG, "LEAGUES TO HASHMAP");
            fireStoreDB.collection("Leagues")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    leagues.put(String.valueOf(document.get("leagueId")), document.getId());

                                }

                                NameTabs(view1);

                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        } catch (Exception error) {
            Log.d(TAG, "getLeaguesID: FIREBASE FAILURE ==  " + error);

        }

    }


    public void NameTabs(@NonNull View view) {
        Log.d(TAG, "NameTabs: CALLED");
        Log.d(TAG, String.valueOf(leagues.size()));

        //SORT LEAGUES
        LinkedHashMap<String, String> sortedMap = new LinkedHashMap<>();

        leagues.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

        ////

        iniTeamSelectTabAdapter = new iniTeamSelectTabAdapter(getChildFragmentManager(), getLifecycle(),(LinkedHashMap<String, String>) sortedMap);
        viewPager.setAdapter(iniTeamSelectTabAdapter);
        setPageTitle(sortedMap);

    }


    public void setPageTitle(LinkedHashMap<String, String> sortedMap) {

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("All Teams");
                    break;

                default:
                    String value = (new ArrayList<String>(sortedMap.values())).get(position -1);
                    String leagueNameSplit = value.split("-")[1];
                    tab.setText(leagueNameSplit);

            }
        }).attach();

//        if (position == 0) {
//
//            String PageTitleAllTeams = "All Teams";
//            return PageTitleAllTeams;
//
//        } else {
//
//            String value = (new ArrayList<String>(Leagues.values())).get(position -1);
//            String leagueNameSplit = value.split("-")[1];
//            return leagueNameSplit;
//
//        }

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


    public String getPostKey() {
        postKey = getArguments().getString("title");
        return postKey;
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



