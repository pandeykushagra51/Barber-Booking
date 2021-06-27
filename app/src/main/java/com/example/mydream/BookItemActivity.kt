package com.example.mydream

import android.app.Application
import android.content.SharedPreferences
import android.graphics.Paint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.internal.NavigationMenuView
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
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
    var navDrawer: DrawerLayout? = null
    var toolbar: Toolbar? = null
    var cardDetail: MaterialCardView? = null
    var bookItem: Button? = null
    var seller: Seller? = null
    var autoCompleteTextView: AutoCompleteTextView? = null
    var customer: Customer? = null
    var images: List<ByteArray>? = null
    var timings = Arrays.asList("8 a.m.", "9 a.m.", "10 p.m.", "1 p.m.", "5 p.m.", "6 p.m.", "7 p.m.")
    var productViewModel: ProductViewModel? = null
    var sellerViewModel: SellerViewModel? = null
    var customerViewModel: CustomerViewModel? = null
    var sharedPreferences: SharedPreferences? = null
    var productId: String = "dummy"
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_item)
        setId()
        var toggle= ActionBarDrawerToggle(this,navDrawer,toolbar,R.string.common_open_on_phone,R.string.app_name)
        navDrawer!!.addDrawerListener(toggle)
        toggle.syncState()

        val it = intent
        productId = it.getStringExtra("itemId").toString()
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
//            var nav: NavigationView = findViewById(R.id.navigationView)
//            nav.visibility = View.VISIBLE

            CoroutineScope(IO).launch {

                if (!customerViewModel!!.isLoggedIn()) {
                    withContext(Main) {
                        val loginFragment = LoginFragment()
                        loginFragment.show(supportFragmentManager, "my login fragment")
                    }
                    return@launch
                } else {
                    customer = customerViewModel!!.getCustomerDetail()
                }
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
        navDrawer = findViewById(R.id.navigationViewpar)
        autoCompleteTextView = findViewById(R.id.slot_timing)
        toolbar = findViewById(R.id.drawer_toolbar)
//        sellerName!!.setPaintFlags(sellerName!!.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
//        phoneNumber!!.setPaintFlags(phoneNumber!!.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
//        emailId!!.setPaintFlags(emailId!!.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
//        pinCode!!.setPaintFlags(pinCode!!.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
//        adress!!.setPaintFlags(adress!!.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
    }

    fun setData() {
        CoroutineScope(IO).launch {
            val product = async {  productViewModel!!.getProductDetail(productId) }
            val seller = async { sellerViewModel!!.getSellerDetail(product!!.await()!!.getSellerId()) }
            withContext(Main) {
                try {image!!.setImageBitmap(Tools.stringToBitmap(product.await()!!.getItemImages())) }
                catch (e:Exception){}
                itemName!!.text = product.await()!!.getItemName()
                itemRate!!.text = product.await()!!.getRate().toString()
                sellerName!!.text = seller.await().shop_name
                phoneNumber!!.text = seller.await().getPhone_number()
                emailId!!.text = seller.await().getEmail_id()
                pinCode!!.text = seller.await().pin_code
                adress!!.text = seller.await().adress
            }
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
            customer!!.setMyOrders(productId)
            CustomerViewModel(application).Update(customer)
            seller!!.setOrders(productId, customer!!.getUser_name(), autoCompleteTextView!!.text.toString())
//            SellerViewModel(application).Update(seller)
        }
        builder.setPositiveButton("Book") { dialog, which -> }
        builder.setCancelable(false)
        builder.show()
    }
}