package com.example.mydream

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.util.*
class Tools {
    companion object {

        fun ImageViewToString(img: ImageView): String?  {
            var bmp: Bitmap? = null
            var bos: ByteArrayOutputStream? = null
            var bt: String? = null
            try {
                val bitmap = (img.drawable as BitmapDrawable).bitmap
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                val b = baos.toByteArray()
                bt = Base64.encodeToString(b, Base64.DEFAULT)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return bt
        }

        suspend fun stringToBitmap(img: String): Bitmap = withContext(Dispatchers.Default){
            val imageBytes = Base64.decode(img, 0)
            val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            return@withContext image
        }

        fun RemoveDuplicate(list: MutableList<String?>, str1: String?): MutableList<String?> {
            val s: Set<String?> = LinkedHashSet(list)
            list.clear()
            list.addAll(s)
            return list
        }

        suspend fun timeToLong(stringTime: String) : Long{
            var index: Int = stringTime.indexOf("a.m.")
            var longTime:Long = -1;
            if(index!=-1){
                longTime = stringTime.substring(0,index-1).toLong()
            }
            else{
                index = stringTime.indexOf("p.m.")
                if(index>0)
                longTime = 12 + stringTime.substring(0,index-1).toLong()
            }
            return longTime
        }

    }
}