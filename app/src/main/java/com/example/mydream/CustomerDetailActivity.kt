package com.example.mydream

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class CustomerDetailActivity : AppCompatActivity() {
    var image: ImageView? = null
    var first_name: TextView? = null
    var last_name: TextView? = null
    var phone_number: TextView? = null
    var email_id: TextView? = null
    var user_name: TextView? = null
    var customer: Customer? = null
    var customerViewModel: CustomerViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_detail)
        setId()
        val it = intent
        val id = it.getStringExtra("id")
        customerViewModel = ViewModelProvider(this).get(CustomerViewModel::class.java)
      //  customer = customerViewModel!!.getCustomerDetail()
        setData()
    }

    private fun setId() {
        image = findViewById(R.id.customer_detail_image)
        first_name = findViewById(R.id.customer_detail_last_name)
        last_name = findViewById(R.id.customer_detail_first_name)
        phone_number = findViewById(R.id.customer_detail_phone_number)
        email_id = findViewById(R.id.customer_detail_email_id)
        user_name = findViewById(R.id.customer_detail_user_name)
    }

    private fun setData() {
        CoroutineScope(Main).launch {
           // image!!.setImageBitmap(byteToBitmap(customer!!.getImage()))
            first_name!!.text = customer!!.getFirst_name()
            last_name!!.text = customer!!.getLast_name()
            phone_number!!.text = customer!!.getPhone_number()
            email_id!!.text = customer!!.getEmail_id()
            user_name!!.text = customer!!.getUser_name()
        }
    }
}