package ypw.app.wdsportz.Adapters;

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

import java.util.List;

import ypw.app.wdsportz.ViewModels.NewsFeedViewModel;

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


    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new  MyViewHolder(from(context).inflate(R.layout.fragment_homepage_feeditem, parent,false));
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        CardView newsTile;
        ImageView newsView;
        ImageView imgWDReccommended;
        ItemClickListener itemClickListener;

        MyViewHolder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.newsDescription);
            title = itemView.findViewById(R.id.newsTitle);
            newsTile = itemView.findViewById(R.id.newsCard);
            newsView = itemView.findViewById(R.id.homeImg);
            imgWDReccommended = itemView.findViewById(R.id.img_WdRecommended);

        }

    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.title.setText(getItem(position).getTitle());
        holder.description.setText(getItem(position).getNewsDesc());
        final String currentUrl = getItem(position).getNewsImageURL();
        String date1 = getItem(position).getDate();
        String chatId = getItem(position).getChatbox_id();


        final String description1 = getItem(position).getNewsDesc();
        final String title1 = getItem(position).getTitle();
        final Boolean isWDRecommded = getItem(position).getIsWDRecommended();
        holder.imgWDReccommended.setVisibility(View.GONE);

        Glide.with(context)
                .load(currentUrl)
                .into(holder.newsView);

        try{
            if (isWDRecommded) {
                holder.imgWDReccommended.setVisibility(View.VISIBLE);
            } else {
                holder.imgWDReccommended.setVisibility(View.GONE);
            }
        }catch(Exception e) {


         }


        holder.newsTile.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   Bundle bundle = new Bundle();
                                                   bundle.putString("image",currentUrl);
                                                   bundle.putString("desc", description1);
                                                   bundle.putString("title", title1);
                                                   bundle.putString("chatID", chatId);
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


    public NewsFeedViewModel getItem(int position) {

        return newsFeedViewModels.get(position);
    }

    public interface ItemClickListener {

        void onItemClick(View view, int position);
    }


}
