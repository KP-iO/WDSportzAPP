package com.example.wdsportz.MainFragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wdsportz.Adapters.ScoreFeedAdpater;
import com.example.wdsportz.Model.League;
import com.example.wdsportz.R;
import com.example.wdsportz.ViewModels.ScoreViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Frag_Watch.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Frag_Watch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_Score extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Frag_Watch fragwatch;
    private static final String TAG = "Video Activity";
    FirebaseFirestore fireStoreDB = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;

    private List<League> leagueList;
    private ScoreFeedAdpater scoreFeedAdpater;
    private ScoreViewModel scoreViewModel;


    ProgressDialog pd;


    public Frag_Score() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Frag_Watch newInstance(String param1, String param2) {
        Frag_Watch fragment = new Frag_Watch();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_score, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState ) {
    final Context context = view.getContext();


        initViews(context);
        enterData(context);




    }



    private void initViews(Context context) {

        recyclerView = getView().findViewById(R.id.scores_feed);
        int numberOfColumns = 2;




        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(context, numberOfColumns));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        }


    }

private void enterData(final Context context){
    Task<QuerySnapshot> docRef = fireStoreDB.collection("Scores")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {

                        List<ScoreViewModel> list = new ArrayList<>();


////// Change FROM download url to stroage url in firestore?

                        for (QueryDocumentSnapshot document : task.getResult()) {


                            Log.d(TAG, "DOCUMENT PRINT :" + document.getData().toString());
                            Log.d(TAG, "Team Added to List " + document.get("event_id").toString());

                            list.add(new ScoreViewModel(document.get("homeName").toString(),document.get("awayName").toString(),document.get("homeScore").toString(),document.get("awayScore").toString(),document.get("homeLogo").toString(), document.get("awayLogo").toString(), document.get("eventDate").toString(),document.get("event_id").toString() ));

                            //Log.d(TAG, ("LOGO URL: " + list.));

                            scoreFeedAdpater = new ScoreFeedAdpater(context, list);
                            recyclerView.setAdapter(scoreFeedAdpater);

                        }

                        // List check (in Log)
                        for (int i = 0; i < list.size() - 1; i++) {

                            Log.d(TAG, ("ID = " + list.get(i).getEventId()));
                            Log.d(TAG, "Home Team   " + list.get(i).getHomeTeam());
                            Log.d(TAG, "Away Team   " + list.get(i).getAwayTeam());
                            Log.d(TAG, "Home Score   " + list.get(i).getHomeScore());
                            Log.d(TAG, "Away Score   " + list.get(i).getAwayScore());

                        }

                    } else {
                        Log.d(TAG, "No such document");
                    }

                }

            });
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

    private class SPORTS_DB_API_TOKEN {
    }
}