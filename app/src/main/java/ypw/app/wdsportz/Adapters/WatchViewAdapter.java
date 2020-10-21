package ypw.app.wdsportz.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wdsportz.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import ypw.app.wdsportz.ViewModels.WatchViewModel;
import ypw.app.wdsportz.videoPlayback_Activity;

import static android.view.LayoutInflater.from;

//import com.example.wdsportz.supportFeatures.Frag_videoplay;

/**
 * Created by khrishawn
 */
public class WatchViewAdapter extends RecyclerView.Adapter<WatchViewAdapter.MyViewHolder> {
    private List<WatchViewModel> videoViewModels;
    private LayoutInflater lInflater;
    private ItemClickListener lClickListener;
    private Context context;
    public NavController navController;

    public WatchViewAdapter(Context context, List<WatchViewModel> list) {
        this.lInflater = LayoutInflater.from(context);
        this.videoViewModels = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new  MyViewHolder(from(context).inflate(R.layout.watch_tile, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final String videoTitle = (videoViewModels.get(position).getTitle());
        final String videoImage = videoViewModels.get(position).getVideoImageURL();
        final String videoUrl = (videoViewModels.get(position).getVideoURL());
        final String chatID = (videoViewModels.get(position).getChatBox_ID());
        final String videoDesc  = (videoViewModels.get(position).getVideo_desc());

        holder.title.setText(videoViewModels.get(position).getTitle());
        Glide.with(context)
                .load(videoImage)
                .into(holder.btnimg);

        holder.btnimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                throw new RuntimeException("Test Crash");
                Intent intent = new Intent(context, videoPlayback_Activity.class);
                // used to bundle strings and enable it to be collected
                Bundle bundle = new Bundle();
                bundle.putString("videoTitle", videoTitle);
                bundle.putString("videoUrl", videoUrl);
                bundle.putString("chatID", chatID);
                bundle.putString("videoDesc", videoDesc);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });


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

        return videoViewModels.get(id).getTitle();
    }

    public interface ItemClickListener {

        void onItemClick(View view, int position);
    }


}
