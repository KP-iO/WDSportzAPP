package com.example.wdsportz;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wdsportz.LoginDatabase_Temp.DatabaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    ImageButton _btnreg;
    EditText _txtfname, _txtlname, _txtpass, _txtemail, _txtphone;
    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        openHelper = new DatabaseHelper(this);
        _btnreg = (ImageButton) findViewById(R.id.btnCreateAcc);
        _txtfname = (EditText) findViewById(R.id.Name);
        _txtpass = (EditText) findViewById(R.id.Password);
        _txtemail = (EditText) findViewById(R.id.Email);
        // _txtphone = (EditText)findViewById(R.id.txtphone);
        // _btnlogin = (Button)findViewById(R.id.btnlog);
        firebaseAuth = firebaseAuth.getInstance();

        _btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(_txtemail.getText().toString(),
                        _txtpass.getText().toString())      // code used to create user
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                    _txtemail.setText("");
                                    _txtpass.setText("");
                                    openloginscreen();
                                } else {
                                    Toast.makeText(Register.this, task.getException().getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }
    public void openloginscreen(){
        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
    }
}














//            @Override
//            public void onClick(View v) {
//                db=openHelper.getWritableDatabase();
//                String fname=_txtfname.getText().toString();
//                //String lname=_txtlname.getText().toString();
//                String pass=_txtpass.getText().toString();
//                String email=_txtemail.getText().toString();
//              //  String phone=_txtphone.getText().toString();
//                insertdata(fname,pass,email);
//                Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_LONG).show();
//                openloginscreen();
//            }
//        });
//
//    }

//
//
//    public void insertdata(String fname, String pass, String email){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.COL_2,fname);
//        //contentValues.put(DatabaseHelper.COL_3,lname);
//        contentValues.put(DatabaseHelper.COL_3,pass);
//        contentValues.put(DatabaseHelper.COL_4,email);
//       // contentValues.put(DatabaseHelper.COL_6,phone);
//        long id = db.insert(DatabaseHelper.TABLE_NAME,null, contentValues);
//    }
//}


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
