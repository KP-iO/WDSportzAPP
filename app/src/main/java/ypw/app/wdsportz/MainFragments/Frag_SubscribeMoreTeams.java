package ypw.app.wdsportz.MainFragments;

import android.content.Context;
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
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ypw.app.wdsportz.Adapters.SelectTeamsRecyclerViewAdapter;
import com.example.wdsportz.R;
import ypw.app.wdsportz.ViewModels.SelectTeamsRecyclerViewModel;
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
public class Frag_SubscribeMoreTeams extends Fragment {

    private frag_Register.OnFragmentInteractionListener mListener;

    public static final String ARG_OBJECT = "object";
    private static final String TAG = "iniTeamSelect_teams";
    FirebaseFirestore fireStoreDB = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    SelectTeamsRecyclerViewAdapter selectTeamsRecyclerViewAdapter = new SelectTeamsRecyclerViewAdapter();
    Menu menu1 ;
    ArrayList<String> leagueNames;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.initeamselectrecycler, container, false);
}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();

        leagueNames =  args.getStringArrayList(ARG_OBJECT);

        Log.d("LOADED TAB: ", String.valueOf(leagueNames));

        recyclerviewcontent(view, leagueNames);

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

    @Override
    public boolean onOptionsItemSelected (MenuItem searchItem){
        // Handle item selection
        switch (searchItem.getItemId()) {
            case R.id.search:
                getActivity().findViewById(R.id.lbl_selectfavteams).setVisibility(View.GONE);
                return true;
        }
        return super.onOptionsItemSelected(searchItem);
    }

    public void recyclerviewcontent(View view,  ArrayList<String> currentleagueNames){

        final Context context = view.getContext();
        // set up the RecyclerView
        recyclerView = getView().findViewById(R.id.RecyclerViewTeam);
        int numberOfColumns = 3;

        //The recycler view has been created using the references as above.
        recyclerView.setLayoutManager(new GridLayoutManager(context , numberOfColumns));

    // Implement error handling for all cases e.g (Name/ Logo not accessible) ------>
    //// Create a new method for the code below.
    ///+++ Or possibly migrate to the SelectTeamsRecyclerViewAdapter class

//// -----> this is where the images and text is added to the recycler's items from the db.

        List<SelectTeamsRecyclerViewModel> listOfTeams = new ArrayList<>();

            for (String currentLeague : currentleagueNames) {

                fireStoreDB.collection("Leagues").document(currentLeague).collection("Teams")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.get("teamName").toString() + document.get("teamLogo").toString());
                                        listOfTeams.add(new SelectTeamsRecyclerViewModel(document.get("teamName").toString(), document.get("teamLogo").toString(), document.get("teamId").toString()));

                                    }

                                    selectTeamsRecyclerViewAdapter = new SelectTeamsRecyclerViewAdapter(context, listOfTeams);
                                    recyclerView.setAdapter(selectTeamsRecyclerViewAdapter);

                                } else {
                                    Log.d(TAG, "Document Get Error");
                                }

                            }

                        });

            }

            Log.d(TAG, String.valueOf(listOfTeams));

    }

    public void getselections(View view){


}




}
