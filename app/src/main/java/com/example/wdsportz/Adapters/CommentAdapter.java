package com.example.wdsportz.Adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wdsportz.R;
import com.example.wdsportz.ViewModels.Comments;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;


/**
 * Created by khrishawn
 */


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {


    private Context mContext;
    private List<Comments> mData;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    DatabaseReference databaseReference;
    ProgressDialog pd;
    String reportComment, deleteCommment;
    FirebaseDatabase reportedDatabase,commentsDatabase;
    DatabaseReference reference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String currentUID = user.getUid();
    UserInfo userInfo;
    String user1 = FirebaseAuth.getInstance().getUid();
    static String USERS = "Users";
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    long time;


//    String chat_ID = (videoViewModels.getChatBox_ID());




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
    public void onBindViewHolder(@NonNull final CommentViewHolder holder, int position) {

        String UID = mData.get(position).getUid();

        db.collection("Users")
                .whereEqualTo("uid", UID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = document.get("name").toString();
                                String email = document.get("email").toString();
                                String image = document.get("image").toString();
//                                String cover = document.get("cover").toString();
                                Log.d(TAG, image);

                                Glide.with(mContext.getApplicationContext())
                                        .load(image)
                                        .into(holder.img_user);

                                //set data
                                holder.tv_name.setText(name);

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });




        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CardClicked", "Yes");


                String message = mData.get(position).getContent();
                String uid = mData.get(position).getUid();
                String userName =mData.get(position).getUname();
                String chatID = mData.get(position).getChatID();
                String key = mData.get(position).getKey();



                pd = new ProgressDialog(mContext);
        /* Show dialog containing options
        1) Edit Profile Picture
        2) Edit Cover photo
        3) Edit Name
        4) Edit Phone *

         */

                String options1 [] = {"Report Comment", "Delete Comment",
//                "Delete Comment"
                };

                String options2 [] = {"Report Comment",
//                "Delete Comment"
                };






                Log.d(TAG, uid);
                Log.d(TAG,currentUID);

                if (uid.equals(currentUID)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    // set title
                    builder.setTitle("Choose Action");
                    // set items to dialog
                    builder.setItems(options1, (DialogInterface.OnClickListener) (dialog, which) -> {
                        switch (which){
                            case 0:
                                pd.setMessage("Reporting Comment");
                                reportComment = "image";




                                reportComment(which);
                                break;
                            case 1:
                                pd.setMessage("Deleting Comment");
                                deleteCommment = "cover";


                                deleteCommment(which);
                                notifyDataSetChanged();
                                break;

                        }


                    });
                    builder.create().show();






                }else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    // set title
                    builder.setTitle("Choose Action");
                    // set items to dialog
                    builder.setItems(options2, (DialogInterface.OnClickListener) (dialog, which) -> {
                        switch (which){
                            case 0:
                                pd.setMessage("Reporting Comment");
                                reportComment = "image";




                                reportComment(which);
                                break;

                        }


                    });
                    builder.create().show();



                }







