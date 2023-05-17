package com.example.babycare.data;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;


public class Database extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    public void writeNewUser(String firstName, String lastName, String email, Date birthday,long key) {
        User user = new User(key, firstName,lastName,email,birthday);
        databaseReference.child("users").child(String.valueOf(key)).setValue(user);
    }

}
