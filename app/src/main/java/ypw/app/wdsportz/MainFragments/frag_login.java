package ypw.app.wdsportz.MainFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.example.wdsportz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import ypw.app.wdsportz.MainActivities.Auth_Activity;
import ypw.app.wdsportz.ViewModels.LoginViewModel;
import ypw.app.wdsportz.utils.PreferenceUtils;


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
    View view;
    boolean isUp;


    public frag_login() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        isUp = false;
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

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        viewModel = ViewModelProviders.of(requireActivity()).get(LoginViewModel.class);

        this.view = view;
        btnSignIn = view.findViewById(R.id.btn_signIn);
        btnSignUp = view.findViewById(R.id.signUp);
        forgot = view.findViewById(R.id.txtForgot);
        linearLayoutCredentials = view.findViewById(R.id.linearLayoutLogin);
//        guideline = view.findViewById(R.id.guideline2);
        ConsLayout_Login = view.findViewById(R.id.ConstraintLayout_Login);

//      Initially hide the content view.
        linearLayoutCredentials.setVisibility(View.GONE);

        TextView txtUsername = view.findViewById(R.id.txtUsername);
        final TextView txtPassword = view.findViewById(R.id.txtPassword);


        btnSignIn.setOnClickListener(new OnClickListener() {
              public void onClick(final View view) {

               if (linearLayoutCredentials.getVisibility() == View.VISIBLE){

                   //Sign In Process
                    pd = new ProgressDialog(getContext());
                    pd.setTitle("Authenticating");
                    pd.show();

                    final String email = txtUsername.getText().toString();
                    final String password = txtPassword.getText().toString();
        if (email.isEmpty() || password.isEmpty()){

            Toast.makeText(getActivity(), "Email an password cannot be empty",Toast.LENGTH_SHORT).show();
            pd.dismiss();
            }else {

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
}
                   } else {

                   animateLogin();
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

/*
                btnSignIn.performClick();
                txtUsername.setText("Ore.yusuf@hotmail.co.uk");
                txtPassword.setText("Password1");
                btnSignIn.performClick();
*/

//                Navigation.findNavController(view).navigate(R.id.action_global_frag_IniTeamSelection);
            //    ((Auth_Activity)getActivity()).goToMainFeed();


            }

        });
    }

    private void animateLogin() {

        Transition transition = new Fade();
        transition.setDuration(1200);
        transition.addTarget(linearLayoutCredentials);

        TransitionManager.beginDelayedTransition(getView().findViewById(R.id.ConstraintLayout_Login), transition);
        linearLayoutCredentials.setVisibility(View.VISIBLE);

        ConstraintLayout constraintLayout = getView().findViewById(R.id.ConstraintLayout_Login);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(R.id.btn_signIn,ConstraintSet.TOP,R.id.linearLayoutLogin,ConstraintSet.BOTTOM,24);
        constraintSet.applyTo(constraintLayout);

        // slide the view from below itself to the current position

//        onSlideViewButtonClick(getView().findViewById(R.id.linearLayoutLogin));

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
