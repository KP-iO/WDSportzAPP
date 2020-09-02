package com.example.wdsportz;


import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wdsportz.Adapters.CommentAdapter;
import com.example.wdsportz.ViewModels.Comments;
import com.example.wdsportz.utils.FullScreenHelper;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * 
 * 
 * 
 */



public class LivestreamFragment extends AppCompatActivity {

    private OnFragmentInteractionListener mListener;
    private EditText editText;
    private TextView txtVideoTitle;
    private Button addButton;
    private MediaController mediaController;
    private Uri uri;
    String postKey;
    RecyclerView RvComment;
    CommentAdapter commentAdapter;
    List<Comments> listComments;
    static String COMMENT_KEY = "Comment";

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    private SimpleExoPlayer simpleExoPlayer;
    private PlayerView playerView;

    FirebaseDatabase firebaseDatabase;
    String videoId;
    private static final String TAG = "livstreamID" ;
    private YouTubePlayerView youTubePlayerView ;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private FullScreenHelper fullScreenHelper = new FullScreenHelper(this);
    String uid = user.getUid();

    public LivestreamFragment() {
    }


    String getVideoId() {
        return videoId;
    }


@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fragment_liveplayback);


    RvComment = findViewById(R.id.chat_box);
    txtVideoTitle = findViewById(R.id.Video_title);
    editText = findViewById(R.id.edit_box);
    addButton = findViewById(R.id.add);
    youTubePlayerView = findViewById(R.id.youtube_player_view);
    TextView date = findViewById(R.id.txtDate);
    TextView desc = findViewById(R.id.txtDescription);
    String dateTxt = getIntent().getExtras().getString("date");
    String descTxt= getIntent().getExtras().getString("videoDesc");
    date.setText(dateTxt);
    desc.setText(descTxt);

    firebaseAuth = FirebaseAuth.getInstance();
    firebaseUser = firebaseAuth.getCurrentUser();
    firebaseDatabase = FirebaseDatabase.getInstance();

    addButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            addButton.setVisibility(View.INVISIBLE);
            DatabaseReference commentReference =firebaseDatabase.getReference(COMMENT_KEY).child(getPostKey()).push();
            String comment_content = editText.getText().toString();
            String uid = firebaseUser.getUid();
            String uname = firebaseUser.getDisplayName();
            String uimg = firebaseUser.getPhotoUrl().toString();
            String key = commentReference.getKey();
            String chatID = getIntent().getExtras().getString("chatID");
            String reportID = "";
            Comments comments = new Comments(comment_content,uid,uname,uimg,key, chatID,reportID, ServerValue.TIMESTAMP);

            commentReference.setValue(comments).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    showMessage("comment added");
                    editText.setText("");
                    addButton.setVisibility(View.VISIBLE);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    showMessage("fail to add comment: "+e.getMessage());

                }
            });

        }
    });


//    setVideoView(context);
    iniRvComment();
    initYouTubePlayerView();
//    Glide.with(this)
//            .load(currentUseImg)
//            .into(imageView);

}

    @Override
    public void onConfigurationChanged(Configuration newConfiguration) {
        super.onConfigurationChanged(newConfiguration);
        youTubePlayerView.getPlayerUiController().getMenu().dismiss();
    }

    @Override
    public void onBackPressed() {
        if (youTubePlayerView.isFullScreen())
            youTubePlayerView.exitFullScreen();
        else
            super.onBackPressed();
    }
    private void iniRvComment() {

        RvComment.setLayoutManager(new LinearLayoutManager(this));


        String postKey1 = getIntent().getExtras().getString("chatID");

        DatabaseReference commentRef = firebaseDatabase.getReference(COMMENT_KEY).child(postKey1);


        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listComments = new ArrayList<>();


                for (DataSnapshot snap:dataSnapshot.getChildren()) {

                    Comments comments = snap.getValue(Comments.class);
                    if (snap.hasChild(uid)) {

                        listComments.remove(comments);

                    } else {
                        listComments.add(comments);
                    }
                }

                commentAdapter = new CommentAdapter(LivestreamFragment.this,listComments);
                Collections.reverse(listComments);
                RvComment.setAdapter(commentAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }





    private void initYouTubePlayerView() {
        String video = getIntent().getExtras().getString("video");

//        Log.d(TAG, videoId );
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                YouTubePlayerUtils.loadOrCueVideo(
                        youTubePlayer,
                        getLifecycle(),
                        video
                        , 0f
                );
                addFullScreenListenerToPlayer();
            }
        });
    }


    private void addFullScreenListenerToPlayer() {
        youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
            @Override
            public void onYouTubePlayerEnterFullScreen() {
                fullScreenHelper.enterFullScreen();
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


//                addCustomActionsToPlayer();
            }

            @Override
            public void onYouTubePlayerExitFullScreen() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                fullScreenHelper.exitFullScreen();

//                removeCustomActionsFromPlayer();
            }
        });
    }



    private void   showMessage(String message) {
        Toast.makeText(LivestreamFragment.this, message,Toast.LENGTH_LONG).show();
    }



    public String getPostKey() {
        postKey = getIntent().getExtras().getString("chatID");

        return postKey;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

}