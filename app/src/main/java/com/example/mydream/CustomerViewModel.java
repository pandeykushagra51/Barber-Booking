package com.example.mydream;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class CustomerViewModel extends AndroidViewModel {

    private  CustomerRepo customerRepo;
    private Customer customer;
    private LiveData<List<Customer>> allCustomer;
    public CustomerViewModel(@NonNull Application application) {
        super(application);
        customerRepo=new CustomerRepo(application);
        allCustomer=getAllCustomer();
    }


    public void Insert(Customer customer){
        customerRepo.insert(customer);
    }
    public void Delete(String id){
        customerRepo.delete(id);
    }
    public  LiveData<List<Customer>> getAllCustomer(){
        return customerRepo.getAllCustomer();
    }
    public  Customer getCustomerDetail(String id){
        return customerRepo.getCustomerDetail(id);
    }
    public List<String> getAllUserName(){
        return customerRepo.getAllUserName();
    }
    public  String getPassword(String id){
        return customerRepo.getPassword(id);
    }


}
