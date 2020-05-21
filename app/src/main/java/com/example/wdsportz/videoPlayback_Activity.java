package com.example.wdsportz;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class videoPlayback_Activity extends AppCompatActivity {

    private EditText editText;
    private TextView textView, textView2;
    private Button button;
    private ImageView imageView, imgAvatar;
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
    MaterialButton shareAction1;

    FirebaseAuth firebaseAuth;

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

    private PlayerView playerView;
    private MediaSource mVideoSource;

    private  DataSource.Factory dataSourceFactory;

    private SimpleExoPlayer player;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_videoplayback);

        RvComment =findViewById(R.id.chat_box);
        textView = findViewById(R.id.match_title);
        textView2 = findViewById(R.id.desc);
        imageView = findViewById(R.id.avatar);
        editText = findViewById(R.id.comment_box);
        button = findViewById(R.id.add);
        mExoPlayerView= findViewById(R.id.Watch_view1);
        shareAction1 = findViewById(R.id.action_button_shareVP);
        firebaseDatabase = firebaseDatabase.getInstance();


        String currentUseImg = user.getPhotoUrl().toString();
        Log.d("DEBUGGGGGGGGGGGGGGGGGG", "Oncreate Launched");
        super.onCreate(savedInstanceState);

        dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, getString(R.string.app_name)));

        if (savedInstanceState != null) {
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }

        shareAction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareAction();

            }
        });


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {


                button.setVisibility(View.INVISIBLE);
                DatabaseReference commentReference =firebaseDatabase.getReference(COMMENT_KEY).child(getPostKey()).push();
                String key = commentReference.getKey();
                String chatID = getIntent().getExtras().getString("chatID");
                String comment_content = editText.getText().toString();
                String uid = user.getUid();
                String uname = user.getDisplayName();
                String uimg = user.getPhotoUrl().toString();
                String reportID = "";
                Comments comments = new Comments(comment_content,uid,uname,uimg,key, chatID,reportID);


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
        Glide.with(this)
                .load(currentUseImg)
                .into(imageView);
        initVideoInfo();
        iniRvComment();

    }


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
            mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(videoPlayback_Activity.this, R.drawable.ic_fullscreen_skrink));
            mFullScreenDialog.show();
        }
    }

    public void initVideoInfo(){

        TextView videoTitle;
        TextView videoDesc;

        videoTitle = findViewById(R.id.match_title);
//        videoDesc = findViewById(R.id.match_Desc);

        videoTitle.setText(getIntent().getExtras().getString("videoTitle"));
//        videoDesc.setText(getIntent().getExtras().getString("videoDesc"));

//        Log.d("Match Desc", String.valueOf(videoDesc));


    }


    private void initExoPlayer() {

        Log.d("DEBUGGGGGGGGGGGGGGGGGG"," initExoplayer launched ");

        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;

        String videoUrl;

        videoUrl = getIntent().getExtras().getString("videoUrl");

        MediaSource mVideoSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(videoUrl));
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
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(videoPlayback_Activity.this, R.drawable.ic_fullscreen_skrink));
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
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(videoPlayback_Activity.this, R.drawable.ic_fullscreen_expand));

    }




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


    public void shareAction(){
        Intent intent1 = new Intent(Intent.ACTION_SEND);
        intent1.setType("text/plain");
        String shareBody = "VIDEO CONTENT";
        intent1.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(intent1, "Share Using"));
    }
    public String getPostKey() {
        String postKey = getIntent().getExtras().getString("chatID");
        return postKey;
    }

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

                commentAdapter = new CommentAdapter(videoPlayback_Activity.this,listComments);
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

}

