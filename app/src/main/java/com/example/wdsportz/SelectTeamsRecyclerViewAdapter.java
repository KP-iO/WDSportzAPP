package com.example.wdsportz;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SelectTeamsRecyclerViewAdapter extends RecyclerView.Adapter<SelectTeamsRecyclerViewAdapter.ViewHolder> {

    private List<SelectTeamsRecyclerViewModel> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    SelectTeamsRecyclerViewAdapter(Context context, List<SelectTeamsRecyclerViewModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.selectteam_recyclerviewitem, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.myTextView.setText(mData.get(position).teamName);
        String currentUrl = mData.get(position).teamLogoURl;
        Glide.with(context)
                .load(currentUrl)
                .into(holder.myImageView);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        ImageView myImageView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.info_text);
            myImageView = itemView.findViewById(R.id.BtnImgTeamLogo);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            Log.d("CLICK", myTextView.getText() + "  Clicked");
        }

    }

    // convenience method for getting data at click position
    String getItem(int id) {

        return mData.get(id).teamName;
    }

    // parent activity will implement this method to respond to click events

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
