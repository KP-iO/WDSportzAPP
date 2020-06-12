package com.example.wdsportz.MainFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.wdsportz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag_forgot#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_forgot extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "Email";

    EditText email;
    Button button;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    ProgressDialog pd;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Frag_forgot() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_forgot.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag_forgot newInstance(String param1, String param2) {
        Frag_forgot fragment = new Frag_forgot();
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
        return inflater.inflate(R.layout.frag_forgotassword, container, false);
    }


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final Context context = view.getContext();

        button = view.findViewById(R.id.btnForgotPass);
        email = view.findViewById(R.id.forgotEmail);

        button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        pd = new ProgressDialog(getContext());
        pd.setTitle("Authenticating");
        pd.show();
        final String emailAddress = email.getText().toString();
        if (isNullOrEmpty(emailAddress)) {
            Toast.makeText(getActivity(), "Check if email correctly entered?", Toast.LENGTH_SHORT).show();
            pd.dismiss();
        } else{
            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Email sent.");
                                pd.dismiss();
                                Navigation.findNavController(view).navigate(R.id.action_global_frag_login);
                                Toast.makeText(getActivity(), "Check email for email", Toast.LENGTH_SHORT).show();

                            } else {

                            }
                        }
                    });
    }
    }
});



    }


    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.trim().isEmpty())
            return false;
        return true;
    }
}
