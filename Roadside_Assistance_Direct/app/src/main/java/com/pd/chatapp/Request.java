package com.pd.chatapp;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Request {
    private Client client;
    private String client_name;
    private String client_rating;
    private String username;
    private String status;
    private String problemDescription;
    private String location;
    private String regoNum;
    private String colour;
    private String make;
    private String model;
    private VehicleDetails vehicle;
    private Professional pro;
    private List<Professional> pendingPros = new ArrayList<>();


    public Request(){}

    public Request(VehicleDetails v, String p, String l, Client client)
    {
        pro = null;
        status = "PENDING";
        client_name = client.getfName()+" "+client.getlName();
        //TODO: rating here



        vehicle = v;
        problemDescription = p;
        location = l;
        regoNum = v.getRego_num();
        colour = v.getColour();
        make = v.getMake();
        model = v.getModel();
        username = client.getUsername();
    }


    // methods for returning private data
    public String getStatus(){
        return status.toString();
    }
    public String getLocation(){
        return location;
    }
    public String getProblemDescription(){
        return problemDescription;
    }
    public VehicleDetails getVehicle(){
        return vehicle;
    }
    public String getClient_name() {
        return client_name;
    }
    public Professional getPro() {
        return pro;
    }
    public String getClient_rating() {
        return client_rating;
    }
    public List<Professional> getPendingPros(){return pendingPros;}


    public void setClient_name(String in){client_name = in;}
    public void setUsername(String in){username = in;}
    public void setProblemDescription(String in){problemDescription = in;}
    public void setClient_rating(String in){client_rating = in;}
    public void setStatus(String in){status = in;}
    public void setLocation(String in){location = in;}
    public void setVehicle(VehicleDetails in){vehicle = in;}
    public void setPro(Professional input){ pro = input;}

    public String getUsername(){
        return username;
    }


    @Override
    public String toString(){

        return client_name;
    }

    private void setClient(Client in){
        this.client = in;

    }

    public void addPendingPro(Professional input){
        pendingPros.add(input);
    }


    private void Update(){
        Firebase reference = new Firebase("https://testpro-b5951.firebaseio.com/clients");
        reference.child(Login.getUser()).setValue(client);
    }
}
