package com.example.wdsportz.Adapters;

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
import com.example.wdsportz.ViewModels.Comments;

import java.util.List;


/**
 * Created by khrishawn
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {


    private Context mContext;
    private List<Comments> mData;


    public CommentAdapter(Context mContext, List<Comments> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.row_comment, parent, false);
        return new CommentViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Glide.with(mContext).load(mData.get(position).getUimg()).into(holder.img_user);
        holder.tv_name.setText(mData.get(position).getUname());
        holder.tv_content.setText(mData.get(position).getContent());
//        holder.tv_date.setText(timestampToString((Long)mData.get(position).getTimestamp()));




    }

    @Override
    public int getItemCount() {
        return mData.size() ;
    }


    public class CommentViewHolder extends RecyclerView.ViewHolder{

        ImageView img_user;
        TextView tv_name, tv_content, tv_date;


            public CommentViewHolder(@NonNull View itemView) {
                super(itemView);
                img_user = itemView.findViewById(R.id.comment_user_img);
                tv_name = itemView.findViewById(R.id.comment_username);
                tv_content = itemView.findViewById(R.id.comment_content);
                tv_date = itemView.findViewById(R.id.comment_date);



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
