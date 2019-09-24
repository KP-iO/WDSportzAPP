package com.example.wdsportz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wdsportz.R;
import com.example.wdsportz.viewmodels.VideoViewModel;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by khrishawn
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<VideoViewModel>videoViewModels;

    public MyAdapter(Context c, ArrayList<VideoViewModel> v)
    {
        context = c;
        videoViewModels = v;



    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new  MyViewHolder(LayoutInflater.from(context).inflate(R.layout.watch_card_view, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(videoViewModels.get(position).getName());
        holder.email.setText(videoViewModels.get(position).getName());
        

    }

    @Override
    public int getItemCount() {
        return videoViewModels.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,email;
        ImageView profilepic;


        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name);
            email = (TextView)itemView.findViewById(R.id.email);
            profilepic = (ImageView)itemView.findViewById(R.id.watchpic);
        }



    }
}
