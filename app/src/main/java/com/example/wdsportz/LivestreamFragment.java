package com.example.wdsportz;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wdsportz.Adapters.CommentAdapter;
import com.example.wdsportz.ViewModels.Comments;
import com.example.wdsportz.utils.FullScreenHelper;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
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
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * 
 * 
 * 
 */



public class LivestreamFragment extends AppCompatActivity {

    private OnFragmentInteractionListener mListener;
    private VideoView videoView;
    private EditText editText;
    private TextView textView, textView2, textView3;
    private Button button;
    private ImageView imageView;
    private MediaController mediaController;
    private Uri uri;
    String postKey;
    RecyclerView RvComment;
    CommentAdapter commentAdapter;
    List<Comments> listComments;
    static String COMMENT_KEY = "Comment";
    MaterialButton shareAction;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    private SimpleExoPlayer simpleExoPlayer;
    private PlayerView playerView;


    FirebaseDatabase firebaseDatabase;
    String videoId;
    private static final String TAG = "livstreamID" ;
    private YouTubePlayerView youTubePlayerView ;

    private FullScreenHelper fullScreenHelper = new FullScreenHelper(this);

    public LivestreamFragment() {
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
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fragment_live);


    RvComment = findViewById(R.id.chat_box);
    textView = findViewById(R.id.match_title);
    textView2 = findViewById(R.id.desc);
    imageView = findViewById(R.id.avatar);
    editText = findViewById(R.id.edit_box);
    button = findViewById(R.id.add);
    videoView =findViewById(R.id.Watch_view1);
    youTubePlayerView = findViewById(R.id.youtube_player_view);
    shareAction =findViewById(R.id.action_button_share);


    firebaseAuth = FirebaseAuth.getInstance();
    firebaseUser = firebaseAuth.getCurrentUser();
    firebaseDatabase = FirebaseDatabase.getInstance();


    shareAction.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            shareAction();

        }
    });


    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            button.setVisibility(View.INVISIBLE);
            DatabaseReference commentReference =firebaseDatabase.getReference(COMMENT_KEY).child(getPostKey()).push();
            String comment_content = editText.getText().toString();
            String uid = firebaseUser.getUid();
            String uname = firebaseUser.getDisplayName();
                String uimg = firebaseUser.getPhotoUrl().toString();
            Comments comments = new Comments(comment_content,uid,uname, uimg);

            commentReference.setValue(comments).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    showMessage("comment added");
                    editText.setText("");
                    button.setVisibility(View.VISIBLE);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    showMessage("fail to add comment: "+e.getMessage());

                }
            });





        }










    });









//    setVideoView(context);
    iniRvComment();
    initYouTubePlayerView();






}


    private void iniRvComment() {

        RvComment.setLayoutManager(new LinearLayoutManager(this));

        String postKey1 = getIntent().getExtras().getString("chat");

        DatabaseReference commentRef = firebaseDatabase.getReference(COMMENT_KEY).child(postKey1);


        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listComments = new ArrayList<>();
                for (DataSnapshot snap:dataSnapshot.getChildren()){

                    Comments comments = snap.getValue(Comments.class);
                    listComments.add(comments);
                }

                commentAdapter = new CommentAdapter(LivestreamFragment.this,listComments);
                RvComment.setAdapter(commentAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_live, container, false);
//
//        youTubePlayerView = view.findViewById(R.id.youtube_player_view);
//        initYouTubePlayerView();
//
//
//
//
//        return view;
//    }







//        MediaController mediaController = new MediaController(getContext());
//        videoView.setMediaController(mediaController);
//        mediaController.setAnchorView(videoView);
//
//    Uri uri = Uri.parse(str);
////        videoView.setVideoURI(uri);
////        videoView.requestFocus();
//    }



    private void initYouTubePlayerView() {
        String video = getIntent().getExtras().getString("video");

//        Log.d(TAG, videoId );
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                YouTubePlayerUtils.loadOrCueVideo(
                        youTubePlayer, getLifecycle(),video
                        , 0f
                );
                addFullScreenListenerToPlayer();
            }
        });
    }


    private void addFullScreenListenerToPlayer() {
        youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
            @Override
            public void onYouTubePlayerEnterFullScreen() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                fullScreenHelper.enterFullScreen();
//                addCustomActionsToPlayer();
            }

            @Override
            public void onYouTubePlayerExitFullScreen() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                fullScreenHelper.exitFullScreen();

//                removeCustomActionsFromPlayer();
            }
        });
    }


    private void   showMessage(String message) {
        Toast.makeText(LivestreamFragment.this, message,Toast.LENGTH_LONG).show();
    }



    public String getPostKey() {
        postKey = getIntent().getExtras().getString("chat");

        return postKey;
    }

//Setting shareaction details
    public void shareAction(){
        Intent intent1 = new Intent(Intent.ACTION_SEND);
        intent1.setType("text/plain");
        String shareBody = "LIGVESTREAM CONTENT";
        intent1.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(intent1, "Share Using"));
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

}