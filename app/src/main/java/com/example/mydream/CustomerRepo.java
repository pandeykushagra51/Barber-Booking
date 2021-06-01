package com.example.mydream;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CustomerRepo {
    private CustomerDao customerDao;
    private LiveData<List<Customer>> allCustomer;
    private Customer customer;
    public CustomerRepo(Application application) {
        CustomerDatabase customerDatabase= CustomerDatabase.getInstance(application);
        customerDao=customerDatabase.CustomerDao();
        allCustomer=customerDao.getAll();
    }
    public void insert(Customer customer){
        customerDao.Insert(customer);           // Do it asynchronously on background thread
    }
    public void delete(String id){
        customerDao.Delete(id);           // Do it asynchronously on background thread
    }
    public String  getPassword(String id){
        return customerDao.getPassword(id);
    }

    public Customer getCustomerDetail(String id){
        return customer=customerDao.getCustomerDetail(id);
    }

    public List<String> getAllUserName(){
        return customerDao.getAllUserName();
    }

    public LiveData<List<Customer>> getAllCustomer() {
        return customerDao.getAll();
    }
}
