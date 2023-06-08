package com.example.babycare.data;

public class Address extends User {
    String region;
    String country;
    String street;
    int postalCode;

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    public String getStreet() {
        return street;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public Address(boolean doctor, long key, String firstName, String lastName, String email, String password, String region, String country, String street, int postalCode) {
        super(doctor,key, firstName, lastName, email, password);
    }
}
