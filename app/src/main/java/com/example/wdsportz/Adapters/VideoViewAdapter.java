package com.example.wdsportz.Adapter;

import android.content.Context;
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
import com.example.wdsportz.viewmodels.VideoViewModel;

import java.util.ArrayList;

import static android.view.LayoutInflater.from;

/**
 * Created by khrishawn
 */
public class VideoViewAdapter extends RecyclerView.Adapter<VideoViewAdapter.MyViewHolder> {
    private ArrayList<VideoViewModel>videoViewModels;
    private LayoutInflater lInflater;
    private ItemClickListener lClickListener;
    private Context context;



    public VideoViewAdapter(Context c, ArrayList<VideoViewModel> v)
    {
       this.lInflater = LayoutInflater.from(c);
       this.videoViewModels = v;
       this.context = c;



    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new  MyViewHolder(from(context).inflate(R.layout.video_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(videoViewModels.get(position).getTitle());
        String currentUrl = videoViewModels.get(position).getVideoimageURL();
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


        MyViewHolder(View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.video_text);
            btnimg = itemView.findViewById(R.id.BtnImgVideo);
            itemView.setOnClickListener(this);
        }


         @Override
         public void onClick(View view) {
             if (lClickListener != null) lClickListener.onItemClick(view, getAdapterPosition());
             Log.d("CLICK", title.getText() + "  Clicked");

         }
    }
    String getItem(int id) {

        return videoViewModels.get(id).getTitle();
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}
