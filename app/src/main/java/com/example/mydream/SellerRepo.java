package com.example.mydream;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SellerRepo {
    private SellerDao sellerDao;
    private LiveData<List<Seller>> allSeller;
    private LiveData<Seller> seller;
    public SellerRepo(Application application) {
        SellerDatabase sellerDatabase= SellerDatabase.getInstance(application);
        sellerDao=sellerDatabase.SellerDao();
        allSeller=sellerDao.getAll();
    }
    public void insert(Seller seller){
        sellerDao.Insert(seller);           // Do it asynchronously on background thread
    }
    public void delete(String id){
        sellerDao.Delete(id);           // Do it asynchronously on background thread
    }
    public String  getPassword(String id){
        return sellerDao.getPassword(id);
    }

    public LiveData<Seller> getSellerDetailLive(String id){
        return seller=sellerDao.getSellerDetailLive(id);
    }

    public Seller getSellerDetail(String id){
        return sellerDao.getSellerDetail(id);
    }

    public List<String> getAllUserName(){
        return sellerDao.getAllUserName();
    }

    public LiveData<List<Seller>> getAllSeller() {
        return sellerDao.getAll();
    }
}
