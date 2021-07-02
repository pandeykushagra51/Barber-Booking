package com.example.mydream

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class CustomerViewModel(application: Application) : AndroidViewModel(application) {
    private val customerRepo: CustomerRepo
    private val customer: Customer? = null
   // private val allCustomer: LiveData<List<Customer>>


    init {
        customerRepo = CustomerRepo(application)
        //allCustomer = getAllCustomer()
    }


    fun Insert(customer: Customer) {
        customerRepo.insert(customer)
    }

    fun Delete(id: String?) {
        customerRepo.delete(id)
    }

//    fun getAllCustomer(): LiveData<List<Customer>> {
//        return customerRepo.allCustomer
//    }

    suspend fun getCustomerDetail(): Customer {
        return customerRepo.getCustomerDetail()
    }

    val allUserName: List<String>
        get() = customerRepo.allUserName!!

    fun getPassword(id: String?): String {
        return customerRepo.getPassword(id)
    }

    fun Update(customer: Customer?) {
        customerRepo.Update(customer)
    }

    fun isUserExist(id: String?): Boolean {
        return customerRepo.isUserExist(id)
    }

    fun isLoggedIn(): Boolean {
        return customerRepo.isLoggedIn();
    }


    fun logOut(){
        customerRepo.logOut()
    }

    suspend fun logIn(userName: String, pasword: String): Boolean {
        var isSuccess = customerRepo.logIn(userName,pasword)
        return isSuccess!!
    }

    suspend fun getOrderDetail(id: String) : CustomerOrders{
        return customerRepo.getOrderDetails(id);
    }

    suspend fun setOrder(customerOrders: CustomerOrders): String?{
        return customerRepo.setOrder(customerOrders)
    }

}