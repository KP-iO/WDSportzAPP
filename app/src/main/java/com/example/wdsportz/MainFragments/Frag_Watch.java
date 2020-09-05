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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wdsportz.Adapters.LiveStreamAdapter;
import com.example.wdsportz.Adapters.WatchViewAdapter;
import com.example.wdsportz.R;
import com.example.wdsportz.ViewModels.CategoriesModel;
import com.example.wdsportz.ViewModels.WatchViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Frag_Watch extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Frag_Watch fragwatch;
    private static final String TAG = "Video Activity";
    FirebaseFirestore fireStoreDB = FirebaseFirestore.getInstance();
    ChipGroup chipGroupSort;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;

    Menu menu;

    private WatchViewAdapter watchViewAdapter;
    private LiveStreamAdapter liveStreamAdapter;

    String VidUri;

    public Frag_Watch() {
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

        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_watch, container, false);
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

            case R.id.filter_btn:
                Log.d("ORE", "LOOOOOK HERE1");
//                DialogFragment DialogFragment_WatchSort = new DialogFragment_WatchSort ();
//                DialogFragment_WatchSort.show(getParentFragmentManager(), "createDialog");

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final Context context = view.getContext();



        chipGroupSort = getView().findViewById(R.id.chipGroupSort);
        chipGroupSort.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {
                Chip chip = chipGroup.findViewById(i);
                if(chip != null){
                    //show selection
                    Toast.makeText(getContext(), chip.getText().toString(),Toast.LENGTH_LONG).show();
                } else {
                    //Else show normal
                }
            }
        });

        recyclerView = getView().findViewById(R.id.RecyclerViewV);
        recyclerView1 = getView().findViewById(R.id.RecyclerViewVM);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(context, numberOfColumns));
        recyclerView1.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));


// Implement error handling for all cases e.g (Name/ Logo not accessible) ------>
        RecyclerSort(context);
        BottomRecycler(context);
        TopRecycler(context);
    }

    private void RecyclerSort(Context context) {

        Task<QuerySnapshot> docRef = fireStoreDB.collection("Categories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 0;
                            List<CategoriesModel> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {


                                Log.d(TAG, "DOCUMENT PRINT :" + document.getData().toString());
                                list.add(new CategoriesModel(document.get("Name").toString(),document.getId()));

                                Chip chip = (Chip) getLayoutInflater().inflate(R.layout.layout_chip_choice, chipGroupSort, false);
                                chip.setText(list.get(i).getTitle());
                                chipGroupSort.addView(chip);

                                i = i +1;
                            }




                        } else {
                            Log.d(TAG, "No such document");
                        }

                    }

                });

    }


    private void TopRecycler(final Context context) {
        Task<QuerySnapshot> docRef = fireStoreDB.collection("Live Stream")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            List<WatchViewModel> list = new ArrayList<>();


////// Change FROM download url to stroage url in firestore?

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                list.add(new WatchViewModel(document.get("Match_Name").toString(), document.get("Match_Image").toString(), document.get("Match_Video").toString(), document.get("Chatbox_ID").toString(), document.get("Video_desc").toString(), document.get("Live").toString(), document.get("Date").toString()));

                                //Log.d(TAG, ("LOGO URL: " + list.));

                                liveStreamAdapter = new LiveStreamAdapter(context, list);
                                recyclerView1.setAdapter(liveStreamAdapter);

                            }


                        } else {
                            Log.d(TAG, "No such document");
                        }

                    }

                });
    }

    private void BottomRecycler(final Context context) {
        Task<QuerySnapshot> docRef = fireStoreDB.collection("Videos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            List<WatchViewModel> list = new ArrayList<>();


////// Change FROM download url to stroage url in firestore?

                            for (QueryDocumentSnapshot document : task.getResult()) {


                                Log.d(TAG, "DOCUMENT PRINT :" + document.getData().toString());

                                list.add(new WatchViewModel(document.get("Match_Name").toString(), document.get("Match_Image").toString(), document.get("Match_Video").toString(), document.get("Chatbox_ID").toString(), document.get("Video_desc").toString()));

                                watchViewAdapter = new WatchViewAdapter(context, list);
                                recyclerView.setAdapter(watchViewAdapter);

                            }

                            // List check (in Log)
                            for (int i = 0; i < list.size() - 1; i++) {

                                Log.d(TAG, (" Team Name = " + list.get(i).getTitle()));
                                Log.d(TAG, "List Url test   " + list.get(i).getVideoImageURL());
                                Log.d(TAG, "Video Url test   " + list.get(i).getVideoURL());
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
