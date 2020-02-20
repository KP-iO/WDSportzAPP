package com.example.wdsportz.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wdsportz.R;
import com.example.wdsportz.ViewModels.LeagueViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import static android.view.LayoutInflater.from;

/**
 * Created by khrishawn
 */
public class LeagueAdapter extends RecyclerView.Adapter<LeagueAdapter.MyViewHolder> {
    private List<LeagueViewModel> leagueViewModels;
    private LayoutInflater lInflater;
    private ItemClickListener lClickListener;
    private Context context;
    public NavController navController;

    public LeagueAdapter(Context context, List<LeagueViewModel> list) {
        this.lInflater = LayoutInflater.from(context);
        this.leagueViewModels = list;
        this.context = context;
    }




//    public WatchViewAdapter(Context context, List<VideoViewModel> leagueViewModels) {
//
//    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new  MyViewHolder(from(context).inflate(R.layout.video_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.title.setText(leagueViewModels.get(position).getLeagueTitle());
        String currentUrl = leagueViewModels.get(position).getLeagueImageURL();
        final String leagueURL = (leagueViewModels.get(position).getLeagueURL());
        final String title = (leagueViewModels.get(position).getLeagueTitle());

        Glide.with(context)
                .load(currentUrl)
                .into(holder.btnimg);

        holder.btnimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // used to bundle strings and enable it to be collected
                Bundle bundle = new Bundle();
                bundle.putString("title01", title);
                bundle.putString("URL", leagueURL);
                Navigation.findNavController(v).navigate(R.id.action_global_scores, bundle);
                for (String key: bundle.keySet())
                {
                    Log.d ("myApplication", key + " is a key in the bundle");
                }
            }
        });


    }

    @Override
    public int getItemCount() {

        return leagueViewModels.size();

    }




     public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageButton btnimg;
        ItemClickListener itemClickListener;
         FirebaseFirestore fireStoreDB = FirebaseFirestore.getInstance();


        MyViewHolder(View itemView) {
            super(itemView);

            title= itemView.findViewById(R.id.video_text);
            btnimg = itemView.findViewById(R.id.BtnImgVideo);
            btnimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent i= new Intent(view.getContext(), VideoPlayback.class);
                    Log.d("CLICK", title.getText() + "  Clicked");

//                    int itemPosition = getLayoutPosition();
//                    Log.d("position", Integer.toString(itemPosition));
//
//                    MyViewHolder item = (MyViewHolder) WatchViewModel.getItem(itemPosition);
//                    String url = item.getUrl();


//                    view.getContext().startActivity(i);


                }
            });
        }





         @Override
         public void onClick(View view) {
             if (lClickListener != null) lClickListener.onItemClick(view, getAdapterPosition());
             Log.d("CLICK", title.getText() + "  Clicked");

//             int itemPosition = getLayoutPosition();
//             Log.d("position", Integer.toString(itemPosition));
//
//             YourItemClass item = (YourItemClass)adapter.getItem(itemPosition);
//             String url = item.getUrl();
         }
    }
public String getItem(int id) {

        return leagueViewModels.get(id).getLeagueTitle();
    }

    public interface ItemClickListener {

        void onItemClick(View view, int position);
    }


}
