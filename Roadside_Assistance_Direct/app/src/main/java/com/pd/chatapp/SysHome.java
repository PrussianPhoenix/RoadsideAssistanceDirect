package com.pd.chatapp;

import android.support.v7.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class SysHome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syshome);

        //include all listener definitions here
        Button logout = (Button) findViewById(R.id.b_slogout);
        logout.setOnClickListener(logoutPressed);
        Button rcButton = (Button) findViewById(R.id.b_removeC);
        rcButton.setOnClickListener(clientPressed);
        Button rpButton = (Button) findViewById(R.id.b_removeP);
        rpButton.setOnClickListener(professionalPressed);
        final TextView uName = findViewById(R.id.tv_adminname);
        uName.setText(SysLogin.getUser());
    }

    public OnClickListener logoutPressed = new OnClickListener(){
        @Override
        public void onClick(View v) {
            startActivity(new Intent(SysHome.this, SysLogin.class));
        }
    };

    public OnClickListener clientPressed = new OnClickListener(){
        @Override
        public void onClick(View v) {

            startActivity(new Intent(SysHome.this, ClientRemove.class));
        }
    };

    public OnClickListener professionalPressed = new OnClickListener(){
        @Override
        public void onClick(View v) {
            startActivity(new Intent(SysHome.this, ProRemove.class));
        }
    };
}
