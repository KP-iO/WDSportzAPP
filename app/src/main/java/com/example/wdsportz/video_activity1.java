package com.example.wdsportz;

import android.app.Activity;
import android.app.Dialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wdsportz.Adapters.CommentAdapter;
import com.example.wdsportz.Adapters.WatchViewAdapter;
import com.example.wdsportz.ViewModels.Comments;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import java.util.ArrayList;

/**
 * Created by khrishawn
 */
public class video_activity1 extends AppCompatActivity implements UniversalVideoView.VideoViewCallback {



//    private Frag_videoplay.OnFragmentInteractionListener mListener;
        private EditText editText;
        private TextView textView, textView2;
        private Button button;
        private ImageView imageView;
        MediaSource videoSource;
        private Uri uri;
        String postKey;
        RecyclerView RvComment;
        CommentAdapter commentAdapter;
        ArrayList<Comments> listComments;
        static String COMMENT_KEY = "Comment";
        private SimpleExoPlayer simpleExoPlayer;
        private PlayerView mExoPlayerView;
        private static final String TAG = "Video Activity";
        FirebaseDatabase commentsDatabase;
        DatabaseReference reference;
        private ImageView mFullScreenIcon;
        private FrameLayout mFullScreenButton;

        FirebaseAuth firebaseAuth;
        FirebaseUser firebaseUser;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        boolean mExoPlayerFullscreen = false;
        String uid = user.getUid();
        WatchViewAdapter watchViewAdapter;
//    Bundle extras = getIntent().getExtras();
        private int mResumeWindow;
        private long mResumePosition;
        FirebaseDatabase firebaseDatabase;
        private Dialog mFullScreenDialog;
        boolean MUTE = false;
        private final String STATE_RESUME_WINDOW = "resumeWindow";
        private final String STATE_RESUME_POSITION = "resumePosition";
        private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
        private int mSeekPosition;

        View mBottomLayout;
        View mVideoLayout;
        TextView mStart;
        UniversalVideoView mVideoView;
        UniversalMediaController mMediaController;
        int cachedHeight;
        boolean isFullscreen;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoplay_universal);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

            UniversalVideoView mVideoView;
            UniversalMediaController mMediaController;
//            mVideoLayout = findViewById(R.id.video_layout);
//            mBottomLayout = findViewById(R.id.bottom_layout);
            mVideoView = (UniversalVideoView) findViewById(R.id.videoView);
            mMediaController = (UniversalMediaController) findViewById(R.id.media_controller);
            mVideoView.setMediaController(mMediaController);
        RvComment =findViewById(R.id.chat_box);
        textView = findViewById(R.id.match_title);
        textView2 = findViewById(R.id.desc);
        imageView = findViewById(R.id.avatar);
        editText = findViewById(R.id.edit_box1);
        button = findViewById(R.id.add);
        mExoPlayerView= findViewById(R.id.Watch_view1);


        firebaseAuth = firebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = firebaseDatabase.getInstance();
        String strTitle = getIntent().getExtras().getString("title01");

        String desc = getIntent().getExtras().getString("desc");
            String str = getIntent().getExtras().getString("amount");
//        String strTitle = getString("title01");
//        String desc = getArguments().getString("desc");


        textView.setText(strTitle);
        textView2.setText(desc);
//        videoView.start();
            mVideoView.setVideoPath(str);
            mVideoView.requestFocus();
            mVideoView.start();





        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {


                button.setVisibility(View.INVISIBLE);
                DatabaseReference commentReference =firebaseDatabase.getReference(COMMENT_KEY).child(getPostKey()).push();
                String key = commentReference.getKey();
                String chatID = getIntent().getExtras().getString("chatID");
                String comment_content = editText.getText().toString();
                String uid = firebaseUser.getUid();
                String uname = firebaseUser.getDisplayName();
                String uimg = firebaseUser.getPhotoUrl().toString();
                String reportID = "";
                Comments comments = new Comments(comment_content,uid,uname,uimg,key, chatID,reportID);
                Glide.with(view)
                        .load(uimg)
                        .into(imageView);

                commentReference.setValue(comments).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showMessage("comment added");
                        editText.setText("");
                        button.setVisibility(View.VISIBLE);
                        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showMessage("fail to add comment: "+e.getMessage());

                    }
                });




            }










        });







//
//            setVideoAreaSize();

        iniRvComment();







    }

//    private void setVideoAreaSize() {
//
////            mVideoLayout.post(new Runnable() {
////                @Override
////                public void run() {
////                    int width = mVideoLayout.getWidth();
////                    cachedHeight = (int) (width * 405f / 720f);
//////                cachedHeight = (int) (width * 3f / 4f);
//////                cachedHeight = (int) (width * 9f / 16f);
////                    ViewGroup.LayoutParams videoLayoutParams = mVideoLayout.getLayoutParams();
////                    videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
////                    videoLayoutParams.height = cachedHeight;
////                    mVideoLayout.setLayoutParams(videoLayoutParams);
//
//
//            ;
//        }



    private void iniRvComment() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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

                commentAdapter = new CommentAdapter(video_activity1.this,listComments);
                RvComment.setAdapter(commentAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


        private void   showMessage(String message) {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Toast.makeText(this, message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onScaleChange(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
        if (isFullscreen) {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mVideoLayout.setLayoutParams(layoutParams);
            mBottomLayout.setVisibility(View.GONE);

        } else {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = this.cachedHeight;
            mVideoLayout.setLayoutParams(layoutParams);
            mBottomLayout.setVisibility(View.VISIBLE);
        }

        switchTitleBar(!isFullscreen);
    }

    private void switchTitleBar(boolean show) {
       ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            if (show) {
                supportActionBar.show();
            } else {
                supportActionBar.hide();
            }
        }
    }


//    public void onDestroy() {
//        super.onDestroy();
//        simpleExoPlayer.release();
////        mListener = null;
//    }

        public String getPostKey() {
        String postKey = getIntent().getExtras().getString("chatID");
        return postKey;
    }




    @Override
    public void onPause(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onStart(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onBufferingStart(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onBufferingEnd(MediaPlayer mediaPlayer) {

    }
}

