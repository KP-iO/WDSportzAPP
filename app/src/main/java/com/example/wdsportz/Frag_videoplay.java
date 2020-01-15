package com.example.wdsportz;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wdsportz.Adapters.CommentAdapter;
import com.example.wdsportz.ViewModels.Comments;
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
import java.util.List;


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
    private String mParam1;
    private String mParam2;
//instances
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

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


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

        RvComment = getView().findViewById(R.id.chat_box);
        textView = getView().findViewById(R.id.match_title);
        textView2 = getView().findViewById(R.id.date);
        imageView = getView().findViewById(R.id.avatar);
        editText = getView().findViewById(R.id.edit_box);
        button = getView().findViewById(R.id.add);
        videoView = getView().findViewById(R.id.Watch_view1);

        firebaseAuth = firebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = firebaseDatabase.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                button.setVisibility(View.INVISIBLE);
                DatabaseReference commentReference =firebaseDatabase.getReference(COMMENT_KEY).child(getPostKey()).push();
                String comment_content = editText.getText().toString();
                String uid = firebaseUser.getUid();
                String uname = firebaseUser.getDisplayName();
//                String uimg = firebaseUser.getPhotoUrl().toString();
                Comments comments = new Comments(comment_content,uid,uname);

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










        iniRvComment();


        setVideoView(context);



    }

    private void iniRvComment() {

        RvComment.setLayoutManager(new LinearLayoutManager(getContext()));

        String postKey1 = getArguments().getString("title");
        DatabaseReference commentRef = firebaseDatabase.getReference(COMMENT_KEY).child(postKey1);


        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listComments = new ArrayList<>();
                for (DataSnapshot snap:dataSnapshot.getChildren()){

                    Comments comments = snap.getValue(Comments.class);
                    listComments.add(comments);
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
        Toast.makeText(getContext(), message,Toast.LENGTH_LONG).show();
    }

    private void setVideoView(final Context context){
        //used to collect argument
        String str = getArguments().getString("amount");


        MediaController mediaController = new MediaController(getContext());
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        Uri uri = Uri.parse(str);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
    }

    public String getPostKey() {
        postKey = getArguments().getString("title");
        return postKey;
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
