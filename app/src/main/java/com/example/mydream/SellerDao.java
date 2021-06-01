package com.example.mydream;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface SellerDao {
    @Insert
    public void Insert(Seller seller);
    @Query("DELETE FROM SELLER WHERE user_name LIKE :id")
    void Delete(String id);

    @Query("SELECT * FROM SELLER")
    LiveData<List<Seller>> getAll();

    @Query("SELECT password FROM SELLER WHERE user_name LIKE :id")
    String getPassword(String id);

    @Query("SELECT user_name FROM SELLER")
    List<String> getAllUserName();

    @Query("SELECT * FROM SELLER WHERE user_name LIKE :id")
    LiveData<Seller> getSellerDetailLive(String id);



    @Query("SELECT * FROM SELLER WHERE user_name LIKE :id")
    Seller getSellerDetail(String id);

    @Query("SELECT productIds FROM SELLER WHERE user_name LIKE :id")
    public String getProductIds(String id);

    @Query("UPDATE SELLER SET productIds = :val WHERE user_name LIKE :id")
    public void setProductId(String id,String val);
}
