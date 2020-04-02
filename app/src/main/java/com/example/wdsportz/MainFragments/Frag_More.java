package com.example.wdsportz.MainFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.wdsportz.MainActivities.Auth_Activity;
import com.example.wdsportz.R;
import com.example.wdsportz.utils.PreferenceUtils;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag_More#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_More extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public Frag_More() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment More1.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag_More newInstance(String param1, String param2) {
        Frag_More fragment = new Frag_More();
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
        return inflater.inflate(R.layout.fragment_more, container, false);

    }


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        final Button btnManageProfile = view.findViewById(R.id.btnManageProfile);
        btnManageProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).navigate(R.id.action_global_frag_Profile);
                }
        });

        final Button btnTnC = view.findViewById(R.id.btnTnC);
        btnTnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_global_frag_About);
            }
        });


        Button btnSignout = getView().findViewById(R.id.btnSignOut);

        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Sign", "Signout pressed");
                firebaseAuth.getInstance().signOut();
                PreferenceUtils.saveEmail("", getContext());
                PreferenceUtils.savePassword("", getContext());
                goToLogIn();
            }
        });

    }

    public void goToLogIn() {
        Intent intent = new Intent(getContext(), Auth_Activity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        }
}
