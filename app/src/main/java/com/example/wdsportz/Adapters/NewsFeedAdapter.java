package com.example.wdsportz.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wdsportz.R;
import com.example.wdsportz.ViewModels.NewsFeedViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import static android.view.LayoutInflater.from;

/**
 * Created by khrishawn
 */
public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.MyViewHolder> {

    private List<NewsFeedViewModel> newsFeedViewModels;
    private LayoutInflater lInflater;
    private ItemClickListener lClickListener;
    private Context context;

    public NewsFeedAdapter(Context context, List<NewsFeedViewModel> list) {
        this.lInflater = LayoutInflater.from(context);
        this.newsFeedViewModels = list;
        this.context = context;
    }


//    public WatchViewAdapter(Context context, List<VideoViewModel> newsFeedViewModels) {
//
//    }


    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new  MyViewHolder(from(context).inflate(R.layout.fragment_homepage_feeditem, parent,false));
    }



    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.title.setText(newsFeedViewModels.get(position).getTitle());
        holder.description.setText(newsFeedViewModels.get(position).getNewsDesc());
        final String currentUrl = newsFeedViewModels.get(position).getNewsImageURL();
        String date1 = newsFeedViewModels.get(position).getDate();


        final String description1 = newsFeedViewModels.get(position).getNewsDesc();
        final String title1 = newsFeedViewModels.get(position).getTitle();



        Glide.with(context)
                .load(currentUrl)
                .into(holder.newsView);


        holder.newsTile.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   Bundle bundle = new Bundle();
                                                   bundle.putString("image",currentUrl);
                                                   bundle.putString("desc", description1);
                                                   bundle.putString("title", title1);
                                                   Navigation.findNavController(v).navigate(R.id.action_global_newsDetail, bundle);
                                                   for (String key: bundle.keySet())
                                                   {
                                                       Log.d ("myApplication", key + " is a key in the bundle");
                                                   }
                                               }
                                                   
                                               });




    }


    @Override
    public int getItemCount() {

        return newsFeedViewModels.size();

    }




    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView description;
        CardView newsTile;
        ImageView newsView;
        ItemClickListener itemClickListener;
        FirebaseFirestore fireStoreDB = FirebaseFirestore.getInstance();
        private ItemClickListener lClickListener;


        MyViewHolder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.newsDescription);
            title = itemView.findViewById(R.id.newsTitle);
            newsTile = itemView.findViewById(R.id.newsCard);
            newsView = itemView.findViewById(R.id.homeImg);
            newsTile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("CLICK", title.getText() + "  Clicked");




                }
            });
        }





        @Override
        public void onClick(View view) {
            if (lClickListener != null) lClickListener.onItemClick(view, getAdapterPosition());
            Log.d("CLICK", title.getText() + "  Clicked");


        }
    }
    public String getItem(int id) {

        return newsFeedViewModels.get(id).getTitle();
    }

    public interface ItemClickListener {

        void onItemClick(View view, int position);
    }


}
