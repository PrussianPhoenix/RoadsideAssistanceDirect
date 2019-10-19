package com.pd.chatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SysLogin extends AppCompatActivity {
    TextView returnText;
    EditText ausername, apassword;
    Button adminLoginButton;
    static String auser, apass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ausername = findViewById(R.id.tv_adminUname);
        apassword = findViewById(R.id.tv_adminPass);
        adminLoginButton = findViewById(R.id.a_loginButton);
        returnText = findViewById(R.id.tv_return);

        returnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SysLogin.this, Login.class));
            }
        });

        adminLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auser = ausername.getText().toString();
                apass = apassword.getText().toString();


                if(auser.equals("")){
                    ausername.setError("can't be blank");
                }
                else if(apass.equals("")){
                    apassword.setError("can't be blank");
                }
                else{
                    String url = "https://testpro-b5951.firebaseio.com/admins.json";
                    final ProgressDialog pd = new ProgressDialog(SysLogin.this);
                    pd.setMessage("Loading...");
                    pd.show();

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            if (s.equals("null")) {
                                Toast.makeText(SysLogin.this, "user not found", Toast.LENGTH_LONG).show();
                            } else {
                                try {
                                    JSONObject obj = new JSONObject(s);

                                    if (!obj.has(auser)) {
                                        Toast.makeText(SysLogin.this, "user not found", Toast.LENGTH_LONG).show();
                                    } else if (obj.getJSONObject(auser).getString("password").equals(apass)) {
                                        //UserDetails.username = user;
                                        //UserDetails.password = pass;
                                        startActivity(new Intent(SysLogin.this, SysHome.class));
                                    } else {
                                        Toast.makeText(SysLogin.this, "incorrect password", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            pd.dismiss();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError);
                            pd.dismiss();
                        }
                    });


                    RequestQueue rQueue = Volley.newRequestQueue(SysLogin.this);
                    rQueue.add(request);

                }

            }
        });
    }
    public static String getUser(){
        return auser;
    }
}
