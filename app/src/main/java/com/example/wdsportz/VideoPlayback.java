package com.example.wdsportz;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class VideoPlayback extends AppCompatActivity {
    VideoView videoView;
    String VidUri;
    FirebaseFirestore fireStoreDB = FirebaseFirestore.getInstance();
    private static final String TAG = "Video Playback";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewmatches);
        Intent intent = getIntent();


        MediaController mediaController = new MediaController(this);


        videoView = findViewById(R.id.Watch_view1);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        String str = getIntent().getStringExtra("Video");
        Uri uri = Uri.parse(str);
        videoView.setVideoURI(uri);
        videoView.requestFocus();




    }







}