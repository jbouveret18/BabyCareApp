package com.example.babycare.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Database {
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public static Date convertDateIntoString(String string) throws ParseException {
        return simpleDateFormat.parse(string);
    }
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public DatabaseReference databaseReference = firebaseDatabase.getReference();
    public User writeNewUser(boolean doctor, long key, String firstName, String lastName, String email, String password) {
        User user = new User(doctor,key,firstName,lastName,email,password);
        databaseReference.child("user").child(String.valueOf(key)).setValue(user);
        return user;
    }

    public Address writeUserAddress(boolean doctor, long key, String firstName, String lastName, String email, String password, String region, String country, String street, int postalCode) {
        User user = writeNewUser(doctor,key,firstName,lastName,email,password);
        Address address = new Address(user.isDoctor(), user.getKey(), user.getFirstName(), user.getLastName(), user.getEmail(),user.getPassword(), region,country,street,postalCode);
        databaseReference.child("address").child(String.valueOf(user.getKey())).setValue(address);
        return address;
    }
}

