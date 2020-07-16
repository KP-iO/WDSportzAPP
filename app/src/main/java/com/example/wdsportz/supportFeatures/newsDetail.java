package com.example.wdsportz.supportFeatures;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wdsportz.Adapters.CommentAdapter;
import com.example.wdsportz.R;
import com.example.wdsportz.ViewModels.Comments;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link newsDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link newsDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class newsDetail extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageView imageView, imageView1;
    EditText editText;
    TextView titleBox, descBox, textView, textView2;
    Button button;
    private AdView mAdView;

    ScrollView scrollView;

    String postKey;
    RecyclerView RvComment;
    CommentAdapter commentAdapter;
    List<Comments> listComments;
    static String COMMENT_KEY = "Comment";

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    MaterialButton shareAction;
    String uid = user.getUid();


    FirebaseDatabase firebaseDatabase;

    private OnFragmentInteractionListener mListener;

    public newsDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment newsDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static newsDetail newInstance(String param1, String param2) {
        newsDetail fragment = new newsDetail();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_detail, container, false);
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
        textView2 = getView().findViewById(R.id.desc);
        imageView1 = getView().findViewById(R.id.avatar);
        editText = getView().findViewById(R.id.edit_box);
        button = getView().findViewById(R.id.add);
        shareAction = getView().findViewById(R.id.action_button_share);


        firebaseAuth = firebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = firebaseDatabase.getInstance();

        String strTitle = getArguments().getString("title01");


        imageView = getView().findViewById(R.id.articleImages);
        titleBox = getView().findViewById(R.id.title1);
        descBox = getView().findViewById(R.id.desc1);
//        scrollView = getView().findViewById(R.id.sV);


        MobileAds.initialize(getContext(),new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = getView().findViewById(R.id.adView);
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

        String title = getArguments().getString("title");
        String image = getArguments().getString("image");
        String desc = getArguments().getString("desc");
        String currentUseImg = firebaseUser.getPhotoUrl().toString();

        descBox.setMovementMethod(new ScrollingMovementMethod());


//        String img = getIntent().getStringExtra("Image");

        titleBox.setText(title);
        descBox.setText(desc);

        Glide.with(view)
                .load(currentUseImg)
                .into(imageView1);

        Glide.with(view)
                .load(image)
                .into(imageView);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {


                button.setVisibility(View.INVISIBLE);
                DatabaseReference commentReference =firebaseDatabase.getReference(COMMENT_KEY).child(getPostKey()).push();
                String comment_content = editText.getText().toString();
                String key = commentReference.getKey();
                String chatID = getArguments().getString("chatID");
                String uid = firebaseUser.getUid();
                String uname = firebaseUser.getDisplayName();
                String uimg = firebaseUser.getPhotoUrl().toString();
                String reportID = "";

                Comments comments = new Comments(comment_content,uid,uname,uimg,key, chatID,reportID, ServerValue.TIMESTAMP);
                Glide.with(view)
                        .load(uimg)
                        .into(imageView1);

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

//        scrollBottom();
        iniRvComment();

        shareAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareAction();

            }
        });

    }

//    private void scrollBottom()
//    {
//        scrollView.post(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        scrollView.smoothScrollTo(0,descBox.getBottom());
//                    }
//                });
//
//    }


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

    private void   showMessage (String message) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Toast.makeText(getContext(), message,Toast.LENGTH_LONG).show();
    }
public String getPostKey() {
    postKey = getArguments().getString("chatID");
    return postKey;
}

    private void iniRvComment() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        RvComment.setLayoutManager(new LinearLayoutManager(getContext()));

        String postKey1 = getArguments().getString("chatID");
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


    public void shareAction(){
        Intent intent1 = new Intent(Intent.ACTION_SEND);
        intent1.setType("text/plain");
        String shareBody = "NEWS CONTENT";
        intent1.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(intent1, "Share Using"));
    }
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
