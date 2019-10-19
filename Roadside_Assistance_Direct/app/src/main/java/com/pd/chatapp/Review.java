package com.pd.chatapp;

public class Review {
    private String message;
  // Extra functionality to Assess helpfulness of users review
  //  private int numSupp=0;
    private Client reviewer;

    public Review(String message, Client reviewer){this.message = message; this.reviewer = reviewer;}
    public String getReviewer(){return reviewer.getfName()+""+reviewer.getlName();}
    public String getMessage(){return message;}
  //  public void likeRev(){numSupp++;}
  //  public void dislikeRev(){numSupp--;}
}
