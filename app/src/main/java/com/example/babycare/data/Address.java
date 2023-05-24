package com.example.babycare.data;

import java.util.Date;

public class Address extends User {
    String region;
    String country;
    String street;
    int postalCode;


    public Address(long key, String firstName, String lastName, String email, Date birthday, String userPassword, String region, String country, String password, int postalCode) {
        super(key, firstName, lastName, email, birthday, password);
    }
}
