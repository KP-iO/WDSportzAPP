package com.example.wdsportz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by khrishawn
 */
public class NewsDetail extends AppCompatActivity {
    ImageView imageView;
    TextView titleBox;
    TextView descBox;

    String VidUri;
    FirebaseFirestore fireStoreDB = FirebaseFirestore.getInstance();
    private static final String TAG = "Video Playback";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_newsviewer);
        imageView = findViewById(R.id.articleImages);
        titleBox = findViewById(R.id.title1);
        descBox = findViewById(R.id.desc1);

        Intent intent = getIntent();

        String desc = getIntent().getStringExtra("Description");
        String title = getIntent().getStringExtra("Title");
//        String img = getIntent().getStringExtra("Image");

        titleBox.setText(title);
        descBox.setText(desc);










   




    }







}