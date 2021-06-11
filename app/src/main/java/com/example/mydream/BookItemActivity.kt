package com.example.mydream

import android.app.Application
import android.content.SharedPreferences
import android.graphics.Paint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import com.example.mydream.Tools.Companion.byteToBitmap
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.reflect.Field
import java.util.*

class BookItemActivity : AppCompatActivity() {
    var image: ImageView? = null
    var itemName: TextView? = null
    var sellerName: TextView? = null
    var itemRate: TextView? = null
    var phoneNumber: TextView? = null
    var emailId: TextView? = null
    var pinCode: TextView? = null
    var adress: TextView? = null
    var card: MaterialCardView? = null
    var cardDetail: MaterialCardView? = null
    var bookItem: Button? = null
    var autoCompleteTextView: AutoCompleteTextView? = null
    var product: Product? = null
    var seller: Seller? = null
    var customer: Customer? = null
    var images: List<ByteArray>? = null
    var timings = Arrays.asList("8 a.m.", "9 a.m.", "10 p.m.", "1 p.m.", "5 p.m.", "6 p.m.", "7 p.m.")
    var productViewModel: ProductViewModel? = null
    var sellerViewModel: SellerViewModel? = null
    var customerViewModel: CustomerViewModel? = null
    var sharedPreferences: SharedPreferences? = null
    var productId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_item)
        val it = intent
        productId = it.getIntExtra("id", -1)
        setId()
        productViewModel = ProductViewModel((this.applicationContext as Application))
        sellerViewModel = SellerViewModel((this.applicationContext as Application))
        customerViewModel = ViewModelProvider(this).get(CustomerViewModel::class.java)
        sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE)
        setData()
        cardDetail!!.setOnLongClickListener {
            cardDetail!!.isChecked = !cardDetail!!.isChecked
            val popup = PopupMenu(this@BookItemActivity, cardDetail!!, Gravity.RIGHT)
            //Inflating the Popup using xml file
            popup.menuInflater.inflate(R.menu.pop_up_menu, popup.menu)

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener {
                cardDetail!!.isChecked = !cardDetail!!.isChecked
                //Toast.makeText(BookItemActivity.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                true
            }
            setPopupIcon(popup)
            popup.show()
            true
        }
        cardDetail!!.setOnClickListener { if (cardDetail!!.isChecked) cardDetail!!.isChecked = !cardDetail!!.isChecked }
        bookItem!!.setOnClickListener(View.OnClickListener {
            if (sharedPreferences?.getBoolean("isLoggedIn", false)==true) {
                val loginFragment = LoginFragment()
                loginFragment.show(supportFragmentManager, "my grrsf")
                return@OnClickListener
            }
            else {
                askConfirmationDialogue()
            }
        })
        val arrayAdpter: ArrayAdapter<*> = ArrayAdapter<Any?>(this, R.layout.slot_timing, timings as List<Any?>)
        autoCompleteTextView!!.setAdapter(arrayAdpter)
    }

    fun setId() {
        image = findViewById(R.id.main_item_image)
        itemName = findViewById(R.id.main_item_name)
        itemRate = findViewById(R.id.main_item_rate)
        sellerName = findViewById(R.id.main_seller_name)
        phoneNumber = findViewById(R.id.main_phone_number)
        emailId = findViewById(R.id.main_emailid)
        pinCode = findViewById(R.id.main_pincode)
        adress = findViewById(R.id.main_adress)
        card = findViewById(R.id.image_card)
        cardDetail = findViewById(R.id.product_row)
        bookItem = findViewById(R.id.book_item)
        autoCompleteTextView = findViewById(R.id.slot_timing)
        sellerName!!.setPaintFlags(sellerName!!.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        phoneNumber!!.setPaintFlags(phoneNumber!!.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        emailId!!.setPaintFlags(emailId!!.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        pinCode!!.setPaintFlags(pinCode!!.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        adress!!.setPaintFlags(adress!!.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
    }

    fun setData() {
        CoroutineScope(Main).launch {
            product = productViewModel!!.getProductDetail(productId)
            if (product != null) seller = sellerViewModel!!.getSellerDetail(product!!.getSellerId())
            if (seller == null) return@launch
            val images = product!!.getItemImages()
            image!!.setImageBitmap(byteToBitmap(images[0]))
            itemName!!.text = product!!.getItemName()
            itemRate!!.text = product!!.getRate().toString()
            sellerName!!.text = seller!!.getShop_name()
            phoneNumber!!.text = seller!!.getPhone_number()
            emailId!!.text = seller!!.getEmail_id()
            pinCode!!.text = seller!!.getPin_code()
            adress!!.text = seller!!.getAdress()
        }

    }

    fun setPopupIcon(popup: PopupMenu) {
        try {
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun isTimeAvailable(toString: String): Boolean {
//        Customer customer = customerViewModel.getCustomerDetail(sharedPreferences.getString("username","xyz"));
//        List<List<Object>> list =customer.getMyOrders();
//        int check=0,size=list.size(),cnt=0;
        val ans: List<Any>? = null
        //        while(cnt<size){
//            List<Object> list1 = list.get(cnt);
//            if(list1.get(1)==autoCompleteTextView.getText().toString()){
//                ans=list1;
//                return true;
//            }
//        }
        showTimeUnAvailableDialog(ans)
        return false
    }

    private fun showTimeUnAvailableDialog(ans: List<Any>?) {
        val view = LayoutInflater.from(this).inflate(R.layout.time_clash_dialog_layout, null)
        val builder = MaterialAlertDialogBuilder(this@BookItemActivity)
        builder.setView(view)
        builder.show()
    }

    fun askConfirmationDialogue() {
        if (!isTimeAvailable(autoCompleteTextView!!.text.toString())) return
        val builder = MaterialAlertDialogBuilder(this@BookItemActivity)
        builder.setTitle("Are You Sure ?")
        builder.setMessage("book service at 9 p.m.")
        builder.setNegativeButton("No Change Time") { dialog, which ->
            customer!!.setMyOrders(productId, seller!!.user_name, autoCompleteTextView!!.text.toString())
            CustomerViewModel(application).Update(customer)
            seller!!.setOrders(productId, customer!!.getUser_name(), autoCompleteTextView!!.text.toString())
            SellerViewModel(application).Update(seller)
        }
        builder.setPositiveButton("Book") { dialog, which -> }
        builder.setCancelable(false)
        builder.show()
    }
}