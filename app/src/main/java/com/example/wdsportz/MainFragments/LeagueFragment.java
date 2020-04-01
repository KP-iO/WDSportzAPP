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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wdsportz.API.Client;
import com.example.wdsportz.API.Service;
import com.example.wdsportz.Adapters.League2Adapter;
import com.example.wdsportz.BuildConfig;
import com.example.wdsportz.Model.League;
import com.example.wdsportz.Model.LeagueResponse;
import com.example.wdsportz.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Watchfragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Watchfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeagueFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Watchfragment watchfragment;
    private static final String TAG = "Video Activity";
    FirebaseFirestore fireStoreDB = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private List<League> leagueList;
    private League2Adapter adapter;


    ProgressDialog pd;


    public LeagueFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Watchfragment newInstance(String param1, String param2) {
        Watchfragment fragment = new Watchfragment();
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
        return inflater.inflate(R.layout.fragment_league, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final Context context = view.getContext();
        initViews();
        Log.d("initViews","done");
        loadJSON();
        Log.d("loadJSON","done");

        leagueList = new ArrayList<>();
        adapter = new League2Adapter(getContext(), leagueList);
// Implement error handling for all cases e.g (Name/ Logo not accessible) ------>

    }

    private void loadJSON() {

//        recyclerView = getView().findViewById(R.id.recyclerleague);


        /*try {
            if (BuildConfig.SPORTS_DB_API.isEmpty()) {
                Toast.makeText(getContext(), "Please obtain API Key firstly from SPORTSDB", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                return;
            }
*/
            Client Client = new Client();
            Log.d("Check", Client.toString());

            Service apiService = Client.getClient().create(Service.class);
            Log.d("Check 2", apiService.toString());

            Call<LeagueResponse> call = apiService.getEvents(BuildConfig.SPORTS_DB_API);
            call.enqueue(new Callback<LeagueResponse>() {

                @Override
                public void onResponse(Call<LeagueResponse> call, Response<LeagueResponse> response) {
                    List<League> movies = response.body().getMatches();

                    LeagueResponse leagues = response.body();
                    Log.d("SUCCESS", movies.toString());
                    recyclerView.setAdapter(new League2Adapter(getContext(), movies));
                    recyclerView.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(Call<LeagueResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(getContext(), "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                }

            });
       /* } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }*/
    }

    private void initViews() {

        final Context context = getContext();

        recyclerView = getView().findViewById(R.id.recyclerleague);

        leagueList = new ArrayList<>();
        //adapter = new League2Adapter(getContext(), leagueList);


        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }

       // recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();

    }


//    private void BottomRecycler(final Context context) {
//        Task<QuerySnapshot> docRef = fireStoreDB.collection("leaguedetails")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//
//                            List<LeagueViewModel> list = new ArrayList<>();
//
//
//////// Change FROM download url to stroage url in firestore?
//
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//
//
//                                Log.d(TAG, "DOCUMENT PRINT :" + document.getData().toString());
//                                Log.d(TAG, "Team Added to List " + document.get("League_Name").toString());
//
//                                list.add(new LeagueViewModel(document.get("League_Name").toString(), document.get("League_Image").toString(), document.get("League_URL").toString()));
//
//                                //Log.d(TAG, ("LOGO URL: " + list.));
//
//                                leagueAdapter = new LeagueAdapter(context, list);
//                                recyclerView.setAdapter(leagueAdapter);
////                                recyclerView1.setAdapter(watchViewAdapter);
//
//                            }
//
//                            // List check (in Log)
//                            for (int i = 0; i < list.size() - 1; i++) {
//
//                                Log.d(TAG, (" Team Name = " + list.get(i).getLeagueTitle()));
//                                Log.d(TAG, "List Url test   " + list.get(i).getLeagueImageURL());
//                                Log.d(TAG, "Video Url test   " + list.get(i).getLeagueURL());
//
//                            }
//
//                        } else {
//                            Log.d(TAG, "No such document");
//                        }
//
//                    }
//
//                });
//    }
//    private void BottomRecycler(final Context context) {
//        Task<QuerySnapshot> docRef = fireStoreDB.collection("leaguedetails")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//
//                            List<LeagueViewModel> list = new ArrayList<>();
//
//
//////// Change FROM download url to stroage url in firestore?
//
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//
//
//                                Log.d(TAG, "DOCUMENT PRINT :" + document.getData().toString());
//                                Log.d(TAG, "Team Added to List " + document.get("League_Name").toString());
//
//                                list.add(new LeagueViewModel(document.get("League_Name").toString(), document.get("League_Image").toString(), document.get("League_URL").toString()));
//
//                                //Log.d(TAG, ("LOGO URL: " + list.));
//
//                                leagueAdapter = new LeagueAdapter(context, list);
//                                recyclerView.setAdapter(leagueAdapter);
////                                recyclerView1.setAdapter(watchViewAdapter);
//
//                            }
//
//                            // List check (in Log)
//                            for (int i = 0; i < list.size() - 1; i++) {
//
//                                Log.d(TAG, (" Team Name = " + list.get(i).getLeagueTitle()));
//                                Log.d(TAG, "List Url test   " + list.get(i).getLeagueImageURL());
//                                Log.d(TAG, "Video Url test   " + list.get(i).getLeagueURL());;
//                            }
//
//                        } else {
//                            Log.d(TAG, "No such document");
//                        }
//
//                    }
//
//                });
//    }


//
//    @Override
////    public void onAttach(Context context) {
////        super.onAttach(context);
////        if (context instanceof OnFragmentInteractionListener) {
////            mListener = (OnFragmentInteractionListener) context;
////        } else {
////            throw new RuntimeException(context.toString()
////                    + " must implement OnFragmentInteractionListener");
////        }
////    }

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