package com.example.wdsportz;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class Signout extends Fragment {

    TextView userEmail;
    Button userSignout;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_signout, container, false);
//        userEmail = getActivity().findViewById(R.id.tvUserEmail1);
//
//
//        userSignout = getActivity().findViewById(R.id.signout);
//
//        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseUser = firebaseAuth.getCurrentUser();
//
//        userEmail.setText(firebaseUser.getEmail());
//
//        userSignout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//               // Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), Login.class);
//                getActivity().startActivity(intent);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//
//
//            }
//        });
//
//
//
//    }
//
}}
