package com.example.mydream

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import java.util.*

class ProductRepo(application: Application?) {
    private val productDao: ProductDao
    private val allProduct: LiveData<List<Product?>?>?
    private var product : Product? = null
    private var suggestions : List<String?>? = null


    init {
        val productDatabase = ProductDatabase.getInstance(application)
        productDao = productDatabase.productDao()
        allProduct = productDao.allProduct
    }

    suspend fun insert(product: Product?) {
        productDao.Insert(product) // Do it asynchronously on background thread
    }

    suspend fun delete(product: Product?) {
        productDao.Delete(product)
    }

    val id: Int
        get() = productDao.id

    suspend fun  getProductDetail(id: Int): Product?  = withContext(Dispatchers.Default) {
        val product = productDao.getProductDetail(id)
        return@withContext product
    }

    suspend fun getAllProduct(): LiveData<List<Product?>?>? = withContext(Dispatchers.Default)  {
        System.out.println(Thread.currentThread().toString() + "thread name\n")
        return@withContext productDao.allProduct
    }

    suspend fun getSuggestion1(str: String): List<String?>  {
        System.out.println(str + " " + Thread.currentThread().toString() + "thread name\n")
        var str : String? = str
        var pr: List<Product>? = null
        val str1 : String? = str
        str = "$str*"
        return if (productDao.autoSuggestion(str)?.size != 0) {
            pr = productDao.autoSuggestion(str) as List<Product>?
            var st: MutableList<String?> = ArrayList()
            val size = pr?.size
            var u = 0
            while (u < size!!) {
                st.add(pr?.get(u)?.itemSimilarName)
                u++
            }
            st = Tools.RemoveDuplicate(st, str1)
            st.add(0, str1)
            st
        } else {
            val st: MutableList<String?> = ArrayList()
            if (str1!!.length > 0) st.add(str1)
            st
        }
    }

}