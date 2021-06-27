package com.example.mydream

import android.app.Application
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase

class CustomerRepo(application: Application?) {
    private val customerDao: CustomerDao
    val allCustomer: LiveData<List<Customer>>
    private var mauth: FirebaseAuth? = null
    private var customer: Customer? = null
    private var reference: CollectionReference? = null


    init {
        val customerDatabase = CustomerDatabase.getInstance(application)
        customerDao = customerDatabase.CustomerDao()
        allCustomer = customerDao.all
        mauth = FirebaseAuth.getInstance()
        reference = FirebaseFirestore.getInstance().collection("Customer")
    }

    public fun insert(customer: Customer?) {
        var status : Boolean? = null
        reference!!.document(customer!!.user_name).set(customer)
                .addOnSuccessListener(OnSuccessListener {
                    Log.e(ContentValues.TAG, "insert: Success")
                    status = true;
                })
                .addOnFailureListener(OnFailureListener {
                    Log.e(ContentValues.TAG, "insert: Failure")
                    status = false
                })
    }

    fun delete(id: String?) {
        customerDao.Delete(id) // Do it asynchronously on background thread
    }

    fun getPassword(id: String?): String {
        return customerDao.getPassword(id)
    }

    fun getCustomerDetail(): Customer {
        var check: Boolean? = null
        var customer: Customer? = null
        var user = mauth!!.currentUser

        var email = user!!.email
        reference!!.whereEqualTo("email_id", email)
                .get().addOnSuccessListener(OnSuccessListener {
            customer = toCustomer(it)
            check = true
        })
        while (check==null){
            check = null
        }
        return customer!!
    }

    fun Update(customer: Customer?) {
        customerDao.Update(customer)
    }

    val allUserName: List<String>
        get() = customerDao.allUserName

    @JvmName("getAllCustomer1")
    fun getAllCustomer(): LiveData<List<Customer>> {
        return customerDao.all
    }

    fun isUserExist(id: String?): Boolean {
        return customerDao.isUserIsExist(id)
    }

    fun isLoggedIn(): Boolean{
        Log.e(TAG, "isLoggedIn: is logged in invoked", )
        if(mauth!!.currentUser==null)
            return false
        else {
            Log.e(TAG, "isLoggedIn: is logged in invoked true", )
            return true
        }
    }


    private fun toCustomer(it1: QuerySnapshot): Customer {
        val it=it1!!.documents.get(0)
        var customer = Customer(it!!.getString("user_name"),it.getString("password"),it.getString("first_name"),it.getString("last_name"),
                it.getString("phone_number"),it.getString("email_id"),it.getString("image") );
        customer.setMyOrders(it.getString("myOrders"))
        return customer
    }

    fun logIn(userName: String?, pasword1: String?) {
        reference!!.document(userName!!).get().addOnSuccessListener(OnSuccessListener {
            if(it.getString("password")==pasword1){
                mauth!!.signInWithEmailAndPassword(it.getString("email_id")!!,pasword1!!)
            }
        })
    }

    fun logOut(){
        mauth!!.signOut()
    }

}