package com.example.wdsportz;
//////////////
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
/////ORE COMMIT CHECK
public class Login extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        findViewById(R.id.signup).setOnClickListener(this);

            }

    public void onClick(View view){
        switch (view.getId()) {
            case R.id.signup:

            startActivity(new Intent(this, Register.class));
        }
    }
        };

