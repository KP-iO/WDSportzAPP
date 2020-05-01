package com.example.wdsportz.supportFeatures;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wdsportz.Adapters.CommentAdapter;
import com.example.wdsportz.FullscreenActivity;
import com.example.wdsportz.R;
import com.example.wdsportz.ViewModels.Comments;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Frag_videoplay.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Frag_videoplay#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_videoplay extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

//instances
    private OnFragmentInteractionListener mListener;
    private EditText editText;
    private TextView textView, textView2;
    private Button button;
    private ImageView imageView;

    private Uri uri;
    String postKey;
    RecyclerView RvComment;
    CommentAdapter commentAdapter;
    ArrayList<Comments> listComments;
    static String COMMENT_KEY = "Comment";
    private SimpleExoPlayer simpleExoPlayer;
    private PlayerView playerView;
    private ImageView fullscreenButton;
    private static final String TAG = "Video Activity";
    FirebaseDatabase commentsDatabase;
    DatabaseReference reference;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    boolean fullscreen = false;
    String uid = user.getUid();

    FirebaseDatabase firebaseDatabase;

    public Frag_videoplay() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_videoplay.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag_videoplay newInstance(String param1, String param2) {
        Frag_videoplay fragment = new Frag_videoplay();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_videoplay, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        final Context context = view.getContext();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        RvComment = getView().findViewById(R.id.chat_box);
        textView = getView().findViewById(R.id.match_title);
        textView2 = getView().findViewById(R.id.desc);
        imageView = getView().findViewById(R.id.avatar);
        editText = getView().findViewById(R.id.edit_box1);
        button = getView().findViewById(R.id.add);
        playerView= getView().findViewById(R.id.Watch_view1);

        firebaseAuth = firebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = firebaseDatabase.getInstance();

        String strTitle = getArguments().getString("title01");
        String desc = getArguments().getString("desc");

        textView.setText(strTitle);
        textView2.setText(desc);
//        videoView.start();





        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {


                button.setVisibility(View.INVISIBLE);
                DatabaseReference commentReference =firebaseDatabase.getReference(COMMENT_KEY).child(getPostKey()).push();
                String key = commentReference.getKey();
                String chatID = getArguments().getString("title");
                String comment_content = editText.getText().toString();
                String uid = firebaseUser.getUid();
                String uname = firebaseUser.getDisplayName();
                String uimg = firebaseUser.getPhotoUrl().toString();
                String reportID = "";
                Comments comments = new Comments(comment_content,uid,uname,uimg,key, chatID,reportID);
                Glide.with(view)
                        .load(uimg)
                        .into(imageView);

                commentReference.setValue(comments).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showMessage("comment added");
                        editText.setText("");
                        button.setVisibility(View.VISIBLE);
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showMessage("fail to add comment: "+e.getMessage());

                    }
                });




            }










        });










        iniRvComment();


        setVideoView(context);



    }



    private void iniRvComment() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        RvComment.setLayoutManager(new LinearLayoutManager(getContext()));

        String postKey1 = getArguments().getString("title");
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

                commentAdapter = new CommentAdapter(getContext(),listComments);
                RvComment.setAdapter(commentAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    private void   showMessage(String message) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Toast.makeText(getContext(), message,Toast.LENGTH_LONG).show();
    }

    private void setVideoView(final Context context){
        //used to collect argument
        String str = getArguments().getString("amount");


        playerView = getView().findViewById(R.id.Watch_view1);
        simpleExoPlayer = new SimpleExoPlayer.Builder(context).build();
        playerView.setPlayer(simpleExoPlayer);



        fullscreenButton = playerView.findViewById(R.id.exo_fullscreen_icon);


        fullscreenButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {


                if(fullscreen) {
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.exo_icon_fullscreen_enter));

                    requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//
//                    if( .getSupportActionBar() != null){
//                        getSupportActionBar().show();
//                    }

                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = (int) ( 400 * getActivity().getApplicationContext().getResources().getDisplayMetrics().density);
                    playerView.setLayoutParams(params);

                    fullscreen = false;
                }else{
                    Log.d(TAG,"Ways" + str);

                    Intent intent = new Intent(getActivity(), FullscreenActivity.class);
//                    Bundle bundle = new Bundle();
                    intent.putExtra("key1", str);;
                    startActivity(intent);

                }
            }
        });



        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "appname"));
        MediaSource videoSource =
                new ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(str));
        simpleExoPlayer.prepare(videoSource);
        simpleExoPlayer.setPlayWhenReady(true);


//        MediaController mediaController = new MediaController(getContext());
//        videoView.setMediaController(mediaController);
//        mediaController.setAnchorView(videoView);
//
//    Uri uri = Uri.parse(str);
//        videoView.setVideoURI(uri);
//        videoView.requestFocus();
}

    public void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.release();
        mListener = null;
    }
    public String getPostKey() {
        postKey = getArguments().getString("title");
        return postKey;
    }
    public static void hideKeyboardFrom() {

    }

//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
