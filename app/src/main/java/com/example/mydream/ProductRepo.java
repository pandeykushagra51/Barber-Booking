package com.example.mydream;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class ProductRepo {
    private ProductDao productDao;
    private LiveData<List<Product>> allProduct;
    public ProductRepo(Application application) {
        ProductDatabase productDatabase= ProductDatabase.getInstance(application);
        productDao=productDatabase.productDao();
        allProduct=productDao.getAllProduct();
    }

    public void insert(Product product){
        productDao.Insert(product);           // Do it asynchronously on background thread
    }
    public void delete(Product product){
        productDao.Delete(product);           // Do it asynchronously on background thread
    }

    public Product getProductDetail(int id){
        return productDao.getProductDetail(id);
    }

    public LiveData<List<Product>> getAllProduct() {
        return productDao.getAllProduct();
    }
    public List<String> getSuggestion1(String str){

        List<Product> pr=null;
        String str1=str;
        str = str + "*";
        if(productDao.autoSuggestion(str).size()!=0) {
            pr=productDao.autoSuggestion(str);
            List<String> st = new ArrayList<String>();
            int size=pr.size(),u=0;
            while(u<size){
                st.add(pr.get(u).itemSimilarName);
                u++;
            }
            st=Tools.RemoveDuplicate(st,str1);
            st.add(0,str1);
            return st;
        }
        else{
            List<String> st = new ArrayList<String>();
            st.add(str1);
            return st;
        }
    }
}
