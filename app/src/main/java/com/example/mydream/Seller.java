package com.example.mydream;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "seller")
public class Seller extends User {
    @ColumnInfo
    protected String shop_name;
    @ColumnInfo
    protected String pin_code="000000";
    @ColumnInfo
    protected String adress;
    @ColumnInfo
    protected String productIds;
    @ColumnInfo
    protected String totalSlots;
    @ColumnInfo
    protected String bookedSlots;
    @ColumnInfo
    protected String orders;

    protected Seller(String user_name, String password, String first_name,
                     String last_name, String phone_number, String email_id,
                     byte[] image, String pin_code, String adress, String shop_name ) {
        super(user_name, password, first_name, last_name, phone_number, email_id, image);
        this.shop_name=shop_name;
        this.pin_code=pin_code;
        this.adress=adress;
    }

    public String getShop_name() {
        return shop_name;
    }

    public List<Integer> getProductIds() {
        Gson gson = new Gson();
        Type intType = new TypeToken<List<Integer>>(){}.getType();
        List<Integer> list = gson.fromJson(productIds,intType);
        return list;
    }

    public void setProductIds(int id) {
        Gson gson = new Gson();
        Type intType = new TypeToken<List<Integer>>(){}.getType();
        List<Integer> list = gson.fromJson(productIds,intType);
        if(list==null){
            list=new ArrayList<Integer>();
        }
        list.add(id);
        String json = gson.toJson(list);
        this.productIds = json;
    }

    public String getPin_code() {
        return pin_code;
    }

    public void setPin_code(String pin_code) {
        this.pin_code = pin_code;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }


    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getTotalSlots() {
        return totalSlots;
    }

    public void setTotalSlots(String totalSlots) {
        this.totalSlots = totalSlots;
    }

    public String getBookedSlots() {
        return bookedSlots;
    }

    public void setBookedSlots(String bookedSlots) {
        this.bookedSlots = bookedSlots;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(int productId, String customerId, String time) {
        List<Object> list=null;
        list.add(productId);
        list.add(customerId);
        list.add(time);
        Gson gson = new Gson();
        Type objectType = new TypeToken<List<Object>>(){}.getType();
        List<Object> list1 = gson.fromJson(orders,objectType);
        list1.add(list);
        this.orders = gson.toJson(list1);
    }

}
