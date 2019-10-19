package com.pd.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prohome);

        //include all listener definitions here
        Button logout = (Button) findViewById(R.id.b_logout);
        logout.setOnClickListener(logoutPressed);

        Button chat = (Button) findViewById(R.id.b_chatpro);
        chat.setOnClickListener(chatPressed);

        Button help = (Button) findViewById(R.id.b_requestHelp);
        help.setOnClickListener(helpPressed);

        TextView uName = findViewById(R.id.tv_username);


        uName.setText(Login.getUser());
    }

    public View.OnClickListener helpPressed = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            startActivity(new Intent(ProHome.this, ViewRequests.class));
        }
    };

    public View.OnClickListener logoutPressed = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            startActivity(new Intent(ProHome.this, Login.class));
        }
    };

    public View.OnClickListener chatPressed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(ProHome.this, Chat.class));
        }
    };



}
