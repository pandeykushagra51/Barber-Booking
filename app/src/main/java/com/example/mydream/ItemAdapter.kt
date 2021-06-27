package com.example.mydream

import android.content.ContentValues.TAG
import android.content.Context
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList;

class ItemAdapter(context: Context, itemClic: ItemClic, products: ArrayList<Product>?) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    var products: ArrayList<Product>? = null
    var itemClic: ItemClic
    var context: Context
    var currItemCount = 0

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
            try {
                val bmp = Tools.stringToBitmap(products!![position].getItemImages())
                holder.iv?.setImageBitmap(bmp)
            }
            catch (e: Exception){

            }
        }
    }

    override fun getItemCount(): Int {
        return if (products == null) 0 else products!!.size
    }


    suspend fun Insert(list: ArrayList<Product>?){
        Log.e(TAG, "Insert: inadbk hadd ${list!!.size}", )
        if(products == null){
            products = list
        }
        else{
            products!!.addAll(list!!)
        }
        withContext(Main){
            notifyItemRangeInserted(currItemCount,list.size)
            currItemCount = products!!.size
        }
    }

    inner class ViewHolder(view: View, itemClic: ItemClic) : RecyclerView.ViewHolder(view), View.OnClickListener, OnLongClickListener {
        var iv: ImageView? = null
        var name: TextView? = null
        var rate: TextView? = null
        var sellerName: TextView? = null
        var rb: RatingBar? = null
        var itemClic: ItemClic
        override fun onClick(v: View) {
            itemClic.onItemClick(v,  products!!.get(adapterPosition).getItemId())
            Log.e(TAG, "onClick: $adapterPosition ${products!!.get(adapterPosition).getItemId()}", )
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
        fun onItemClick(view: View?, id: String)
        fun onItemLongClick(view: View?, position: Int): Boolean
    }

}