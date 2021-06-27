package com.example.mydream

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import java.util.*

class SellerItemAdapter(var context: Context, products: List<Int>?) : RecyclerView.Adapter<SellerItemAdapter.ViewHolder>() {
    var products: List<Int>? = ArrayList()
    var productViewModel: ProductViewModel? = null


    init {
        this.products = products
        productViewModel = ProductViewModel((context.applicationContext as Application))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        CoroutineScope(Main).launch(){
            val product = productViewModel!!.getProductDetail("dummy")
            holder.name.text = product?.getItemName()
            holder.rate.text = product?.getRate().toString()
            holder.rb.rating = 4f
            val bmp = Tools.stringToBitmap(product!!.getItemImages())
            holder.iv.setImageBitmap(bmp)
        }
    }

    override fun getItemCount(): Int {
        return if (products == null) 0 else products!!.size
    }

    fun setData(products: List<Int>?) {
        if (products != null) this.products = products
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var iv: ImageView
        var name: TextView
        var rate: TextView
        var rb: RatingBar

        init {
            iv = view.findViewById(R.id.item_image)
            name = view.findViewById(R.id.item_name)
            rate = view.findViewById(R.id.item_rate)
            rb = view.findViewById(R.id.main_item_rating)
        }
    }

}