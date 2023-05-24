package com.example.babycare.data;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@IgnoreExtraProperties
public class User {
    private static final AtomicLong counter = new AtomicLong(0);

    public final long key;
    public String firstName;
    public String lastName;
    public String email;
    public Date birthday;

    public String password;

    public long getKey() {
        return key;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public User(long key, String firstName, String lastName, String email, Date birthday, String password) {
        this.key = key;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static long generatePrimaryKey() {
        return counter.incrementAndGet();
    }
}
