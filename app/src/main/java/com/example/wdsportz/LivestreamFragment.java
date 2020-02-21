package com.example.wdsportz;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wdsportz.Adapters.CommentAdapter;
import com.example.wdsportz.ViewModels.Comments;
import com.example.wdsportz.supportFeatures.Frag_videoplay;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * 
 * 
 * 
 */



public class LivestreamFragment extends Fragment  {

    private Frag_videoplay.OnFragmentInteractionListener mListener;
    private VideoView videoView;
    private EditText editText;
    private TextView textView, textView2, textView3;
    private Button button;
    private ImageView imageView;
    private MediaController mediaController;
    private Uri uri;
    String postKey;
    RecyclerView RvComment;
    CommentAdapter commentAdapter;
    List<Comments> listComments;
    static String COMMENT_KEY = "Comment";

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    FirebaseDatabase firebaseDatabase;
    String videoId;
    private static final String TAG = "livstreamID" ;
    private YouTubePlayerView youTubePlayerView;


    public LivestreamFragment() {
    }


    String getVideoId() {
        return videoId;
    }

//    class VideoIds {private String[] videoIds = {"6JYIGclVQdw", "LvetJ9U_tVY", "S0Q4gqBUs7c", "zOa-rSM4nms"};
//
//
//        int liveIds = liveVideoIds.length();
//
//        private String[] Ids = new String[liveIds];
//        private Random random = new Random();
//
//        public String getNextVideoId() {
//            return videoIds[random.nextInt(videoIds.length)];
//        }
//
//        public  String getNextLiveVideoId() {
//            return Ids[random.nextInt(liveIds)];
//        }
//    }
public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
    final Context context = view.getContext();

    RvComment = getView().findViewById(R.id.chat_box);
    textView = getView().findViewById(R.id.match_title);
    textView2 = getView().findViewById(R.id.date);
    imageView = getView().findViewById(R.id.avatar);
    editText = getView().findViewById(R.id.edit_box);
    button = getView().findViewById(R.id.add);
    videoView = getView().findViewById(R.id.Watch_view1);

    firebaseAuth = firebaseAuth.getInstance();
    firebaseUser = firebaseAuth.getCurrentUser();
    firebaseDatabase = firebaseDatabase.getInstance();

    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            button.setVisibility(View.INVISIBLE);
            DatabaseReference commentReference =firebaseDatabase.getReference(COMMENT_KEY).child(getPostKey()).push();
            String comment_content = editText.getText().toString();
            String uid = firebaseUser.getUid();
            String uname = firebaseUser.getDisplayName();
//                String uimg = firebaseUser.getPhotoUrl().toString();
            Comments comments = new Comments(comment_content,uid,uname);

            commentReference.setValue(comments).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    showMessage("comment added");
                    editText.setText("");
                    button.setVisibility(View.VISIBLE);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    showMessage("fail to add comment: "+e.getMessage());

                }
            });





        }










    });










    iniRvComment();






}


    private void iniRvComment() {

        RvComment.setLayoutManager(new LinearLayoutManager(getContext()));

        String postKey1 = getArguments().getString("chat");
        DatabaseReference commentRef = firebaseDatabase.getReference(COMMENT_KEY).child(postKey1);


        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listComments = new ArrayList<>();
                for (DataSnapshot snap:dataSnapshot.getChildren()){

                    Comments comments = snap.getValue(Comments.class);
                    listComments.add(comments);
                }

                commentAdapter = new CommentAdapter(getContext(),listComments);
                RvComment.setAdapter(commentAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live, container, false);

        youTubePlayerView = view.findViewById(R.id.youtube_player_view);

        initYouTubePlayerView();




        return view;
    }

    private void initYouTubePlayerView() {
        final String video = getArguments().getString("video");
//        Log.d(TAG, videoId );
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                YouTubePlayerUtils.loadOrCueVideo(
                        youTubePlayer, getLifecycle(),video
                        , 0f
                );
            }
        });
    }


    private void   showMessage(String message) {
        Toast.makeText(getContext(), message,Toast.LENGTH_LONG).show();
    }



    public String getPostKey() {
        postKey = getArguments().getString("chat");
        return postKey;
    }


}