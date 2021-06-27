package com.example.mydream

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import com.example.mydream.SellerViewModel
import android.os.Bundle
import com.example.mydream.R
import androidx.lifecycle.ViewModelProvider
import android.content.Intent
import android.widget.Button
import com.example.mydream.SellerDetailActivity
import android.widget.Toast
import com.example.mydream.SellerFormActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.sql.DriverManager.println

class SellerLoginActivity : AppCompatActivity() {
    var login: Button? = null
    var sign_up: Button? = null
    var user_name: EditText? = null
    var password: EditText? = null
    private var sellerViewModel: SellerViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_login)
        setId()
        sellerViewModel = ViewModelProvider(this).get(SellerViewModel::class.java)
        login!!.setOnClickListener {
            CoroutineScope(IO).launch {

                val check = sellerViewModel!!.allUserName
                val check1 = 0
                val str2 = password!!.text.toString()
                val str1 = async {  sellerViewModel!!.getPassword(user_name!!.text.toString())}
                if (str1.await().equals(str2) ) {
                    withContext(Main) {
                        val it = Intent(this@SellerLoginActivity, SellerDetailActivity::class.java)
                        it.putExtra("id", user_name!!.text.toString())
                        Toast.makeText(this@SellerLoginActivity, "password is correct!!!", Toast.LENGTH_SHORT).show()
                        startActivity(it)
                    }
                } else {
                    withContext(Main) {
                        Toast.makeText(this@SellerLoginActivity, "password is incorrecttt!!!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        sign_up!!.setOnClickListener {
            val it = Intent(this@SellerLoginActivity, SellerFormActivity::class.java)
            startActivity(it)
        }
    }

    fun setId() {
        user_name = findViewById(R.id.login_username)
        password = findViewById(R.id.login_password)
        login = findViewById(R.id.login_button)
        sign_up = findViewById(R.id.signup_button)
    }
}