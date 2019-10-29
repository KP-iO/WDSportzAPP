package com.example.wdsportz.MainFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wdsportz.Adapters.WatchViewAdapter;
import com.example.wdsportz.R;
import com.example.wdsportz.ViewModels.VideoViewModel;
import com.example.wdsportz.ViewModels.WatchViewModel;
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
public class Frag_Watch extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Frag_Watch fragWatch = this;
    private static final String TAG = "Video Activity";
    FirebaseFirestore fireStoreDB = FirebaseFirestore.getInstance();
    public RecyclerView recyclerView;
    public RecyclerView recyclerView1;

    private WatchViewAdapter videoViewAdapter;
    String VidUri;

    public Frag_Watch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_Watch.
     */
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
        return inflater.inflate(R.layout.fragment_watch, container, false);
    }


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //setContentView(R.layout.fragment_watch);

        final Context context = getContext();

        recyclerView = view.findViewById(R.id.RecyclerViewV);
        recyclerView1 = view.findViewById(R.id.RecyclerViewVM);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(context, numberOfColumns));
        recyclerView1.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));


// Implement error handling for all cases e.g (Name/ Logo not accessible) ------>

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
                                Log.d(TAG, "Team Added to List " + document.get("Match_Name").toString());

                                list.add(new WatchViewModel(document.get("Match_Name").toString(), document.get("Match_Image").toString(), document.get("Match_Video").toString()));

                                //Log.d(TAG, ("LOGO URL: " + list.));

                                videoViewAdapter = new WatchViewAdapter(context, list);
                                recyclerView.setAdapter(videoViewAdapter);
                                recyclerView1.setAdapter(videoViewAdapter);


                            }

                            // List check (in Log)
                            for (int i = 0; i < list.size() - 1; i++) {

                                Log.d(TAG, (" Team Name = " + list.get(i).getTitle()));
                                Log.d(TAG, "List Url test   " + list.get(i).getVideoimageURL());
                            }

                        } else {
                            Log.d(TAG, "No such document");
                        }

                    }

                });
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
