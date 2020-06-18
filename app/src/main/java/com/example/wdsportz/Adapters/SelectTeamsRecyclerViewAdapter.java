package com.example.wdsportz.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.wdsportz.R;
import com.example.wdsportz.ViewModels.SelectTeamsRecyclerViewModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectTeamsRecyclerViewAdapter extends RecyclerView.Adapter<SelectTeamsRecyclerViewAdapter.ViewHolder> implements Filterable {

    private List<SelectTeamsRecyclerViewModel> mData;
    private List<SelectTeamsRecyclerViewModel> mDataFull;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;
    public static ArrayList<String> list = new ArrayList<String>();
    public static ArrayList<String> UrlList = new ArrayList<String>();
    String selectedTeam;
    String selectedUrl;
    String team1;

    public SelectTeamsRecyclerViewAdapter() {

    }

    // data is passed into the constructor
    public SelectTeamsRecyclerViewAdapter(Context context, List<SelectTeamsRecyclerViewModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        mDataFull = new ArrayList<>(data);
        this.context = context;
    }


    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.initeamselectionitem, parent, false);
        return new ViewHolder(view);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MaterialCardView cardView;
        TextView textView;
        ImageView ImageView;

        ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.selectionTile);
            textView = itemView.findViewById(R.id.TxtTeamName);
            ImageView = itemView.findViewById(R.id.BtnImgTeamLogo);

        }


        @Override
        public void onClick(View view) {
            Log.d("CLICK", textView.getText() + "  Clicked");
        }
    }


    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(mData.get(position).teamName);

        String currentUrl = mData.get(position).teamLogoURl;

        Glide.with(context)
                .load(currentUrl)
                .apply(new RequestOptions().override(150, 250))
                .into(holder.ImageView);

        // Mark as selected
        // Get the selected
        // Get the names
        // Add to array
        // Send array to database


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialCardView cardView = v.findViewById(R.id.selectionTile);
                cardView.toggle();
                if (cardView.isChecked()){
                    selectedTeam = mData.get(position).teamId;
                    selectedUrl = mData.get(position).teamLogoURl;

                list.add(selectedTeam);
                UrlList.add(selectedUrl);

            }else {
                    selectedTeam = mData.get(position).teamId;
                    selectedUrl = mData.get(position).teamLogoURl;
                    list.remove(selectedTeam);
                    UrlList.remove(selectedUrl);

                }
                Log.d("CLICK", Arrays.toString(getArrayList().toArray()) + "  Clicked");

        }

        });

    }



    @Override
    public Filter getFilter() {

        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<SelectTeamsRecyclerViewModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0 ) {
                filteredList.addAll(mDataFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (SelectTeamsRecyclerViewModel item : mDataFull) {
                    if (item.getText1().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mData.clear();
            mData.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };


//    public String getTeamN()
//    {
//        return selectedTeam;
//    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // convenience method for getting data at click position
    public String getItem(int id) {

        return mData.get(id).teamName;
    }

    public static ArrayList<String> getArrayList()
    {
        return  list;
    }

    public static ArrayList<String> getArrayUrlList()
    {
        return  UrlList;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}