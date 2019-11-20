package com.example.wdsportz;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;


/**
 * A simple {@link Fragment} subclass.
 * 
 * 
 * 
 */



public class BlankFragment extends Fragment  {
    String videoId;
    private static final String TAG = "livstreamID" ;
    private YouTubePlayerView youTubePlayerView;


    public BlankFragment() {
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
    

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live, container, false);

        youTubePlayerView = view.findViewById(R.id.youtube_player_view);

        initYouTubePlayerView();




        return view;
    }

    private void initYouTubePlayerView() {
        final String Video = getActivity().getIntent().getStringExtra("Video");
        Log.d(TAG, videoId );
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                YouTubePlayerUtils.loadOrCueVideo(
                        youTubePlayer, getLifecycle(),Video
                        , 0f
                );
            }
        });
    }

}