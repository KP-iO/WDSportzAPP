package com.example.wdsportz;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    EditText _txtEmail, _txtName, _txtPassword, _txtDOB;
    ImageButton _btnCreateAcc;
    Spinner country_spinner, city_spinner;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
}


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.registration);
//
//        mAuth = FirebaseAuth.getInstance();
//
//        _btnCreateAcc=(ImageButton)findViewById(R.id.btnCreateAcc);
//        _txtEmail=(EditText)findViewById(R.id.Email);
//        _txtPassword=(EditText)findViewById(R.id.Password);
//        _btnCreateAcc.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                String email = _txtEmail.getText().toString();
//                String pass = _txtPassword.getText().toString();
//        }
//
//    });

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }
//
//    public void createAccount(){
//        mAuth.createUserWithEmailAndPassword(_txtEmail, _txtPassword)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(Register.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//
//                        // ...
//                    }
//                });
//    }
//};
//
