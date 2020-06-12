package com.example.wdsportz.MainFragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wdsportz.Adapters.ScoreFeedAdpater;
import com.example.wdsportz.CustomAppBarLayoutBehavior;
import com.example.wdsportz.Model.League;
import com.example.wdsportz.R;
import com.example.wdsportz.ViewModels.ScoreViewModel;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Frag_Watch.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Frag_Watch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_Score extends Fragment {

    private AppBarLayout appBarLayout;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy", /*Locale.getDefault()*/Locale.ENGLISH);
    private CompactCalendarView compactCalendarView;
    private boolean isExpanded = false;


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

    //ogScoreList MUST NOT BE ALTERED AFTER IT INITIALISATION ON "VIEW CREATED -> "enterData"
    List<ScoreViewModel> ogScoresList = new ArrayList<>();

    List<ScoreViewModel> dateSortedlist = new ArrayList<>();
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

        initSortBar(view);
        initViews(context);
        enterData(context);

    }

    private void initSortBar(View view) {

        Toolbar toolbar = view.findViewById(R.id.toolbar);

        appBarLayout = view.findViewById(R.id.app_bar_layout);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        ((CustomAppBarLayoutBehavior)layoutParams.getBehavior()).setScrollBehavior(false);

        compactCalendarView = view.findViewById(R.id.compactcalendar_view);

        compactCalendarView.setLocale(TimeZone.getDefault(), /*Locale.getDefault()*/Locale.ENGLISH);

        compactCalendarView.setShouldDrawDaysHeader(true);

        // Set current date to today
        Date todaydate = new Date();

        compactCalendarView.setCurrentDate(todaydate);

        Button datePickerButton = view.findViewById(R.id.date_picker_button);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                datePickerButton.setText(dateFormat.format(dateClicked));
                sortbydate(dateClicked);

                isExpanded = !isExpanded;
                appBarLayout.setExpanded(isExpanded, true);

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                //toolbar.setSubtitle(dateFormat.format(firstDayOfNewMonth));
                datePickerButton.setText(dateFormat.format(firstDayOfNewMonth));

            }
        });

        //Button datePickerButton = view.findViewById(R.id.date_picker_button);

        datePickerButton.setOnClickListener(v -> {

            isExpanded = !isExpanded;
            appBarLayout.setExpanded(isExpanded, true);

            for (int i = 0; i < ogScoresList.size(); i++) {

                SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

                Date highlightAvailableDate = null;

                //parse requires try git catch for some reason :/
                try {
                    highlightAvailableDate = sdformat.parse(ogScoresList.get(i).getEventDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                Event ev1 = new Event(Color.GREEN,highlightAvailableDate.getTime() , "MATCH");

                compactCalendarView.addEvent(ev1);

            }

        });



        ///// Team league spinner

        Spinner spinner = view.findViewById(R.id.spinner_team_league);


        //TEST LEAGUES

        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("All Leagues");
        spinnerArray.add("League 2");
        spinnerArray.add("League 3");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                int item = spinner.getSelectedItemPosition();
                String selected;

//                if (item !=  ) {
                    selected = spinner.getItemAtPosition(item).toString();
                    Log.d("TESTTTT", "initSortBar:YOU SELECTED " + selected);
//                };

            }
            public void onNothingSelected(AdapterView<?> arg0) { }
        });




        }



    private void sortbydate(Date selectedDate) {

        dateSortedlist.clear();

        for (int i = 0; i < ogScoresList.size(); i++) {

                //Format of the date
                SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

                Date matchDate = null;
                try {

                    matchDate = sdformat.parse(ogScoresList.get(i).getEventDate());

                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if (matchDate.compareTo(selectedDate) == 0) {
                    dateSortedlist.add(ogScoresList.get(i));
                    Log.d(TAG, "sortbydate: RECYCLER HAS BEEN SORTED BY DATE");

                }

            } //FOR LOOP ENDS

        if (dateSortedlist.size()== 0) {

            getView().findViewById(R.id.ConstrLayoutNoScore).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.scores_feed).setVisibility(View.GONE);
            getView().findViewById(R.id.ConstrLayoutSeeAllBtn).setVisibility(View.VISIBLE);

            Button btnSeeAllScore = getView().findViewById(R.id.btn_SeeAllScores);
            btnSeeAllScore.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: ogscorelist");
                    initViews(getContext());
                    getView().findViewById(R.id.scores_feed).setVisibility(View.VISIBLE);
                    enterData(getContext());
                }
            });


        } else {

            getView().findViewById(R.id.ConstrLayoutNoScore).setVisibility(View.GONE);
            getView().findViewById(R.id.scores_feed).setVisibility(View.VISIBLE);
            scoreFeedAdpater = new ScoreFeedAdpater(getContext(), dateSortedlist);
            recyclerView.setAdapter(scoreFeedAdpater);
            getView().findViewById(R.id.ConstrLayoutSeeAllBtn).setVisibility(View.GONE);

        }

//        btnRestoreAllScores();

        }



//    private void btnRestoreAllScores(){
//
//        RecyclerView.Adapter adapter = scoreFeedAdpater;
//        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//            @Override
//            public void onChanged() {
//                Log.d(TAG, "onChanged: CHANGED DETECTED");
//                if (scoreFeedAdpater.getItemCount() != ogScoresList.size()) {
//
//
//
//                } else {
//
//
//                }
//
//            }
//        });
//
//
//    }

    private void initViews(Context context) {

        getView().findViewById(R.id.ConstrLayoutNoScore).setVisibility(View.GONE);

        getView().findViewById(R.id.ConstrLayoutSeeAllBtn).setVisibility(View.GONE);

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

////// Change FROM download url to stroage url in firestore?

                        for (QueryDocumentSnapshot document : task.getResult()) {


                            Log.d(TAG, "DOCUMENT PRINT :" + document.getData().toString());
                            Log.d(TAG, "Team Added to List " + document.get("event_id").toString());

                            ogScoresList.add(new ScoreViewModel(document.get("homeName").toString(),document.get("awayName").toString(),document.get("homeScore").toString(),document.get("awayScore").toString(),document.get("homeLogo").toString(), document.get("awayLogo").toString(), document.get("eventDate").toString(),document.get("event_id").toString() ));




                            //Log.d(TAG, ("LOGO URL: " + list.));

                            scoreFeedAdpater = new ScoreFeedAdpater(context, ogScoresList);
                            recyclerView.setAdapter(scoreFeedAdpater);

                        }

                        // List check (in Log)
                        for (int i = 0; i < ogScoresList.size() - 1; i++) {

                            Log.d(TAG, ("ID = " + ogScoresList.get(i).getEventId()));
                            Log.d(TAG, "Home Team   " + ogScoresList.get(i).getHomeTeam());
                            Log.d(TAG, "Away Team   " + ogScoresList.get(i).getAwayTeam());
                            Log.d(TAG, "Home Score   " + ogScoresList.get(i).getHomeScore());
                            Log.d(TAG, "Away Score   " + ogScoresList.get(i).getAwayScore());

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

}