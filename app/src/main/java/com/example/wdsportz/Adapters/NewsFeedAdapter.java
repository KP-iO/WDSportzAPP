package com.example.wdsportz.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wdsportz.NewsDetail;
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
        return new  MyViewHolder(from(context).inflate(R.layout.fragment_mainfeed_row, parent,false));
    }



    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.title.setText(newsFeedViewModels.get(position).getTitle());
        holder.description.setText(newsFeedViewModels.get(position).getNewsDesc());
        final String currentUrl = newsFeedViewModels.get(position).getNewsImageURL();

        final String description1 = newsFeedViewModels.get(position).getNewsDesc();
        final String title1 = newsFeedViewModels.get(position).getTitle();



        Glide.with(context)
                .load(currentUrl)
                .into(holder.newsView);


        holder.newsTile.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   Intent i = new Intent(v.getContext(), NewsDetail.class);
                                                   i.putExtra("Description", description1);
                                                   i.putExtra("Title", title1);
                                                   i.putExtra("Image", currentUrl);
                                                   v.getContext().startActivity(i);
                                                   
                                               }
                                           });
//        holder.newsTile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(v.getContext(), YouTube_playback.class);
//                i.putExtra("Video", Video1);
//                v.getContext().startActivity(i);
//            }
//        });


    }


    @Override
    public int getItemCount() {

        return newsFeedViewModels.size();

    }




    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView description;
        CardView newsTile;
        ImageView newsView;
        ItemClickListener itemClickListener;
        FirebaseFirestore fireStoreDB = FirebaseFirestore.getInstance();


        MyViewHolder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.newsDescription);
            title = itemView.findViewById(R.id.newsTitle);
            newsTile = itemView.findViewById(R.id.newsCard);
            newsView = itemView.findViewById(R.id.newsImage);
            newsTile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent i= new Intent(view.getContext(),VideoPlayback.class);
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

        return newsFeedViewModels.get(id).getTitle();
    }

    public interface ItemClickListener {

        void onItemClick(View view, int position);
    }


}
