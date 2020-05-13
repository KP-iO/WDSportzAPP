package com.example.wdsportz.supportFeatures;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wdsportz.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

/**
 * Created by khrishawn
 */
public class FullScreenActivity extends AppCompatActivity {
    private static final String TAG = "Video Activity";
    Intent intent = getIntent();
    private YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String value = intent.getExtras().getString("videoPath");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreenvideoview);
//        Log.d(TAG, "DOCUMENT PRINT :" + video);
        youTubePlayerView = findViewById(R.id.youtube_player_view);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                YouTubePlayerUtils.loadOrCueVideo(
                        youTubePlayer, getLifecycle(),value
                        , 0f
                );

            }
        });








    }

//    private void initYouTubePlayerView() {
//        String value = intent.getExtras().getString("videoPath");
////        final String video = getArguments().getString("video");
////        Log.d(TAG, videoId );
//        getLifecycle().addObserver(youTubePlayerView);
//        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//            @Override
//            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                YouTubePlayerUtils.loadOrCueVideo(
//                        youTubePlayer, getLifecycle(),value
//                        , 0f
//                );
//
//            }
//        });
//    }






}
