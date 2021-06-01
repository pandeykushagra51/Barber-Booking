package com.example.mydream;


import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "CUSTOMER")
public class Customer extends User{

    protected String myOrders;


    public Customer(String user_name, String password, String first_name, String last_name, String phone_number, String email_id, byte[] image) {
        super(user_name, password, first_name, last_name, phone_number, email_id,image);
    }

    public String getMyOrders() {
        return myOrders;
    }

    public void setMyOrders(String myOrders) {
        this.myOrders = myOrders;
    }

}
