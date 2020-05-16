package com.example.wdsportz;


import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.net.Uri;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.C.ContentType;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wdsportz.Adapters.CommentAdapter;
import com.example.wdsportz.Adapters.WatchViewAdapter;
import com.example.wdsportz.ViewModels.Comments;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class video_Activity extends AppCompatActivity {

    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";

    private PlayerView playerView;
    private MediaSource mVideoSource;
    private boolean mExoPlayerFullscreen = false;
    private FrameLayout mFullScreenButton;
    private ImageView mFullScreenIcon;
    private Dialog mFullScreenDialog;
    private  DataSource.Factory dataSourceFactory;

    private SimpleExoPlayer player;

    private int mResumeWindow;
    private long mResumePosition;

//    private EditText editText;
//    private TextView textView, textView2;
//    private Button addComment;
//    private ImageView imageView;
//    MediaSource videoSource;
//    private Uri uri;
//    String postKey;
//    RecyclerView RvComment;
//    CommentAdapter commentAdapter;
//    ArrayList<Comments> listComments;
//    static String COMMENT_KEY = "Comment";
//    private SimpleExoPlayer simpleExoPlayer;
//    private PlayerView mExoPlayerView;
//    private static final String TAG = "Video Activity";
//    FirebaseDatabase commentsDatabase;
//    DatabaseReference reference;
//    private ImageView mFullScreenIcon;
//    private FrameLayout mFullScreenButton;
//
//    FirebaseAuth firebaseAuth;
//    FirebaseUser firebaseUser;
//    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//    boolean mExoPlayerFullscreen = false;
//    String uid = user.getUid();
//    WatchViewAdapter watchViewAdapter;
////    Bundle extras = getIntent().getExtras();
//private int mResumeWindow;
//    private long mResumePosition;
//    FirebaseDatabase firebaseDatabase;
//    private Dialog mFullScreenDialog;
//    boolean MUTE = false;
//    private final String STATE_RESUME_WINDOW = "resumeWindow";
//    private final String STATE_RESUME_POSITION = "resumePosition";
//    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("DEBUGGGGGGGGGGGGGGGGGG", "Oncreate Launched");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayback);

        dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, getString(R.string.app_name)));

        if (savedInstanceState != null) {
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }
    }


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_videoplayback);
//            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//            RvComment =findViewById(R.id.chat_box);
//            textView = findViewById(R.id.match_title);
//            textView2 = findViewById(R.id.desc);
//            imageView = findViewById(R.id.avatar);
//            editText = findViewById(R.id.edit_box1);
//            addComment = findViewById(R.id.add);
//            mExoPlayerView= findViewById(R.id.Watch_view1);
//
//            firebaseAuth = firebaseAuth.getInstance();
//            firebaseUser = firebaseAuth.getCurrentUser();
//            firebaseDatabase = firebaseDatabase.getInstance();
//
//            String strTitle = getIntent().getExtras().getString("title01");
//            String desc = getIntent().getExtras().getString("desc");
//
//            textView.setText(strTitle);
//            textView2.setText(desc);
//
//            addComment.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(final View view) {
//
//                    addComment.setVisibility(View.INVISIBLE);
//                    DatabaseReference commentReference =firebaseDatabase.getReference(COMMENT_KEY).child(getPostKey()).push();
//                    String key = commentReference.getKey();
//                    String chatID = getIntent().getExtras().getString("chatID");
//                    String comment_content = editText.getText().toString();
//                    String uid = firebaseUser.getUid();
//                    String uname = firebaseUser.getDisplayName();
//                    String uimg = firebaseUser.getPhotoUrl().toString();
//                    String reportID = "";
//                    Comments comments = new Comments(comment_content,uid,uname,uimg,key, chatID,reportID);
//                    Glide.with(view)
//                            .load(uimg)
//                            .into(imageView);
//
//                    commentReference.setValue(comments).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            showMessage("comment added");
//                            editText.setText("");
//                            addComment.setVisibility(View.VISIBLE);
//                            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
//                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            showMessage("fail to add comment: "+e.getMessage());
//
//                        }
//                    });
//
//                }
//
//            });
//
//            iniRvComment();
//
//            initExoPlayer();
//
//    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        Log.d("DEBUGGGGGGGGGGGGGGGGGG", "OnResume");
        super.onResume();

        if (playerView == null) {
            playerView =  findViewById(R.id.exoplayer);
            initFullscreenDialog();
            initFullscreenButton();
        }

        initExoPlayer();

        if (mExoPlayerFullscreen) {
            ((ViewGroup) playerView.getParent()).removeView(playerView);
            mFullScreenDialog.addContentView(playerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(video_Activity.this, R.drawable.ic_fullscreen_skrink));
            mFullScreenDialog.show();
        }
    }



    private void initExoPlayer() {

        Log.d("DEBUGGGGGGGGGGGGGGGGGG"," initExoplayer launched ");


        //player = ExoPlayerFactory.newSimpleInstance(this);
        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;

        String str = "https://firebasestorage.googleapis.com/v0/b/wdsportz-3e91f.appspot.com/o/Videos%2FMatches%2FArsenal%20vs%20Manchester%20United%20(1-3)%20%20%20Emirates%20FA%20Cup%20Highlights.mp4?alt=media&token=f4edecd4-ce6d-46a2-83be-80ff9b1171bc";
        MediaSource mVideoSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(str));
        // mVideoSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(str));
        Log.i("DEBUG"," mVideoSource "+mVideoSource);
        player.prepare(mVideoSource);
        //player.setPlayWhenReady(true);

        if (haveResumePosition) {
            Log.d("DEBUGGGGGGGGGGGGGGGGGG"," haveResumePosition ");
            Log.d("DEBUGGGGGGGGGGGGGGGGGG","mResumeWindow = " + mResumeWindow);
            Log.d("DEBUGGGGGGGGGGGGGGGGGG", "mResumePosition" + mResumePosition);


            player.seekTo(mResumeWindow, mResumePosition);
            player.setPlayWhenReady(true);
        }

    }


    private void initFullscreenButton() {
        Log.d("DEBUGGGGGGGGGGGGGGGGGG", "initFullscreenButton");
        PlayerControlView controlView = playerView.findViewById(R.id.exo_controller);

        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mExoPlayerFullscreen) {
                    Log.d("DEBUGGGGGGGGGGGGGGGGGG", String.valueOf(player.getCurrentPosition()));
                    Log.d("DEBUGGGGGGGGGGGGGGGGGG", "Full Screen Clicked");
                    openFullscreenDialog();

                 } else {
                    closeFullscreenDialog();
            }
        }
    });
    }


    @SuppressLint("SourceLockedOrientationActivity")
    private void openFullscreenDialog() {
        Log.d("DEBUGGGGGGGGGGGGGGGGGG", "OPENFullscreenDialog");
        mResumePosition = playerView.getPlayer().getCurrentPosition();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ((ViewGroup) playerView.getParent()).removeView(playerView);
        mFullScreenDialog.addContentView(playerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(video_Activity.this, R.drawable.ic_fullscreen_skrink));
        mExoPlayerFullscreen = true;

        Log.d("DEBUGGG resumePosition=", String.valueOf(mResumePosition));
        mFullScreenDialog.show();

    }

    private void initFullscreenDialog() {
        Log.d("DEBUGGGGGGGGGGGGGGGGGG", "initFullscreenDialog");

        mFullScreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {

            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }



    @SuppressLint("SourceLockedOrientationActivity")
    private void closeFullscreenDialog() {

        Log.d("DEBUGGGGGGGGGGGGGGGGGG", "CLOSEFullscreenDialog");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ((ViewGroup) playerView.getParent()).removeView(playerView);
        ((FrameLayout) findViewById(R.id.main_media_frame)).addView(playerView);

        mResumePosition = player.getCurrentPosition();
        player.seekTo(mResumeWindow,mResumePosition);
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(video_Activity.this, R.drawable.ic_fullscreen_expand));

    }


