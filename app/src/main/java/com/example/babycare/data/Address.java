package com.example.babycare.data;

import java.util.Date;

public class Address extends User {
    String region;
    String country;
    String street;
    int postalCode;

    public Address(long key, String firstName, String lastName, String email, Date birthday, String region, String country, String street, int postalCode) {
        super(key, firstName, lastName, email, birthday);
        this.region = region;
        this.country = country;
        this.street = street;
        this.postalCode = postalCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }
}