//                showEditProfileDialog(mContext,position);



            }


        });




        holder.tv_name.setText(mData.get(position).getUname());
        long time =  Long.parseLong(String.valueOf(mData.get(position).getTimestamp()));

        long now = System.currentTimeMillis();
        Log.d("Time ", String.valueOf(now));
        Log.d("Time ", String.valueOf(time));

        if(time > now || time <= 0 ){
            Log.d("Time ", "Time Error");

        }
        final long diff = now - time;
        Log.d("Time ", String.valueOf(time));
        if (diff < MINUTE_MILLIS) {
            holder.tv_date.setText("just now") ;
        } else if (diff < 2 * MINUTE_MILLIS) {
            holder.tv_date.setText("a minute ago") ;
        } else if (diff < 50 * MINUTE_MILLIS) {
            holder.tv_date.setText(diff / MINUTE_MILLIS + " minutes ago") ;
            return ;
        } else if (diff < 90 * MINUTE_MILLIS) {
            holder.tv_date.setText("an hour ago") ;
        } else if (diff < 24 * HOUR_MILLIS) {
            holder.tv_date.setText(diff / HOUR_MILLIS + " hours ago") ;
            return ;
        } else if (diff < 48 * HOUR_MILLIS) {
            holder.tv_date.setText("yesterday");
            return ;
        } else {
            holder.tv_date.setText(diff / DAY_MILLIS + " days ago");
        }


        holder.tv_content.setText(mData.get(position).getContent());











    }

    @Override
    public int getItemCount() {
        return mData.size() ;
    }


    public class CommentViewHolder extends RecyclerView.ViewHolder{

        String us  ;

        ImageView img_user;
        TextView tv_name, tv_content, tv_date;
        ConstraintLayout card;


            public CommentViewHolder(@NonNull View itemView) {
                super(itemView);
                card = itemView.findViewById(R.id.view);
                img_user = itemView.findViewById(R.id.comment_user_img);
                tv_name = itemView.findViewById(R.id.comment_username);
                tv_content = itemView.findViewById(R.id.comment_content);
                tv_date = itemView.findViewById(R.id.comment_date);



            }
        }

    private void showEditProfileDialog(Context context, int position) {


    }

    private void deleteCommment(int position ) {
//        notifyDataSetChanged();

//        String message = mData.get(position).getContent();
//        String uid = mData.get(position).getUid();
//        String userName =mData.get(position).getUname();

//        String chatID = mData.get(position).getChatID();
//        String key = mData.get(position).getKey();


        Log.d("ChatID", String.valueOf(mData.size()));
//        Log.d("Key",key);

        if(mData.size() > 1){
            String chatID = mData.get(position).getChatID();
            String key = mData.get(position).getKey();


            DatabaseReference commentRef = firebaseDatabase.getReference();

            commentRef.child("Comment").child(chatID).child(key).removeValue();


            Query applesQuery = commentRef.child("Comment").child(chatID).child(key);




            applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                        notifyDataSetChanged();
                        appleSnapshot.getRef().removeValue();

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "onCancelled", databaseError.toException());
                }
            });





        }
        else {
            String chatID = mData.get(0).getChatID();
            String key = mData.get(0).getKey();


            DatabaseReference commentRef = firebaseDatabase.getReference();

            commentRef.child("Comment").child(chatID).child(key).removeValue();


            Query applesQuery = commentRef.child("Comment").child(chatID).child(key);




            applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                        notifyDataSetChanged();
                        appleSnapshot.getRef().removeValue();

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "onCancelled", databaseError.toException());
                }
            });


        }








    }

    private void reportComment(int position) {
        String message = mData.get(position).getContent();
        String uid = mData.get(position).getUid();
        String userName =mData.get(position).getUname();
        String chatID = mData.get(position).getChatID();
        String key = mData.get(position).getKey();
        String options [] = {"Yes", "No"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Are you sure?");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {

                    Map<String, Object> hashMap = new HashMap<String,Object>();
                    Map<String, Object> hashMap2 = new HashMap<String,Object>();

//                    HashMap<Object, String> hashMap = new HashMap<>();
//                    HashMap<Object, String> hashMap2 = new HashMap<>();
                    hashMap.put("message",message );
                    hashMap.put("uid", uid);
                    hashMap.put("name", userName);




                    reportedDatabase = FirebaseDatabase.getInstance();
                    //path to store data named "Users"
                    reference = reportedDatabase.getReference("Reported");
                    //put data within hashmap in database
                    reference.child(uid).setValue(currentUID);


                    DatabaseReference commentRef = firebaseDatabase.getReference("Comment");

                    DatabaseReference report = commentRef.child(chatID).child(key).child(currentUID);
                    report.setValue("Reported!");





                } else if (which == 1){


                }




            }
        });
        builder.create().show();


    }





//    public static final String DATE_FORMAT_1 = "hh:mm a";
//
//        private String timeAgo(long time){
//
//        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
//        calendar.setTimeInMillis(time);
//        String date = DateFormat.format(DATE_FORMAT_1, calendar).toString();
//        return date;
//        }
}
