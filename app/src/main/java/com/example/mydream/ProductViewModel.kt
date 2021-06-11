package com.example.mydream
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val productRepo: ProductRepo
    private var allProduct: LiveData<List<Product?>?>? = null
    private var product : Product? = null
    private var suggestions : List<String?>? = null
    var cnt=0

    init {
        productRepo = ProductRepo(application)
        viewModelScope.launch {
            allProduct = productRepo.getAllProduct()
        }
    }



    fun Insert(product: Product) {
        viewModelScope.launch {
            productRepo.insert(product)
        }
    }

    fun Delete(product: Product) {
        viewModelScope.launch {
            productRepo.delete(product)
        }
    }

    suspend fun getAllProduct(): LiveData<List<Product?>?>? = withContext(IO) {
        viewModelScope.launch {
            allProduct =  productRepo.getAllProduct()
        }
        return@withContext allProduct
    }

    suspend fun getProductDetail(id: Int): Product? = withContext(IO) {
            product = productRepo.getProductDetail(id);
        while (product==null){

        }
        return@withContext product
    }



    fun getAutoSuggestion(str: String): List<String?>? {
        viewModelScope.launch {
            suggestions = productRepo.getSuggestion1(str)
        }
        return suggestions
    }

    val id: Int
        get() = productRepo.id

}