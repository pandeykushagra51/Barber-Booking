package com.example.mydream

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mydream.Tools.Companion.byteToBitmap
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class SellerDetailActivity : AppCompatActivity() {
    var image: ImageView? = null
    var shop_name: TextView? = null
    var rv: RecyclerView? = null
    var fab: FloatingActionButton? = null
    var sellerItemAdapter: SellerItemAdapter? = null
    var id: String? = null
    var sellerViewModel: SellerViewModel? = null
    var products: List<Int>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_detail)
        setId()
        val it = intent
        id = it.getStringExtra("id")
        val productViewModel = ProductViewModel(application)
        sellerViewModel = SellerViewModel(application)
        CoroutineScope(Main).launch {
            val seller = sellerViewModel!!.getSellerDetail(id)
            products = seller.getProductIds()
            setData(seller)
        }
        sellerItemAdapter = SellerItemAdapter(this, products)
        val sellerLive = sellerViewModel!!.getSellerDetailLive(id)
        sellerLive.observe(this, { seller ->
            sellerItemAdapter!!.setData(seller.getProductIds())
            sellerItemAdapter!!.notifyDataSetChanged()
        })
        fab!!.setOnClickListener {
            val it = Intent(this@SellerDetailActivity, AddItemActivity::class.java)
            it.putExtra("sellerId", id)
            startActivity(it)
        }
        rv!!.layoutManager = LinearLayoutManager(this)
        rv!!.adapter = sellerItemAdapter
    }

    fun setId() {
        image = findViewById(R.id.seller_detail_image)
        rv = findViewById(R.id.product_recycler_view)
        shop_name = findViewById(R.id.seller_shop_name)
        fab = findViewById(R.id.seller_add_item)
    }

    fun setData(seller: Seller) {
        CoroutineScope(Main).launch {
            image?.setImageBitmap(byteToBitmap(seller.image))
            shop_name?.text = seller.shop_name
        }
    }
}