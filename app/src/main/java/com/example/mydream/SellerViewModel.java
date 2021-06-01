package com.example.mydream;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SellerViewModel extends AndroidViewModel {

    private  SellerRepo sellerRepo;
    private LiveData<Seller> seller;
    private LiveData<List<Seller>> allSeller;
    public SellerViewModel(@NonNull Application application) {
        super(application);
        sellerRepo=new SellerRepo(application);
        allSeller=getAllSeller();
    }

    public void Insert(Seller seller){
        sellerRepo.insert(seller);
    }
    public void Delete(String id){
        sellerRepo.delete(id);
    }
    public  LiveData<List<Seller>> getAllSeller(){
        return sellerRepo.getAllSeller();
    }
    public  LiveData<Seller> getSellerDetailLive(String id){
        return sellerRepo.getSellerDetailLive(id);
    }
    public Seller getSellerDetail(String id){
        return sellerRepo.getSellerDetail(id);
    }
    public List<String> getAllUserName(){
        return sellerRepo.getAllUserName();
    }
    public  String getPassword(String id){
        return sellerRepo.getPassword(id);
    }
}
