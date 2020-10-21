package ypw.app.wdsportz.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wdsportz.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import ypw.app.wdsportz.CheckLiveStreamPassword_DialogFragment;
import ypw.app.wdsportz.LivestreamFragment;
import ypw.app.wdsportz.ViewModels.WatchViewModel;

import static android.view.LayoutInflater.from;

public class LiveStreamAdapter extends RecyclerView.Adapter<LiveStreamAdapter.MyViewHolder> implements CheckLiveStreamPassword_DialogFragment.Listener {
    private List<WatchViewModel> videoViewModels;
    private LayoutInflater lInflater;
    private ItemClickListener lClickListener;
    private Context context;
    private FragmentManager fm;
    FirebaseFirestore firebaseDatabase = FirebaseFirestore.getInstance();
    private String userSubmittedPassword;
    boolean allowAccess = false;
    private String passwordForSelected;
    private Bundle selectedBundle;

    public LiveStreamAdapter(Context context, List<WatchViewModel> list, FragmentManager fm) {
        this.lInflater = LayoutInflater.from(context);
        this.videoViewModels = list;
        this.context = context;
        this.fm = fm;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new  MyViewHolder(from(context).inflate(R.layout.watch_tile_live, parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        holder.title.setText(videoViewModels.get(position).getTitle());
        final String currentUrl = videoViewModels.get(position).getVideoImageURL();
        final String Video1 = (videoViewModels.get(position).getVideoURL());
        final String chat_ID = (videoViewModels.get(position).getChatBox_ID());
        final Boolean live = (videoViewModels.get(position).getLive());
        final String date = (videoViewModels.get(position).getDate());
        final String videoDesc = (videoViewModels.get(position).getVideo_desc());
        final String accessPassword = (videoViewModels.get(position).getAccessPassword());

        if (accessPassword != "null"){

            holder.img_locked.setImageResource(R.drawable.lock);
        } else {

            holder.img_locked.setImageResource(R.drawable.unlock);
        }

        if (live == true ){
            holder.liveIndicator.setVisibility(View.VISIBLE);
        } else {
            holder.liveIndicator.setVisibility(View.GONE);
        }

        Glide.with(context)
                .load(currentUrl)
                .into(holder.btnimg);

        holder.btnimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("chatID", chat_ID);
                bundle.putString("video", Video1);
                bundle.putString("videoDesc", videoDesc);
                bundle.putString("date", date);

//              Check password for livestream...
                selectedBundle = bundle;
                checkUserLiveStreamPass(videoViewModels.get(position).getAccessPassword());

            }
        });

    }

    private void checkUserLiveStreamPass(String accessPassword) {
        //Loading sign for users??
        passwordForSelected = accessPassword;
        Log.d("passwordForSelected", passwordForSelected);

        if (passwordForSelected == "null") {

            Intent intent = new Intent(context, LivestreamFragment.class);
            intent.putExtras(selectedBundle);
            context.startActivity(intent);

        } else {

            DialogFragment checkLiveStreamPassword_DialogFragment = new CheckLiveStreamPassword_DialogFragment();
            ((CheckLiveStreamPassword_DialogFragment) checkLiveStreamPassword_DialogFragment).setListener(LiveStreamAdapter.this);
            checkLiveStreamPassword_DialogFragment.show(fm, "CheckPassword");

        }

    }

    @Override
    public void returnData(String result) {
        Log.d("test1", "returnData: " + result);
        userSubmittedPassword = result;

        Log.d("test2", "returnData: " + passwordForSelected);

        if(passwordForSelected.matches(userSubmittedPassword)){
            Log.d("test", "TRUEEEE");
            Intent intent = new Intent(context, LivestreamFragment.class);
            intent.putExtras(selectedBundle);
            context.startActivity(intent);

        } else {

            Log.d("test", "FALSEEE");
            invalidLiveStreamPass();
        }
    }


    private void invalidLiveStreamPass() {

        TextView showText = new TextView(context);
        showText.setText("The password you have entered is incorrect, a password can be requested from ' w.ude@wdsportz.com ' ");
        showText.setTextIsSelectable(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Build the Dialog
        builder.setView(showText)
                .setTitle("Incorrect Password")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue
                    }
                })

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    @Override
    public int getItemCount() {

        return videoViewModels.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageButton btnimg;
        LinearLayout liveIndicator;
        ImageView img_locked;
        ItemClickListener itemClickListener;
        FirebaseFirestore fireStoreDB = FirebaseFirestore.getInstance();


        MyViewHolder(View itemView) {
            super(itemView);

            title= itemView.findViewById(R.id.video_text);
            btnimg = itemView.findViewById(R.id.BtnImgVideo);
            liveIndicator = itemView.findViewById(R.id.Live_Indicator);
            img_locked = itemView.findViewById(R.id.img_locked);

            btnimg.setClipToOutline(true);

            btnimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent i= new Intent(view.getContext(),VideoPlayback.class);
                    Log.d("CLICK", title.getText() + "  Clicked");


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
