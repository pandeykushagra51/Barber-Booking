package com.example.mydream.seller

import android.app.Application
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.mydream.Seller
import com.example.mydream.SellerDao
import com.example.mydream.SellerDatabase
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import java.io.ByteArrayOutputStream


class SellerRepo(application: Application?) {
    private val sellerDao: SellerDao
    internal var allSeller: LiveData<List<Seller>>
    private var seller: LiveData<Seller>? = null
    var db : FirebaseFirestore? = null
    var firebaseAuth : FirebaseAuth? = null
    var reference : CollectionReference? = null
    var seller1 : Seller? = null


    init {
        val sellerDatabase = SellerDatabase.getInstance(application)
        sellerDao = sellerDatabase.SellerDao()
        allSeller = sellerDao.all
        db= FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        reference = FirebaseFirestore.getInstance().collection("Seller")
    }

    //    public void insert(Seller seller){
    //        sellerDao.Insert(seller);           // Do it asynchronously on background thread
    //    }
    suspend fun insert(seller: Seller?) : Boolean {
        var status : Boolean = false
        reference!!.document(seller!!.getUser_name()).set(seller)
                .addOnSuccessListener(OnSuccessListener {
                    Log.e(TAG, "insert: Success")
                    status = true;
                })
                .addOnFailureListener(OnFailureListener {
                    Log.e(TAG, "insert: Failure")
                    status = false
                })
        return status
    }

    fun delete(id: String?) {
        sellerDao.Delete(id) // Do it asynchronously on background thread
    }

    suspend fun getPassword(id: String?): String {
        var password : String? = null
        CoroutineScope(IO).async {
            reference!!.document(id!!).get().addOnSuccessListener(OnSuccessListener {
                password = it.getString("password")
                Log.e(TAG, "getPassword: " + password, )
            })
        }.await()
        while (password==null){

        }

        Log.e(TAG, "getPassword: " + password, )
        return password!!
    }

    fun getSellerDetailLive(id: String?): LiveData<Seller> {
        return sellerDao.getSellerDetailLive(id)
    }

    suspend fun getSellerDetail(id: String?): Seller {
        var seller : Seller? = null
        reference!!.document(id!!).get()
                .addOnFailureListener(OnFailureListener {
                    Log.e(TAG, "getSellerDetail: Operation failed")
                })
                .addOnSuccessListener(OnSuccessListener {
                    if (it.exists()) {
                        seller = Seller(it.getString("user_name"),it.getString("password"),it.getString("first_name"),it.getString("last_name"),
                                it.getString("phone_number"),it.getString("email_id"),it.getString("image"),it.getString("pin_code"),it.getString("adress"),
                                it.getString("shop_name"),it.getString("productIds")
                        )
                    } else {
                        Log.e(TAG, "getSellerDetail: seller not exist", )
                        seller = null
                    }
                })
        while(seller==null){
            System.out.println("shbdbcksd svdbjkj")
        }
        Log.e(TAG, "getSellerDetail: ${seller!!.adress}", )
        return seller!!
    }

    fun BitMapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    val allUserName: List<String>
        get() = sellerDao.allUserName

    fun getAllSeller(): LiveData<List<Seller>> {
        return sellerDao.all
    }

    suspend fun Update(seller: Seller, id : String) : Boolean {
        val result = "undone"
        return reference!!.document(id).set(seller).isComplete
    }


}
