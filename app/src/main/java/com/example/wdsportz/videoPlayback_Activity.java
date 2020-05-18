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

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;



import android.annotation.SuppressLint;
import android.widget.TextView;

public class videoPlayback_Activity extends AppCompatActivity {

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

        initVideoInfo();

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

        videoTitle = findViewById(R.id.match_Title);
        videoDesc = findViewById(R.id.match_Desc);

        videoTitle.setText(getIntent().getExtras().getString("videoTitle"));
        videoDesc.setText(getIntent().getExtras().getString("videoDesc"));

        Log.d("Match Desc", String.valueOf(videoDesc));


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

}


