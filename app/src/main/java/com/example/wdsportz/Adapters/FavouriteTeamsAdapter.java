package com.example.wdsportz.Adapters;


import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wdsportz.R;
import com.example.wdsportz.ViewModels.FavouriteTeamsViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


/**
 * Created by khrishawn
 */


public class FavouriteTeamsAdapter extends RecyclerView.Adapter<FavouriteTeamsAdapter.MyViewHolder> {


    private Context mContext;
    private List<FavouriteTeamsViewModel> mData;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    DatabaseReference databaseReference;
    ProgressDialog pd;
    String reportComment, deleteCommment;
    FirebaseDatabase reportedDatabase,commentsDatabase;
    DatabaseReference reference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String currentUID = user.getUid();
    UserInfo userInfo;
    String user1 = FirebaseAuth.getInstance().getUid();
    static String USERS = "Users";






    public FavouriteTeamsAdapter(Context mContext, List<FavouriteTeamsViewModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.favouriteteamstile, parent, false);

        return new MyViewHolder(row);
    }



    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        String Url = mData.get(position).getTeamImg();
        holder.title.setText(mData.get(position).getTeam());
        Glide.with(mContext)
                .load(Url)
                .into(holder.image);
    }



    @Override
    public int getItemCount() {
        return mData.size() ;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

      TextView title;
      ImageView image;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.favouriteTeam);
            image = itemView.findViewById(R.id.teamImg);

        }
    }








//    public static final String DATE_FORMAT_1 = "hh:mm a";

//        private String timestampToString(long time){
//
//        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
//        calendar.setTimeInMillis(time);
//        String date = DateFormat.format(DATE_FORMAT_1, calendar).toString();
//        return date;
//        }
}