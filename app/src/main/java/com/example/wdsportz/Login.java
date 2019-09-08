package com.example.wdsportz;
//////////////
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/////ORE COMMIT CHECK
public class Login extends AppCompatActivity {
    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;
    Button _btnlogin;
    EditText _txtEmail, _txtPass;
    Cursor cursor;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);
        openHelper = new DatabaseHelper(this);
        db=openHelper.getReadableDatabase();
        _btnlogin=(Button)findViewById(R.id.button3);
        _txtEmail=(EditText)findViewById(R.id.username);
        _txtPass=(EditText)findViewById(R.id.Pass);
        firebaseAuth = firebaseAuth.getInstance();


        _btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signInWithEmailAndPassword(_txtEmail.getText().toString(), _txtPass.getText().toString())   // Code used to authenticate user
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task){
                                if(task.isSuccessful()) {
                                    openMainActivity();
                                }else{
                                    Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }

                            }

                        });

            }
        });






//            @Override
//            public void onClick(View v) {
//                String email = _txtEmail.getText().toString();
//                String pass = _txtPass.getText().toString();
//                cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COL_4 + "=? AND " + DatabaseHelper.COL_3 + "=?", new String[]{email, pass});
//                if(cursor!=null){
//                    if (cursor.getCount() > 0){
//                        cursor.moveToNext();
//                        Toast.makeText(getApplicationContext(), "Successfully Logged In", Toast.LENGTH_LONG).show();
//                        openMainActivity();
//                    }else{
//                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//        });
    }


    public void openMainActivity() {
        Intent intent = new Intent(Login.this, HomePage.class);
        startActivity(intent);
    }
}

