package com.example.mydream

import android.app.Application
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.util.*
import kotlin.collections.ArrayList

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
    var selectedTime: Long? = null
    var autoCompleteTextView: AutoCompleteTextView? = null
    var customer: Customer? = null
    var timings = listOf("8 a.m.", "9 a.m.", "10 p.m.", "1 p.m.", "5 p.m.", "6 p.m.", "7 p.m.")
    var productViewModel: ProductViewModel? = null
    var sellerViewModel: SellerViewModel? = null
    var customerViewModel: CustomerViewModel? = null
    var productId: String = "dummy"
    var menu: Menu? = null
    var logOut: MenuItem? = null
    var navView: NavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_item)
        setId()
        val toggle= ActionBarDrawerToggle(this, navDrawer, toolbar, R.string.common_open_on_phone, R.string.app_name)
        navDrawer!!.addDrawerListener(toggle)
        toggle.syncState()
        toggle.onOptionsItemSelected(logOut)

        val it = intent
        productId = it.getStringExtra("itemId").toString()
        productViewModel = ProductViewModel((this.applicationContext as Application))
        sellerViewModel = SellerViewModel((this.applicationContext as Application))
        customerViewModel = ViewModelProvider(this).get(CustomerViewModel::class.java)

        setData()

        cardDetail!!.setOnLongClickListener {
            cardDetail!!.isChecked = !cardDetail!!.isChecked
            val popup = PopupMenu(this@BookItemActivity, cardDetail!!, Gravity.RIGHT)
            popup.menuInflater.inflate(R.menu.pop_up_menu, popup.menu)
            popup.setOnMenuItemClickListener {
                cardDetail!!.isChecked = !cardDetail!!.isChecked
                true
            }
            setPopupIcon(popup)
            popup.show()
            true
        }

        cardDetail!!.setOnClickListener { if (cardDetail!!.isChecked) cardDetail!!.isChecked = !cardDetail!!.isChecked }

        bookItem!!.setOnClickListener(View.OnClickListener {
            CoroutineScope(IO).launch {

                if (!customerViewModel!!.isLoggedIn()) {
                    withContext(Main) {
                        val loginFragment = LoginFragment()
                        loginFragment.show(supportFragmentManager, "my login fragment")
                    }
                    return@launch
                } else {
                    launch { customer = customerViewModel!!.getCustomerDetail() }
                    while (customer == null) {
                        delay(1)
                    }
                    Log.e("jsd", "onCreate: login ask confirmation dialog invoked")
                    val order = customer!!.getMyOrders()
                    var bookedTime: ArrayList<Long>? = ArrayList()
                    bookedTime = if (order != null) {
                        var orderList = customer!!.getMyOrders()
                        if(orderList.size>0)
                        for (str in orderList) {
                            bookedTime!!.add(customerViewModel!!.getOrderDetail(str).time)
                        }
                        bookedTime
                    } else {
                        null
                    }
                    if(bookedTime==null) {
                        askConfirmationDialogue()
                        return@launch
                    }
                    selectedTime = Tools.timeToLong(autoCompleteTextView!!.text.toString())
                    if(selectedTime!!>=0) {
                        for (currTime in bookedTime) {
                            if (selectedTime!! >= 0 && currTime == selectedTime) {
                                withContext(Main) {
                                    showTimeUnAvailableDialog(selectedTime!!.toInt())
                                }
                                return@launch
                            }
                        }
                        withContext(Main) {
                            askConfirmationDialogue()
                        }
                    } else {
                        withContext(Main) {
                            Toast.makeText(this@BookItemActivity, "select valid time to proceed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })

        navView!!.setNavigationItemSelectedListener {
            when (it) {
                logOut -> customerViewModel!!.logOut()
                else -> {
                    Toast.makeText(this, "invalid selection", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }

        val arrayAdpter: ArrayAdapter<*> = ArrayAdapter<Any?>(this, R.layout.slot_timing, timings as List<Any?>)
        autoCompleteTextView!!.setAdapter(arrayAdpter)
    }

    private fun setId() {
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
        navView = findViewById(R.id.navigationView)
        autoCompleteTextView = findViewById(R.id.slot_timing)
        toolbar = findViewById(R.id.drawer_toolbar)
        menu = findViewById(R.id.group_main)
        logOut = findViewById(R.id.nav_logout)
        sellerName!!.setPaintFlags(sellerName!!.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        phoneNumber!!.setPaintFlags(phoneNumber!!.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        emailId!!.setPaintFlags(emailId!!.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        pinCode!!.setPaintFlags(pinCode!!.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        adress!!.setPaintFlags(adress!!.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
    }

    private fun setData() {
        CoroutineScope(IO).launch {
            val product = async {  productViewModel!!.getProductDetail(productId) }
            val seller = async { sellerViewModel!!.getSellerDetail(product.await()!!.getSellerId()) }
            withContext(Main) {
                try {image!!.setImageBitmap(Tools.stringToBitmap(product.await()!!.getItemImages())) }
                catch (e: Exception){}
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

    private fun setPopupIcon(popup: PopupMenu) {
        try {
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showTimeUnAvailableDialog(time: Int) {
        val view = LayoutInflater.from(this).inflate(R.layout.time_clash_dialog_layout, null)

        val builder = MaterialAlertDialogBuilder(this@BookItemActivity)
        builder.setTitle(time.toString())
        builder.setView(view)
        builder.show()
    }

    fun askConfirmationDialogue() {
        val builder = MaterialAlertDialogBuilder(this@BookItemActivity)

        builder.setTitle("Are You Sure ?")
        builder.setMessage("book service at ${autoCompleteTextView!!.text.toString()}")
        builder.setNegativeButton("No Change Time") { dialog, which ->

        }
        builder.setPositiveButton("Book") { dialog, which ->
            CoroutineScope(IO).launch {
                var customerOrders: CustomerOrders? = null
                val product = async {  productViewModel!!.getProductDetail(productId) }
                val customer = async { customerViewModel!!.getCustomerDetail()}
                customerOrders = CustomerOrders(null, customer.await().getUser_name(),
                        selectedTime, product.await()!!.getSellerId(),
                        product.await()!!.getItemId(), 0)
                while (customerOrders==null){
                    delay(1)
                }
                val id= customerViewModel!!.setOrder(customerOrders)
                Log.e("fnvkd", "askConfirmationDialogue: $id")
                customer.await().getMyOrders().add(id)
                customerViewModel!!.Update(customer.await())
            }
        }
        builder.setCancelable(false)
        builder.show()
    }


}