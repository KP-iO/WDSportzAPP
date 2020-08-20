package com.example.wdsportz.MainFragments;


import android.content.Context;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
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

public class Frag_Score extends Fragment {

    FirebaseFirestore fireStoreDB = FirebaseFirestore.getInstance();
    private OnFragmentInteractionListener mListener;
    private static final String TAG = "Frag_Score";

    private RecyclerView recyclerViewScores;

    private AppBarLayout appBarLayout;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy", /*Locale.getDefault()*/Locale.ENGLISH);
    private CompactCalendarView compactCalendarView;
    private boolean isExpanded = false;

    //ogScoreList MUST NOT BE ALTERED AFTER IT INITIALISATION ON "VIEW CREATED -> "initScoreFeed"
    List<ScoreViewModel> ogScoresList = new ArrayList<>();
    List<ScoreViewModel> dateSortedlist = new ArrayList<>();

    Spinner LeagueSpinner;
    ArrayAdapter<String> LeagueSpinnerAdapter;
    String LeagueSpinnerSelectedStr;
    List<League> leagueList = new ArrayList<>();
    ExtendedFloatingActionButton btnfab_standings;

    private ScoreFeedAdpater scoreFeedAdpater;

    View view;


    public Frag_Score() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final Context context = view.getContext();
        this.view = view;
        recyclerViewScores = getView().findViewById(R.id.scores_feed);
        recyclerViewScores.setLayoutManager(new GridLayoutManager(context, 2));
        btnfab_standings = getView().findViewById(R.id.fab_standings);
        iniScoreFeed(context);
        initCalenderSortBar(view);
        initNoDateUI(false);

        initLeagueSortBar();

        btnfab_standings.setVisibility(View.VISIBLE);
        btnfab_standings.setOnClickListener(new View.OnClickListener(){
            public void onClick(final View view) {
                showLeagueStandings();
            }
        });

    }


    private void iniScoreFeed(final Context context) {
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

                                ogScoresList.add(new ScoreViewModel(document.get("homeName").toString(), document.get("awayName").toString(), document.get("homeScore").toString(), document.get("awayScore").toString(), document.get("homeLogo").toString(), document.get("awayLogo").toString(), document.get("eventDate").toString(), document.get("event_id").toString()));


                                scoreFeedAdpater = new ScoreFeedAdpater(context, ogScoresList);
                                recyclerViewScores.setAdapter(scoreFeedAdpater);

                            }

                            // List check (in Log)
//                            for (int i = 0; i < ogScoresList.size() - 1; i++) {
//
//                                Log.d(TAG, ("ID = " + ogScoresList.get(i).getEventId()));
//                                Log.d(TAG, "Home Team   " + ogScoresList.get(i).getHomeTeam());
//                                Log.d(TAG, "Away Team   " + ogScoresList.get(i).getAwayTeam());
//                                Log.d(TAG, "Home Score   " + ogScoresList.get(i).getHomeScore());
//                                Log.d(TAG, "Away Score   " + ogScoresList.get(i).getAwayScore());
//
//                            }

                        } else {
                            Log.d(TAG, "No such document");
                        }

                    }

                });
    }

    private void initCalenderSortBar(View view) {

        Toolbar toolbar = view.findViewById(R.id.toolbar);

        appBarLayout = view.findViewById(R.id.app_bar_layout);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        ((CustomAppBarLayoutBehavior) layoutParams.getBehavior()).setScrollBehavior(false);

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


                Event ev1 = new Event(Color.GREEN, highlightAvailableDate.getTime(), "MATCH");

                compactCalendarView.addEvent(ev1);

            }

        });


    }

    private void sortbydate(Date selectedDate) {

        dateSortedlist.clear();

////change ogScoresList to getitemsfrom current recycler and then sort by date
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

        if (dateSortedlist.size() == 0) {

          initNoDateUI(true);

        } else {

            initNoDateUI(false);

        }
    }

    private void initNoDateUI(boolean isTrue) {
        ///if the UI is empty this is shown

        if(isTrue){

            getView().findViewById(R.id.scores_feed).setVisibility(View.GONE);
            btnfab_standings.setVisibility(View.GONE);

            getView().findViewById(R.id.ConstrLayoutNoScore).setVisibility(View.VISIBLE);

            Button btnSeeAllScore = getView().findViewById(R.id.btn_SeeAllScores);
            btnSeeAllScore.setVisibility(View.VISIBLE);
            getView().findViewById(R.id.ConstrLayoutSeeAllBtn).setVisibility(View.VISIBLE);
            btnSeeAllScore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "SeeAllScore");
                    btnfab_standings.setVisibility(View.VISIBLE);
                    getView().findViewById(R.id.scores_feed).setVisibility(View.VISIBLE);
                    iniScoreFeed(getContext());
                    getView().findViewById(R.id.ConstrLayoutNoScore).setVisibility(View.GONE);
                    getView().findViewById(R.id.ConstrLayoutSeeAllBtn).setVisibility(View.GONE);
                }
            });

        } else {

            btnfab_standings.setVisibility(View.VISIBLE);
            getView().findViewById(R.id.scores_feed).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.ConstrLayoutNoScore).setVisibility(View.GONE);
            getView().findViewById(R.id.ConstrLayoutSeeAllBtn).setVisibility(View.GONE);

            scoreFeedAdpater = new ScoreFeedAdpater(getContext(),dateSortedlist);
            recyclerViewScores.setAdapter(scoreFeedAdpater);
        }
    }


    private void initLeagueSortBar() {

        ///// Team league spinner

        LeagueSpinner = view.findViewById(R.id.spinner_team_league);

        // LEAGUES
        List<String> spinnerArrayLeagues = new ArrayList<String>();
        spinnerArrayLeagues.add("All Leagues");

        fireStoreDB.collection("Leagues")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                spinnerArrayLeagues.add(document.getId());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        LeagueSpinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, spinnerArrayLeagues);
        LeagueSpinner.setAdapter(LeagueSpinnerAdapter);
        LeagueSpinner.setSelection(0);

        LeagueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected (AdapterView < ? > arg0, View view,int position, long id){

                int item = LeagueSpinner.getSelectedItemPosition();
                LeagueSpinnerSelectedStr = LeagueSpinner.getItemAtPosition(item).toString();
                Log.d("TESTTTT", "initSortBar:YOU SELECTED " + LeagueSpinnerSelectedStr);

                getLeagueSelectedScores(LeagueSpinnerSelectedStr);

        }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Nothing happen
            }

        });

}

    private void getLeagueSelectedScores(String selectedLeague) {

        //I need the scores for leagues here!!
//        Log.d("TESTTTT", "initSortBar:YOU SELECTED " + selectedLeague);
//        fireStoreDB.collection("Leagues")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {

                    //        leagueList.clear();
                    //        scoreFeedAdpater = new ScoreFeedAdpater(getContext(),leagueList);
                    //        recyclerViewScores.setAdapter(scoreFeedAdpater);

//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
    }


    private void showLeagueStandings() {
        Log.d(TAG,"FAB Standing Clicked");

        if (LeagueSpinnerSelectedStr == "All Leagues") {

            LeagueSpinner.setBackgroundResource(R.color.red_100);
            Toast toast = Toast.makeText(getContext(), "Select A League", Toast.LENGTH_LONG);
            toast.show();

        } else {

            Bundle bundle = new Bundle();
            bundle.putString("SelectedLeague", LeagueSpinnerSelectedStr);
            Navigation.findNavController(view).navigate(R.id.action_frag_scores_to_frag_Score_Standings, bundle);

        }

    }



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