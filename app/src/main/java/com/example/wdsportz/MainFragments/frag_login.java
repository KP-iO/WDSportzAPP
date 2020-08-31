package com.example.wdsportz.MainFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.example.wdsportz.MainActivities.Auth_Activity;
import com.example.wdsportz.R;
import com.example.wdsportz.ViewModels.LoginViewModel;
import com.example.wdsportz.utils.PreferenceUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;


public class frag_login extends Fragment {

    private OnFragmentInteractionListener mListener;
    public FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private LoginViewModel viewModel;
    ProgressDialog pd;

    Guideline guideline;
    ConstraintLayout ConsLayout_Login;

    TextView forgot;
    Button btnSignUp;
    Button btnSignIn;

    View linearLayoutCredentials;

    public frag_login() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loginpage, container, false);

    }


    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Auto Sign In
        if (PreferenceUtils.getEmail(getContext()) != null && !PreferenceUtils.getEmail(getContext()).equals("")) {
            ((Auth_Activity) getActivity()).goToMainFeed();
        }
        viewModel = ViewModelProviders.of(requireActivity()).get(LoginViewModel.class);

        btnSignIn = view.findViewById(R.id.btn_signIn);
        btnSignUp = view.findViewById(R.id.signUp);
        forgot = view.findViewById(R.id.txtForgot);
        linearLayoutCredentials = view.findViewById(R.id.linearLayoutLogin);
        guideline = view.findViewById(R.id.guideline2);
        ConsLayout_Login = view.findViewById(R.id.ConstraintLayout_Login);

//      Initially hide the content view.
        linearLayoutCredentials.setVisibility(View.GONE);

        TextView txtUsername = view.findViewById(R.id.txtUsername);
        final TextView txtPassword = view.findViewById(R.id.txtPassword);


        View.OnFocusChangeListener focusListener = new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){

                    Log.d("OFCUSSSS","ORe");
                    guideline.setGuidelineBegin(100);
                    ConsLayout_Login.getBackground().setColorFilter(Color.parseColor("EB0DB14B"), PorterDuff.Mode.SRC_OVER);


                } else {

                    guideline.setGuidelineBegin(357);
                    ConsLayout_Login.getBackground().setColorFilter(Color.parseColor("#00FFFFFF"), PorterDuff.Mode.SRC_OVER);

                }
            }
        };

        txtUsername.setOnFocusChangeListener(focusListener);
        txtPassword.setOnFocusChangeListener(focusListener);

        btnSignIn.setOnClickListener(new OnClickListener() {
              public void onClick(final View view) {

               if (linearLayoutCredentials.getVisibility() == View.VISIBLE){

                   //Sign In Process
                    pd = new ProgressDialog(getContext());
                    pd.setTitle("Authenticating");
                    pd.show();

                    final String email = txtUsername.getText().toString();
                    final String password = txtPassword.getText().toString();
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            // Code used to authenticate user
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task){
                                    if(task.isSuccessful()) {
                                        viewModel.authenticate(email, password);
                                        PreferenceUtils.saveEmail(email, getContext());
                                        PreferenceUtils.savePassword(password, getContext());
                                        ((Auth_Activity)getActivity()).goToMainFeed();
                                        pd.dismiss();

                                    }

                                }

                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                notifyUser("Invalid password");
                            } else if (e instanceof FirebaseAuthInvalidUserException) {
                                String errorCode =
                                        ((FirebaseAuthInvalidUserException) e).getErrorCode();
                                if (errorCode.equals("ERROR_USER_NOT_FOUND")) {
                                    notifyUser("No matching account found with email");
                                } else if (errorCode.equals("ERROR_USER_DISABLED")) {
                                    notifyUser("User account has been disabled");
                                } else {
                                    notifyUser(e.getLocalizedMessage());
                                }
                            }
                    }

                        private void notifyUser(String toast) {
                            pd.dismiss();
                            Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT).show();

                        }
                    });


                   } else {

                   animateLinearLayout();
               }
              }
        });



        btnSignUp.setOnClickListener(new OnClickListener(){
            public void onClick(final View view) {
                Navigation.findNavController(view).navigate(R.id.globalAction_Register);

             }
        });

        forgot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_global_frag_forgot);
            }
        });


        Button button = view.findViewById(R.id.Btn_Test);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

//                btnSignIn.performClick()
                txtUsername.setText("Oreyusuf@hotmail.com");
                txtPassword.setText("Password1");
                btnSignIn.performClick();

//                Navigation.findNavController(view).navigate(R.id.action_global_frag_IniTeamSelection);
            //    ((Auth_Activity)getActivity()).goToMainFeed();



            }

        });
    }

    private void animateLinearLayout() {

        Transition transition = new Fade();
        transition.setDuration(600);
        transition.addTarget(linearLayoutCredentials);

        TransitionManager.beginDelayedTransition(getView().findViewById(R.id.ConstraintLayout_Login), transition);
        linearLayoutCredentials.setVisibility(View.VISIBLE);

    }



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
