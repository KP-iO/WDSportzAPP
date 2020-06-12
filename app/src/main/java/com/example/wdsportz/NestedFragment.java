package com.example.wdsportz;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wdsportz.Adapters.SelectTeamsRecyclerViewAdapter;
import com.example.wdsportz.MainActivities.Auth_Activity;

import java.util.Observable;
import java.util.Observer;

public class NestedFragment extends Fragment implements Observer {
    private boolean listUpdated = false; // init the update checking value

            // setup the listview and the list adapter
    RecyclerView recyclerView = getView().findViewById(R.id.RecyclerViewTeam);
    SelectTeamsRecyclerViewAdapter selectTeamsRecyclerViewAdapter;

    // use onResume to filter the list if it's not already done
    @Override
    public void onResume() {
        super.onResume();
        // get the filter value
        final String query = Auth_Activity.getFilterManager().getQuery();
        if (recyclerView != null && selectTeamsRecyclerViewAdapter != null
                && query != null && !listUpdated) {
            // update the list with filter value
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    listUpdated = true; // set the update checking value
                    selectTeamsRecyclerViewAdapter.getFilter().filter(query);
                }
            });
        }
    }

    // automatically triggered when setChanged() and notifyObservers() are called
    public void update(Observable obs, Object obj) {
        if (obs instanceof FilterManager) {
            String result = ((FilterManager) obs).getQuery(); // retrieve the search value
            if (selectTeamsRecyclerViewAdapter != null) {
                listUpdated = true; // set the update checking value
                selectTeamsRecyclerViewAdapter.getFilter().filter(result); // filter the list (with #2)
            }
        }
    }
}