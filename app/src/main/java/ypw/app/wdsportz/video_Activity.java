//package com.example.wdsportz;
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Intent;
//import android.content.pm.ActivityInfo;
//import android.net.Uri;
//import android.os.Bundle;
//
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.ContextCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.wdsportz.Adapters.CommentAdapter;
//import com.example.wdsportz.Adapters.WatchViewAdapter;
//import com.example.wdsportz.ViewModels.Comments;
//import com.google.android.exoplayer2.C;
//import com.google.android.exoplayer2.DefaultLoadControl;
//import com.google.android.exoplayer2.SimpleExoPlayer;
//import com.google.android.exoplayer2.source.MediaSource;
//import com.google.android.exoplayer2.source.ProgressiveMediaSource;
//import com.google.android.exoplayer2.ui.PlayerControlView;
//import com.google.android.exoplayer2.ui.PlayerView;
//import com.google.android.exoplayer2.upstream.DataSource;
//import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
//import com.google.android.exoplayer2.util.Util;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.material.button.MaterialButton;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
////import com.example.wdsportz.supportFeatures.Frag_videoplay;
//
//public class video_Activity extends AppCompatActivity {
////    private Frag_videoplay.OnFragmentInteractionListener mListener;
//    private EditText editText;
//    private TextView textView, textView2;
//    private Button button;
//    private ImageView imageView;
//    MediaSource videoSource;
//    private Uri uri;
//    String postKey;
//    RecyclerView RvComment;
//    CommentAdapter commentAdapter;
//    ArrayList<Comments> listComments;
//    static String COMMENT_KEY = "Comment";
//    private SimpleExoPlayer simpleExoPlayer;
//    private PlayerView mExoPlayerView;
//    private static final String TAG = "Video Activity";
//    FirebaseDatabase commentsDatabase;
//    DatabaseReference reference;
//    private ImageView mFullScreenIcon;
//    private FrameLayout mFullScreenButton;
//    MaterialButton shareAction;
//
//    FirebaseAuth firebaseAuth;
//    FirebaseUser firebaseUser;
//    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//    boolean mExoPlayerFullscreen = false;
//    String uid = user.getUid();
//    WatchViewAdapter watchViewAdapter;
////    Bundle extras = getIntent().getExtras();
//private int mResumeWindow;
//    private long mResumePosition;
//    FirebaseDatabase firebaseDatabase;
//    private Dialog mFullScreenDialog;
//    boolean MUTE = false;
//    private final String STATE_RESUME_WINDOW = "resumeWindow";
//    private final String STATE_RESUME_POSITION = "resumePosition";
//    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_frag_videoplay);
//            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//            RvComment =findViewById(R.id.chat_box);
//            textView = findViewById(R.id.match_title);
//            textView2 = findViewById(R.id.desc);
//            imageView = findViewById(R.id.avatar);
//            editText = findViewById(R.id.edit_box1);
//            button = findViewById(R.id.add);
//            mExoPlayerView= findViewById(R.id.Watch_view1);
//        shareAction = findViewById(R.id.action_button_share);
//
//            firebaseAuth = firebaseAuth.getInstance();
//            firebaseUser = firebaseAuth.getCurrentUser();
//            firebaseDatabase = firebaseDatabase.getInstance();
//            String strTitle = getIntent().getExtras().getString("title01");
//
//            String desc = getIntent().getExtras().getString("desc");
//
////        String strTitle = getString("title01");
////        String desc = getArguments().getString("desc");
//
//            textView.setText(strTitle);
//            textView2.setText(desc);
////        videoView.start();
//
//
//        shareAction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                shareAction();
//
//            }
//        });
//
//
//            button.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(final View view) {
//
//
//                    button.setVisibility(View.INVISIBLE);
//                    DatabaseReference commentReference =firebaseDatabase.getReference(COMMENT_KEY).child(getPostKey()).push();
//                    String key = commentReference.getKey();
//                    String chatID = getIntent().getExtras().getString("chatID");
//                    String comment_content = editText.getText().toString();
//                    String uid = firebaseUser.getUid();
//                    String uname = firebaseUser.getDisplayName();
//                    String uimg = firebaseUser.getPhotoUrl().toString();
//                    String reportID = "";
//                    Comments comments = new Comments(comment_content,uid,uname,uimg,key, chatID,reportID);
//                    Glide.with(view)
//                            .load(uimg)
//                            .into(imageView);
//
//                    commentReference.setValue(comments).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            showMessage("comment added");
//                            editText.setText("");
//                            button.setVisibility(View.VISIBLE);
//                            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
//                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            showMessage("fail to add comment: "+e.getMessage());
//
//                        }
//                    });
//
//
//
//
//                }
//
//
//
//
//
//
//
//
//
//
//            });
//
//
//
//
//
//
//
//
//
//
//            iniRvComment();
//
//
//            initExoPlayer();
//
//
//
//
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//
//        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
//        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
//        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);
//
//        super.onSaveInstanceState(outState);
//    }
//
//    private void initFullscreenDialog() {
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
//        mFullScreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
//            public void onBackPressed() {
//                if (mExoPlayerFullscreen)
//                    closeFullscreenDialog();
//                super.onBackPressed();
//            }
//        };
//    }
//
//    private void openFullscreenDialog() {
////        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
//        mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(video_Activity.this, R.drawable.ic_add_image));
//        mExoPlayerFullscreen = true;
//        mFullScreenDialog.show();
//
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
//    }
//
//    private void closeFullscreenDialog() {
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
//        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
//        ((FrameLayout) findViewById(R.id.main_media_frame)).addView(mExoPlayerView);
//        mExoPlayerFullscreen =  false;
//        mFullScreenDialog.dismiss();
//        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(video_Activity.this, R.drawable.exo_controls_fullscreen_exit));
//    }
//
//    private void initFullscreenButton() {
//
//        PlayerControlView controlView = mExoPlayerView.findViewById(R.id.exo_controller);
//        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
//        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
//        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!mExoPlayerFullscreen)
//                    openFullscreenDialog();
//                else
//                    closeFullscreenDialog();
//            }
//        });
//    }
//
//
//
//    private void iniRvComment() {
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        RvComment.setLayoutManager(new LinearLayoutManager(this));
//
//        String postKey1 = getIntent().getExtras().getString("chatID");
//        DatabaseReference commentRef = firebaseDatabase.getReference(COMMENT_KEY).child(postKey1);
//
//
//        commentRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//                listComments = new ArrayList<>();
//
//                for (DataSnapshot snap:dataSnapshot.getChildren()) {
//
//                    Comments comments = snap.getValue(Comments.class);
//                    if (snap.hasChild(uid)) {
//
//                        listComments.remove(comments);
//
//                    } else {
//                        listComments.add(comments);
//                    }
//                }
//
//                commentAdapter = new CommentAdapter(video_Activity.this,listComments);
//                RvComment.setAdapter(commentAdapter);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//
//    }
//
//
//    private void   showMessage(String message) {
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        Toast.makeText(this, message,Toast.LENGTH_LONG).show();
//    }
//
//    private void initExoPlayer(){
//        //used to collect argument
//        String str = getIntent().getExtras().getString("amount");
//        DefaultLoadControl loadControl = new DefaultLoadControl.Builder().setBufferDurationsMs(32*1024, 64*1024, 1024, 1024).createDefaultLoadControl();
//
//
//
//        mExoPlayerView = findViewById(R.id.exoplayer);
//        simpleExoPlayer = new SimpleExoPlayer.Builder(getApplicationContext()).setLoadControl(loadControl).build();
//        mExoPlayerView.setPlayer(simpleExoPlayer);
//        float currentvolume = simpleExoPlayer.getVolume();
//        simpleExoPlayer.setVolume(currentvolume);
//
//
//
//
//
//
//        mFullScreenIcon = mExoPlayerView.findViewById(R.id.exo_fullscreen_icon);
//
//
//        boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;
//
//        if (haveResumePosition) {
//            mExoPlayerView.getPlayer().seekTo(mResumeWindow, mResumePosition);
//        }
//
//        initFullscreenDialog();
//
//        initFullscreenButton();
//;
//
//        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
//                Util.getUserAgent(this, "appname"));
//        MediaSource videoSource =
//                new ProgressiveMediaSource.Factory(dataSourceFactory)
//                        .createMediaSource(Uri.parse(str));
//        simpleExoPlayer.prepare(videoSource);
//        simpleExoPlayer.setPlayWhenReady(true);
//
//
////        MediaController mediaController = new MediaController(getContext());
////        videoView.setMediaController(mediaController);
////        mediaController.setAnchorView(videoView);
////
////    Uri uri = Uri.parse(str);
////        videoView.setVideoURI(uri);
////        videoView.requestFocus();
//    }
//
////    public void onDestroy() {
////        super.onDestroy();
////        simpleExoPlayer.release();
//////        mListener = null;
////    }
//
//    public String getPostKey() {
//        String postKey = getIntent().getExtras().getString("chatID");
//        return postKey;
//    }
//    public static void hideKeyboardFrom() {
//
//    }
//    @Override
//    protected void onResume() {
//
//        super.onResume();
//        if (mExoPlayerView == null) {
//            mExoPlayerView =  findViewById(R.id.exoplayer);
//            initFullscreenDialog();
//            initFullscreenButton();
//
//        }
//
//
//        if (mExoPlayerFullscreen) {
//            ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
//            mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(video_Activity.this, R.drawable.ic_add_image));
//            mFullScreenDialog.show();
//        }
//    }
//    @Override
//    protected void onPause() {
//
//        super.onPause();
//
//        if (mExoPlayerView != null && simpleExoPlayer != null) {
//            mResumeWindow = simpleExoPlayer.getCurrentWindowIndex();
//            mResumePosition = Math.max(0, simpleExoPlayer.getContentPosition());
//
//           simpleExoPlayer.release();
//        }
//
//        if (mFullScreenDialog != null)
//            mFullScreenDialog.dismiss();
//    }
//
//    public void shareAction(){
//        Intent intent1 = new Intent(Intent.ACTION_SEND);
//        intent1.setType("text/plain");
//        String shareBody = "VIDEO CONTENT";
//        intent1.putExtra(Intent.EXTRA_TEXT, shareBody);
//        startActivity(Intent.createChooser(intent1, "Share Using"));
//    }
//
//
//
//}


