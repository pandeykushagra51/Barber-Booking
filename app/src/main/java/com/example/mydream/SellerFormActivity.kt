package com.example.mydream

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class SellerFormActivity : AppCompatActivity() {
    private var select_image: Button? = null
    private var customer_form_register: Button? = null
    private var first_name: EditText? = null
    private var last_name: EditText? = null
    private var phone_number: EditText? = null
    private var email_id: EditText? = null
    private var user_name: EditText? = null
    private var password: EditText? = null
    private var city: EditText? = null
    private var locality: EditText? = null
    private var picode: EditText? = null
    private var shop_name: EditText? = null
    private var image: ImageView? = null
    private var iamge: Array<Byte>? = null
    private var sellerViewModel: SellerViewModel? = null
    var SELECT_PICTURE = 200
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_form)
        setId()
        sellerViewModel = SellerViewModel(application);
        customer_form_register!!.setOnClickListener(View.OnClickListener {
            val check = sellerViewModel!!.allUserName

            CoroutineScope(IO).launch {
                sellerViewModel!!.Insert(Seller(user_name!!.text.toString(), password!!.text.toString(),
                        first_name!!.text.toString(), last_name!!.text.toString(), phone_number!!.text.toString(),
                        email_id!!.text.toString(), Tools.ImageViewToString(image!!), picode!!.text.toString(),
                        locality!!.text.toString() + " " + city!!.text.toString(), shop_name!!.text.toString()))
            }
        })
        select_image!!.setOnClickListener {
            val i = Intent()
            i.type = "image/*"
            i.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE)
        }
    }

    fun setId() {
        select_image = findViewById(R.id.login_button)
        customer_form_register = findViewById(R.id.customer_form_register)
        first_name = findViewById(R.id.customer_form_first_name)
        last_name = findViewById(R.id.customer_form_last_name)
        phone_number = findViewById(R.id.customer_form_phone_number)
        email_id = findViewById(R.id.customer_form_email_id)
        user_name = findViewById(R.id.customer_form_username)
        password = findViewById(R.id.customer_form_password)
        image = findViewById(R.id.customer_form_image)
        picode = findViewById(R.id.seller_form_pin_code)
        city = findViewById(R.id.seller_form_city)
        locality = findViewById(R.id.seller_form_locality)
        shop_name = findViewById(R.id.seller_form_shop_name)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                val selectedImageUri = data!!.data
                if (null != selectedImageUri) {
                    image!!.setImageURI(selectedImageUri)
                }
            }
        }
    }
}