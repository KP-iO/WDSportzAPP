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


        videoView = findViewById(R.id.Watch_view);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        String str = "https://firebasestorage.googleapis.com/v0/b/wdsportz-3e91f.appspot.com/o/Videos%2FMatches%2FEngland%20run%20riot%20amidst%20racist%20chants%20%20%20Bulgaria%200-6%20England%20%20%20UEFA%20Euro%202020%20Qualifiers.mp4?alt=media&token=3fb83930-21ac-4022-8e0e-eae4a47576d4";
        Uri uri = Uri.parse(str);
        videoView.setVideoURI(uri);
        videoView.requestFocus();




    }







}