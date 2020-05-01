package com.example.wdsportz.Adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by khrishawn
 */


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {


    private Context mContext;
    private List<Comments> mData;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    DatabaseReference databaseReference;
    ProgressDialog pd;
    String reportComment, deleteCommment;
    FirebaseDatabase reportedDatabase,commentsDatabase;
    DatabaseReference reference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String currentUID = user.getUid();

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
// Get current UIMG
        //print UID in report document
        // if UID exists do not show to user


        Glide.with(mContext).load(mData.get(position).getUimg()).into(holder.img_user);
        holder.tv_name.setText(mData.get(position).getUname());
        holder.tv_content.setText(mData.get(position).getContent());
        //        holder.tv_date.setText(timestampToString((Long)mData.get(position).getTimestamp()));
        databaseReference = firebaseDatabase.getReference("Users");
        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditProfileDialog(mContext);


            }
        });



//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot ds: dataSnapshot.getChildren()){
//                    //get data
//
//                    String name = "" + ds.child("name").getValue();
//                    String email = "" + ds.child("email").getValue();
//                    String phone = "" + ds.child("phone").getValue();
//                    String image = "" + ds.child("image").getValue();
//
//
//                    // Set to user
//                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                            .setPhotoUri(Uri.parse(image))
//                            .build();
//
//                    user.updateProfile(profileUpdates)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        Log.d(TAG, "User profile updated.");
//                                    }
//                                }
//                            });
//
//
//
//
//
//                    Glide.with(mContext)
//                            .load(image)
//                            .into(holder.img_user);
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });



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

    private void showEditProfileDialog(Context context) {

        pd = new ProgressDialog(context);
        /* Show dialog containing options
        1) Edit Profile Picture
        2) Edit Cover photo
        3) Edit Name
        4) Edit Phone *

         */

        String options [] = {"Report Comment", "Delete Comment"};
        //alert dialog

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // set title
        builder.setTitle("Choose Action");
        // set items to dialog
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        pd.setMessage("Reporting Comment");
                        reportComment = "image";




                        reportComment(which);
                        break;
                    case 1:
                        pd.setMessage("Deleting Comment");
                        deleteCommment = "cover";
                        deleteCommment();
                        break;

                }


            }
        });
        builder.create().show();

    }

    private void reportComment(int position) {
        String message = mData.get(position).getContent();
        String uid = mData.get(position).getUid();
        String userName =mData.get(position).getUname();
        String chatID = mData.get(position).getChatID();
        String key =mData.get(position).getKey();
        String options [] = {"Yes", "No"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Pick Image From");

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

    private void deleteCommment() {
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
