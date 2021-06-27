package com.example.mydream

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import com.google.android.gms.common.util.CollectionUtils.listOf
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.reflect.Array.get
import java.util.*

class ProductRepo(application: Application?) {
//    private val productDao: ProductDao
    //private val allProduct: LiveData<List<Product?>?>?
    private var product : Product? = null
    private var suggestions : List<String?>? = null
    var reference : CollectionReference? = null


    init {
        val productDatabase = ProductDatabase.getInstance(application)
        //productDao = productDatabase.productDao()
     //   allProduct = productDao.allProduct
        reference = FirebaseFirestore.getInstance().collection("Product")
    }

    suspend fun insert(product: Product?) : String?{
        var id : String? = null
        id = reference!!.document().id
        var result : String? = "undone"

        Log.e(TAG, "insert: heloo")
        product!!.setItemId(id)
        reference!!.document(id).set(product!!)
                .addOnSuccessListener(OnSuccessListener {

                    result = id;
                    Log.e(TAG, "insert: $id")
                })
                .addOnFailureListener(OnFailureListener {
                    result = null
                    Log.e(TAG, "insert: failed1   $it.message  ")
                })
        while(result=="undone"){

        }
        return result
    }

    suspend fun delete(product: Product?) {
       // productDao.Delete(product)
    }

//    val id: Int
//        get() = productDao.id

    suspend fun  getProductDetail(itemId: String): Product? {
        var check: Boolean? = null
        var product:Product? = null
        reference!!.document(itemId).get().addOnSuccessListener {
            product = toProduct(it)
            check = true
        }
        while (check==null){
            delay(100)
        }
        return product
    }

//    suspend fun getAllProduct(): LiveData<List<Product?>?>? = withContext(Dispatchers.Default)  {
//        System.out.println(Thread.currentThread().toString() + "thread name\n")
//        return@withContext productDao.allProduct
//    }

//    suspend fun getSuggestion1(str: String): List<String?>  {
//        System.out.println(str + " " + Thread.currentThread().toString() + "thread name\n")
//        var str : String? = str
//        var pr: List<Product>? = null
//        val str1 : String? = str
//        str = "$str*"
//        return if (productDao.autoSuggestion(str)?.size != 0) {
//            pr = productDao.autoSuggestion(str) as List<Product>?
//            var st: MutableList<String?> = ArrayList()
//            val size = pr?.size
//            var u = 0
//            while (u < size!!) {
//                st.add(pr?.get(u)?.itemSimilarName)
//                u++
//            }
//            st = Tools.RemoveDuplicate(st, str1)
//            st.add(0, str1)
//            st
//        } else {
//            val st: MutableList<String?> = ArrayList()
//            if (str1!!.length > 0) st.add(str1)
//            st
//        }
//    }

    suspend fun getNextProduct(last: String?, offSet: Int): ArrayList<Product>? {
        var check : Boolean? = null
        Log.e(TAG, "getNextProduct: Onvoked  $last PP")
        var list = ArrayList<Product>()
        if(last==null){
             reference!!.orderBy("itemId").limit(offSet.toLong()).get().addOnSuccessListener {
                 val documents = it.documents
                 for (document in documents) {
                     val product = toProduct(document)
                     list.add(product)
                 }
                 check = true
             }

        }
        else{
            reference!!.orderBy("itemId").startAfter(last).limit(offSet.toLong()).get().addOnSuccessListener {
                val documents = it.documents
                for (document in documents) {
                    val product = toProduct(document)
                    list.add(product)
                }
                check = true

            }
        }
        while (check==null){
            delay(10)
        }
        return list
    }

    fun toProduct(document: DocumentSnapshot): Product {
        val product = Product(document.getString("itemName"), document.getLong("rate")!!.toInt(), null, document.getString("sellerId"), null)
        product.setItemId(document.getString("itemId"))
        try {
            val list = document.getString("itemImages")
            product.setItemImages(list)
        }
        catch (e: Exception){
            Log.e(TAG, "toProduct: ${e.toString()}", )
        }
        return product
    }


}