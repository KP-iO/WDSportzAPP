package com.example.wdsportz.MainFragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wdsportz.Adapters.SelectTeamsRecyclerViewAdapter;
import com.example.wdsportz.R;
import com.example.wdsportz.ViewModels.SelectTeamsRecyclerViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

// Instances of this class are fragments representing a single
// object in our collection.

// For the feed inside the recycler view, inside one of the tabs. Each tabs's content is created by this
public class Frag_iniTeamSelect_teams extends Fragment {

    public static final String ARG_OBJECT = "object";
    private static final String TAG = "iniTeamSelect_teams";
    FirebaseFirestore fireStoreDB = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    SelectTeamsRecyclerViewAdapter selectTeamsRecyclerViewAdapter = new SelectTeamsRecyclerViewAdapter();
    Menu menu1 ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.initeamselectrecycler, container, false);
}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();

        String TabArgs =  String.valueOf(args.getInt(ARG_OBJECT));
        Log.d("LOADED TAB: ", TabArgs);

        int tab = args.getInt(ARG_OBJECT);

        switch(tab) {
            case 1:
                recyclerviewcontent(view,"Suggested");
                break;
            //case y:
                // code block
              //  break;
            //default:
                // code block
        }


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);

        this.menu1 = menu;

        MenuItem searchItem = menu1.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                selectTeamsRecyclerViewAdapter.getFilter().filter(newText);

                return false;
            }
        });

    }



    public void recyclerviewcontent(View view, String str){

        final Context context = view.getContext();
        // set up the RecyclerView
        recyclerView = getView().findViewById(R.id.RecyclerViewTeam);
        int numberOfColumns = 2;

        //The recycler view has been created using the references as above.
        recyclerView.setLayoutManager(new GridLayoutManager(context , numberOfColumns));

    // Implement error handling for all cases e.g (Name/ Logo not accessible) ------>
    //// Create a new method for the code below.
    ///+++ Or possibly migrate to the SelectTeamsRecyclerViewAdapter class

//// -----> this is where the images and text is added to the recycler's items from the db.
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

                            selectTeamsRecyclerViewAdapter = new SelectTeamsRecyclerViewAdapter(context, list);
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
}
