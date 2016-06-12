package com.autosite.lab.mongo;

import org.springframework.data.annotation.Id;


public class Guest extends BaseData {



    private String firstName;
    private String lastName;

    public Guest() {}

    public Guest(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format(
                "Guest[id=%s, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String someName) {
        this.firstName = someName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    
}

