package com.example.babycare.data;

public class Address extends User {
    String region;
    String country;
    String street;
    int postalCode;


    public Address(boolean doctor, long key, String firstName, String lastName, String email, String userPassword, String region, String country, String password, int postalCode) {
        super(doctor,key, firstName, lastName, email, password);
    }
}
