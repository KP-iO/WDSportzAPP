package com.example.wdsportz.MainFragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wdsportz.Adapters.NewsFeedAdapter;
import com.example.wdsportz.R;
import com.example.wdsportz.ViewModels.NewsFeedViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Frag_Watch.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Frag_Watch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_HomePage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Frag_HomePage frag_homePage;
    private static final String TAG = "Home Fragment";
    FirebaseFirestore fireStoreDB = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private NewsFeedAdapter newsFeedAdapter;
    Context context;

    //Never change this in the code below (used for filter)
    ArrayList<NewsFeedViewModel> AllNewsList = new ArrayList<>();
    Menu menu;

    String VidUri;

    public Frag_HomePage() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        context = view.getContext();

        recyclerView1 = getView().findViewById(R.id.Main_feed);
        recyclerView1.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));


// Implement error handling for all cases e.g (Name/ Logo not accessible) ------>
        newsFeed(context);
    }

    //This initializes homepage filter menu in the activity's toolbar
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        //Filter initizaled here!
        inflater.inflate(R.menu.homepage_filter, menu);

        this.menu = menu;
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.AllNews:
                Log.d("ORE","LOOOOOK HERE1");
                showAll(context,AllNewsList);
                return true;
            case R.id.Following:
                Log.d("ORE","LOOOOOK HERE2");
                showFollowingTeams();
                return true;
            case R.id.WDRecommended:
                Log.d("ORE","LOOOOOK HERE3");
                showWDRecommended();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAll(final Context context, ArrayList allNewsList) {

        newsFeedAdapter = new NewsFeedAdapter(context, allNewsList);
        recyclerView1.setAdapter(newsFeedAdapter);

    }

    private void showFollowingTeams() {
        //test list
        ArrayList<NewsFeedViewModel> emptyArray = new ArrayList<>();

        newsFeedAdapter = new NewsFeedAdapter(context, emptyArray);
        recyclerView1.setAdapter(newsFeedAdapter);

    }

    private void showWDRecommended() {
        //new WDRecommnended List
        ArrayList<NewsFeedViewModel> wdRecommnendedList = new ArrayList<>();

        for(int i = 0; i < AllNewsList.size(); i++) {

            try {

                if (AllNewsList.get(i).getIsWDRecommended()) {

                    wdRecommnendedList.add(AllNewsList.get(i));
                }

            }catch(Exception e){
                /// if not wdrecoomeneded or null
            }
        }

        newsFeedAdapter = new NewsFeedAdapter(context, wdRecommnendedList);
        recyclerView1.setAdapter(newsFeedAdapter);
    }




    private void newsFeed(final Context context) {

        Task<QuerySnapshot> docRef = fireStoreDB.collection("news_id")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                ////// Change FROM download url to stroage url in firestore?

                            for (QueryDocumentSnapshot document : task.getResult()) {

//                                Log.d("DOCUMENT PRINT :", document.getData().toString());
//                                Log.d("Team Added to List ", document.get("Title").toString());

//                                     * @param title
//                                        * @param newsImageURL
//                                        * @param newsDesc
//                                        * @param date
//                                        * @param chatbox_id
//                                        * @param isWDRecommended

                                AllNewsList.add(new NewsFeedViewModel(
                                        document.get("Title").toString(),
                                        document.get("Image").toString(),
                                        document.get("Description").toString(),
                                        document.get("Date").toString(),
                                        document.get("Chatbox_ID").toString(),
                                        ((Boolean) document.get("WDReccomended"))));

                                //Log.d(TAG, ("LOGO URL: " + list.));

                                newsFeedAdapter = new NewsFeedAdapter(context, AllNewsList);
                                recyclerView1.setAdapter(newsFeedAdapter);

                            }

                            // List check (in Log)
                            for (int i = 0; i < AllNewsList.size() - 1; i++) {

                                Log.d(TAG, (" News Title = " + AllNewsList.get(i).getTitle()));
                                Log.d(TAG, " News Description =   " + AllNewsList.get(i).getNewsDesc());
                                Log.d(TAG, "Image URL =   " + AllNewsList.get(i).getNewsImageURL());
                            }

                        } else {
                            Log.d(TAG, "No such document");
                        }

                    }

                });
    }





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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}



