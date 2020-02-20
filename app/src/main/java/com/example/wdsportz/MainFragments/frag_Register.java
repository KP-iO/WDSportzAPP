package com.example.wdsportz.MainFragments;

/**
 * Created by khrishawn
 */

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.wdsportz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frag_Register.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frag_Register#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag_Register extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private OnFragmentInteractionListener mListener;


    public frag_Register() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frag_Register.
     */
    // TODO: Rename and change types and number of parameters
    public static frag_Register newInstance(String param1, String param2) {
        frag_Register fragment = new frag_Register();
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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        final EditText _txtpass =  view.findViewById(R.id.Password);
        final EditText _txtemail =  view.findViewById(R.id.Email);
        ImageButton _btnreg =  view.findViewById(R.id.btnCreateAcc);
        EditText _txtfname =  view.findViewById(R.id.Name);
        ImageButton button = view.findViewById(R.id.btnCreateAcc);
        String username = _txtfname.getText().toString();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //   progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(_txtemail.getText().toString(),
                        _txtpass.getText().toString())      // code used to create user
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                                    String email = user1.getEmail();
                                    String uid = user1.getUid();

                                    HashMap<Object, String> hashMap = new HashMap<>();

                                    hashMap.put("email", email);
                                    hashMap.put("uid", uid);
                                    hashMap.put("name", "");
                                    hashMap.put("phone", "");
                                    hashMap.put("image", "");

                                    // firebase datatabase instance
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                                    //path to store data named "Users"
                                    DatabaseReference reference = database.getReference("Users");

                                    //put data within hashmap in database
                                    reference.child(uid).setValue(hashMap);

                                    // Used to make FirebaseProfile for user
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(email)
//                                                .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                                                .build();

                                        user1.updateProfile(profileUpdates)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d(TAG, "User profile updated.");
                                                        }
                                                    }
                                                });






                                    Toast.makeText(getActivity(), "Registered successfully", Toast.LENGTH_SHORT).show();
                                    _txtemail.setText("");
                                    _txtpass.setText("");
                                    Navigation.findNavController(view).navigate(R.id.action_RegisterToLogin);
                                } else {
                                    Toast.makeText(getActivity(), task.getException().getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });


     

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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