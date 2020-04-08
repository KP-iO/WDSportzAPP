package com.example.wdsportz.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class SelectTeamsRecyclerViewAdapter extends RecyclerView.Adapter<SelectTeamsRecyclerViewAdapter.ViewHolder> {

    private List<SelectTeamsRecyclerViewModel> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;
    public static ArrayList<String> list = new ArrayList<>();
    String team, team1;








//    String [] T

    // data is passed into the constructor
    public SelectTeamsRecyclerViewAdapter(Context context, List<SelectTeamsRecyclerViewModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    public SelectTeamsRecyclerViewAdapter() {

    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.initeamselectionitem, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



        holder.textView.setText(mData.get(position).teamName);

        String currentUrl = mData.get(position).teamLogoURl;





        Glide.with(context)
                .load(currentUrl)
                .apply(new RequestOptions().override(180, 270))
                .into(holder.ImageView);

        // Mark as selected
        // Get the selected
        // Get the names
        // Add to array
        // Send array to database



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialCardView cardView = v.findViewById(R.id.selectionTile);
                cardView.toggle();
                if (cardView.isChecked()){
                team = mData.get(position).teamName;
                list.add(team);

            }else {
                    team1 = mData.get(position).teamName;
                    list.remove(team1);




                }
                Log.d("CLICK", Arrays.toString(getArrayList().toArray()) + "  Clicked");




        }




        });



    }
    public String getTeamN()
    {
        return team;
    }

    public static ArrayList<String> getArrayList()
    {
        return  list;
    }




    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MaterialCardView cardView;
        TextView textView;
        ImageView ImageView;

        ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.selectionTile);
            textView = itemView.findViewById(R.id.info_text);
            ImageView = itemView.findViewById(R.id.BtnImgTeamLogo);




        }


        @Override
        public void onClick(View view) {

            Log.d("CLICK", textView.getText() + "  Clicked");

        }
    }






    // convenience method for getting data at click position
    public String getItem(int id) {

        return mData.get(id).teamName;
    }

    // parent activity will implement this method to respond to click events

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}