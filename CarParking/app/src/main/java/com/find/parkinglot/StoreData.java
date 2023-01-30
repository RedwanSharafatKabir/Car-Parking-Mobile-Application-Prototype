package com.find.parkinglot;

public class StoreData {

    String Email;
    String Username;

    public StoreData(){

    }

    public StoreData(String email, String username) {
        Email = email;
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
