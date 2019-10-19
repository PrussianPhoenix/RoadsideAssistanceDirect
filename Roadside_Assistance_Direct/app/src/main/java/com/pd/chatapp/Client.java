package com.pd.chatapp;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Client extends UserDetails {
    private /*PaymentPlan*/ String payDetails = "Normal";//PaymentPlan.NORMAL;
    private List<VehicleDetails> vehicles = new ArrayList<VehicleDetails>();

   /* enum PaymentPlan
    {
        NORMAL,SUBSCRIPTION;
        @Override
        public String toString() {
            switch(this) {
                case NORMAL: return "Normal";
                case SUBSCRIPTION: return "Subscription";
                default: throw new IllegalArgumentException();
            }
        }
    }*/

    public Client() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)

    }

    public Client(String fName, String lName, String email, String pNum, String u_id, String pWord){
        super(fName,lName,email,pNum,u_id,pWord);
        vehicles.add(null);
    }


    public void setPayDetails (String plan){
        payDetails = plan;//PaymentPlan.valueOf(plan);
    }
    public String getPayDetails(){
        return payDetails.toString();
    }

    public void addVehicle(String rego, String colour, String model, String make){
        vehicles.add(new VehicleDetails(rego,colour,model,make));
    }
    public List<VehicleDetails> getVehicles(){return vehicles;}
    public void setVehicles(List<VehicleDetails> vList){vehicles = vList;}
}
