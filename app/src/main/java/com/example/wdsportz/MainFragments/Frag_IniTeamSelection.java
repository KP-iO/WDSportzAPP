package com.example.wdsportz.MainFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wdsportz.MainActivities.Auth_Activity;
import com.example.wdsportz.R;
import com.example.wdsportz.Adapters.SelectTeamsRecyclerViewAdapter;
import com.example.wdsportz.viewmodels.SelectTeamsRecyclerViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.Uri;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;


public class Frag_IniTeamSelection extends Fragment implements SelectTeamsRecyclerViewAdapter.ItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Frag_IniTeamSelection iniTeamSelection = this;

    private static final String TAG = "MainActivity";
    FirebaseFirestore fireStoreDB = FirebaseFirestore.getInstance();

    private RecyclerView recyclerView;
    private SelectTeamsRecyclerViewAdapter selectTeamsRecyclerViewAdapter;

    ////////// TODO: https://stackoverflow.com/questions/48169265/creating-a-top-navigation-menu << For League Selector & Scores Tab!!!




    public static Frag_IniTeamSelection newInstance(String param1, String param2) {
        Frag_IniTeamSelection fragment = new Frag_IniTeamSelection();
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ini_team_selection, container, false);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(@NonNull final Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }


    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        final Context context = view.getContext();
        // set up the RecyclerView
        recyclerView = getView().findViewById(R.id.RecyclerView);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(context , numberOfColumns));

        //getActivity().findViewById(R.id.bottom_nav).setVisibility(View.INVISIBLE);


// Implement error handling for all cases e.g (Name/ Logo not accessible) ------>
//// Create a new method for the code below.
        ///+++ Or possibly migrate to the SelectTeamsRecyclerViewAdapter class


        Task<QuerySnapshot> docRef = fireStoreDB.collection("Isthmian_Teams")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<SelectTeamsRecyclerViewModel> list = new ArrayList<>();

////// Change FROM download url to stroage url in firestore?

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d(TAG, "DOCUMENT PRINT :" + document.getData().toString());
                                Log.d(TAG, "Team Added to List " + document.get("Team_Name").toString());

                                list.add(new SelectTeamsRecyclerViewModel(document.get("Team_Name").toString(),document.get("Team_Logo_Location").toString()));

                                //Log.d(TAG, ("LOGO URL: " + list.));

                                selectTeamsRecyclerViewAdapter = new SelectTeamsRecyclerViewAdapter(context, list);
                                recyclerView.setAdapter(selectTeamsRecyclerViewAdapter);
                            }

                            // List check (in Log)
                            for(int i = 0; i < list.size() -1 ; i++ ){

                                Log.d(TAG, (" Team Name = " + list.get(i).teamName));
                                Log.d(TAG, "List Url test   " +  list.get(i).teamLogoURl);
                            }

                        } else {
                            Log.d(TAG, "No such document");
                        }

                    }

                });


        // Btn to Homepage from ini team select
        Button button = view.findViewById(R.id.btn_Finish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                ((Auth_Activity)getActivity()).goToMainFeed();
            }

        });


    }



    @Override
    public void onItemClick(View view, int position) {
        Log.d("TAG", "You clicked number " + selectTeamsRecyclerViewAdapter.getItem(position) + ", which is at cell position " + position);
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}