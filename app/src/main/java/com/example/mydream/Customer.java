package com.example.mydream;


import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Customer extends User{

    protected List<String> myOrders;


    public Customer(String user_name, String password, String first_name, String last_name, String phone_number, String email_id, String image) {
        super(user_name, password, first_name, last_name, phone_number, email_id,image);
    }

    public List<String> getMyOrders() {
        return myOrders;
    }

    public void setMyOrders(List<String> myOrders) {
        this.myOrders = myOrders;
    }
}
