package com.example.mydream

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class CustomerViewModel(application: Application) : AndroidViewModel(application) {
    private val customerRepo: CustomerRepo
    private val customer: Customer? = null
    private val allCustomer: LiveData<List<Customer>>


    init {
        customerRepo = CustomerRepo(application)
        allCustomer = getAllCustomer()
    }


    fun Insert(customer: Customer) {
        customerRepo.insert(customer)
    }

    fun Delete(id: String?) {
        customerRepo.delete(id)
    }

    fun getAllCustomer(): LiveData<List<Customer>> {
        return customerRepo.allCustomer
    }

    fun getCustomerDetail(id: String?): Customer {
        return customerRepo.getCustomerDetail(id)
    }

    val allUserName: List<String>
        get() = customerRepo.allUserName

    fun getPassword(id: String?): String {
        return customerRepo.getPassword(id)
    }

    fun Update(customer: Customer?) {
        customerRepo.Update(customer)
    }

    fun isUserExist(id: String?): Boolean {
        return customerRepo.isUserExist(id)
    }

}