//    private MediaSource buildMediaSource(Uri uri) {
//        @C.ContentType int type = Util.inferContentType(uri);
//        switch (type) {
//            case C.TYPE_DASH:
//                return new DashMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
//            case C.TYPE_SS:
//                return new SsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
//            case C.TYPE_HLS:
//                return new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
//            case C.TYPE_OTHER:
//                return new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
//            default:
//                throw new IllegalStateException("Unsupported type: " + type);
//        }
//    }



//    @Override
//    protected void onPause() {
//        Log.d("DEBUGGGGGGGGGGGGGGGGGG", "onPause launched");
//        super.onPause();
//
//        if (playerView != null && player != null) {
//            mResumeWindow = player.getCurrentWindowIndex();
//            mResumePosition = Math.max(0, player.getCurrentPosition());
//            Log.d("DEBUGGGGGGGGGGGGGGGGGG", String.valueOf(mResumePosition));
//
//            player.release();
//        }
//
//        if (mFullScreenDialog != null)
//            mFullScreenDialog.dismiss();
//    }


    @Override
    protected void onStop() {
        super.onStop();

        Log.d("DEBUGGGGGG STOP", "ACTIVITY STOP");

        if (playerView != null && player != null) {
            mResumeWindow = player.getCurrentWindowIndex();
            mResumePosition = Math.max(0, player.getCurrentPosition());
            Log.d("DEBUGGGGGGGGGGGGGGGGGG", String.valueOf(mResumePosition));

            player.release();
        }

        if (mFullScreenDialog != null)
            mFullScreenDialog.dismiss();

    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
// Restore UI state using savedInstanceState.

        Long playerpos = savedInstanceState.getLong("mResumePosition");
        Log.d("DEBUG RESTORE STATE", String.valueOf(playerpos));
//        Var2 = savedInstanceState.getDouble("StringKeyForDouble");
//        Var3 = savedInstanceState.getInt("StringKeyForInteger");
//        Var4 = savedInstanceState.getString("StringKey");

    }

}


