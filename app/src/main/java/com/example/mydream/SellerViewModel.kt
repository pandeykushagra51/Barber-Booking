package com.example.mydream

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mydream.seller.SellerRepo

class SellerViewModel(application: Application) : AndroidViewModel(application) {
    private val sellerRepo: SellerRepo
    private val seller: LiveData<Seller>? = null
    var seller1 : Seller? = null
    private val allSeller: LiveData<List<Seller>>
    suspend fun Insert(seller: Seller?) {
        sellerRepo.insert(seller)
    }

    fun Delete(id: String?) {
        sellerRepo.delete(id)
    }

    fun getAllSeller(): LiveData<List<Seller>> {
        return sellerRepo.allSeller
    }

    fun getSellerDetailLive(id: String?): LiveData<Seller> {
        return sellerRepo.getSellerDetailLive(id)
    }

    suspend fun getSellerDetail(id: String?): Seller  {
        Log.e(TAG, "getSellerDetail: unava", )
        return sellerRepo.getSellerDetail(id)
    }

    val allUserName: List<String>
        get() = sellerRepo.allUserName

    suspend fun getPassword(id: String?): String {
        return sellerRepo.getPassword(id)
    }


    suspend fun Update(seller : Seller , id : String ) : Boolean{
        return sellerRepo.Update(seller,id)
    }

    init {
        sellerRepo = SellerRepo(application)
        allSeller = getAllSeller()
    }
}