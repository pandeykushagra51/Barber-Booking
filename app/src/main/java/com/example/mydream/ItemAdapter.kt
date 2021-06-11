package com.example.mydream

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mydream.Tools.Companion.byteToBitmap
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import java.util.*

class ItemAdapter(context: Context, itemClic: ItemClic, products: List<Product>?) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    var products: List<Product>? = ArrayList(0)
    var itemClic: ItemClic
    var context: Context


    init {
        this.products = products
        this.itemClic = itemClic
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view, itemClic)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        CoroutineScope(Main).launch() {
            holder.name?.text = products!![position].itemName
            holder.rate?.text = products!![position].rate.toString()
            holder.rb?.rating = 4f
            holder.sellerName?.text = products!![position].sellerId
            holder.sellerName?.paintFlags = holder.sellerName?.paintFlags!! or Paint.UNDERLINE_TEXT_FLAG
            val bmp = byteToBitmap(products!![position].getItemImages()[0])
            holder.iv?.setImageBitmap(bmp)
        }
    }

    override fun getItemCount(): Int {
        return if (products == null) 0 else products!!.size
    }

    fun setData(products: List<Product>?) {
        this.products = products
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View, itemClic: ItemClic) : RecyclerView.ViewHolder(view), View.OnClickListener, OnLongClickListener {
        var iv: ImageView? = null
        var name: TextView? = null
        var rate: TextView? = null
        var sellerName: TextView? = null
        var rb: RatingBar? = null
        var itemClic: ItemClic
        override fun onClick(v: View) {
            itemClic.onItemClick(v, adapterPosition)
        }

        override fun onLongClick(v: View): Boolean {
            return itemClic.onItemLongClick(v, adapterPosition)
        }

        init {
            CoroutineScope(Dispatchers.Default).launch {
                iv = view.findViewById(R.id.item_image)
                name = view.findViewById(R.id.item_name)
                sellerName = view.findViewById(R.id.item_seller_name)
                rate = view.findViewById(R.id.item_rate)
                rb = view.findViewById(R.id.main_item_rating)
            }
            this.itemClic = itemClic
            view.setOnClickListener(this)
            view.setOnLongClickListener(this)
        }
    }

    interface ItemClic {
        fun onItemClick(view: View?, id: Int)
        fun onItemLongClick(view: View?, position: Int): Boolean
    }

}