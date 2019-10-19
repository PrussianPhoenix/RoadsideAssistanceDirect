package com.pd.chatapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewRequests extends AppCompatActivity {
    ArrayList<Request> requests = new ArrayList<>();
    private Spinner sRequests;
    private Button bAccept;
    private TextView description, location, vehicleDesc;
    private Professional pro;
    private int Position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrequest);


        //get information from DB
        final Firebase ref =  new Firebase("https://testpro-b5951.firebaseio.com/professionals/");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map map = dataSnapshot.child(Login.getUser()).getValue(Map.class);
                Professional professional = dataSnapshot.child(Login.getUser()).getValue(Professional.class);

                setPro(professional);


            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        bAccept = (Button)findViewById(R.id.b_AcceptRequest);
        sRequests = (Spinner)findViewById(R.id.s_requests);
        description = (TextView)findViewById(R.id.tv_Description);
        location = (TextView)findViewById(R.id.tv_Location);
        vehicleDesc = (TextView)findViewById(R.id.tv_VehicleDesc);


        //include all listener definitions here
        bAccept.setOnClickListener(acceptPressed);

        try{
            ArrayAdapter<Request> adapter = new ArrayAdapter<>
                    (this, android.R.layout.simple_spinner_item, requests);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            sRequests.setAdapter(adapter);
        }
        catch(NullPointerException E){

        }
        refresh();
        populate();


    }

    //populates fields after a request is selected
    private void populate(){
        //TODO: populate the spinner and fields from the data in the array
        ArrayAdapter<Request> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, requests);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sRequests.setAdapter(adapter);

        sRequests.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Request request = (Request) parent.getItemAtPosition(position);
                Position = position;
                // Notify the selected item text
                location.setText(request.getLocation());
                VehicleDetails v = request.getVehicle();
                description.setText((request.getProblemDescription()));
                String vDesc = "A "+v.getColour()+
                        " "+v.getMake()+
                        " "+v.getModel()+
                        " registered as "+v.getRego_num();
                vehicleDesc.setText(vDesc);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //refreshes the scrollview
    private void refresh(){

        //TODO: display all requests
        //get information from DB
        Firebase ref =  new Firebase("https://testpro-b5951.firebaseio.com/requests");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Request request;
                for(DataSnapshot child: dataSnapshot.getChildren())
                {
                    request = child.getValue(Request.class);
                    requests.add(request);
                    populate();


                }

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        populate();
    }


    //accept the request wait for client approval
    public View.OnClickListener acceptPressed = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Request request = requests.get(Position);
            System.out.println(request.getUsername());
            request.addPendingPro(pro);

            Firebase reference = new Firebase("https://testpro-b5951.firebaseio.com/requests/");
            reference.child(request.getUsername()).setValue(request);
            ViewRequests.super.onBackPressed();

        }


    };

    private void setPro(Professional in){
        pro = in;
    }
    private void update(){
        Firebase reference = new Firebase("https://testpro-b5951.firebaseio.com/professionals");
        reference.child(Login.getUser()).setValue(pro);
    }
}
