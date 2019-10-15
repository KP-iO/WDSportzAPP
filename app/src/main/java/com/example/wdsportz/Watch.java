package com.example.wdsportz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wdsportz.Adapter.VideoViewAdapter;
import com.example.wdsportz.viewmodels.VideoViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khrishawn
 */
public class Watch extends FragmentActivity implements VideoViewAdapter.ItemClickListener {

    Watch watch = this;
    private static final String TAG = "Video Activity";
    FirebaseFirestore fireStoreDB = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;


    private VideoViewAdapter videoViewAdapter;
    String VidUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_watch);

        recyclerView = findViewById(R.id.RecyclerViewV);
        recyclerView1 = findViewById(R.id.RecyclerViewVM);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


// Implement error handling for all cases e.g (Name/ Logo not accessible) ------>

        Task<QuerySnapshot> docRef = fireStoreDB.collection("Isthmian_Teams")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            List<VideoViewModel> list = new ArrayList<>();


////// Change FROM download url to stroage url in firestore?

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String Uri1 = document.get("Team_Video_Play").toString();


                                Log.d(TAG, "DOCUMENT PRINT :" + document.getData().toString());
                                Log.d(TAG, "Team Added to List " + document.get("Team_Name").toString());

                                list.add(new VideoViewModel(document.get("Team_Name").toString(), document.get("Team_Logo_Location").toString(), document.get("Team_Video_Play").toString()));

                                //Log.d(TAG, ("LOGO URL: " + list.));

                                videoViewAdapter = new VideoViewAdapter(watch, list);
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


    public void vidView(View view) {


    }


    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, VideoPlayback.class);
        startActivity(intent);

    }
}


