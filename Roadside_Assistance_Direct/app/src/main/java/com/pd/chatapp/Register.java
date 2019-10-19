package com.pd.chatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {
    EditText username, password, fname, lname, phone, email, fee;
    Button registerButton;
    String user, pass, FName, LName, Email, proFee;
    TextView login;
    String Phone;
    Switch type;
    boolean professional = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fee = (EditText)findViewById(R.id.et_Fee);
        username = (EditText)findViewById(R.id.username);
        fname = (EditText)findViewById(R.id.et_FName);
        lname = (EditText)findViewById(R.id.et_LName);
        email = (EditText)findViewById(R.id.et_email);
        phone = (EditText)findViewById(R.id.et_Phone);
        password = (EditText)findViewById(R.id.password);
        registerButton = (Button)findViewById(R.id.registerButton);
        login = (TextView)findViewById(R.id.login);
        type= (Switch) findViewById(R.id.sw_type);


        Firebase.setAndroidContext(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });


        type.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(isChecked){
                    fee.setVisibility(View.VISIBLE);
                    professional = true;
                }
                else{fee.setVisibility(View.INVISIBLE); professional = false;}
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proFee = fee.getText().toString();
                user = username.getText().toString();
                FName = fname.getText().toString();
                LName = lname.getText().toString();
                Phone = phone.getText().toString();
                Email = email.getText().toString();
                pass = password.getText().toString();

                if(user.equals("")){
                    username.setError("can't be blank");
                }
                else if(FName.equals("")){
                    fname.setError("can't be blank");
                }
                else if(LName.equals("")){
                    lname.setError("can't be blank");
                }
                else if(Phone.equals("")){
                    phone.setError("can't be blank");
                }
                else if(Email.equals("")){
                    email.setError("can't be blank");
                }
                else if(professional && proFee.equals("")){
                    fee.setError("can't be blank");
                }
                else if(pass.equals("")){
                    password.setError("can't be blank");
                }
                else if(!user.matches("[A-Za-z0-9]+")){
                    username.setError("only alphabet or number allowed");
                }
                else if(user.length()<5){
                    username.setError("at least 5 characters long");
                }
                else if(pass.length()<5){
                    password.setError("at least 5 characters long");
                }
                else {

                    professional = type.isChecked();
                    System.out.println(professional);
                    String url = "https://testpro-b5951.firebaseio.com/clients.json";
                    if(professional) {  url = "https://testpro-b5951.firebaseio.com/professionals.json"; }
                    final ProgressDialog pd = new ProgressDialog(Register.this);
                    pd.setMessage("Loading...");
                    pd.show();

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String s) {
                            Firebase reference;
                            if(professional) { reference = new Firebase("https://testpro-b5951.firebaseio.com/professionals"); }
                            else{ reference = new Firebase("https://testpro-b5951.firebaseio.com/clients"); }

                            if(s.equals("null")) {
                                if(professional)
                                {
                                    Professional pro = new Professional(FName, LName, Email, Phone, user, pass, proFee);
                                    reference.child(user).setValue(pro);
                                }
                                else
                                {
                                    Client client = new Client(FName, LName, Email, Phone, user, pass);
                                    reference.child(user).setValue(client);
                                }
                                Toast.makeText(Register.this, "registration successful", Toast.LENGTH_LONG).show();
                            }
                            else {
                                try {
                                    JSONObject obj = new JSONObject(s);

                                    if (!obj.has(user)) {
                                        if(professional)
                                        {
                                            Professional pro = new Professional(FName, LName, Email, Phone, user, pass, proFee);
                                            reference.child(user).setValue(pro);
                                        }
                                        else
                                        {
                                            Client client = new Client(FName, LName, Email, Phone, user, pass);
                                            reference.child(user).setValue(client);
                                        }

                                        Toast.makeText(Register.this, "registration successful", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(Register.this, "username already exists", Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            pd.dismiss();
                        }

                    },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError );
                            pd.dismiss();
                        }
                    });

                    RequestQueue rQueue = Volley.newRequestQueue(Register.this);
                    rQueue.add(request);
                }
            }
        });
    }
}