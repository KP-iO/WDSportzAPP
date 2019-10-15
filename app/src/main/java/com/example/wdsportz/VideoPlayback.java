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

        String str = "https://firebasestorage.googleapis.com/v0/b/wdsportz-3e91f.appspot.com/o/Videos%2FMovie%20on%2017-09-2019%20at%2011.45%20%232.mov?alt=media&token=c2ab79e0-d8b3-4915-9002-8c7f4ce9b926";
        Uri uri = Uri.parse(str);
        videoView.setVideoURI(uri);
        videoView.requestFocus();




    }







}