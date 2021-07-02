package com.example.mydream

import android.app.Application
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlin.math.log

class CustomerRepo(application: Application?) {
 //   private val customerDao: CustomerDao
 //   val allCustomer: LiveData<List<Customer>>
    private var mauth: FirebaseAuth? = null
    private var customer: Customer? = null
    private var reference: CollectionReference? = null
    private var referenceO : CollectionReference? = null


    init {
//        val customerDatabase = CustomerDatabase.getInstance(application)
//        customerDao = customerDatabase.CustomerDao()
//        allCustomer = customerDao.all
        mauth = FirebaseAuth.getInstance()
        reference = FirebaseFirestore.getInstance().collection("Customer")
        referenceO = FirebaseFirestore.getInstance().collection("CustomerOrders")

    }

    public fun insert(customer: Customer?) {
        var status : Boolean? = null
        reference!!.document(customer!!.user_name).set(customer)
                .addOnSuccessListener(OnSuccessListener {
                    Log.e(ContentValues.TAG, "insert: Success")
                    mauth!!.createUserWithEmailAndPassword(customer.getEmail_id(), customer.getPassword())
                            .addOnSuccessListener(OnSuccessListener {
                        Log.e(TAG, "insert: authenticatio done", )
                    })
                    status = true;
                })
                .addOnFailureListener(OnFailureListener {
                    Log.e(ContentValues.TAG, "insert: Failure")
                    status = false
                })
    }

    fun delete(id: String?) {
        //customerDao.Delete(id) // Do it asynchronously on background thread
    }

    fun getPassword(id: String?): String{
        return "qsj"
    }

    suspend fun getCustomerDetail(): Customer {
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
            delay(1)
        }
        return customer!!
    }

    fun Update(customer: Customer?) {
        reference!!.document(customer!!.getUser_name()).set(customer)
    }

    val allUserName: List<String>?
        get() = null

    @JvmName("getAllCustomer1")
    fun getAllCustomer(): LiveData<List<Customer>>? {
        return null
    }

    fun isUserExist(id: String?): Boolean {
        return true
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

    suspend fun logIn(userName: String, pasword1: String): Boolean? {
        var isSuccess: Boolean? = null
        reference!!.document(userName!!).get().addOnSuccessListener(OnSuccessListener {
            if (it.getString("password") == pasword1) {
                Log.e(TAG, "logIn: success listner", )
                isSuccess = true
                mauth!!.signInWithEmailAndPassword(it.getString("email_id")!!, pasword1!!)
                        .addOnSuccessListener {
                            Log.e(TAG, "logIn: ${it.toString()} login repo", )
                        }
                        .addOnFailureListener(OnFailureListener {
                            Log.e(TAG, "logIn: failed  ${it.toString()}", )
                        })
            } else {
                isSuccess = false
            }
        })
        while (isSuccess == null){
            delay(10)
        }
        return isSuccess
    }

    private fun toCustomer(it1: QuerySnapshot): Customer {
        val it=it1!!.documents.get(0)
        var customer = Customer(it!!.getString("user_name"),it.getString("password"),it.getString("first_name"),it.getString("last_name"),
                it.getString("phone_number"),it.getString("email_id"),it.getString("image") );
        var list = it.get("myOrders") as List<String>
        customer.setMyOrders(list)
        return customer
    }


    fun logOut(){
        mauth!!.signOut()
    }

    suspend fun getOrderDetails(id: String): CustomerOrders {
        var customerOrders: CustomerOrders? = null
        referenceO!!.document(id).get().addOnSuccessListener(OnSuccessListener {
            customerOrders = toCustomerOrders(it)
        })
        while (customerOrders==null){
            delay(1)
        }
        return customerOrders!!
    }

    suspend fun setOrder(customerOrders: CustomerOrders): String?{
        var id = referenceO!!.document().id
        customerOrders.orderedId= id
        var isSuccess: Boolean? = null
        referenceO!!.document(id).set(customerOrders)
                .addOnSuccessListener(OnSuccessListener {
                    isSuccess = true
                })
                .addOnFailureListener(OnFailureListener {
                    isSuccess = false
                })

        while (isSuccess==null){
            delay(1)
        }
        Log.e(TAG, "setOrder: $isSuccess  $id", )
        if(isSuccess!=null)
        return id
        else
            return null
    }

    fun toCustomerOrders(it: DocumentSnapshot): CustomerOrders?{
        val customerOrders = CustomerOrders(it.getString("orderId"),it.getString("customerId"),
                                            it.getLong("time"),it.getString("sellerId"),it.getString("productIs"),it.getLong("status"))
        return customerOrders
    }

}