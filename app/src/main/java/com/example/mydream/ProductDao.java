package com.example.mydream;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static android.webkit.WebSettings.PluginState.ON;

@Dao
public interface ProductDao {
    @Insert
    public void Insert(Product product);

    @Delete
    public void Delete(Product product);

    @Query("SELECT * FROM PRODUCT WHERE itemId LIKE :itemId")
    public Product getProductDetail(int itemId);

    @Query("SELECT * FROM PRODUCT")
    public LiveData<List<Product>> getAllProduct();

    @Query(" SELECT * FROM PRODUCT JOIN PRODCUTFTS ON PRODUCT.itemName = ProdcutFts OR PRODUCT.itemSimilarName=PRODCUTFTS.itemSimilarName WHERE ProdcutFts MATCH :query ")
    public List<Product> autoSuggestion(String query);

//    @Query(" SELECT itemName FROM PRODUCT JOIN PRODCUTFTS ON PRODUCT.itemName = PRODCUTFTS.itemName  WHERE ProdcutFts MATCH '\"str*\"' LIMIT 10 ")
//    public List<String> autoSuggestion(String str);



}
