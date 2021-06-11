package com.example.mydream

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SellerViewModel(application: Application) : AndroidViewModel(application) {
    private val sellerRepo: SellerRepo
    private val seller: LiveData<Seller>? = null
    var seller1 : Seller? = null
    private val allSeller: LiveData<List<Seller>>
    fun Insert(seller: Seller?) {
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

    suspend fun getSellerDetail(id: String?): Seller {
        CoroutineScope(IO).async {
            seller1 = sellerRepo.getSellerDetail(id)
        }.await()
        return seller1!!
    }

    val allUserName: List<String>
        get() = sellerRepo.allUserName

    fun getPassword(id: String?): String {
        return sellerRepo.getPassword(id)
    }

    fun Update(seller: Seller?) {
        sellerRepo.Update(seller)
    }

    init {
        sellerRepo = SellerRepo(application)
        allSeller = getAllSeller()
    }
}