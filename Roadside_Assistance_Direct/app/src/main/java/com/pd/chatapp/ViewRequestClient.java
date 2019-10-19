package com.pd.chatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


import java.util.ArrayList;
import java.util.List;


public class ViewRequestClient extends AppCompatActivity {
    ListView usersList;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;
    Firebase url = new Firebase("https://testpro-b5951.firebaseio.com/requests/");
    Request request;
    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        //Toast.makeText(ViewRequestClient.this, "Not Yet", Toast.LENGTH_LONG).show();

        usersList = (ListView)findViewById(R.id.usersList);
        noUsersText = (TextView)findViewById(R.id.noUsersText);

        pd = new ProgressDialog(ViewRequestClient.this);
        pd.setMessage("Loading...");
        pd.show();

        url = new Firebase("https://testpro-b5951.firebaseio.com/clients/");
        url.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                client = dataSnapshot.child(Login.getUser()).getValue(Client.class);

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        url = new Firebase("https://testpro-b5951.firebaseio.com/requests/");
        url.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Request req;
                for(DataSnapshot child: dataSnapshot.getChildren()) {
                    req = child.getValue(Request.class);

                    if(req.getUsername().equals(Login.getUser()))
                    {

                        setRequest(req);
                        break;
                    }
                }

                next();

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });



        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                update(al.get(position));
            }
        });

        pd.dismiss();
    }
    public void setRequest(Request requestIN){
        request = requestIN;
    }

    public void next(){

        List<Professional> pros = request.getPendingPros();
        for(int i = 0; i < pros.size(); i++)
        {
            al.add(pros.get(i).username + "        $"+pros.get(i).getFee()+"     rating = "+pros.get(i).getRating()+"/5");
            totalUsers++;
        }

        if(totalUsers <1){
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
        }
    }

    public void update(final String in){


        url = new Firebase("https://testpro-b5951.firebaseio.com/professionals/");
        url.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String[] input = in.split("\\s*[-+*/$]\\s*", 3);
                System.out.println();
                Professional pro =dataSnapshot.child(input[0]).getValue(Professional.class);
                request.setPro(pro);
                request.setStatus("In Progress");
                client.setChatWith(input[0]);
                pro.setChatWith(client.getUsername());
                url.child(pro.getUsername()).setValue(pro);

                url = new Firebase("https://testpro-b5951.firebaseio.com/clients/");
                url.child(client.getUsername()).setValue(client);

                url = new Firebase("https://testpro-b5951.firebaseio.com/requests/");
                url.child(request.getUsername()).setValue(request);

                startActivity(new Intent(ViewRequestClient.this, Chat.class));
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }


        });




    }

}
