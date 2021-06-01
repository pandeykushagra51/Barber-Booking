package com.example.mydream;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

@Entity(tableName = "PRODUCT")
public class Product {
    @PrimaryKey(autoGenerate = true) @NonNull
    @ColumnInfo(name = "itemId")
    protected int itemId;

    @ColumnInfo(name = "itemName")
    public String itemName;

    @ColumnInfo(name = "rate")
    protected int rate;

    @ColumnInfo(name = "itemImages")
    protected String itemImages;

    @ColumnInfo(name = "sellerId")
    protected String sellerId;

    @ColumnInfo(name = "itemSimilarName")
    public String itemSimilarName;


    public Product(String itemName, int rate, String itemImages, String sellerId, String itemSimilarName) {
        this.itemName = itemName;
        this.rate = rate;
        this.itemImages = itemImages;
        this.sellerId = sellerId;
        this.itemSimilarName = itemSimilarName;
    }

    public void setItemId(int Itemid){
        this.itemId=itemId;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getRate() {
        return rate;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getItemSimilarName() {
        return itemSimilarName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public void setItemSimilarName(String itemSimilarName) {
        this.itemSimilarName = itemSimilarName;
    }

    public List<Bitmap> getItemImages() {
        Gson gson = new Gson();
        Type bitMapType = new TypeToken<List<Bitmap>>(){}.getType();
        List<Bitmap> list = gson.fromJson(itemImages,bitMapType);
        return list;
    }

    public void setItemImages(String itemImages) {
        this.itemImages = itemImages;
    }
}
