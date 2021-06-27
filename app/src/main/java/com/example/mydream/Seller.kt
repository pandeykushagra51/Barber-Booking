package com.example.mydream

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

@Entity(tableName = "seller")
class Seller : User {
    @ColumnInfo
    var shop_name: String? = null

    @ColumnInfo
    var pin_code : String? = "000000"

    @ColumnInfo
    var adress: String? = null

    @ColumnInfo
    var productIds: String? = null
        get() = field

    @ColumnInfo
    var totalSlots: String? = null

    @ColumnInfo
    var bookedSlots: String? = null

    @ColumnInfo
    var orders: String? = null

    constructor(user_name: String?, password: String?, first_name: String?,
                          last_name: String?, phone_number: String?, email_id: String?,
                          image: String?, pin_code: String?, adress: String?, shop_name: String?,productIds : String? = null) : super(user_name, password, first_name, last_name, phone_number, email_id, image) {
        this.shop_name = shop_name
        this.pin_code = pin_code
        this.adress = adress
        this.productIds = productIds
    }


    @kotlin.jvm.JvmName("setProductIds1")
    fun setProductIds(id: String?) {
        val gson = Gson()
        val strType = object : TypeToken<List<String?>?>() {}.type
        var list = gson.fromJson<MutableList<String?>>(productIds, strType)
        if (list == null) {
            list = ArrayList()
        }
        list.add(id)
        val json = gson.toJson(list)
        productIds = json
    }

    fun setOrders(productId: String, customerId: String, time: String) {
        val list: MutableList<Any>? = null
        list!!.add(productId)
        list.add(customerId)
        list.add(time)
        val gson = Gson()
        val objectType = object : TypeToken<List<Any?>?>() {}.type
        val list1 = gson.fromJson<MutableList<Any?>>(orders, objectType)
        list1.add(list)
        orders = gson.toJson(list1)
    }
}