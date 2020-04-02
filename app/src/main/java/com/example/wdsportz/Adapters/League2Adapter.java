package com.example.wdsportz.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wdsportz.Model.League;
import com.example.wdsportz.R;

import java.util.List;

import static android.view.LayoutInflater.from;

/**
 * Created by khrishawn
 */

public class League2Adapter extends RecyclerView.Adapter<League2Adapter.MyViewHolder> {

    private Context mContext;
    private List<League> mData;

    public League2Adapter(Context mContext, List<League> mData){
        this.mContext = mContext;
        this.mData = mData;
    }


    @NonNull
    @Override

    public League2Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new League2Adapter.MyViewHolder(from(mContext).inflate(R.layout.match_tiles, parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull League2Adapter.MyViewHolder holder, int i) {
        holder.matchTitle.setText(mData.get(i).getStrEvent());
        holder.awayName.setText(mData.get(i).getStrAwayTeam());
        holder.homeName.setText(mData.get(i).getStrHomeTeam());

        String HTeam = mData.get(i).getStrHomeTeam();
        String ATeam = mData.get(i).getStrAwayTeam();
        String HTeamScore = Double.toString(mData.get(i).getIntHomeScore());
        String ATeamScore = Double.toString(mData.get(i).getIntAwayScore());
        String date = mData.get(i).getDateEventLocal();


    }


    @Override
    public int getItemCount(){
//        int i = 2;
//        return i;
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView matchTitle, matchDate, awayName, homeName;


        public MyViewHolder(View view){
            super(view);
            matchTitle = (TextView) view.findViewById(R.id.eventName);
            matchDate = (TextView) view.findViewById(R.id.matchDate);
            awayName = (TextView) view.findViewById(R.id.awayName);
            homeName = (TextView) view.findViewById(R.id.homeName);



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