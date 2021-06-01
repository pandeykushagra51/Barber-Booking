package com.example.mydream;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static android.icu.text.MessagePattern.ArgType.SELECT;

@Dao
public interface CustomerDao {
    @Insert
    void Insert(Customer customer);

    @Query("DELETE FROM CUSTOMER WHERE user_name LIKE :id")
    void Delete(String id);

    @Query("SELECT * FROM CUSTOMER")
    LiveData<List<Customer>> getAll();

    @Query("SELECT password FROM CUSTOMER WHERE user_name LIKE :id")
    String getPassword(String id);

    @Query("SELECT user_name FROM CUSTOMER")
    List<String> getAllUserName();

    @Query("SELECT * FROM CUSTOMER WHERE user_name LIKE :id")
    Customer getCustomerDetail(String id);


}
