package com.example.mydream;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private  ProductRepo productRepo;
    private LiveData<List<Product>> allProduct;
    public ProductViewModel(@NonNull Application application) {
        super(application);
        productRepo=new ProductRepo(application);
        allProduct=productRepo.getAllProduct();
    }


    public void Insert(Product product){
        productRepo.insert(product);
    }

    public void Delete(Product product){
        productRepo.delete(product);
    }
    public  LiveData<List<Product>> getAllProduct(){
        return productRepo.getAllProduct();
    }
    public  Product getProductDetail(int id){
        return productRepo.getProductDetail(id);
    }
    List<String> getAutoSuggestion(String str){
        return productRepo.getSuggestion1(str);
    }


}
