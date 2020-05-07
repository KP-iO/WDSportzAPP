package com.example.wdsportz;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by khrishawn
 */
public class FullScree extends AppCompatActivity {
    Intent intent = getIntent();
    private PlayerView playerView;
    private SimpleExoPlayer simpleExoPlayer;
    private ImageView fullscreenButton;
    boolean MUTE = false;

//    String value = intent.getStringExtra("key1");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
        Bundle extras = getIntent().getExtras();

        String value = extras.getString("key");
        Long videoPosition = extras.getLong("time");
        setContentView(R.layout.activity_fullscreen);











        playerView = findViewById(R.id.Watch_view2);
        simpleExoPlayer = new SimpleExoPlayer.Builder(context).build();
        playerView.setPlayer(simpleExoPlayer);



        fullscreenButton = playerView.findViewById(R.id.exo_fullscreen_icon);


        fullscreenButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.exo_icon_fullscreen_enter));

        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

//                    if(getSupportActionBar() != null){
//                        getSupportActionBar().hide();
//                    }
//
//        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
//        params.width = params.MATCH_PARENT;
//        params.height = params.MATCH_PARENT;
//        playerView.setLayoutParams(params);
//                    getActivity().








        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(), Util.getUserAgent(getApplicationContext(),getApplicationContext().getString(R.string.app_name)));
        MediaSource videoSource =
                new ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(value));
        simpleExoPlayer.prepare(videoSource);
        simpleExoPlayer.seekTo(videoPosition);
        simpleExoPlayer.setPlayWhenReady(true);

    };

    public void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.release();

    }

}
