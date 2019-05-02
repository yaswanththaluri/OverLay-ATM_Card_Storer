package com.audigosolutions.android.outlay;

import io.realm.RealmObject;


public class User extends RealmObject {

    private String name;
    private String pin;

    public User()
    {}


    public User(String name, String pin)
    {
        this.name = name;
        this.pin = pin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getName() {
        return name;
    }

    public String getPin() {
        return pin;
    }
}
