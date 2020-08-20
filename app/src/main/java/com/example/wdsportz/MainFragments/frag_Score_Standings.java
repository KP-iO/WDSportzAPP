package com.example.wdsportz.MainFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.wdsportz.R;
import com.example.wdsportz.ViewModels.LeagueStandingsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;


public class frag_Score_Standings extends Fragment {

    private static final String TAG = "frag_Score_Standings" ;
    String SelectedLeague;
    FirebaseFirestore fireStoreDB = FirebaseFirestore.getInstance();

    View view;
    TableLayout tableScores;
    ArrayList<LeagueStandingsModel> leagueStandings = new ArrayList<>();
//    LeagueStandingsModel leagueStanding;

    public frag_Score_Standings() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SelectedLeague = getArguments().getString("SelectedLeague");


        return inflater.inflate(R.layout.fragment_frag__score__standings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;

        TextView LeagueName = view.findViewById(R.id.labelLeagueName);
        LeagueName.setText(SelectedLeague);

        tableScores = view.findViewById(R.id.TableScores);

        loadTable();
    }

    private void loadTable() {

        fireStoreDB.collection("Leagues")
                .document(SelectedLeague)
                .collection("Standings")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                leagueStandings.add(new LeagueStandingsModel(
                                        document.get("teamId").toString(),
                                        document.get("rank").toString(),
                                        document.get("teamName").toString(),
                                        document.get("win").toString(),
                                        document.get("draw").toString(),
                                        document.get("lose").toString(),
                                        document.get("goalsFor").toString(),
                                        document.get("goalsAgainst").toString(),
                                        "NULLLL",
                                        document.get("logo").toString(),
                                        document.get("form").toString(),
                                        document.get("points").toString()
                                ));
                            }

                            //add row and model....to function to do work
                            addNewRow(leagueStandings);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    private void addNewRow(ArrayList<LeagueStandingsModel> leagueStandings) {

        ///Sort Leagues
        Collections.sort(leagueStandings);

        ///Loop through and add row
        for (LeagueStandingsModel leagueStanding: leagueStandings) {

            View layout2 = LayoutInflater.from(getContext()).inflate(R.layout.score_standings_row, (ViewGroup) view.findViewById(R.id.TableScores), false);

            TextView txtRank = (TextView) layout2.findViewById(R.id.txtRankNum);
            txtRank.setText(leagueStanding.getTeamRank());

            ImageView imgView = (ImageView) layout2.findViewById(R.id.imgTeamLogo);
            Glide.with(getContext())
                    .load(leagueStanding.getTeamIcon())
                    .into(imgView);

            TextView txtTeamName = (TextView) layout2.findViewById(R.id.txtTeamName);
            txtTeamName.setText(leagueStanding.getTeamName());

            TextView txtWon = (TextView) layout2.findViewById(R.id.txtWon);
            txtWon.setText(leagueStanding.getTeamWins());

            TextView txtDrawn = (TextView) layout2.findViewById(R.id.txtDrawn);
            txtDrawn.setText(leagueStanding.getTeamDraw());

            TextView txtLost = (TextView) layout2.findViewById(R.id.txtLost);
            txtLost.setText(leagueStanding.getTeamLose());

            TextView txtFor = (TextView) layout2.findViewById(R.id.txtFor);
            txtFor.setText(leagueStanding.getGoalsFor());

            TextView txtAgainst = (TextView) layout2.findViewById(R.id.txtAgainst);
            txtAgainst.setText(leagueStanding.getGoalsAgainst());

            TextView txtPoints = (TextView) layout2.findViewById(R.id.txtPoints);
            txtPoints.setText(leagueStanding.getPoints());

            TextView txtForm = (TextView) layout2.findViewById(R.id.txtForm);
            txtForm.setText(leagueStanding.getTeamForm());

            ((ViewGroup) view.findViewById(R.id.TableScores)).addView(layout2);

        }
    }

}