package com.example.wdsportz.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wdsportz.R;
import com.example.wdsportz.VideoPlayback;
import com.example.wdsportz.ViewModels.WatchViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import static android.view.LayoutInflater.from;

/**
 * Created by khrishawn
 */
public class WatchViewAdapter extends RecyclerView.Adapter<WatchViewAdapter.MyViewHolder> {
    private List<WatchViewModel> videoViewModels;
    private LayoutInflater lInflater;
    private ItemClickListener lClickListener;
    private Context context;

    public WatchViewAdapter(Context context, List<WatchViewModel> list) {
        this.lInflater = LayoutInflater.from(context);
        this.videoViewModels = list;
        this.context = context;
    }




//    public WatchViewAdapter(Context context, List<VideoViewModel> videoViewModels) {
//
//    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new  MyViewHolder(from(context).inflate(R.layout.video_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(videoViewModels.get(position).getTitle());
        String currentUrl = videoViewModels.get(position).getVideoimageURL();
        String Video = videoViewModels.get(position).getVideoURL();
        Glide.with(context)
                .load(currentUrl)
                .into(holder.btnimg);


    }

    @Override
    public int getItemCount() {

        return videoViewModels.size();

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
                    Intent i= new Intent(view.getContext(),VideoPlayback.class);
//                    i.putExtra("VideoURL", VideoViewModel.getVideoURL() );
                    view.getContext().startActivity(i);

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

        return videoViewModels.get(id).getTitle();
    }

    public interface ItemClickListener {

        void onItemClick(View view, int position);
    }


}
