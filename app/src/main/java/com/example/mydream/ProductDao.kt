package com.example.mydream

import androidx.room.Dao
import com.example.mydream.Product
import androidx.room.Delete
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Dao
interface ProductDao {
    @Insert
    suspend fun Insert(product: Product?)

    @Delete
    suspend fun Delete(product: Product?)

    @Query("SELECT * FROM PRODUCT WHERE itemId LIKE :itemId")
    suspend fun getProductDetail(itemId: Int): Product?

    @get:Query("SELECT * FROM PRODUCT")
    val allProduct: LiveData<List<Product?>?>?

    @Query(" SELECT * FROM PRODUCT JOIN PRODCUTFTS ON PRODUCT.itemName = ProdcutFts OR PRODUCT.itemSimilarName=PRODCUTFTS.itemSimilarName WHERE ProdcutFts MATCH :query ")
    fun autoSuggestion(query: String?): List<Product?>?

    //    @Query(" SELECT itemName FROM PRODUCT JOIN PRODCUTFTS ON PRODUCT.itemName = PRODCUTFTS.itemName  WHERE ProdcutFts MATCH '\"str*\"' LIMIT 10 ")
    @get:Query("SELECT MAX(itemId) FROM PRODUCT")
    val id: Int
    //    public List<String> autoSuggestion(String str);
}