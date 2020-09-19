package com.example.wdsportz.Adapters;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wdsportz.R;
import com.example.wdsportz.ViewModels.ScoreViewModel;

import java.util.List;

import static android.view.LayoutInflater.from;

/**
 * Created by khrishawn
 */

public class ScoreFeedAdpater extends RecyclerView.Adapter<ScoreFeedAdpater.MyViewHolder> {

    private Context mContext;
    private List<ScoreViewModel> mData;

    public ScoreFeedAdpater(Context mContext, List<ScoreViewModel> mData){
        this.mContext = mContext;
        this.mData = mData;
    }


    @NonNull
    @Override

    public ScoreFeedAdpater.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScoreFeedAdpater.MyViewHolder(from(mContext).inflate(R.layout.match_tiles, parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull ScoreFeedAdpater.MyViewHolder holder, int i) {
        String HTeam = mData.get(i).getHomeTeam();
        String ATeam = mData.get(i).getAwayTeam();
        String HTeamScore = mData.get(i).getHomeScore();
        String ATeamScore = mData.get(i).getAwayScore();
        String homeImage = mData.get(i).getHomeIcon();
        String awayImage = mData.get(i).getAwayIcon();


        holder.awayName.setText(HTeam);
        holder.homeName.setText(ATeam);
        holder.awayScore.setText(ATeamScore);
        holder.homeScore.setText(HTeamScore);

        Glide.with(mContext)
                .load(homeImage)
                .into(holder.homeImage);

        Glide.with(mContext)
                .load(awayImage)
                .into(holder.awayImage);

    }


    @Override
    public int getItemCount(){
//        int i = 2;
//        return i;
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView  matchDate, awayName, homeName, awayScore, homeScore;
        ImageView homeImage, awayImage;



        public MyViewHolder(View view){
            super(view);
            homeImage = view.findViewById(R.id.homeImg);
            awayImage = view.findViewById(R.id.awayImg);
            awayName = (TextView) view.findViewById(R.id.awayName);
            homeName = (TextView) view.findViewById(R.id.homeName);
            awayScore = (TextView) view.findViewById(R.id.awayScore);
            homeScore = (TextView) view.findViewById(R.id.homeScore);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    displayPopupWindow(v);
                }
            });
        }
    }


    private void displayPopupWindow(View anchorView) {

        PopupWindow popup = new PopupWindow(mContext);

//        return new ScoreFeedAdpater.MyViewHolder(from(mContext).inflate(R.layout.match_tiles, parent,false));

        View layout = LayoutInflater.from(mContext).inflate(R.layout.popup_content, null);
        popup.setContentView(layout);
        // Set content width and height
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        // Show anchored to button
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAsDropDown(anchorView);

    }


}