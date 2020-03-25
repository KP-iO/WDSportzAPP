package com.example.wdsportz.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.telephony.IccOpenLogicalChannelResponse;
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

import java.util.List;

public class SelectTeamsRecyclerViewAdapter extends RecyclerView.Adapter<SelectTeamsRecyclerViewAdapter.ViewHolder> {

    private List<SelectTeamsRecyclerViewModel> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    public SelectTeamsRecyclerViewAdapter(Context context, List<SelectTeamsRecyclerViewModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.ini_team_selection_item, parent, false);
        return new ViewHolder(view);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MaterialCardView cardView;
        TextView TextView;
        ImageView ImageView;

        ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.newsCard);
            TextView = itemView.findViewById(R.id.info_text);
            ImageView = itemView.findViewById(R.id.BtnImgTeamLogo);


            itemView.setOnClickListener(this);
           // cardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            if (mClickListener != null)

                mClickListener.onItemClick(view, getAdapterPosition());

                int highlightcolor = Color.YELLOW;
                cardView.setStrokeColor(highlightcolor);
                Log.d("CLICK", TextView.getText() + "  Clicked");

        }

     }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.TextView.setText(mData.get(position).teamName);

        String currentUrl = mData.get(position).teamLogoURl;

        Glide.with(context)
                .load(currentUrl)
                .apply(new RequestOptions().override(180, 270))
                .into(holder.ImageView);

    }

    @Override
    public int getItemCount() {
        return mData.size();
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