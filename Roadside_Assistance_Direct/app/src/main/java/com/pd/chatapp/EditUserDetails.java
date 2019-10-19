package com.pd.chatapp;

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

import java.util.Map;

public class EditUserDetails extends AppCompatActivity {
    private EditText firstName;
    private EditText lastName;
    private EditText phone;
    private EditText subPlan;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edituserdetails);

        firstName = (EditText) findViewById(R.id.et_FirstName);
        lastName = (EditText) findViewById(R.id.et_LastName);
        phone = (EditText) findViewById(R.id.et_Phone);
        subPlan = (EditText) findViewById(R.id.et_Splan);

        //get information from DB
        Firebase ref1 = new Firebase("https://testpro-b5951.firebaseio.com/clients/");
        ref1.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Map map = dataSnapshot.child(Login.getUser()).getValue(Map.class);
                Client client = new Client(
                        (String) map.get("fName"),
                        (String) map.get("lName"),
                        (String) map.get("email"),
                        (String) map.get("pnum"),
                        (String) map.get("username"),
                        (String) map.get("password"));
                client.setPayDetails((String) dataSnapshot.child(Login.getUser()).child("payDetails").getValue());
                setClient(client);
                update();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError)
            {
            }
        });

        Button editNew = (Button) findViewById(R.id.b_AddNew);
        editNew.setOnClickListener(editPressed);
    }
    public View.OnClickListener editPressed = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Firebase reference = new Firebase("https://testpro-b5951.firebaseio.com/clients");
            reference.child(Login.getUser()).child("fName").setValue( firstName.getText().toString() );
            reference.child(Login.getUser()).child("lName").setValue(  lastName.getText().toString() );
            reference.child(Login.getUser()).child("pnum").setValue(  phone.getText().toString() );
            reference.child(Login.getUser()).child("payDetails").setValue(  subPlan.getText().toString() );
        }


    };
    public void setClient(Client input)
    {
        client = input;

    }
    // Update All the textfields
    public void update(){
        firstName.setText(client.getfName());
        lastName.setText(client.getlName());
        phone.setText(client.getPNum());
        subPlan.setText(client.getPayDetails());
    }
}
