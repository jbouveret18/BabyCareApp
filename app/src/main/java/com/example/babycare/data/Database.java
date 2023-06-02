package com.example.babycare.data;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.babycare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Database {
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public static Date convertDateIntoString(String string) throws ParseException {
        return simpleDateFormat.parse(string);
    }
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public DatabaseReference databaseReference = firebaseDatabase.getReference();
    public User writeNewUser(String firstName, String lastName, String email, Date birthday,long key, String password) {
        User user = new User(key, firstName,lastName,email,birthday, password);
        databaseReference.child("user").child(String.valueOf(key)).setValue(user);
        return user;
    }
    public Address writeUserAddress(User user,String region, String country,String street, int postalCode){
        Address address = new Address(user.getKey(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getBirthday(),user.getPassword(),region,country,street,postalCode);
        databaseReference.child("address").child(String.valueOf(user.getKey())).setValue(address);
        return address;
    }
}

