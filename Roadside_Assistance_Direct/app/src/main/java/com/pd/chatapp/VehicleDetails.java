package com.pd.chatapp;

import java.security.PublicKey;

public class VehicleDetails {
    private String rego_num = "";
    private String colour = "";
    private String model = "";
    private String make = "";

    public VehicleDetails(){}

    public VehicleDetails(String rego_num, String colour, String model, String make)
    {
        this.rego_num= rego_num;
        this.colour = colour;
        this.model = model;
        this.make = make;
    }

    @Override
    public String toString(){

        return rego_num;
    }

    //get methods for returning data
    public String getRego_num(){
        return rego_num;
    }
    public String getColour(){
        return colour;
    }
    public String getModel(){
        return model;
    }
    public String getMake(){
        return make;
    }

    public void setRego_num(String in){rego_num = in;}
    public void setColour(String in){colour = in;}
    public void setModel(String in){model=in;}
    public void setMake(String in){make = in;}
}
