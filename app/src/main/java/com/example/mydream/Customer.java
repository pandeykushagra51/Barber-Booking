package com.example.mydream;


import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

@Entity (tableName = "CUSTOMER")
public class Customer extends User{

    protected String myOrders;


    public Customer(String user_name, String password, String first_name, String last_name, String phone_number, String email_id, byte[] image) {
        super(user_name, password, first_name, last_name, phone_number, email_id,image);
    }

    public List<List<Object>> getMyOrders() {

        Gson gson = new Gson();
        Type objectType = new TypeToken<List<Object>>(){}.getType();
        List<List<Object>> list1 = gson.fromJson(myOrders,objectType);
        return list1;
    }

    public void setMyOrders(int productId, String sellerId, String time) {
        List<Object> list=null;
        list.add(productId);
        list.add(sellerId);
        list.add(time);
        Gson gson = new Gson();
        Type objectType = new TypeToken<List<Object>>(){}.getType();
        List<List<Object>> list1 = gson.fromJson(myOrders,objectType);
        list1.add(list);
        this.myOrders = gson.toJson(list1);
    }

}
