package com.pd.chatapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateRequest extends AppCompatActivity {
    Spinner spinner;
    String pd;
    String sl;
    long id;
    private Client client;
    VehicleDetails vehicle;
    EditText problem_description;
    EditText street_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        spinner = (Spinner) findViewById(R.id.s_VehiclesReq);


        //get information from DB
        Firebase ref =  new Firebase("https://testpro-b5951.firebaseio.com/clients/");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                Map map = dataSnapshot.child(Login.getUser()).getValue(Map.class);
                Client client = new Client(
                        (String)map.get("fName"),
                        (String)map.get("lName"),
                        (String)map.get("email"),
                        (String)map.get("pNum"),
                        (String)map.get("username"),
                        (String)map.get("password"));

                List<VehicleDetails> vList = new ArrayList<>();
                for(DataSnapshot child : dataSnapshot.child(Login.getUser()).child("vehicles").getChildren())
                {
                    map = child.getValue(Map.class);
                    VehicleDetails v = new VehicleDetails(
                        (String)map.get("rego_num"),
                        (String)map.get("colour"),
                        (String)map.get("make"),
                        (String)map.get("model"));

                    vList.add(v);
                }
                client.setVehicles(vList);
                setClient(client);
                Update();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });




        //include all listener definitions here
        try{
            ArrayAdapter<VehicleDetails> adapter = new ArrayAdapter<VehicleDetails>
                    (this, android.R.layout.simple_spinner_item, client.getVehicles());
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);
        }
        catch(NullPointerException E){
            //TODO: go to vehiclemanage and toast that they have no damn vehicles how the fuck can we help bruh
        }
        problem_description = (EditText) findViewById(R.id.et_UserProblem);
        street_location = (EditText) findViewById(R.id.et_StreetLocation);

        Button submit = (Button) findViewById(R.id.b_Submit);
        submit.setOnClickListener(submitPressed);

        //String vd = vehicle_detail.getText().toString();





    }

    public View.OnClickListener submitPressed = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            pd = problem_description.getText().toString();
            sl = street_location.getText().toString();

            Request request = new Request(vehicle, pd, sl, client);
            // Notify the selected item text

            Firebase ref1 = new Firebase("https://testpro-b5951.firebaseio.com/requests");

            ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {

                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });




            //VehicleDetails vehicle = request.getVehicle();
            //generate request from ID
            ref1.setValue(client.getUsername());
            ref1.child(client.getUsername()).setValue(request); //send the request to the database
        }

    };

    public void setId(long id) {
       this.id = id;
    }

    private void setClient(Client in){
        client = in;
    }
    private void Update(){

        ArrayAdapter<VehicleDetails> adapter = new ArrayAdapter<VehicleDetails>
                (this, android.R.layout.simple_spinner_item, client.getVehicles());
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                VehicleDetails v = (VehicleDetails) parent.getItemAtPosition(position);
                // Notify the selected item text
                setVehicle(v);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Firebase reference = new Firebase("https://testpro-b5951.firebaseio.com/clients");
        reference.child(Login.getUser()).setValue(client);
    }

    private void setVehicle(VehicleDetails vehicleDetails)
    {
        vehicle = vehicleDetails;
    }

}
