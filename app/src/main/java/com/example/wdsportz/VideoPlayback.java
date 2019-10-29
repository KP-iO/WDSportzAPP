package com.example.wdsportz;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.wdsportz.Watch;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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

        String str = getIntent().getStringExtra("Video");
        Uri uri = Uri.parse(str);
        videoView.setVideoURI(uri);
        videoView.requestFocus();




    }







}