package com.pd.chatapp;

public abstract class UserDetails {
    protected String username;
    protected String password;
    protected String chatWith;
    protected String fName;
    protected String lName;
    protected String email;
    protected String pNum;

    public UserDetails() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserDetails(String ifirst, String ilast, String iemail, String ipNum, String iusername, String ipassword) {

        this.fName = ifirst;
        this.lName = ilast;
        this.pNum = ipNum;
        this.username = iusername;
        this.email = iemail;
        this.password = ipassword;

    }
    public String getfName(){
        return fName;
    }
    public String getlName(){
        return lName;
    }
    public String getEmail() {
        return email;
    }
    public String getPNum() {
        return pNum;
    }
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
    public String getChatWith() {
        return chatWith;
    }
    public void setfName(String input){
        fName = input;
    }
    public void setlName(String input){
        lName = input;
    }
    public void setEmail(String input){
        email = input;
    }
    public void setPNum(String input){
        pNum = input;
    }
    public void setPassword(String input){
        password = input;
    }
    public void setUsername(String input){
        username = input;
    }
    public void setChatWith(String input) {
        chatWith = input;
    }
}
