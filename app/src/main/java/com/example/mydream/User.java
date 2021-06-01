package com.example.mydream;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey @NonNull
    @ColumnInfo(name = "user_name")
    protected String user_name;

    @ColumnInfo(name = "password")
    protected String password;

    @ColumnInfo(name = "first_name")
    protected String first_name;

    @ColumnInfo(name = "last_name")
    protected String last_name;

    @ColumnInfo(name = "phone_number")
    protected String phone_number;

    @ColumnInfo(name = "email_id")
    protected String email_id;

    @ColumnInfo(name = "image")
    protected byte[] image;

    protected User(String user_name, String password, String first_name, String last_name, String phone_number, String email_id, byte[] image) {
        this.user_name = user_name;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.email_id = email_id;
        this.image = image;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getPassword() {
        return password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getEmail_id() {
        return email_id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setUser_name(@NonNull String user_name) {
        this.user_name = user_name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
