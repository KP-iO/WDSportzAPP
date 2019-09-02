package com.example.wdsportz;
//////////////
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);
        openHelper = new DatabaseHelper(this);
        db=openHelper.getReadableDatabase();
        _btnlogin=(Button)findViewById(R.id.button3);
        _txtEmail=(EditText)findViewById(R.id.username);
        _txtPass=(EditText)findViewById(R.id.Pass);
        _btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = _txtEmail.getText().toString();
                String pass = _txtPass.getText().toString();
                cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COL_4 + "=? AND " + DatabaseHelper.COL_3 + "=?", new String[]{email, pass});
                if(cursor!=null){
                    if (cursor.getCount() > 0){
                        cursor.moveToNext();
                        Toast.makeText(getApplicationContext(), "Successfully Logged In", Toast.LENGTH_LONG).show();
                        openMainActivity();
                    }else{
                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    public void openMainActivity() {
        Intent intent = new Intent(Login.this, HomePage.class);
        startActivity(intent);
    }
}

