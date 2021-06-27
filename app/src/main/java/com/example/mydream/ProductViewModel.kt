package com.example.mydream
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.util.*

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val productRepo: ProductRepo
 //   private var allProduct: LiveData<List<Product?>?>? = null
    private var product : Product? = null
    private var suggestions : List<String?>? = null
    var cnt=0

    init {
        productRepo = ProductRepo(application)
        viewModelScope.launch {
           // allProduct = productRepo.getAllProduct()
        }
    }



    suspend fun Insert(product: Product) : String? {
            return productRepo.insert(product)
    }

    fun Delete(product: Product) {
        viewModelScope.launch {
            productRepo.delete(product)
        }
    }

    suspend fun getAllProduct(): LiveData<List<Product?>?>? = withContext(IO) {
//        viewModelScope.launch {
//            allProduct =  productRepo.getAllProduct()
//        }
        return@withContext null
    }


    suspend fun getProductDetail(id: String): Product? = withContext(IO) {
        val product =async { productRepo.getProductDetail(id)}
        return@withContext product.await()
    }



    fun getAutoSuggestion(str: String): List<String?>? {
//        viewModelScope.launch {
//            suggestions = productRepo.getSuggestion1(str)
//        }
        return null
    }

    suspend fun getNextProduct(last : String?, offSet: Int): ArrayList<Product>? = withContext(IO) {
        var list = async{productRepo.getNextProduct(last,offSet)}
        return@withContext list.await()
    }

    val id: Int
        get() = 2

}