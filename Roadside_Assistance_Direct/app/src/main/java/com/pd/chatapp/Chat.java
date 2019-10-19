package com.pd.chatapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class Chat extends AppCompatActivity {
    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase reference1, reference2;
    Button endReq;
    Request req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Firebase.setAndroidContext(this);
        layout = findViewById(R.id.layout1);
        layout_2 = findViewById(R.id.layout2);
        sendButton = findViewById(R.id.sendButton);
        messageArea = findViewById(R.id.messageArea);
        scrollView = findViewById(R.id.scrollView);
        endReq = findViewById(R.id.b_endRequest);
        endReq.setOnClickListener(endPressed);

        //get information from DB
        if(Login.isProfessional())
        {
            Firebase ref =  new Firebase("https://testpro-b5951.firebaseio.com/professionals/");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Professional pro = dataSnapshot.child(Login.getUser()).getValue(Professional.class);
                    reference1 = new Firebase("https://testpro-b5951.firebaseio.com/messages/" + pro.getUsername() + "_" + pro.getChatWith());
                    reference2 = new Firebase("https://testpro-b5951.firebaseio.com/messages/" + pro.getChatWith() + "_" + pro.getUsername());
                    next();
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });
        }
        else
        {
            Firebase ref =  new Firebase("https://testpro-b5951.firebaseio.com/clients/");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Client client = dataSnapshot.child(Login.getUser()).getValue(Client.class);
                    reference1 = new Firebase("https://testpro-b5951.firebaseio.com/messages/" + client.getUsername() + "_" + client.getChatWith());
                    reference2 = new Firebase("https://testpro-b5951.firebaseio.com/messages/" + client.getChatWith() + "_" + client.getUsername());
                    next();
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });
        }


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", Login.getUser());
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                    messageArea.setText("");
                }
            }
        });


    }
    public void next(){
        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if(userName.equals(Login.getUser())){
                    addMessageBox(message, 1);
                }
                else{
                    addMessageBox(message, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void addMessageBox(String message, int type){
        TextView textView = new TextView(Chat.this);
        textView.setText(message);
        textView.setTextColor(Color.WHITE);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 7.0f;


        if(type == 1) {
            lp2.gravity = Gravity.LEFT;
            lp2.bottomMargin = 5;

            textView.setPadding(10,0,10,0);
            textView.setBackgroundResource(R.drawable.bubble_in);

        }
        else{
            lp2.gravity = Gravity.RIGHT;
            lp2.bottomMargin = 5;

            textView.setPadding(10,0,10,0);
            textView.setBackgroundResource(R.drawable.bubble_out);

        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    public View.OnClickListener endPressed = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            if(!Login.isProfessional())
            {
                final Firebase url = new Firebase("https://testpro-b5951.firebaseio.com/requests/");
                url.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        req = dataSnapshot.child(Login.getUser()).getValue(Request.class);
                        req.setStatus("Completed");
                        url.child(Login.getUser()).setValue(req);
                        startActivity(new Intent(Chat.this, FinaliseRequest.class));
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
            }


            //todo: go to review process and show a tax invoice
            Chat.super.onBackPressed();
        }
    };
}