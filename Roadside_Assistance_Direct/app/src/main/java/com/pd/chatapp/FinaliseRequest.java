package com.pd.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class FinaliseRequest extends AppCompatActivity {
    TextView pro, fee, rating;
    EditText ratingField;
    Button home;
    Request request;
    String ratingText = "Rating /5";
    Firebase url =  new Firebase("https://testpro-b5951.firebaseio.com/requests");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalise_request);

        home = findViewById(R.id.b_return);
        home.setOnClickListener(homePressed);

        ratingField = findViewById(R.id.et_rating);
        pro = findViewById(R.id.tv_finalPro);
        fee = findViewById(R.id.tv_finalFee);
        rating = findViewById(R.id.tv_finalRating);



        //get information from DB

        url.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                request = dataSnapshot.child(Login.getUser()).getValue(Request.class);
                next();

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }


        });

    }

    public View.OnClickListener homePressed = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Professional pro = request.getPro();
            double ratingScore = Double.valueOf(ratingField.getText().toString());
            if(ratingScore > 5)ratingScore = 5;
            else if(ratingField.getText().toString().equals("")){ratingScore = 2.5;}
            pro.addRating(ratingScore);

            url.child(Login.getUser()).removeValue();
            url = new Firebase("https://testpro-b5951.firebaseio.com/professionals");
            url.child(pro.getUsername()).setValue(pro);

            startActivity(new Intent(FinaliseRequest.this, Home.class));
        }
    };


    private void next(){
        url.child(Login.getUser()).removeValue();
        pro.setText("Your Professional: "+request.getPro().getUsername());
        fee.setText("Fee(void if subscribed): $"+request.getPro().getFee());
        rating.setText(ratingText);
    }
}
