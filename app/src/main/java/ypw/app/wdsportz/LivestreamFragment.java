package ypw.app.wdsportz;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wdsportz.R;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ypw.app.wdsportz.Adapters.CommentAdapter;
import ypw.app.wdsportz.ViewModels.Comments;
import ypw.app.wdsportz.utils.FullScreenHelper;



public class LivestreamFragment extends AppCompatActivity {

    private OnFragmentInteractionListener mListener;
    private EditText editText;
    private TextView txtVideoTitle;
    private Button addCommentButton;
    private MediaController mediaController;
    private Uri uri;
    String postKey;
    RecyclerView RvComment;
    CommentAdapter commentAdapter;
    List<Comments> listComments;
    static String COMMENT_KEY = "Comment";

    AdView mAdView;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    private SimpleExoPlayer simpleExoPlayer;
    private PlayerView playerView;

    FirebaseDatabase firebaseDatabase;
    String videoId;
    private static final String TAG = "livstreamID" ;
    private YouTubePlayerView youTubePlayerView ;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private FullScreenHelper fullScreenHelper = new FullScreenHelper(this);
    String uid = user.getUid();

    ConstraintLayout constLayoutDescription;
    ImageButton btnDropdown;
    Boolean isVisible = false;

    public LivestreamFragment() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fragment_liveplayback);

    RvComment = findViewById(R.id.chat_box);
    txtVideoTitle = findViewById(R.id.Video_title);
    editText = findViewById(R.id.comment_box);
    addCommentButton = findViewById(R.id.add);
    youTubePlayerView = findViewById(R.id.youtube_player_view);

    btnDropdown = findViewById(R.id.btnDropDown);

    firebaseAuth = FirebaseAuth.getInstance();
    firebaseUser = firebaseAuth.getCurrentUser();
    firebaseDatabase = FirebaseDatabase.getInstance();


        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

    addCommentButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            addCommentButton.setVisibility(View.INVISIBLE);
            DatabaseReference commentReference = firebaseDatabase.getReference(COMMENT_KEY).child(getPostKey()).push();
            String comment_content = editText.getText().toString();
            String uid = firebaseUser.getUid();
            String uname = firebaseUser.getDisplayName();
            String uimg = firebaseUser.getPhotoUrl().toString();
            String key = commentReference.getKey();
            String chatID = getIntent().getExtras().getString("chatID");
            String reportID = "";
            Comments comments = new Comments(comment_content, uid, uname, uimg, key, chatID, reportID, ServerValue.TIMESTAMP);

            commentReference.setValue(comments).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    showMessage("comment added");
                    editText.setText("");
                    addCommentButton.setVisibility(View.VISIBLE);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    showMessage("fail to add comment: " + e.getMessage());

                }
            });

        }
    });

    iniRvComment();
    initYouTubePlayerView();
    iniVideoDescription();

}

    private void iniVideoDescription() {

        TextView date = findViewById(R.id.txtDate);
        TextView desc = findViewById(R.id.txtDescription);
        String dateTxt = getIntent().getExtras().getString("date");
        String descTxt = getIntent().getExtras().getString("videoDesc");

        date.setText(dateTxt);
        desc.setText(descTxt);

        MotionLayout motionLayout = findViewById(R.id.motionLayoutDesc);

        btnDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("desc check", dateTxt + descTxt);

               // motionLayout.transitionToEnd();

                if (isVisible == false){

                    motionLayout.transitionToEnd();
                    isVisible = true;

                }else{
                    isVisible = false;
                     motionLayout.transitionToStart();

                }

            }
        });
    }


    @Override
    public void onConfigurationChanged(Configuration newConfiguration) {
        super.onConfigurationChanged(newConfiguration);
        youTubePlayerView.getPlayerUiController().getMenu().dismiss();
    }

    @Override
    public void onBackPressed() {
        if (youTubePlayerView.isFullScreen())
            youTubePlayerView.exitFullScreen();
        else
            super.onBackPressed();
    }

    private void iniRvComment() {

        RvComment.setLayoutManager(new LinearLayoutManager(this));

        String postKey1 = getIntent().getExtras().getString("chatID");

        DatabaseReference commentRef = firebaseDatabase.getReference(COMMENT_KEY).child(postKey1);

        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listComments = new ArrayList<>();

                for (DataSnapshot snap:dataSnapshot.getChildren()) {

                    Comments comments = snap.getValue(Comments.class);
                    if (snap.hasChild(uid)) {

                        listComments.remove(comments);

                    } else {
                        listComments.add(comments);
                    }
                }

                commentAdapter = new CommentAdapter(LivestreamFragment.this,listComments);
                Collections.reverse(listComments);
                RvComment.setAdapter(commentAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initYouTubePlayerView() {
        String video = getIntent().getExtras().getString("video");

        youTubePlayerView.getPlayerUiController().enableLiveVideoUi(true);
        youTubePlayerView.getPlayerUiController().showYouTubeButton(false);
        youTubePlayerView.getPlayerUiController().showMenuButton(false);


        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                YouTubePlayerUtils.loadOrCueVideo(
                        youTubePlayer,
                        getLifecycle(),
                        video
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
                fullScreenHelper.enterFullScreen();
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


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

    private void showMessage(String message) {
        Toast.makeText(LivestreamFragment.this, message,Toast.LENGTH_LONG).show();
    }

    public String getPostKey() {
        postKey = getIntent().getExtras().getString("chatID");

        return postKey;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}