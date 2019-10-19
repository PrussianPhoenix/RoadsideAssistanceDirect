package com.pd.chatapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManageVehicle extends AppCompatActivity {
    private EditText rego;
    private EditText make;
    private EditText model;
    private EditText colour;
    long id;
    private Client client;
    Spinner vehicles;
    ArrayAdapter<VehicleDetails> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_vehicle);


        //get information from DB
        Firebase ref1 =  new Firebase("https://testpro-b5951.firebaseio.com/clients/");
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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

        rego = (EditText) findViewById(R.id.et_Rego);
        make = (EditText) findViewById(R.id.et_Make);
        model = (EditText) findViewById(R.id.et_Model);
        colour = (EditText) findViewById(R.id.et_Colour);

        vehicles = (Spinner) findViewById(R.id.s_Vehicles);
        Button addNew = (Button) findViewById(R.id.b_AddNew);
        addNew.setOnClickListener(addNewPressed);
        try{
            ArrayAdapter<VehicleDetails> adapter = new ArrayAdapter<VehicleDetails>
                    (this, android.R.layout.simple_spinner_item, client.getVehicles());
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            vehicles.setAdapter(adapter);
        }
        catch(NullPointerException E){

        }




    }
    public View.OnClickListener addNewPressed = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            VehicleDetails vehicle = new VehicleDetails(
                    rego.getText().toString(),
                    colour.getText().toString(),
                    model.getText().toString(),
                    make.getText().toString());
            client.addVehicle(vehicle.getRego_num(), vehicle.getColour(), vehicle.getModel(), vehicle.getMake());
            setClient(client);

            Update();
        }


    };
    public void setClient(Client input)
    {
        client = input;

    }

    //updates the spinner item after a new vehicle is created
    public void Update()
    {
        ArrayAdapter<VehicleDetails> adapter = new ArrayAdapter<VehicleDetails>
                (this, android.R.layout.simple_spinner_item, client.getVehicles());

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        vehicles.setAdapter(adapter);


        vehicles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               VehicleDetails v = (VehicleDetails) parent.getItemAtPosition(position);
               // Notify the selected item text

               rego.setText(v.getRego_num());
               colour.setText(v.getColour());
               make.setText(v.getMake());
               model.setText(v.getModel());
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });


        Firebase reference = new Firebase("https://testpro-b5951.firebaseio.com/clients");
        reference.child(Login.getUser()).setValue(client);



    }
}
