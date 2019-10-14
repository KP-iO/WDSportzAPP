package com.example.wdsportz;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IniTeamSelection extends AppCompatActivity implements SelectTeamsRecyclerViewAdapter.ItemClickListener {

    IniTeamSelection iniTeamSelection = this;

    private static final String TAG = "MainActivity";
    FirebaseFirestore fireStoreDB = FirebaseFirestore.getInstance();

    private RecyclerView recyclerView;
    private SelectTeamsRecyclerViewAdapter selectTeamsRecyclerViewAdapter;

    //https://stackoverflow.com/questions/48169265/creating-a-top-navigation-menu << For League Selector & Scores Tab!!!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectteams);

        // set up the RecyclerView
        recyclerView = findViewById(R.id.RecyclerView);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));


// Implement error handling for all cases e.g (Name/ Logo not accessible) ------>

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

                                    selectTeamsRecyclerViewAdapter = new SelectTeamsRecyclerViewAdapter(iniTeamSelection, list);
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

    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d("TAG", "You clicked number " + selectTeamsRecyclerViewAdapter.getItem(position) + ", which is at cell position " + position);
    }
}