package com.example.phploginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class Home extends AppCompatActivity {

    TextView name,email;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionManager=new SessionManager(this);
        sessionManager.checkLogin();


        name=findViewById(R.id.name_id);
        email=findViewById(R.id.email_id);

        HashMap<String,String> user=sessionManager.userDetails();

        String uName=user.get(sessionManager.NAME);
        String uEmail=user.get(sessionManager.EMAIL);

        name.setText(uName);
        email.setText(uEmail);


    }

    public void logout(View view) {
        sessionManager.logOut();
    }
}
