package com.pd.chatapp;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class Home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //include all listener definitions here
        Button logout = (Button) findViewById(R.id.b_logout);
        logout.setOnClickListener(logoutPressed);
        Button vehicle = (Button) findViewById(R.id.b_ManageVehicles);
        vehicle.setOnClickListener(vehiclePressed);
        Button viewReq = (Button) findViewById(R.id.b_viewRequest);
        viewReq.setOnClickListener(viewReqPressed);
        Button chat = (Button) findViewById(R.id.b_chat);
        chat.setOnClickListener(chatPressed);
        Button help = (Button) findViewById(R.id.b_requestHelp);
        help.setOnClickListener(helpPressed);

        Button profile = (Button) findViewById(R.id.b_Profile);
        profile.setOnClickListener(profilePressed);

        final TextView uName = findViewById(R.id.tv_username);


        //get information from DB
        final Firebase ref = new Firebase("https://testpro-b5951.firebaseio.com/clients/");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map map = dataSnapshot.child(Login.getUser()).getValue(Map.class);
                Client client = new Client(
                        (String) map.get("fName"),
                        (String) map.get("lName"),
                        (String) map.get("email"),
                        (String) map.get("pNum"),
                        (String) map.get("username"),
                        (String) map.get("password"));

                String name = client.getfName() + " " + client.getlName();
                uName.setText(name);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        //System.out.println(client.fName);

    }

    public OnClickListener helpPressed = new OnClickListener() {
        @Override
        public void onClick(View v) {

            startActivity(new Intent(Home.this, CreateRequest.class));
        }
    };

    public OnClickListener logoutPressed = new OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(Home.this, Login.class));
        }
    };


    public OnClickListener vehiclePressed = new OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(Home.this, ManageVehicle.class));
        }
    };
    public OnClickListener viewReqPressed = new OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(Home.this, ViewRequestClient.class));
        }
    };

    public OnClickListener chatPressed = new OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(Home.this, Chat.class));
        }
    };

    public OnClickListener profilePressed = new OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(Home.this, EditUserDetails.class));
        }
    };
}









