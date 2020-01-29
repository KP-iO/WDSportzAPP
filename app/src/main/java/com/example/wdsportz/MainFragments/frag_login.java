package com.example.wdsportz.MainFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.wdsportz.R;
import com.example.wdsportz.ViewModels.LoginViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frag_login.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frag_login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag_login extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final String TAG = "MyActivity";
    private OnFragmentInteractionListener mListener;
    public FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private LoginViewModel viewModel;


    public frag_login() {
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
    public static frag_login newInstance(String param1, String param2) {
        frag_login fragment = new frag_login();
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
        return inflater.inflate(R.layout.fragment_loginpage, container, false);
    }

    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(requireActivity()).get(LoginViewModel.class);













        final TextView txtUsername = view.findViewById(R.id.username);
        final TextView txtPassword = view.findViewById(R.id.password);
        Button btnSignUp = view.findViewById(R.id.signUp);
        Button signIn = view.findViewById(R.id.btn_signIn);

        signIn.setOnClickListener(new View.OnClickListener(){
            public void onClick(final View view) {




                firebaseAuth.signInWithEmailAndPassword(txtUsername.getText().toString(), txtPassword.getText().toString())   // Code used to authenticate user
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task){
                                if(task.isSuccessful()) {
                                    viewModel.authenticate(txtUsername.getText().toString(),
                                            txtPassword.getText().toString());
//                                    Navigation.findNavController(view).navigate(R.id.action_global_frag_IniTeamSelection);
                                
//                                    viewModel.refuseAuthentication();
//                                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }

                            }

                        });

            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener(){
            public void onClick(final View view) {
                Navigation.findNavController(view).navigate(R.id.globalAction_Register);

                                         }
        });




        Button button = view.findViewById(R.id.Btn_Test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_global_frag_IniTeamSelection);
            }


        });


        viewModel.authenticationState.observe(getViewLifecycleOwner(),
                new Observer<LoginViewModel.AuthenticationState>() {
                    @Override
                    public void onChanged(LoginViewModel.AuthenticationState authenticationState) {
                        switch (authenticationState) {
                            case AUTHENTICATED:
                                Navigation.findNavController(view).navigate(R.id.action_global_frag_IniTeamSelection);
                                break;
                            case INVALID_AUTHENTICATION:
                                Log.d(TAG, "Wrongggg");
                                Toast.makeText(getActivity(), "Invalid text", Toast.LENGTH_LONG).show();
                                break;
                        }
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
