package com.example.wdsportz.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        String date = mData.get(i).getEventDate();
        String homeImage = mData.get(i).getHomeIcon();
        String awayImage = mData.get(i).getAwayIcon();


        holder.matchDate.setText(date);
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
            matchDate = (TextView) view.findViewById(R.id.matchDate);
            awayName = (TextView) view.findViewById(R.id.awayName);
            homeName = (TextView) view.findViewById(R.id.homeName);
            awayScore = (TextView) view.findViewById(R.id.awayScore);
            homeScore = (TextView) view.findViewById(R.id.homeScore);




//            view.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View v){
//                    int pos = getAdapterPosition();
//                    if (pos != RecyclerView.NO_POSITION){
//                        Movie clickedDataItem = movieList.get(pos);
//                        Intent intent = new Intent(mContext, DetailActivity.class);
//                        intent.putExtra("movies", clickedDataItem );
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        mContext.startActivity(intent);
//                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getOriginalTitle(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
        }
    }
}