package com.example.mydream

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mydream.Tools.Companion.ImageViewToByte
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.util.*

class AddItemActivity : AppCompatActivity() {
    var selectImage: Button? = null
    var addItem: Button? = null
    var itemImage: ImageView? = null
    var name: EditText? = null
    var rate: EditText? = null
    var tags: EditText? = null
    var sellerId: String? = null
    var SELECT_PICTURE = 200
    var images: MutableList<ByteArray?> = ArrayList()
    var sellerViewModel: SellerViewModel? = null
    var seller: Seller? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_add_item)
        setId()
        val it = intent
        sellerId = it.getStringExtra("sellerId")
        val productViewModel = ProductViewModel(application)
        sellerViewModel = ViewModelProvider(this@AddItemActivity).get(SellerViewModel::class.java)
        CoroutineScope(Main).launch {
            seller = sellerViewModel!!.getSellerDetail(sellerId)
        }
        selectImage!!.setOnClickListener {
            val i = Intent()
            i.type = "image/*"
            i.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE)
        }
        addItem!!.setOnClickListener {
            val json = Gson().toJson(images)
            if (itemImage != null) {
                productViewModel.Insert(Product(name!!.text.toString(), Integer.valueOf(rate!!.text.toString()), json, sellerId, tags!!.text.toString()))
                seller!!.setProductIds(productViewModel.id)
                sellerViewModel!!.Update(seller)
            } else Toast.makeText(this@AddItemActivity, "Must Select Image To add Item", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun setId() {
        selectImage = findViewById(R.id.add_item_image_button)
        addItem = findViewById(R.id.add_item_button)
        itemImage = findViewById(R.id.add_item_image)
        name = findViewById(R.id.add_item_name)
        rate = findViewById(R.id.add_item_rate)
        tags = findViewById(R.id.add_item_tag)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                val selectedImageUri = data!!.data
                if (null != selectedImageUri) {
                    itemImage!!.setImageURI(selectedImageUri)
                    images.add(ImageViewToByte(itemImage!!))
                }
            }
        }
    }
}