package com.pd.chatapp;

import java.util.ArrayList;

public class Professional extends UserDetails {
    private double rating;
    private String fee;
    public ArrayList<Review> reviews;

    public Professional(){}
    public Professional(String first, String last, String email, String pNum, String u_id, String pWord, String fee){
        super(first,last,email,pNum,u_id,pWord);
        this.fee = fee;
    }
    public void addRating(double newRating){
        calcRating(newRating);
    }
    private void calcRating(double newVal){
        rating = (rating+newVal)/2;
    }
    public double getRating(){
        return rating;
    }
    public String getFee(){return fee;}
}
