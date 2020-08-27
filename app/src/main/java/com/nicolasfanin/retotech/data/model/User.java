package com.nicolasfanin.retotech.data.model;

import java.io.Serializable;

public class User implements Serializable {

    public String uid;
    public String name;
    public String phoneNumber;
    public boolean isAuthenticated;
    public boolean isNew;
    public boolean isCreated;

    public User() {}

    User(String uid, String name, String phoneNumber) {
        this.uid = uid;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

}
